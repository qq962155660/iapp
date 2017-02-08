package com.cdf.iapp.adapter;

import java.util.ArrayList;

import com.cdf.iapp.R;
import com.cdf.iapp.bean.CommentBean;

import android.content.Context;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class CommentAdapter extends BaseAdapter{
	private Context mContext;
	private ArrayList<CommentBean> mlist;
	private LayoutInflater mInflater;
	
	

	public CommentAdapter(Context mContext, ArrayList<CommentBean> mlist) {
		super();
		this.mContext = mContext;
		this.mlist = mlist;
		this.mInflater=LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		 int ret = 0;
	        if (mlist != null&&mlist.size()!=0)
	            ret = mlist.size();
	        return ret;
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
		
        View view = null;
        if(convertView!=null)
        {
            view = convertView;
        }
        else
        {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_comment, parent,false);
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        if(holder==null)
        {
            holder = new ViewHolder();
            holder.txt_comment = (TextView) view.findViewById(R.id.txt_comment);

            view.setTag(holder);
        }
        if (mlist != null && mlist.size()!=0) {
            StringBuilder actionText = new StringBuilder();

            //谁回复
            actionText.append("<a style=\"text-decoration:none;\" href='name' ><font color='#1468a3'>"
                    + mlist.get(position).getName()  + "</font> </a>");

            // 回复谁，被回复的人可能不存在。
            if(mlist.get(position).getToname()!=null&&mlist.get(position).getToname().length()>0) {
                actionText.append("回复");
                actionText.append("<font color='#1468a3'><a style=\"text-decoration:none;\" href='toName'>"
                         + mlist.get(position).getToname() + " " + " </a></font>");
            }
            // 内容
            actionText.append("<font color='#484848'><a style=\"text-decoration:none;\" href='content'>"
                    + ":" + mlist.get(position).getContent() + " " + " </a></font>");

            holder.txt_comment.setText(Html.fromHtml(actionText.toString()));
            holder.txt_comment.setMovementMethod(LinkMovementMethod
                    .getInstance());
            CharSequence text = holder.txt_comment.getText();
            int ends = text.length();
            Spannable spannable = (Spannable) holder.txt_comment.getText();
            URLSpan[] urlspan = spannable.getSpans(0, ends, URLSpan.class);
            SpannableStringBuilder stylesBuilder = new SpannableStringBuilder(text);
            stylesBuilder.clearSpans();

            for (URLSpan url : urlspan) {
                FeedTextViewURLSpan myURLSpan = new FeedTextViewURLSpan(url.getURL(),
                        mContext,mlist.get(position).getName(),mlist.get(position).getToname(),mlist.get(position).getContent());
                stylesBuilder.setSpan(myURLSpan, spannable.getSpanStart(url),
                        spannable.getSpanEnd(url), spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            holder.txt_comment.setText(stylesBuilder);
            holder.txt_comment.setFocusable(false);
            holder.txt_comment.setClickable(false);
            holder.txt_comment.setLongClickable(false);

        }

        return view;
	}
	
	class ViewHolder{
		TextView txt_comment;
	}
	
	 static class FeedTextViewURLSpan extends ClickableSpan {
	        private String clickString;
	        private Context context;
	        // 回复人的名字
	        private String name;
	        // 被回复人的名字
	        private String toName;
	        // 评论内容
	        private String content;

	        public FeedTextViewURLSpan(String clickString, Context context, String name, String toName, String content) {
	            this.clickString = clickString;
	            this.context = context;
	            this.name = name;
	            this.toName = toName;
	            this.content = content;
	        }

	        @Override
	        public void updateDrawState(TextPaint ds) {
	            ds.setUnderlineText(false);
	            //给标记的部分 的文字 添加颜色
	            if(clickString.equals("toName")){
	                ds.setColor(context.getResources().getColor(R.color.blue));
	            }else if(clickString.equals("name")){
	                ds.setColor(context.getResources().getColor(R.color.blue));
	            }
	        }

	        @Override
	        public void onClick(View widget) {
	            // 根据文字的标记 来进行相应的 响应事件
	            if (clickString.equals("toName")) {
	                //可以再次进行跳转activity的操作
	                Toast.makeText(context,"点击了"+toName,Toast.LENGTH_SHORT).show();
	            } else if (clickString.equals("name")) {
	                //可以再次进行跳转activity的操作
	                Toast.makeText(context,"点击了"+name,Toast.LENGTH_SHORT).show();
	            } else if(clickString.equals("content")){
	                //可以再次进去回复评论的操作
	                Toast.makeText(context,"点击了"+content,Toast.LENGTH_SHORT).show();
	            }
	        }
	    }
}
