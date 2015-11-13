package com.example.qixingtianxia;

import com.lidroid.xutils.BitmapUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class DetailActivity extends Activity {
	ImageView imageView;
	//实际项目开发中BitmapUtils最好做成单例模式
	BitmapUtils bitmapUtils;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		imageView = (ImageView) findViewById(R.id.imageview);
		bitmapUtils = new BitmapUtils(DetailActivity.this);
		Intent intent = getIntent();
		String url = intent.getStringExtra("url");
		//这里不用担心重新从网络上下载图片，会先从本地缓存中下载
		bitmapUtils.display(imageView, url);
	}

}
