package com.qixing.denglu;

import org.apache.http.Header;

import com.example.qixingtianxia.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.qixing.my.utils.Myappliction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register1 extends Activity {
	EditText shezhiPasswordEditText, querenPasswordEditText;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register1);
		shezhiPasswordEditText = (EditText) findViewById(R.id.shezhipassword_edit);
		querenPasswordEditText = (EditText) findViewById(R.id.querenpassword_edit);
	}

	@SuppressLint("NewApi")
	public void jump(View view) {
		String userId = Register.phone();
		String firstpasswordString = shezhiPasswordEditText.getText().toString()
				.trim();
		String secondpasswordString = querenPasswordEditText.getText()
				.toString().trim();
		if (firstpasswordString.equals(secondpasswordString)) {
			AsyncHttpClient client = new AsyncHttpClient();
			String url = "http://"+Myappliction.ip+":8080/ZQXweb/SerDengLu?flg=2&userid="
					+ userId + "&password=" + firstpasswordString;
			client.get(url, new AsyncHttpResponseHandler() {

				@Override
				public void onSuccess(int arg0, Header[] arg1, byte[] arg2) {
					
					String text = new String(arg2);
					if (text.equals("success")) {
						runOnUiThread(new Runnable() {
							public void run() {
								Intent intent = new Intent(Register1.this,
										LoginActivity.class);
								startActivity(intent);
							}
						});
					} else {
						runOnUiThread(new Runnable() {
							public void run() {
								Myappliction.show(Register1.this, "Sorry,your name  is exit!");
							}
						});
					}

				}

				@Override
				public void onFailure(int arg0, Header[] arg1, byte[] arg2,
						Throwable arg3) {
					

				}
			});
		}else {
			Myappliction.show(Register1.this, "Sorry,your Two times the password is not consistent!");
			
		}

	}
}
