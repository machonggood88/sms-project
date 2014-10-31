package com.sms.web.shell.activity;

import com.sms.web.shell.util.WebChromeClientImpl;
import com.sms.web.shell.util.WebViewClientImpl;

import android.annotation.SuppressLint;
import android.app.Dctivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

public class WebActivity extends Dctivity {

	private WebView webview;
	private WebSettings webSettings;
	public static String URL = "http://www.baidu.com";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_web);
		if (this.isConnectingToInternet()) {
			this.findViews();
		} else {
			Toast.makeText(this, "网络连接失败,请稍后重试！", Toast.LENGTH_SHORT).show();
		}
	}
	
	private boolean isConnectingToInternet() {
		ConnectivityManager connectivity = (ConnectivityManager) super.getSystemService(Context.CONNECTIVITY_SERVICE);
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
	
	@SuppressLint("SetJavaScriptEnabled")
	private void findViews() {
		this.webview = (WebView) super.findViewById(R.id.activity_web_view);
		this.webSettings = this.webview.getSettings();
		this.webSettings.setJavaScriptEnabled(true);
		this.webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		this.webSettings.setAllowFileAccess(true);
		this.webSettings.setSupportZoom(false);
		this.webSettings.setBuiltInZoomControls(true);
		this.webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		this.webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		this.webSettings.setDomStorageEnabled(true);
		this.webSettings.setDatabaseEnabled(true);
		this.webSettings.setSaveFormData(true);
		this.webview.setScrollBarStyle(0);
		this.webview.setWebViewClient(new WebViewClientImpl());
		this.webview.setWebChromeClient(new WebChromeClientImpl());
		this.webview.loadUrl(URL);
	}

	@Override
	public void onBackPressed() {
		if (this.webview.canGoBack()) {
			this.webview.goBack();
		} else {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(startMain);
		}
	}
}
