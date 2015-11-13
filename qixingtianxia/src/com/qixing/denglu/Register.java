package com.qixing.denglu;


import com.example.qixingtianxia.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.FrameLayout.LayoutParams;

public class Register extends Activity implements OnClickListener {

	private TextView testText;
	// 手机号输入框
	private static EditText inputPhoneEt;

	// 验证码输入框
	private EditText inputCodeEt;

	// 获取验证码按钮
	private Button requestCodeBtn;

	// 注册按钮
	private Button commitBtn;

	//
	int i = 60;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		testText = (TextView) findViewById(R.id.textview2);
		
		testText.setText(getClickableSpan());
		
		init();
		testText.setMovementMethod(LinkMovementMethod.getInstance());
	}

	private void init() {
		inputPhoneEt = (EditText) findViewById(R.id.username_edit);
		inputCodeEt = (EditText) findViewById(R.id.mail_edit);
		requestCodeBtn = (Button) findViewById(R.id.button123);
		commitBtn = (Button) findViewById(R.id.signin_button);
		requestCodeBtn.setOnClickListener(this);
		commitBtn.setOnClickListener(this);

		//启动短信验证sdk
		SMSSDK.initSDK(this, "bc4671b8a02c", "f516afaee9e3a3a44fbc9045857ed501");
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	@Override
	public void onClick(View v) {

		String phoneNums = inputPhoneEt.getText().toString();
		switch (v.getId()) {
		case R.id.button123:
			// 1. 通过规则判断手机号
			if (!judgePhoneNums(phoneNums)) {
				return;
			} // 2. 通过sdk发送短信验证֤
			SMSSDK.getVerificationCode("86", phoneNums);
             
			// 3. 把按钮变成不可点击，并且显示倒计时（正在获取）
			requestCodeBtn.setClickable(false);
			requestCodeBtn.setText("重新发送(" + i + ")");
			new Thread(new Runnable() {
				@Override
				public void run() {
					for (; i > 0; i--) {
						handler.sendEmptyMessage(-9);
						if (i <= 0) {
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					handler.sendEmptyMessage(-8);
				}
			}).start();
			break;

		case R.id.signin_button:
			SMSSDK.submitVerificationCode("86", phoneNums, inputCodeEt
					.getText().toString());
			createProgressBar();
			break;
		}
	}

	/**
	 * 
	 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == -9) {
				requestCodeBtn.setText("重新发送(" + i + ")");
			} else if (msg.what == -8) {
				requestCodeBtn.setText("获取验证码");
				requestCodeBtn.setClickable(true);

			} else {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						Toast.makeText(getApplicationContext(), "提交验证码成功",
								Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(Register.this,
								Register1.class);
						startActivity(intent);
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						Toast.makeText(getApplicationContext(), "验证码已经发送",
								Toast.LENGTH_SHORT).show();
					} else {
						((Throwable) data).printStackTrace();
					}
				}
			}
		}
	};

	/**
	 * 判断手机号码是否合理
	 * 
	 * @param phoneNums
	 */
	private boolean judgePhoneNums(String phoneNums) {
		if (isMatchLength(phoneNums, 11) && isMobileNO(phoneNums)) {
			return true;
		}
		Toast.makeText(this, "手机号码输入有误!", Toast.LENGTH_SHORT).show();
		return false;
	}

	/**
	 * 判断一个字符串的位数
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static boolean isMatchLength(String str, int length) {
		if (str.isEmpty()) {
			return false;
		} else {
			return str.length() == length ? true : false;
		}
	}

	/**
	 * 验证手机格式
	 */
	public static boolean isMobileNO(String mobileNums) {

		/**
		 * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
		 * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
		 * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
		 */

		String telRegex = "[1][358]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (TextUtils.isEmpty(mobileNums))
			return false;
		else
			return mobileNums.matches(telRegex);
	}

	/**
	 * progressbar
	 */
	private void createProgressBar() {
		FrameLayout layout = (FrameLayout) findViewById(android.R.id.content);
		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		ProgressBar mProBar = new ProgressBar(this);
		mProBar.setLayoutParams(layoutParams);
		mProBar.setVisibility(View.VISIBLE);
		layout.addView(mProBar);
	}

	@Override
	protected void onDestroy() {
		SMSSDK.unregisterAllEventHandler();
		super.onDestroy();
	}


	private SpannableString getClickableSpan() {
		SpannableString spanStr = new SpannableString("已有账号？登陆");
		
		spanStr.setSpan(new UnderlineSpan(), 5, 7,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

		spanStr.setSpan(new ClickableSpan() {

			@Override
			public void onClick(View widget) {

				startActivity(new Intent(Register.this, LoginActivity.class));
			}
		}, 5, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
	
		spanStr.setSpan(new ForegroundColorSpan(Color.WHITE), 5, 7,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		return spanStr;
	}

	public static String phone() {
		String phoneNums = inputPhoneEt.getText().toString();
		return phoneNums;
	}
}
