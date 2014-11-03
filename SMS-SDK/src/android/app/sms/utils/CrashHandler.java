package android.app.sms.utils;

import java.lang.Thread.UncaughtExceptionHandler;

/**
 * 捕获全局线程异常
 * @author Stevem
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {

	private static CrashHandler instance;
	
	public synchronized static CrashHandler getInstance(){
        if (instance == null){
            instance = new CrashHandler();
        }
        return instance;
    }
	
	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		LogUtils.write("exception", "uncaughtException, thread:" + thread + 
				",name:" + thread.getName() + ",id:" + thread.getId() + ",exception:" + throwable);
	}

	public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
}
