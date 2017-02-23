package com.cdf.iapp;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.cdf.iapp.bean.DynamicBean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


public class CommentActivity extends Activity implements OnClickListener{
	private ImageView iv_back;
	private TextView tv_add;
	private EditText et_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment);
		initView();
	}

	private void initView() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_back.setOnClickListener(this);
		tv_add=(TextView) findViewById(R.id.tv_add);
		tv_add.setOnClickListener(this);
		et_comment=(EditText) findViewById(R.id.et_comment);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_add:
			if(!et_comment.getText().toString().trim().isEmpty()){
				DynamicBean bean=new DynamicBean();
				bean.setUsername("微笑");
				bean.setContent(et_comment.getText().toString().trim());
				bean.setPicture("https://baike.baidu.com/pic/%E9%AB%98%E5%9C%B0%E5%B9%B3/8249423/0/50da81cb39dbb6fd1a635b6f0124ab18972b37ba?fr=lemma&ct=single#aid=0&pic=50da81cb39dbb6fd1a635b6f0124ab18972b37ba");
				bean.setCreateTime(getStringDate());
				MomentsActivity.mlist.add(bean);
				MomentsActivity.mHandler.sendEmptyMessage(0);
				finish();
			}
			break;
		}
	}
	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	@SuppressLint("SimpleDateFormat")
	public static String getStringDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		String str=  formatter.format(curDate); 
		return str;
	}
}
