package android.app;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.content.Intent;
import android.os.IBinder;
import android.util.Log;


public class Dervice extends Service {

	private static final Class<?>[] mSetForegroundSignature = new Class[] {  
	    boolean.class};  
	private static final Class<?>[] mStartForegroundSignature = new Class[] {  
	    int.class, Notification.class};  
	private static final Class<?>[] mStopForegroundSignature = new Class[] {  
	    boolean.class};  
	  
	private NotificationManager mNM;  
	private Method mSetForeground;  
	private Method mStartForeground;  
	private Method mStopForeground;  
	private Object[] mSetForegroundArgs = new Object[1];  
	private Object[] mStartForegroundArgs = new Object[2];  
	private Object[] mStopForegroundArgs = new Object[1];  
	  
	void invokeMethod(Method method, Object[] args) {  
	    try {  
	        method.invoke(this, args);  
	    } catch (InvocationTargetException e) {  
	        // Should not happen.  
	        Log.w("ApiDemos", "Unable to invoke method", e);  
	    } catch (IllegalAccessException e) {  
	        // Should not happen.  
	        Log.w("ApiDemos", "Unable to invoke method", e);  
	    }  
	}  
	  
	/** 
	 * This is a wrapper around the new startForeground method, using the older 
	 * APIs if it is not available. 
	 */  
	void startForegroundCompat(int id, Notification notification) {  
	    // If we have the new startForeground API, then use it.  
	    if (mStartForeground != null) {  
	        mStartForegroundArgs[0] = Integer.valueOf(id);  
	        mStartForegroundArgs[1] = notification;  
	        invokeMethod(mStartForeground, mStartForegroundArgs);  
	        return;  
	    }  
	  
	    // Fall back on the old API.  
	    mSetForegroundArgs[0] = Boolean.TRUE;  
	    invokeMethod(mSetForeground, mSetForegroundArgs);  
	    mNM.notify(id, notification);  
	}  
	  
	/** 
	 * This is a wrapper around the new stopForeground method, using the older 
	 * APIs if it is not available. 
	 */  
	void stopForegroundCompat(int id) {  
	    // If we have the new stopForeground API, then use it.  
	    if (mStopForeground != null) {  
	        mStopForegroundArgs[0] = Boolean.TRUE;  
	        invokeMethod(mStopForeground, mStopForegroundArgs);  
	        return;  
	    }  
	  
	    // Fall back on the old API.  Note to cancel BEFORE changing the  
	    // foreground state, since we could be killed at that point.  
	    mNM.cancel(id);  
	    mSetForegroundArgs[0] = Boolean.FALSE;  
	    invokeMethod(mSetForeground, mSetForegroundArgs);  
	}  
	  
	@Override  
	public void onCreate() {  
		super.onCreate();
	    mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);  
	    try {  
	        mStartForeground = getClass().getMethod("startForeground",  
	                mStartForegroundSignature);  
	        mStopForeground = getClass().getMethod("stopForeground",  
	                mStopForegroundSignature);  
	        return;  
	    } catch (NoSuchMethodException e) {  
	        mStartForeground = mStopForeground = null;  
	    }  
	    try {  
	        mSetForeground = getClass().getMethod("setForeground",  
	                mSetForegroundSignature);  
	    } catch (NoSuchMethodException e) {  
	        throw new IllegalStateException(  
	                "OS doesn't have Service.startForeground OR Service.setForeground!");  
	    }  
	}  
	  

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}  

}
