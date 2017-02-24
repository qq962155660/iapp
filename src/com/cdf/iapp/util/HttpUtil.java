package com.cdf.iapp.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.cdf.iapp.GuideActivity;
import com.cdf.iapp.IndexActivity;
import com.cdf.iapp.LoginActivity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class HttpUtil {
	
	public static JSONObject doGet(Context _context,String url) throws ClientProtocolException, IOException, JSONException{
		JSONObject res = new JSONObject();
		HttpClient httpclient = new  DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		String _token = getToken(_context);
		httpGet.addHeader("token", _token); //认证token   
		httpGet.addHeader("Content-Type", "application/json");  
		httpGet.addHeader("User-Agent", "imgfornote");  
		HttpResponse resp = httpclient.execute(httpGet);
		
		 int code = resp.getStatusLine().getStatusCode();  
	     if (code == 200) {   
	    	   String rev = EntityUtils.toString(resp.getEntity());          
	           res = new JSONObject(rev);  
	     }
	     if(res.getInt("code") == 40030 || res.getInt("code") == 40029){
	    	 Intent i = new Intent(_context, LoginActivity.class);
	    	 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 _context.startActivity(i);
	    	 
	     }
		return res;
	}

	private static String getToken(Context _context) {
		SharedHelper shp = new SharedHelper(_context);
		
		return shp.getString("token");
	}

	public static JSONObject doPost(Context _context,String url,JSONObject data) throws ClientProtocolException, IOException, JSONException{
		 JSONObject res = new JSONObject();
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url);
		 String _token = getToken(_context);
		 httppost.addHeader("token", _token); //认证token   
	     httppost.addHeader("Content-Type", "application/json");  
	     httppost.addHeader("User-Agent", "imgfornote");  
	    
	     httppost.setEntity(new StringEntity(data.toString()));    
	     HttpResponse response = httpclient.execute(httppost);
	     int code = response.getStatusLine().getStatusCode();  
	     if (code == 200) {   
	    	   String rev = EntityUtils.toString(response.getEntity());          
	           res = new JSONObject(rev);  
	     }
	     if(res.getInt("code") == 40030 || res.getInt("code") == 40029){
	    	 Intent i = new Intent(_context, LoginActivity.class);
	    	 i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	    	 _context.startActivity(i);
	    	 
	     }
		return res;
	}
}
