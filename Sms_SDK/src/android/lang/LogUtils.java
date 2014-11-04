package android.lang;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Environment;
import android.util.Log;

public class LogUtils {

	private static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	private static String clientdir = "smsclient";
	private static int i=1;
	public static boolean isdebug=true;
	public static void write(String tag,String msg){
		if(isdebug){
			Log.i(tag+"", msg+"");
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
				try {
					File dir=getFiledir();
					if(dir!=null){
						File log=new File(dir, "log.txt");
						if(log.length()>10240000){
							log.renameTo(new File(dir,sdf.format(new Date())+".txt"));
							log=new File(dir, "log.txt");
						}
						FileWriter fw=new FileWriter(log,true);
						fw.write(sdf.format(new Date())+" "+tag+" "+i+". "+msg+"\r\n");
						i++;
						fw.flush();
						fw.close();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		}
		
	}
	public static File getFiledir() {
		File file = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			file = new File(Environment.getExternalStorageDirectory(),
					clientdir);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		return file;
	}
}
