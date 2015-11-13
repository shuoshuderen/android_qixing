package com.qixing.shouye;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanFreshonroot;
import com.qixing.gc.adapter.PostImageAdapter;
import com.qixing.gc.view.PostGridview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class JtXinshouslActivity extends Activity implements OnClickListener {
	// 具体内容中的空间定义
	TextView textzhuti, texttitle, textcontent;
	BeanFreshonroot beanFreshonroot;
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText;
	// 显示图片适配器，屏幕宽
	PostImageAdapter imageAdapter;
	PostGridview imaGridView;
	private int width;
	List<String> list = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shouye_xssl_itemmain);
		initView();
		initData();

	}

	private void initView() {
		// 修复了程序进入时GridView会滑动到顶端的小bug。
		ScrollView scrollView = (ScrollView) findViewById(R.id.xinshou_scrollview);
		scrollView.requestChildFocus(imaGridView, null);
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText=(TextView) findViewById(R.id.top_center_text);
		centertText.setText("新手上路");
		mySetOnClickListener(backImageView, backText);

		texttitle = (TextView) findViewById(R.id.text_xssltitle);
		textzhuti = (TextView) findViewById(R.id.text_xssl_zhuti);
		textcontent = (TextView) findViewById(R.id.text_xssl_neirong);
	}

	private void mySetOnClickListener(View... views) {
		for (View view : views) {
			view.setOnClickListener(this);
		}
	}

	private void initData() {
		Intent intent = getIntent();
		beanFreshonroot = (BeanFreshonroot) intent
				.getSerializableExtra("xinshou");
		texttitle.setText(beanFreshonroot.getFreshtitle());
		textcontent.setText(beanFreshonroot.getMaininform());
		textzhuti.setText(beanFreshonroot.getFreshmaincontent());
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		list = beanFreshonroot.getList();
		imaGridView = (PostGridview) findViewById(R.id.xinsh_gridview);

		imageAdapter = new PostImageAdapter(list, JtXinshouslActivity.this,
				imaGridView, width);
		imaGridView.setAdapter(imageAdapter);

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

	@Override
	protected void onDestroy() {
		finish();
		list.clear();
		super.onDestroy();
	}
}
