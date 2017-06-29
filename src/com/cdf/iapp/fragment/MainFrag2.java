package com.cdf.iapp.fragment;

import com.ali.alipay.AlipayAPI;
import com.ali.alipay.PayResult;
import com.alipay.sdk.app.EnvUtils;
import com.cdf.iapp.MomentsActivity;
import com.cdf.iapp.R;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainFrag2 extends Fragment{

	   private static final int SDK_PAY_FLAG = 1;
	
	 @Override  
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
	            Bundle savedInstanceState) {  
	        View v = inflater.inflate(R.layout.frag_two, null); 
	        RelativeLayout rl =  (RelativeLayout) v.findViewById(R.id.fg2_ry1);
	        //朋友圈
	        rl.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), MomentsActivity.class);
					startActivity(i);
					
				}
			});
	        //发起支付
	        RelativeLayout vpay =  (RelativeLayout) v.findViewById(R.id.fg2_alipy);
	        vpay.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("支付开始。。。。。。。。。。");
					new Thread(new AliPayThread()).start();
				}
			});
	        return v;  
	    }  
	 
	 
	 private Handler mHandler=new Handler()
	    {
	    	 @Override
	         public void handleMessage(Message msg) {
	             switch (msg.what) {
	             case SDK_PAY_FLAG: {
	                 PayResult payResult = new PayResult((String) msg.obj);
	                 /**
	                  * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
	                  * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
	                  * docType=1) 建议商户依赖异步通知
	                  */
	                 String resultInfo = payResult.getResult();// 同步返回需要验证的信息

	                 String resultStatus = payResult.getResultStatus();
	                 // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
	                 if (TextUtils.equals(resultStatus, "9000")) {
	                     Toast.makeText(getActivity(), "支付成功",
	                             Toast.LENGTH_SHORT).show();
	                 } else {
	                     // 判断resultStatus 为非"9000"则代表可能支付失败
	                     // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
	                     if (TextUtils.equals(resultStatus, "8000")) {
	                         Toast.makeText(getActivity(), "支付结果确认中",
	                                 Toast.LENGTH_SHORT).show();
	                     } else {
	                         // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
	                         Toast.makeText(getActivity(),
	                                 "支付失败" + resultStatus, Toast.LENGTH_SHORT)
	                                 .show();
	                     }
	                 }
	                 break;
	             }
	             }
	         };
	    };
	 
	  /**
	     * 支付宝支付异步任务
	     * 
	     * @author Simon
	     */
	    private class AliPayThread extends Thread {
	        @Override
	        public void run() {
	        	EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
	            String result = AlipayAPI.pay(getActivity(), "测试的商品",
	                    "测试商品的详细描述", "0.01");
	            Message msg = new Message();
	            msg.what = SDK_PAY_FLAG;
	            msg.obj = result;
	            mHandler.sendMessage(msg);
	        }
	    }
}
