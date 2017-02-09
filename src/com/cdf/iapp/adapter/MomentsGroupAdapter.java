package com.cdf.iapp.adapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cdf.iapp.R;
import com.cdf.iapp.bean.FriendsGroupBean;
import com.cdf.iapp.bean.PraiseBean;
import com.cdf.iapp.util.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;



public class MomentsGroupAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<FriendsGroupBean> mlist=new ArrayList<FriendsGroupBean>();
	private  SharedPreferences prefs;
	private List<PraiseBean> mpraiselist=new ArrayList<PraiseBean>();
	private List<String> mPraisesList=new ArrayList<String>();
	public static List<FriendsGroupBean> mlist1=new ArrayList<FriendsGroupBean>();
	public MomentsGroupAdapter(Context mContext, List<FriendsGroupBean> mlist) {
		super();
		this.mContext = mContext;
		this.mInflater = LayoutInflater.from(mContext);
		this.mlist = mlist;
		this.prefs =  PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_moment_group, null);
			holder=new ViewHolder();
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_content=(TextView) convertView.findViewById(R.id.tv_content);
			holder.tv_time=(TextView) convertView.findViewById(R.id.tv_time);
			holder.rl_zan=(RelativeLayout) convertView.findViewById(R.id.rl_zan);
			holder.tv_zan=(TextView) convertView.findViewById(R.id.tv_zan);
			holder.ll_zan_or_pl=(LinearLayout) convertView.findViewById(R.id.ll_zan_or_pl);
			holder.ll_zan_and_pl=(LinearLayout) convertView.findViewById(R.id.ll_zan_and_pl);
			holder.iv_zan=(ImageView) convertView.findViewById(R.id.iv_zan);
			holder.iv_pl=(ImageView) convertView.findViewById(R.id.iv_pl);
			holder.lv_comment=(ListView) convertView.findViewById(R.id.lv_comment);
			holder.iv_xin=(ImageView) convertView.findViewById(R.id.iv_xin);
			holder.rl_dianzan=(RelativeLayout) convertView.findViewById(R.id.rl_dianzan);
			holder.view=convertView.findViewById(R.id.view);
			holder.ll_fb=(LinearLayout) convertView.findViewById(R.id.ll_fb);
			holder.et_comment=(EditText) convertView.findViewById(R.id.et_comment);
			holder.tv_comment=(TextView) convertView.findViewById(R.id.tv_comment);
			holder.iv_photo=(ImageView) convertView.findViewById(R.id.iv_photo);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tv_name.setText(mlist.get(position).getName());
		holder.tv_content.setText(mlist.get(position).getContent());
		holder.tv_time.setText(mlist.get(position).getTime().subSequence(0, 10));
		if(mlist.get(position).getPraises().size()>0){
			mPraisesList.clear();
			for(int i=0;i<mlist.get(position).getPraises().size();i++){
				mPraisesList.add(mlist.get(position).getPraises().get(i).getName());
			}
			holder.ll_zan_and_pl.setVisibility(View.VISIBLE);
			holder.rl_dianzan.setVisibility(View.VISIBLE);
			holder.tv_zan.setText(StringUtils.join(mPraisesList, ","));
		}else{
			holder.rl_dianzan.setVisibility(View.GONE);
		}
		if(mlist.get(position).getmCommentList().size()>0){
			holder.ll_zan_and_pl.setVisibility(View.VISIBLE);
			holder.view.setVisibility(View.VISIBLE);
			holder.lv_comment.setVisibility(View.VISIBLE);
			CommentAdapter mPAdapter=new CommentAdapter(mContext, mlist.get(position).getmCommentList());
			holder.lv_comment.setAdapter(mPAdapter);
		}else{
			holder.lv_comment.setVisibility(View.GONE);
			holder.view.setVisibility(View.GONE);

		}
		if(mlist.get(position).getPhotoUrl()!=null){
			Bitmap bm = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/"+mlist.get(position).getPhotoUrl());
			holder.iv_photo.setImageBitmap(bm);
		}else{
			holder.iv_photo.setImageDrawable(mContext.getResources().getDrawable(R.drawable.touxiang));
		}
		return convertView;
	}
	class ViewHolder{
		TextView tv_name,tv_content,tv_time,tv_zan,tv_comment;
		RelativeLayout rl_zan,rl_dianzan;
		ImageView iv_xin,iv_zan,iv_pl,iv_photo;
		ListView lv_comment;
		LinearLayout ll_zan_or_pl,ll_zan_and_pl,ll_fb;
		View view;
		EditText et_comment;

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
