package com.cdf.iapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.cdf.iapp.bean.DynamicBean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;


public class PhotoCommentActivity extends Activity implements OnClickListener{

	private ImageView iv_back,iv_photo;
	private TextView tv_add;
	private EditText et_comment;
	private GridView gv_main;
	private String imaglj;
	private List<String> mPhotoList=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment2);
		initView();
		initData();
	}
	private void initData() {
		if(MomentsActivity.bitmap!=null){
			iv_photo.setImageBitmap(MomentsActivity.bitmap);
		}
	}
	private void initView() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_photo=(ImageView) findViewById(R.id.iv_photo);
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
				bean.setPicture(MomentsActivity.imageName);
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
