package android.app.sms.application;

import android.app.Application;
import android.app.sms.utils.CrashHandler;
import android.app.sms.utils.LogUtils;
import android.content.Context;

public class SMSApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		CrashHandler.getInstance().init();
		SMSApplication.regFilter(this);
	}
	
	private static void regFilter(Context context) {
		LogUtils.write("reg", "注册短信监听");
		
	}
}
