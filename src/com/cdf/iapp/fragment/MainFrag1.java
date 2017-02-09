package com.cdf.iapp.fragment;

import java.util.ArrayList;
import java.util.List;

import com.cdf.iapp.R;
import com.cdf.iapp.adapter.BlogGroupAdapter;
import com.cdf.iapp.bean.BlogBean;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class MainFrag1 extends Fragment {

	private ListView mListview;
	private BlogGroupAdapter mAdaper;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_one, null);
		mListview = (ListView) v.findViewById(R.id.fg1_listview);
		mAdaper = new BlogGroupAdapter(getActivity(), mList);
		mListview.setAdapter(mAdaper);
		return v;
	}

	public static List<BlogBean> mList = new ArrayList<BlogBean>();
	static {
		BlogBean b1 = new BlogBean();
		b1.setAuthor("顶峰");
		b1.setIntroduction("简介11");
		b1.setTitle("标题11");
		b1.setTime("2017.02.09");
		b1.setRead("阅读(99)");
		b1.setComment("评论(3)");
		BlogBean b2 = new BlogBean();
		b2.setAuthor("顶峰");
		b2.setIntroduction("简介11");
		b2.setTitle("标题11");
		b2.setTime("2017.02.09");
		b2.setRead("阅读(99)");
		b2.setComment("评论(3)");
		BlogBean b3 = new BlogBean();
		b3.setAuthor("顶峰");
		b3.setIntroduction("简介11");
		b3.setTitle("标题11");
		b3.setTime("2017.02.09");
		b3.setRead("阅读(99)");
		b3.setComment("评论(3)");
		mList.add(b1);
		mList.add(b2);
		mList.add(b3);
	}
}
