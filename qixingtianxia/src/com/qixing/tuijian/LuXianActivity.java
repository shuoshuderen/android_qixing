package com.qixing.tuijian;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.bean.BeanWay;
import com.qixing.bean.BeanWayImage;
import com.qixing.gc.adapter.PostImageAdapter;
import com.qixing.gc.view.PostGridview;
import com.qixing.my.utils.Myappliction;
import com.qixing.util.CustomProgressDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

public class LuXianActivity extends Activity implements OnClickListener {
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;
	boolean b = true;
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerZan";
	String url2 = "http://" + Myappliction.ip + ":8080/ZQXweb/SerWay";

	// 文本控件
	PostGridview gridview;
	TextView title, neirong, time, zan;
	ImageView star1, star2, star3, zanimag;
	// 图片adapter
	BeanWay beanWay;
	int zannum;
	PostImageAdapter adapter;
	List<BeanWayImage> list = new ArrayList<BeanWayImage>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tj_luxian);
		initview();
		initDate();
	}

	private void initview() {
		// 修复了程序进入时GridView会滑动到顶端的小bug。
		ScrollView scrollView = (ScrollView) findViewById(R.id.way_scrollview);
		scrollView.requestChildFocus(gridview, null);
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("路线详情");

		// 内部内容的初始化
		title = (TextView) findViewById(R.id.text_way_title);
		neirong = (TextView) findViewById(R.id.text_way_neirong);
		time = (TextView) findViewById(R.id.text_way_time);
		zan = (TextView) findViewById(R.id.text_way_zan);
		star1 = (ImageView) findViewById(R.id.Start1_way);
		star2 = (ImageView) findViewById(R.id.Start2_way);
		star3 = (ImageView) findViewById(R.id.Start3_way);
		zanimag = (ImageView) findViewById(R.id.zan_way);
		// 图片初始化
		gridview = (PostGridview) findViewById(R.id.way_gridview);

		// 监听事件
		mySetOnClickListener(backImageView, backText, zanimag);
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
		case R.id.zan_way:
			if (Myappliction.id == null) {
				Myappliction.show(LuXianActivity.this, "请你先登录");
			} else {
				if (b) {
					zanimag.setImageResource(R.drawable.good_after);
					getaddzan();

					b = false;
				} else {
					zanimag.setImageResource(R.drawable.good_before);
					getsubzan();
					b = true;
				}
			}

			break;
		default:
			break;
		}
	}

	private void getsubzan() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "6");
		params.addBodyParameter("userid", Myappliction.id);
		String wayid = beanWay.getWayId() + "";
		params.addBodyParameter("wayid", wayid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						Myappliction.show(LuXianActivity.this, result);
						setZanNum(4);
						zannum = zannum - 1;
						zan.setText(zannum + "");
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	private void getaddzan() {

		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "4");
		params.addBodyParameter("userid", Myappliction.id);
		String wayid = beanWay.getWayId() + "";
		params.addBodyParameter("wayid", wayid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						Myappliction.show(LuXianActivity.this, result);
						setZanNum(3);
						zannum = zannum + 1;
						zan.setText(zannum + "");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

						Myappliction.show(LuXianActivity.this, "访问网络失败");
					}
				});
	}

	private void setZanNum(int flg) {

		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", flg + "");
		String wayid = beanWay.getWayId() + "";
		params.addBodyParameter("wayid", wayid.trim());
		httpUtils1.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

					}

					@Override
					public void onFailure(HttpException error, String msg) {

						Myappliction.show(LuXianActivity.this, "访问网络失败");
					}
				});
	}

	private void initDate() {
		// 接收上个页面传来的数据
		Intent intent = getIntent();
		beanWay = (BeanWay) intent.getSerializableExtra("way");
		// 判断师傅赞过
		getzanCheck();
		getZannum();
		// zannum=beanWay.getWayZambia();
		// 设置控件
		title.setText(beanWay.getWayName());
		neirong.setText(beanWay.getWayDecription());
		time.setText("发布时间 ：  " + beanWay.getWayDatatime());

		int num1 = beanWay.getStart1();
		int num2 = beanWay.getStart2();
		int num3 = beanWay.getStart3();
		setstar(num1, star1);
		setstar(num2, star2);
		setstar(num3, star3);

		// 设置图片，需新的adapter
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;

		list = beanWay.getList();
		List<String> lStrings = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			lStrings.add(list.get(i).getWayimageroot());
		}
		adapter = new PostImageAdapter(lStrings, LuXianActivity.this, gridview,
				width);
		gridview.setAdapter(adapter);
	}

	private void getZannum() {
		final CustomProgressDialog dialog = new CustomProgressDialog(
				LuXianActivity.this, "疯狂加载中。。。", R.anim.frame);
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "5");
		String wayid = beanWay.getWayId() + "";
		params.addBodyParameter("wayid", wayid.trim());
		httpUtils1.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;

						zannum = Integer.parseInt(result);
						zan.setText(zannum + "");

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(LuXianActivity.this, "访问网络失败");
					}
				});
	}

	private void getzanCheck() {

		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "5");
		params.addBodyParameter("userid", Myappliction.id);
		String wayid = beanWay.getWayId() + "";
		params.addBodyParameter("wayid", wayid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						if (result.equals("是")) {
							b = false;
							zanimag.setImageResource(R.drawable.good_after);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

						Myappliction.show(LuXianActivity.this, "访问网络失败");
					}
				});

	}

	private void setstar(int num, ImageView star12) {
		switch (num) {
		case 1:
			star12.setImageResource(R.drawable.star1);
			break;
		case 2:
			star12.setImageResource(R.drawable.star2);
			break;
		case 3:
			star12.setImageResource(R.drawable.star3);
			break;
		case 4:
			star12.setImageResource(R.drawable.star4);
			break;
		case 5:
			star12.setImageResource(R.drawable.star5);
			break;

		default:
			break;
		}
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
}
