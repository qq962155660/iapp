package com.cdf.iapp.adapter;

import java.util.ArrayList;
import java.util.List;

import com.cdf.iapp.R;
import com.cdf.iapp.bean.BlogBean;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BlogGroupAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	private List<BlogBean> mList = new ArrayList<BlogBean>();
	
	public BlogGroupAdapter(Context _context, List<BlogBean> _list){
		super();
		this.mContext = _context;
		this.mList = _list;
		this.mInflater = LayoutInflater.from(mContext);
	}
	
	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.item_blog_group, null);
			holder = new ViewHolder();
			holder.vTitle = (TextView) convertView.findViewById(R.id.item_group_title_text);
			holder.vIntroduction = (TextView) convertView.findViewById(R.id.item_group_Introduction_text);
			holder.vTime = (TextView) convertView.findViewById(R.id.item_group_time_text);
			holder.vAuthor = (TextView) convertView.findViewById(R.id.item_group_author_text);
			holder.vRead = (TextView) convertView.findViewById(R.id.item_group_read_text);
			holder.vComment = (TextView) convertView.findViewById(R.id.item_group_comment_text);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.vTitle.setText(mList.get(position).getTitle());
		holder.vIntroduction.setText(mList.get(position).getIntroduction());
		holder.vTime.setText(mList.get(position).getTime());
		holder.vAuthor.setText(mList.get(position).getAuthor());
		holder.vRead.setText(mList.get(position).getRead());
		holder.vComment.setText(mList.get(position).getComment());
		return convertView;
	}
	
	class ViewHolder{
		TextView vTitle;
		TextView vTime;
		TextView vIntroduction;
		TextView vAuthor;
		TextView vComment;
		TextView vRead;
		
	}

}
