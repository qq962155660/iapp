package com.cdf.iapp.fragment;

import java.util.ArrayList;
import java.util.Collections;

import com.cdf.iapp.R;
import com.cdf.iapp.contact.ChineseToEnglish;
import com.cdf.iapp.contact.CompareSort;
import com.cdf.iapp.contact.SideBarView;
import com.cdf.iapp.contact.User;
import com.cdf.iapp.contact.UserAdapter;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class MainFrag3 extends Fragment {

	private ListView mListview;
	private UserAdapter mAdapter;
	private TextView mTip;
	private SideBarView sideBarView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_three, null);
		mListview = (ListView) v.findViewById(R.id.fg3_listview);
		sideBarView = (SideBarView) v.findViewById(R.id.sidebarview);
		mTip = (TextView) v.findViewById(R.id.tip);
		initData();
		return v;
	}

	private void initData() {
		String[] contactsArray = getResources().getStringArray(R.array.data);
		String[] headArray = getResources().getStringArray(R.array.head);
		 //模拟添加数据到Arraylist
        int length = contactsArray.length;
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            User user = new User();
            user.setName(contactsArray[i]);
            String firstSpell = ChineseToEnglish.getFirstSpell(contactsArray[i]);
            String substring = firstSpell.substring(0, 1).toUpperCase();
            if(substring.matches("[A-Z]")){
                user.setLetter(substring);
            }else {
                user.setLetter("#");
            }
            users.add(user);
        }

        for (int i = 0; i < headArray.length; i++) {
            User user = new User();
            user.setName(headArray[i]);
            user.setLetter("@");
            users.add(user);
        }

        //排序
        Collections.sort(users, new CompareSort());

        //设置数据
        mAdapter = new UserAdapter(getActivity());
        mAdapter.setData(users);
        mListview.setAdapter(mAdapter);

        //设置回调
       // sideBarView.setOnLetterSelectListen(getActivity())
	}

}
