package com.qixing.fujin;

import com.example.qixingtianxia.R;

import com.qixing.bean.BeanHelp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class HelpActivity extends Activity implements OnClickListener {
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;

	TextView name, neirong, time,add;
	BeanHelp beanHelp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help);

		initView();
		initData();

	}
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	private void initView() {
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("求助详情");
		// 监听事件
		mySetOnClickListener(backImageView, backText);

		name = (TextView) findViewById(R.id.help_name);
		neirong = (TextView) findViewById(R.id.help_content);
		add=(TextView) findViewById(R.id.help_address);
		time = (TextView) findViewById(R.id.help_time);

	}
	private void mySetOnClickListener(View... views) {
		for (View view : views) {
			view.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.backText:
			finish();
			break;

		default:
			break;
		}
	}

	private void initData() {
		Intent intent = getIntent();
		beanHelp = (BeanHelp) intent.getSerializableExtra("help");

		name.setText(beanHelp.getName());
		neirong.setText(beanHelp.getHelpContent());
		add.setText(beanHelp.getHelpAddress());
		time.setText(beanHelp.getTime());
	}
}
