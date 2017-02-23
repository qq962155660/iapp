package com.cdf.iapp.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedHelper {

	private Context mContext;
	
	public SharedHelper(Context _context){
		this.mContext = _context;
	}
	
	public void saveString(String key, String value){
		SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sp.edit();
	    editor.putString(key, value);
	    editor.commit();
	}
	public void saveBoolean(String key, Boolean value){
		SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
	    SharedPreferences.Editor editor = sp.edit();
	    editor.putBoolean(key, value);
	    editor.commit();
	}
	public String getString(String key){
		SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
		return sp.getString(key, null);
	}
	public boolean getBoolean(String key){
		SharedPreferences sp = mContext.getSharedPreferences("mysp", Context.MODE_PRIVATE);
		return sp.getBoolean(key, true);
	}
}
