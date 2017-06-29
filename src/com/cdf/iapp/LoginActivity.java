package com.cdf.iapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;
import com.cdf.iapp.sys.Global;
import com.cdf.iapp.util.HttpUtil;
import com.cdf.iapp.util.SharedHelper;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener{

	private Button vBtnLogin;
	private EditText vEtAccount;
	private EditText vEtPwd;
	private TextView vTvForgetPwd;
	private TextView vTvRegister;
	public static Handler mHandler;
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
		mHandler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case 0:
					Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
					Intent i = new Intent(LoginActivity.this, MainActivity.class);
					startActivity(i);
					finish();
					break;
				case 201:
					Toast.makeText(LoginActivity.this, "请检验用户名密码", Toast.LENGTH_SHORT).show();
					break;
				default:
					break;
				}
			}
		};
	}

	private void initView() {
		vBtnLogin = (Button) findViewById(R.id.id_btn_login);
		vEtAccount = (EditText) findViewById(R.id.id_et_account);
		vEtPwd = (EditText) findViewById(R.id.id_et_pwd);
		vBtnLogin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_btn_login:
			String v1 = vEtAccount.getText().toString();
			String v2 = vEtPwd.getText().toString();
			if(v1.equals("") || v2.equals("")){
				Toast.makeText(this, "账户密码不能为空", Toast.LENGTH_SHORT).show();
			}else{
				//doLoin(v1,v2);
				mHandler.sendEmptyMessage(0);
			}
			break;

		default:
			break;
		}
		
	}

	private void doLoin(final String account, final String password) {
		new Thread(){
			
			@Override
			public void run() {
				try {
					JSONObject json = HttpUtil.doGet(getApplicationContext(),Global.URL_LOGIN + "?account=" + account + "&password=" + password);;
					if(json.getInt("code") == 0){
						String token = json.getString("data");
						SharedHelper shp = new SharedHelper(getApplicationContext());
						shp.saveString("token", token);
						mHandler.sendEmptyMessage(0);
					}else{
						mHandler.sendEmptyMessage(201);
					}
				} catch (IOException | JSONException e) {
					e.printStackTrace();
				}
			}
		}.start();;
	
	}

	

}
