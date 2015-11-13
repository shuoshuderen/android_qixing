package com.qixing.tuijian;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanEqudetail;
import com.qixing.bean.Beanequdetailphoto;
import com.qixing.gc.adapter.PostImageAdapter;
import com.qixing.gc.view.PostGridview;

import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;

public class JtZbActivity extends Activity implements OnClickListener {
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;
	boolean b = true;
	// 文本控件
	PostGridview gridview;
	TextView title, neirong, jiage;
	// 图片adapter

	PostImageAdapter adapter;
	List<Beanequdetailphoto> list = new ArrayList<Beanequdetailphoto>();
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jt_zb);
		initview();
		initDate();
	}

	private void initDate() {
		Intent intent=getIntent();
		BeanEqudetail beanEqudetail=(BeanEqudetail) intent.getSerializableExtra("bean");
        
		// 设置控件
				title.setText(beanEqudetail.getEqudetailName());
				neirong.setText(beanEqudetail.getEqudetailInform());
				jiage.setText("建议价格 ：  " + beanEqudetail.getEqudetailPrice());
	
				// 设置图片，需新的adapter
				// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
				Display display = getWindowManager().getDefaultDisplay();
				Point size = new Point();
				display.getSize(size);
				int width = size.x;
				
				list=beanEqudetail.getList();
				List<String> lStrings=new ArrayList<String>();
				for (int i = 0; i < list.size(); i++) {
					lStrings.add(list.get(i).getPhoto());
				}
	adapter=new PostImageAdapter(lStrings, JtZbActivity.this, gridview, width);
	gridview.setAdapter(adapter);
	}

	private void initview() {
		// 修复了程序进入时GridView会滑动到顶端的小bug。
		ScrollView scrollView = (ScrollView) findViewById(R.id.zhuangbei_scrollview);
		scrollView.requestChildFocus(gridview, null);
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("装备详情");
		// 监听事件
		mySetOnClickListener(backImageView, backText);
		// 内部内容的初始化
		title = (TextView) findViewById(R.id.jt_zb_name);
		neirong = (TextView) findViewById(R.id.jt_zb_neirong);
		jiage = (TextView) findViewById(R.id.jt_zb_jiage);
		
		gridview = (PostGridview) findViewById(R.id.jt_zb_gridview);
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
}
