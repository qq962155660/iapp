package com.cdf.iapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.http.Header;

import com.cdf.iapp.bean.DynamicBean;
import com.cdf.iapp.sys.Global;
import com.cdf.iapp.util.SharedHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PhotoCommentActivity extends Activity implements OnClickListener {

	private ImageView iv_back, iv_photo;
	private TextView tv_add;
	private EditText et_comment;
	private GridView gv_main;
	private String imaglj;
	private int uploadState = -1;
	private List<String> mPhotoList = new ArrayList<String>();
	
	private Handler mHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 0:
				uploadState = 0;
				Toast.makeText(PhotoCommentActivity.this, "发表动态成功", Toast.LENGTH_SHORT).show();
				break;
			case 1:
				uploadState = 1;
				Toast.makeText(PhotoCommentActivity.this, "发表失败", Toast.LENGTH_SHORT).show();
				break;
			case 2:
				uploadState = 2;
				Toast.makeText(PhotoCommentActivity.this, "文件不存在", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment2);
		initView();
		initData();
	}

	private void initData() {
		if (MomentsActivity.bitmap != null) {
			iv_photo.setImageBitmap(MomentsActivity.bitmap);
		}
	}

	private void initView() {
		iv_back = (ImageView) findViewById(R.id.iv_back);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		iv_back.setOnClickListener(this);
		tv_add = (TextView) findViewById(R.id.tv_add);
		tv_add.setOnClickListener(this);
		et_comment = (EditText) findViewById(R.id.et_comment);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.tv_add:
			if (!et_comment.getText().toString().trim().isEmpty()) {
				try {
					postFile(et_comment.getText().toString().trim());
					if (uploadState == 0) {
						DynamicBean bean = new DynamicBean();
						bean.setUsername("微笑");
						bean.setContent(et_comment.getText().toString().trim());
						bean.setPicture(MomentsActivity.imageName);
						bean.setCreateTime(getStringDate());
						MomentsActivity.mlist.add(0, bean);
						MomentsActivity.mHandler.sendEmptyMessage(0);
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}

				finish();
			}
			break;
		}
	}

	private void postFile(String _content) throws FileNotFoundException {
		String path = Environment.getExternalStorageDirectory() + "/" + MomentsActivity.imageName;
		File file = new File(path);
		if (file.exists() && file.length() > 0) {
			AsyncHttpClient client = new AsyncHttpClient();
			RequestParams params = new RequestParams();
			SharedHelper shp = new SharedHelper(this);
			client.addHeader("token", shp.getString("token"));
			params.put("profile_picture", file);
			params.put("content", _content);
			client.post(Global.URL_CREATEDYNAMIC, params, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
					mHandler.sendEmptyMessage(0);
					
				}

				@Override
				public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
					mHandler.sendEmptyMessage(1);
				}
			});
		} else {
			mHandler.sendEmptyMessage(2);
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
		Date curDate = new Date(System.currentTimeMillis());// 获取当前时间
		String str = formatter.format(curDate);
		return str;
	}
}
