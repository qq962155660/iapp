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

public class HttpUtil {
	
	public static JSONObject doGet(String url) throws ClientProtocolException, IOException, JSONException{
		JSONObject res = new JSONObject();
		HttpClient httpclient = new  DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		httpGet.addHeader("Authorization", "your token"); //认证token   
		httpGet.addHeader("Content-Type", "application/json");  
		httpGet.addHeader("User-Agent", "imgfornote");  
		HttpResponse resp = httpclient.execute(httpGet);
		
		 int code = resp.getStatusLine().getStatusCode();  
	     if (code == 200) {   
	    	   String rev = EntityUtils.toString(resp.getEntity());          
	           res = new JSONObject(rev);  
	     }
		return res;
	}

	public static JSONObject doPost(String url,JSONObject data) throws ClientProtocolException, IOException, JSONException{
		 JSONObject res = new JSONObject();
		 HttpClient httpclient = new DefaultHttpClient();  
		 HttpPost httppost = new HttpPost(url);
		 httppost.addHeader("Authorization", "your token"); //认证token   
	     httppost.addHeader("Content-Type", "application/json");  
	     httppost.addHeader("User-Agent", "imgfornote");  
	    
	     httppost.setEntity(new StringEntity(data.toString()));    
	     HttpResponse response = httpclient.execute(httppost);
	     int code = response.getStatusLine().getStatusCode();  
	     if (code == 200) {   
	    	   String rev = EntityUtils.toString(response.getEntity());          
	           res = new JSONObject(rev);  
	     }
		return res;
	}
}
