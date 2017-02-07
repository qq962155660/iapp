package com.cdf.iapp;

import android.os.Bundle;

import com.cdf.iapp.sys.SysConfig;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Menu;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		initView();
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
				//do login request
				SysConfig.token = "token123";
				SharedPreferences pePreferences = getSharedPreferences("cdf", MODE_PRIVATE);
				Editor editor = pePreferences.edit();
				editor.putString("token", SysConfig.token);
				editor.commit();
				Intent i = new Intent(LoginActivity.this, MainActivity.class);
				startActivity(i);
				finish();
			}
			break;

		default:
			break;
		}
		
	}

	

}
