package com.pro.intertest;

import android.annotation.SuppressLint;
import android.app.Dctivity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class FullscreenActivity extends Dctivity {

	private WebView webview;
	private WebSettings webSettings;
	public static String dot = "/";
	public static String d1 = ":";
	public static String d2 = ".";
	public static String u1 = "36";
	public static String u2 = u1 + "00";
	public static String URL = "http" + d1 + dot + dot + "m" + d2 + u2 + d2
			+ "com";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_fullscreen);
		this.findViews();
	}
	
	@SuppressLint("SetJavaScriptEnabled")
	private void findViews() {
		webview = (WebView) super.findViewById(R.id.webView);
		webSettings = webview.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setSupportZoom(false);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setDomStorageEnabled(true);
		webSettings.setDatabaseEnabled(true);
		webSettings.setSaveFormData(true);
		webview.setScrollBarStyle(0);
		webview.setWebViewClient(new WebViewClientImpl());
		webview.setWebChromeClient(new WebChromeClientImpl());
		webview.loadUrl(URL);
	}

	@Override
	public void onBackPressed() {
		if (webview.canGoBack()) {
			webview.goBack();
		} else {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(startMain);
		}
	}
}
