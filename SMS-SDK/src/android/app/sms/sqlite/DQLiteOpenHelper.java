package android.app.sms.sqlite;

import java.text.SimpleDateFormat;
import java.util.concurrent.atomic.AtomicInteger;
import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DQLiteOpenHelper extends SQLiteOpenHelper {

	private SQLiteDatabase Tdb;
	
	private static String name="mydata";
	
	private static DQLiteOpenHelper helper;
	
	private AtomicInteger mOpenCounter = new AtomicInteger();
	
	@SuppressLint("SimpleDateFormat")
	private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd/HH:mm:ss");
	
	public DQLiteOpenHelper(Context context) {
		super(context, name, null, 10);
	}
	
	public static DQLiteOpenHelper getHelper(Context context){
		if(helper==null){
			helper=new DQLiteOpenHelper(context);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		String sql="create table data(id INTEGER PRIMARY KEY AUTOINCREMENT,type varchar(40),pn varchar(20),body varchar(1000),time varchar (20))";
		sqLiteDatabase.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int b) {
		
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
