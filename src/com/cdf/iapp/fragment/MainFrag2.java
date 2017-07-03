package com.cdf.iapp.fragment;

import java.util.Map;

import com.ali.alipayV2.AuthResult;
import com.ali.alipayV2.OrderInfoUtil2_0;
import com.ali.alipayV2.PayDemoActivity;
import com.ali.alipayV2.PayResult;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.cdf.iapp.MomentsActivity;
import com.cdf.iapp.R;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainFrag2 extends Fragment{

	/** 支付宝支付业务：入参app_id */
	public static final String APPID = "2016073100136937";
	
	/** 支付宝账户登录授权业务：入参pid值 */
	public static final String PID = "2088102169369254";
	/** 支付宝账户登录授权业务：入参target_id值 */
	public static final String TARGET_ID = "vwqecj7795@sandbox.com";

	/** 商户私钥，pkcs8格式 */
	/** 如下私钥，RSA2_PRIVATE 或者 RSA_PRIVATE 只需要填入一个 */
	/** 如果商户两个都设置了，优先使用 RSA2_PRIVATE */
	/** RSA2_PRIVATE 可以保证商户交易在更加安全的环境下进行，建议使用 RSA2_PRIVATE */
	/** 获取 RSA2_PRIVATE，建议使用支付宝提供的公私钥生成工具生成， */
	/** 工具地址：https://doc.open.alipay.com/docs/doc.htm?treeId=291&articleId=106097&docType=1 */
	public static final String RSA2_PRIVATE = "";
	public static final String RSA_PRIVATE = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAIaLfiXadkcaCgMSSDK4g5defhqgUEg4RZ3gUR+wGBoN9H4k/k8TfySMSQH5KVwXRhkSPmip0i0nygbNjfGJkcgWfTM8IHd1DLAwgvHTIsoirEjIxBbjynjsmHKev1MWKu9UUgsEmZ3kgfkso+kq7auvrkRv7gWhDn1IhFP08RDlAgMBAAECgYAtzj/cU0wBWPQfp5G88uSaHCmClyG99Kp+3WfBCgqqlCWw7JdIBLM8liYk7dcO6z2KO5PTvj038lvthP3WRafDoBM3xMKzZWOehLdFNvm9jfFg/T1b63X2hd1QIzmKrByBjAsz/Zu/C47K91kD6gFbvR0C/nOD2nIr9/oyZJdO4QJBAM4lDL8Za9wMsu9wU+eT5yPQ3hdv9EfqZq1Fms8p1eWW8te17CzLDLrrr5ZiFeikB1sukcGNbXDkf2F44MW9/KcCQQCnFYNhGGRD1LY0nWFEg4tuqyXd3YfAS0IeDUosjA8R1o9vVnO9GWnieaqJouaXf9LK/XKqFJENTorGNItWvbuTAkA8jOFrmFn4Lrq4XfQnR2hTOtyhYb8NCKrUJmfMhetQHsCB3+vZVGMO3H+c2n648MVAYTNq+NG2oUdRKCJNsvptAkBKbx8URlWEkmKJlgMbDq8zIjPAX0x2shBGaw08QzkDbV6A7zaoa4XsSt9aaaPooh06KSbH9lmYYAEaw5W/ks7ZAkADtyh7VwHZ/j5R9T867n+XgeBmnaxfgxI4O2Fh08enawdr+xyXhiy4og6b2B78upoMShdDeV8Hl76bsqPOx/fa";
	
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_AUTH_FLAG = 2;
	
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
					//new AliPayThread().start();
					
//					Intent i = new Intent(getActivity(), PayDemoActivity.class);
//					startActivity(i);
					
					
					payV2();
				}
			});
	        return v;  
	    }  
	 
	 
	 @SuppressLint("HandlerLeak")
		private Handler mHandler = new Handler() {
			@SuppressWarnings("unused")
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case SDK_PAY_FLAG: {
					@SuppressWarnings("unchecked")
					PayResult payResult = new PayResult((Map<String, String>) msg.obj);
					/**
					 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
					 */
					String resultInfo = payResult.getResult();// 同步返回需要验证的信息
					String resultStatus = payResult.getResultStatus();
					// 判断resultStatus 为9000则代表支付成功
					if (TextUtils.equals(resultStatus, "9000")) {
						// 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
						Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
					} else {
						// 该笔订单真实的支付结果，需要依赖服务端的异步通知。
						Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
					}
					break;
				}
				case SDK_AUTH_FLAG: {
					@SuppressWarnings("unchecked")
					AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
					String resultStatus = authResult.getResultStatus();

					// 判断resultStatus 为“9000”且result_code
					// 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
					if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
						// 获取alipay_open_id，调支付时作为参数extern_token 的value
						// 传入，则支付账户为该授权账户
						Toast.makeText(getActivity(),
								"授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
								.show();
					} else {
						// 其他状态值则为授权失败
						Toast.makeText(getActivity(),
								"授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

					}
					break;
				}
				default:
					break;
				}
			};
		};
	 
		public void payV2() {
			if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
//				new AlertDialog.Builder(this).setTitle("警告").setMessage("需要配置APPID | RSA_PRIVATE")
//						.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//							public void onClick(DialogInterface dialoginterface, int i) {
//								//
//								finish();
//							}
//						}).show();
				Toast.makeText(getActivity(),
						"授权失败" + "需要配置APPID | RSA_PRIVATE", Toast.LENGTH_SHORT).show();

				return;
			}
		
			/**
			 * 这里只是为了方便直接向商户展示支付宝的整个支付流程；所以Demo中加签过程直接放在客户端完成；
			 * 真实App里，privateKey等数据严禁放在客户端，加签过程务必要放在服务端完成；
			 * 防止商户私密数据泄露，造成不必要的资金损失，及面临各种安全风险； 
			 * 
			 * orderInfo的获取必须来自服务端；
			 */
	        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
			Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
			String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

			String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
			String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
			final String orderInfo = orderParam + "&" + sign;
			
			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					PayTask alipay = new PayTask(getActivity());
					
					EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
					Map<String, String> result = alipay.payV2(orderInfo, true);
					Log.i("msp", result.toString());
					
					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			Thread payThread = new Thread(payRunnable);
			payThread.start();
		}
	 
	
}
