package com.cdf.iapp;

import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import com.cdf.iapp.util.SharedHelper;
import com.cdf.iapp.util.StringUtils;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class IndexActivity extends Activity {

	private boolean isFirstIn = false;
	private static final int TIME = 2000;
	private static final int GO_HOME = 1000;
	private static final int GO_GUIDE =  1001;
	private static final int GO_LOGIN = 1002;
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case GO_HOME:
				goHome();
				break;
			case GO_GUIDE:
				goGuide();
				break;
			case GO_LOGIN:
				goLogin();
			default:
				break;
			}
		}

		
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_index);
		init();
	}
	
	
	private void init(){
//		SharedPreferences pePreferences = getSharedPreferences("cdf", MODE_PRIVATE);
//		isFirstIn = pePreferences.getBoolean("isFirstIn", true);
//		String token = pePreferences.getString("token", null);
		SharedHelper sph = new SharedHelper(getApplicationContext());
		isFirstIn = sph.getBoolean("isFirstIn");
		String token = sph.getString("token");
		if(!isFirstIn){
			if(StringUtils.isBlank(token)){
				mHandler.sendEmptyMessageAtTime(GO_LOGIN, TIME);
			}else{
				mHandler.sendEmptyMessageDelayed(GO_HOME, TIME);
			}
			
		}else{
			mHandler.sendEmptyMessageDelayed(GO_GUIDE, TIME);
//			Editor editor = pePreferences.edit();
//			editor.putBoolean("isFirstIn", false);
//			editor.commit();
			sph.saveBoolean("isFirstIn", false);
		}
	}

	private void goHome(){
		Intent i = new Intent(IndexActivity.this, MainActivity.class);
		startActivity(i);
		finish();
	}
	
	private void goGuide(){
		Intent i = new Intent(IndexActivity.this, GuideActivity.class);
		startActivity(i);
		finish();
	}
	private void goLogin() {
		Intent i = new Intent(IndexActivity.this, LoginActivity.class);
		startActivity(i);
		finish();
		
	};
}
