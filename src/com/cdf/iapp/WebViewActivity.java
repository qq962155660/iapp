package com.cdf.iapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class WebViewActivity extends Activity{

	private WebView myWebView;
	private Intent intent;
	private View progress_bar;
	//private DBHelper db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview_content);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		myWebView = (WebView) findViewById(R.id.webview_content);
		progress_bar = (View) findViewById(R.id.progress_bar);
	
		initView();
	}
	private void initView() {
		
	}
}
