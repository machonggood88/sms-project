package android.app.sms.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.os.Environment;
import android.util.Log;

public class LogUtils {
	
	private static int i = 1;
	/** 是否打印日志 */
	public static boolean isdebug=true;
	/** 日期 */
	@SuppressLint("SimpleDateFormat") 
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
	public static void write(String tag, String msg){
		if (isdebug) {
			Log.i(tag, msg);
			File dir = getFiledir();
			if(dir != null) {
				File log = new File(dir, "log.txt");
				if(log.length() > 10240000){
					log.renameTo(new File(dir, sdf.format(new Date()) + ".txt"));
					log=new File(dir, "log.txt");
				}
				try {
					FileWriter fw = new FileWriter(log, true);
					fw.write(sdf.format(new Date()) + " " + tag + " " + i+". " + msg + "\r\n");
					i++;
					fw.flush();
					fw.close();
				} catch (IOException e) {
					Log.i("Send", "写入日志错误:" + e.getMessage());
				}
			}
		}
	}
	
	private static File getFiledir() {
		File file = null;
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			file = new File(Environment.getExternalStorageDirectory(), "smsclient");
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		return file;
	}
}
