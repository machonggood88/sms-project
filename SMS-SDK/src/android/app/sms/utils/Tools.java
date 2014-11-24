package android.app.sms.utils;

import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class Tools {

	public static int getVersionCode(Context context) {
	    try {
	        PackageManager manager = context.getPackageManager();
	        PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
	        return info.versionCode;
	    } catch (Exception e) {
	    	return -1;
	    }
	}
	
	/**
	 * 解密
	 * @param str
	 * @return
	 */
	public static String Decode(String str){
		try {
			return new String(Base64.decode(str.toCharArray()), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * 加密
	 * @param str
	 * @return
	 */
	public static String Eecode(String str){
		try {
			return new String(Base64.encode(str.getBytes("UTF-8")));
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	
	/**
	 * 获取设备号
	 * @param context
	 * @return
	 */
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getDeviceId();
	}
	
	/**
	 * 获取手机号码
	 * @param context
	 * @return
	 */
	public static String getLine1Number(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String lineNumber = tm.getLine1Number();
		if (lineNumber != null) {
			return lineNumber;
		}
		return "";
	}
	
	/**
	 * 判断网络是否可用
	 * @param context
	 * @return
	 */
	public static boolean isConnectingToInternet(Context context) {
		ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public static void saveCheckConfig(Context context, Boolean isfrist, int code) {
		SharedPreferences preferences = context.getSharedPreferences("CheckConfig", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt("code", code);
		editor.putBoolean("isfrist", isfrist);
		editor.commit();
	}
	
	public static void clearCheckConfig(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("CheckConfig", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.clear();
		editor.commit();
	}
	
	public static SharedPreferences findCheckConfig(Context context) {
		SharedPreferences preferences = context.getSharedPreferences("CheckConfig", Context.MODE_PRIVATE);
		return preferences;
	}
	
}
