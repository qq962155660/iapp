package com.cdf.iapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cdf.iapp.adapter.MomentsGroupAdapter;
import com.cdf.iapp.bean.CommentBean;
import com.cdf.iapp.bean.DynamicBean;
import com.cdf.iapp.bean.PraiseBean;
import com.cdf.iapp.sys.Global;
import com.cdf.iapp.util.HttpUtil;
import com.cdf.iapp.util.SharedHelper;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MomentsActivity extends Activity implements OnClickListener {
	private ImageView iv_back,iv_comment;
	private ListView lv_result;
	public static Handler mHandler;
	public static List<DynamicBean> mlist=new ArrayList<DynamicBean>();
	public static int mPage = 1;
	public static int mLimit = 10;
	//图像相关
		public static Bitmap bitmap;
		public static String imageName;//图片名字
		private ProgressDialog mProgressDialog;
		private static final int GH_PHOTO_REQUEST_TAKEPHOTO_ONE = 1;// 拍照
		private static final int GH_PHOTO_REQUEST_GALLERY_ONE = 2;// 从相册中选取图片
		private static final int GH_PHOTO_EQUEST_CUT_ONE=3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_moments);

		initView();
		mlist.clear();
		initData();
		setData();
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					MomentsGroupAdapter mAdapter=new MomentsGroupAdapter(MomentsActivity.this, mlist);
					lv_result.setAdapter(mAdapter);
					setListViewHeightBasedOnChildren(lv_result, 0);
					break;
				}
			}
		};
		
	}




	private void initView() {
		iv_back=(ImageView) findViewById(R.id.iv_back);
		iv_comment=(ImageView) findViewById(R.id.iv_comment);
		lv_result=(ListView) findViewById(R.id.lv_result);
		iv_back.setOnClickListener(this);
		iv_comment.setOnClickListener(this);
		iv_comment.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				startActivity(new Intent(MomentsActivity.this, CommentActivity.class));
				return true;
			}
		});
	}

	private void initData() {
		new Thread(){
			public void run() {
				try {
					JSONObject j = HttpUtil.doGet(getApplicationContext(),Global.URL_GETDYNAMICS + "?page="  + mPage + "&limit=" + mLimit);
					if(j.getInt("code") == 0){
						JSONArray jarr = j.getJSONArray("data");
						for (int i = 0; i < jarr.length(); i++) {
							JSONObject o = jarr.getJSONObject(i);
							DynamicBean db = new DynamicBean();
							db.setContent(o.getString("content"));
							db.setCreateTime(o.getString("createTime"));
							db.setUsername(o.getString("username"));
							db.setAuid(o.getString("auid"));
							if(o.has("picture")){
								//db.setPicture(o.getString("picture"));
							}
							mlist.add(db);
						}
						mHandler.sendEmptyMessage(0);
					}
				} catch (IOException | JSONException e) {
					e.printStackTrace();
				}
			};
		}.start();
	}
	private void setData() {
		MomentsGroupAdapter mAdapter=new MomentsGroupAdapter(MomentsActivity.this, mlist);
		lv_result.setAdapter(mAdapter);
		setListViewHeightBasedOnChildren(lv_result, 0);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back:
			finish();
			break;
		case R.id.iv_comment:
			changeHeadIcon();
			break;
		}
	}
	private void changeHeadIcon() {
		final CharSequence[] items = { "从相册中选择", "拍照" };
		AlertDialog dlg = new AlertDialog.Builder(MomentsActivity.this)
				.setItems(items, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						// 这里item是根据选择的方式，
						if (item == 0) {
							getNowTime();
							imageName="GH_ONE"+getNowTime()+".png";
							Intent intent=new Intent(Intent.ACTION_PICK,null);
							intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
							startActivityForResult(intent, GH_PHOTO_REQUEST_GALLERY_ONE);
						} else {
							imageName="GH_ONE"+getNowTime()+".png";
							Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
							//指定调用相机拍照后照片的存储路径
							intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/" ,imageName)));
							startActivityForResult(intent, GH_PHOTO_REQUEST_TAKEPHOTO_ONE);
						}
					}
				}).create();
		dlg.show();
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == GH_PHOTO_REQUEST_TAKEPHOTO_ONE) {
			crop(Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/",imageName)),250);
		} else if (requestCode == GH_PHOTO_REQUEST_GALLERY_ONE) {
			crop(data.getData(), 250);
		} else if (requestCode == GH_PHOTO_EQUEST_CUT_ONE) {
			/**
			 * 最关键在此，把options.inJustDecodeBounds = true;
			 * 这里再decodeFile()，返回的bitmap为空
			 * ，但此时调用options.outHeight时，已经包含了图片的高了
			 */
			setImage();
		}
	}
	private void setImage() {
		bitmap=BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/"+imageName);
		Log.i("zxd", "路径="+Environment.getExternalStorageDirectory() + "/"+imageName);
		//iv_user.setImageBitmap(bitmap);
		Intent intent=new Intent(MomentsActivity.this, PhotoCommentActivity.class);
		startActivity(intent);
	}




	private void crop(Uri uri,int size) {
		Intent intent=new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop","true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", size);
		intent.putExtra("outputY", size);
		intent.putExtra("return-data", false);
		intent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(new File(Environment.getExternalStorageDirectory() + "/",imageName)));
		intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
		intent.putExtra("nofaceDetection", true);
		startActivityForResult(intent, GH_PHOTO_EQUEST_CUT_ONE);
	}



	/**
	 * 动态设置listView高度
	 * @param listView
	 * @param attHeight
	 */
	public static void setListViewHeightBasedOnChildren(ListView listView, int attHeight) {  
		ListAdapter listAdapter = listView.getAdapter();   
		if (listAdapter == null) {  
			// pre-condition  
			return;  
		}  

		int totalHeight = 0;  
		for (int i = 0; i < listAdapter.getCount(); i++) {  
			View listItem = listAdapter.getView(i, null, listView);  
			listItem.measure(0, 0);  
			totalHeight += listItem.getMeasuredHeight();  
		}  

		ViewGroup.LayoutParams params = listView.getLayoutParams();  
		params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()-1)) + attHeight;  
		listView.setLayoutParams(params);  
	}
	//获取当前的时间
		@SuppressLint("SimpleDateFormat")
		private String getNowTime(){
			Date date = new Date(System.currentTimeMillis());
			SimpleDateFormat dateFormat = new SimpleDateFormat("MMddHHmmssSS");
			return dateFormat.format(date);
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
