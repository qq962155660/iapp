package com.cdf.iapp.adapter;

import java.util.List;

import com.cdf.iapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class PhotoCommentAdapter extends BaseAdapter{
private Context mContext;
private LayoutInflater mInflater;
private List<String> mlist;

	public PhotoCommentAdapter(Context mContext, List<String> mlist) {
	super();
	this.mContext = mContext;
	this.mlist = mlist;
	this.mInflater=LayoutInflater.from(mContext);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder=null;
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.item_lv_h, null);
			holder=new ViewHolder();
			holder.iv_photo=(ImageView) convertView.findViewById(R.id.iv_photo);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		Bitmap bm = BitmapFactory.decodeFile(mlist.get(position));
		holder.iv_photo.setImageBitmap(bm);
		return convertView;
	}
	class ViewHolder{
		ImageView iv_photo;
	}
}
