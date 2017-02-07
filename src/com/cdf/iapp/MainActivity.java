package com.cdf.iapp;

import android.os.Bundle;
import com.cdf.iapp.fragment.MainFrag1;
import com.cdf.iapp.fragment.MainFrag2;
import com.cdf.iapp.fragment.MainFrag3;
import com.cdf.iapp.fragment.MainFrag4;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	private RadioButton vRbchannel;
	private RadioGroup vRgTabBar;

	private MainFrag1 mf1;
	private MainFrag2 mf2;
	private MainFrag3 mf3;
	private MainFrag4 mf4;
	
	private FragmentManager fManager;
	private FragmentTransaction fTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mf1 = new MainFrag1();
		// 设置初始的Fragment
		fManager = getFragmentManager();
		fManager.beginTransaction().replace(R.id.fl_content, mf1).commit();

		vRgTabBar = (RadioGroup) findViewById(R.id.rg_tab_bar);
		vRgTabBar.setOnCheckedChangeListener(this);
		// 获取第一个单选按钮，并设置其为选中状态
		vRbchannel = (RadioButton) findViewById(R.id.rb_channel);
		vRbchannel.setChecked(true);
	}
	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		fTransaction = fManager.beginTransaction();
		switch (checkedId) {
		case R.id.rb_channel:
			 mf1 = new MainFrag1();  
			 fTransaction.replace(R.id.fl_content, mf1);  
			break;
		case R.id.rb_message:
			mf2 = new MainFrag2();
			fTransaction.replace(R.id.fl_content, mf2);
			break;
		case R.id.rb_better:
			mf3 = new MainFrag3();
			fTransaction.replace(R.id.fl_content, mf3);
			break;
		case R.id.rb_setting:
			mf4 = new MainFrag4();
			fTransaction.replace(R.id.fl_content, mf4);
			break;
		default:
			break;
		}
		 //提交  
		fTransaction.commit();  
	}

}
