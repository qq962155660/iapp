package com.cdf.iapp;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.cdf.iapp.adapter.FriensGroupAdapter;
import com.cdf.iapp.bean.CommentBean;
import com.cdf.iapp.bean.FriendsGroupBean;
import com.cdf.iapp.bean.PraiseBean;
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
	
	private  SharedPreferences prefs;
	private  String userName="zxd";
	public static String mCosde="0";//第几次进入为0表示第一次进入
	public static Handler mHandler;
	
	public static List<FriendsGroupBean> mlist=new ArrayList<FriendsGroupBean>();
	
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
		prefs =  PreferenceManager.getDefaultSharedPreferences(this);
		mCosde=prefs.getString("code", "0");
		initView();
		if(mCosde.equals("0")){
			mCosde="1";
			prefs.edit().putString("code", "1").commit();
			initData();
		}else{
			
		}
		setData();
		mHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				switch (msg.what) {
				case 0:
					FriensGroupAdapter mAdapter=new FriensGroupAdapter(MomentsActivity.this, mlist);
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
		FriendsGroupBean bean=new FriendsGroupBean();
		bean.setName("gogoing");
		bean.setContent("55kai就是一撒比");
		bean.setPhotoUrl("https://baike.baidu.com/pic/%E9%AB%98%E5%9C%B0%E5%B9%B3/8249423/0/50da81cb39dbb6fd1a635b6f0124ab18972b37ba?fr=lemma&ct=single#aid=0&pic=50da81cb39dbb6fd1a635b6f0124ab18972b37ba");
		bean.setTime("2017-01-19 11:11:11");
		ArrayList<CommentBean> mCommentlist=new ArrayList<CommentBean>();
		CommentBean mCommentBean=new CommentBean();
		mCommentBean.setName("若风");
		mCommentBean.setToname("gogoing");
		mCommentBean.setContent("说的对，他就是一撒比");
		mCommentlist.add(mCommentBean);
		CommentBean mCommentBean1=new CommentBean();
		mCommentBean1.setName("Uzi");
		mCommentBean1.setToname("");
		mCommentBean1.setContent("小伙子还阔以啊");
		mCommentlist.add(mCommentBean1);
		CommentBean mCommentBean2=new CommentBean();
		mCommentBean2.setName("gogoing");
		mCommentBean2.setToname("Uzi");
		mCommentBean2.setContent("诶，丢皇族的人啊");
		mCommentlist.add(mCommentBean2);
		CommentBean mCommentBean3=new CommentBean();
		mCommentBean3.setName("微笑");
		mCommentBean3.setToname("若风");
		mCommentBean3.setContent("我就笑笑，不说话");
		mCommentlist.add(mCommentBean3);
		CommentBean mCommentBean4=new CommentBean();
		mCommentBean4.setName("clearlove");
		mCommentBean4.setToname("");
		mCommentBean4.setContent("小伙子，好好练练吧");
		mCommentlist.add(mCommentBean4);
		bean.setmCommentList(mCommentlist);
		ArrayList<PraiseBean> mPraises=new ArrayList<PraiseBean>();
		PraiseBean prabean=new PraiseBean();
		prabean.setName("faker");
		prabean.setTime("2017-01-19 11:11:11");
		prabean.setUsername(userName);
		mPraises.add(prabean);
		bean.setPraises(mPraises);
		mlist.add(bean);
	}
	private void setData() {
		FriensGroupAdapter mAdapter=new FriensGroupAdapter(MomentsActivity.this, mlist);
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
