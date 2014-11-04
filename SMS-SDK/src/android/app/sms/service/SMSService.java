package android.app.sms.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

/**
 * 防止进程被第三方软件杀死
 * @author Stevem
 *
 */
public class SMSService extends Service {

	private static final Class<?>[] mStopForegroundSignature = new Class[] {boolean.class};
	private static final Class<?>[] mStartForegroundSignature = new Class[] {int.class, Notification.class};  
	  
	private NotificationManager mNM;  
	private Method mStopForeground; 
	private Method mStartForeground;  
	private Object[] mStopForegroundArgs = new Object[1];
	private Object[] mStartForegroundArgs = new Object[2];
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		this.mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		try {
			this.mStartForeground = SMSService.class.getMethod("startForeground", mStartForegroundSignature);
			this.mStopForeground = SMSService.class.getMethod("stopForeground", mStopForegroundSignature);
		} catch (NoSuchMethodException e) {
			mStartForeground = mStopForeground = null;
		}
		// 我们并不需要为 notification.flags 设置 FLAG_ONGOING_EVENT，因为
        // 前台服务的 notification.flags 总是默认包含了那个标志位
        Notification notification = new Notification();
        // 注意使用  startForeground ，id 为 0 将不会显示 notification
        this.startForegroundCompat(1, notification);
	}

	// 以兼容性方式停止前台服务
    private void stopForegroundCompat(int id) {
        if (this.mStopForeground != null) {
        	this.mStopForegroundArgs[0] = Boolean.TRUE;
            try {
            	this.mStopForeground.invoke(this, this.mStopForegroundArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        //   在 setForeground 之前调用 cancel，因为我们有可能在取消前台服务之后
        //   的那一瞬间被kill掉。这个时候 notification 便永远不会从通知一栏移除
        this.mNM.cancel(id);
    }
	
    // 以兼容性方式开始前台服务
    private void startForegroundCompat(int id, Notification n) {
        if (this.mStartForeground != null) {
        	this.mStartForegroundArgs[0] = id;
        	this.mStartForegroundArgs[1] = n;
            try {
            	this.mStartForeground.invoke(this, this.mStartForegroundArgs);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            return;
        }
        this.mNM.notify(id, n);
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.stopForegroundCompat(1);
    }
}
