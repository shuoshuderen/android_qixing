package com.qixing.denglu;

import org.apache.http.Header;

import com.example.qixingtianxia.MainActivity;
import com.example.qixingtianxia.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qixing.my.utils.Myappliction;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;

public class LoginActivity extends Activity {
	private TextView testText;
	EditText usernameEditText, passwordEditText;
	String usernameString, passwordsString;
	SharedPreferences preferences;
	CheckBox checkBox;
	Editor editor;
	/*Myappliction myApplication;*/

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		testText = (TextView) findViewById(R.id.textview);
		usernameEditText = (EditText) findViewById(R.id.username_edit);
		passwordEditText = (EditText) findViewById(R.id.password_edit);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
		preferences = getSharedPreferences("userinfo", MODE_PRIVATE);
		editor = preferences.edit();
		String id = preferences.getString("userId", "");
		if (id == null) {
			checkBox.setChecked(false);
		} else {
			checkBox.setChecked(true);
			usernameEditText.setText(id);
		}
		testText.setText(getClickableSpan());
		testText.setMovementMethod(LinkMovementMethod.getInstance());
	}

	// @SuppressWarnings("static-access")
	public void denglu(View view) {
		usernameString = usernameEditText.getText().toString().trim();
		passwordsString = passwordEditText.getText().toString().trim();
		/*
		 * String a = usernameString; myApplication.id = a;
		 */
		AsyncHttpClient client = new AsyncHttpClient();
		String url = "http://" + Myappliction.ip
				+ ":8080/ZQXweb/SerDengLu?flg=1&userid=" + usernameString
				+ "&password=" + passwordsString;
		client.get(url, new AsyncHttpResponseHandler() {

			@Override
			public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
				// 访问服务器成功
				String text = new String(arg2);
				Log.e("aa", text);
				if (!text.equals("0")) {
					if (checkBox.isChecked()) {
						editor.putString("userId", usernameString);
	
						editor.commit();
						//PreferenceUtil.getInstance(LoginActivity.this).cleanUserInfo();
					//	preferences.getString("userid", null);
					} else {
						editor.remove("userId");
						editor.commit();
					}
					runOnUiThread(new Runnable() {
						public void run() {
							Intent intent = new Intent(LoginActivity.this,
									MainActivity.class);
							intent.putExtra("yema", "4");
							startActivity(intent);
							finish();
						}
					});
				} else {

					runOnUiThread(new Runnable() {
						public void run() {
							Myappliction.show(LoginActivity.this,
									"Sorry,your name or password is wrong!");
						}
					});
				}

			}

			@Override
			public void onFailure(int arg0, Header[] arg1, byte[] arg2,
					Throwable arg3) {
				Myappliction.show(LoginActivity.this, "访问服务器失败");
			}
		});
	}

	private SpannableString getClickableSpan() {
		SpannableString spanStr = new SpannableString("没有账号？注册");
		spanStr.setSpan(new UnderlineSpan(), 5, 7,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanStr.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {

				startActivity(new Intent(LoginActivity.this, Register.class));
				finish();
			}
		}, 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		spanStr.setSpan(new ForegroundColorSpan(Color.WHITE), 5, 7,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanStr;
	}
}