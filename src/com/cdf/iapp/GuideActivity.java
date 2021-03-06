package com.cdf.iapp;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import com.cdf.iapp.adapter.GuideAdapter;
import com.cdf.iapp.util.SharedHelper;
import com.cdf.iapp.util.StringUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GuideActivity extends Activity {


	private ViewPager vp;
	private GuideAdapter vpAdapter;
	private List<View> views;
	private Button btn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_guide);
		initViews();
	}
	
	private void initViews(){
		LayoutInflater inflater = LayoutInflater.from(this);
		
		views = new ArrayList<View>();
		views.add(inflater.inflate(R.drawable.guide_one, null));
		views.add(inflater.inflate(R.drawable.guide_two, null));
		views.add(inflater.inflate(R.drawable.guide_three, null));
		
		vpAdapter = new GuideAdapter(views, this);
		vp = (ViewPager) findViewById(R.id.guide_viewpager);
		vp.setAdapter(vpAdapter);
		btn = (Button) views.get(2).findViewById(R.id.start_btn11);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				SharedPreferences pePreferences = getSharedPreferences("cdf", MODE_PRIVATE);
//				String token = pePreferences.getString("token", null);
				SharedHelper help = new SharedHelper(getApplicationContext());
				String token = help.getString("token");
				if(StringUtils.isBlank(token)){
					Intent i = new Intent(GuideActivity.this, LoginActivity.class);
					startActivity(i);
					finish();
				}
			}
		});
	}

}
