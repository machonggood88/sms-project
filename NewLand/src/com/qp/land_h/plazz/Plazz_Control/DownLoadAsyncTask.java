package com.qp.land_h.plazz.Plazz_Control;

import Lib_Control.CDownLoad;
import Lib_Control.CDownLoad.IDownLoadProgress;
import Lib_System.CActivity;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class DownLoadAsyncTask extends AsyncTask<String, Integer, String> implements IDownLoadProgress {

	private static final String	TAG			= "DownLoadAsyncTask";

	String						szDescribe	= "";

	String						path		= "newland";
	/**
	 * 构造函数
	 * 
	 * @param context
	 * @param activity
	 */
	public DownLoadAsyncTask() {
		super();

	}

	@Override
	protected String doInBackground(String... pragams) {

		String url = pragams[0];
		CDownLoad down = new CDownLoad(this);
		boolean bSucceed = down.StartDownLoad(url, path);
		if (bSucceed)
			return "succeed";
		else
			return "fail";
	}

	@Override
	protected void onPostExecute(String result) {
		if (result != null && result.equals("succeed")) {
			Toast.makeText(CActivity.GetInstance(), "下载完成", Toast.LENGTH_LONG).show();
		} else {
			// CActivity.GetInstance().onShowScene(GDF.SCENE_MENU);
		}
	}

	@Override
	protected void onPreExecute() {

	}

	@Override
	protected void onProgressUpdate(Integer... values) {

	}

	@Override
	protected void onCancelled() {

	}

	@Override
	public void onUpdate(int progress, String szDescribe) {
		this.szDescribe = szDescribe;
		this.publishProgress(progress);
		Log.i(TAG, "pro" + progress + "#" + szDescribe);
	}

}
