package android.app.sms.observer;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

public class SMSObserver extends ContentObserver {

	private Context context;
	
	public SMSObserver(Context context, Handler handler) {
		super(handler);
		this.context = context;
	}
	
	@Override
	public void onChange(boolean selfChange) {
		Cursor c = this.context.getContentResolver().query(Uri.parse("content://sms"), null, "type in (1,2)", null, "_id desc");
		
		super.onChange(selfChange);
	}
}
