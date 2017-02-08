package com.cdf.iapp.fragment;

import com.cdf.iapp.MomentsActivity;
import com.cdf.iapp.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainFrag2 extends Fragment{

	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
	        View v = inflater.inflate(R.layout.frag_two, null); 
	        RelativeLayout rl =  (RelativeLayout) v.findViewById(R.id.fg2_ry1);
	        rl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), MomentsActivity.class);
					startActivity(i);
					
				}
			});
	        return v;  
	    }  
}
