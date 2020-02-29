package com.cdf.iapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.cdf.iapp.fragment.MainFrag1;
import com.cdf.iapp.fragment.MainFrag2;
import com.cdf.iapp.fragment.MainFrag3;
import com.cdf.iapp.fragment.MainFrag4;
import com.cdf.iapp.netty.Client;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class MainActivity extends Activity implements OnCheckedChangeListener  {

	private RadioButton vRbBlog;
	private RadioGroup vRgTabBar;

	private MainFrag1 mf1;
	private MainFrag2 mf2;
	private MainFrag3 mf3;
	private MainFrag4 mf4;
	
	private FragmentManager fManager;
	private FragmentTransaction fTransaction;

	private Client client;
	
	public static Handler mHandler;
	
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
		vRbBlog = (RadioButton) findViewById(R.id.rb_blog);
		vRbBlog.setChecked(true);
		
	 	Log.i("log", "启动线程");
	 	 client = new Client();
	     client.start();
	     try {
			client.sendData("hahaha");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					//获取NotificationManager实例
					   NotificationManager notifyManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
					   //实例化NotificationCompat.Builde并设置相关属性
					   NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this)
					           //设置小图标
					           .setSmallIcon(R.drawable.xin)
					           //设置通知标题
					           .setContentTitle("socket信息")
					           //设置通知内容
					           .setContentText(msg.obj.toString());
					           //设置通知时间，默认为系统发出通知的时间，通常不用设置
					           //.setWhen(System.currentTimeMillis());
					   //通过builder.build()方法生成Notification对象,并发送通知,id=1
					   notifyManager.notify(1, builder.build());
					break;
			
				default:
					break;
				}
			}
		};
	}
	
	

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		fTransaction = fManager.beginTransaction();
		switch (checkedId) {
		case R.id.rb_blog:
			 mf1 = new MainFrag1();  
			 fTransaction.replace(R.id.fl_content, mf1);  
			break;
		case R.id.rb_findon:
			mf2 = new MainFrag2();
			fTransaction.replace(R.id.fl_content, mf2);
			break;
		case R.id.rb_friend:
			mf3 = new MainFrag3();
			fTransaction.replace(R.id.fl_content, mf3);
			break;
		case R.id.rb_myself:
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
