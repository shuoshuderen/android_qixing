package com.qixing.shouye;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.adapter.SYLxtopAdapter;
import com.qixing.bean.BeanWay;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.tuijian.LuXianActivity;
import com.qixing.util.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class LuxianTopActivity extends Activity implements OnClickListener {
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;

	private PullToRefreshLayout ptr1;
	ListView listView;
	int tiaoshu = 0;
	private String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerWay";
	SYLxtopAdapter lxadAdapter;
	List<BeanWay> listwWays = new ArrayList<BeanWay>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shouye_luxiantop);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.luxian_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initview();
		initData();
	}

	private void initview() {
		listView = (ListView) findViewById(R.id.listview_tj_lx);
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("路线Top 10");
		// 监听事件
		mySetOnClickListener(backImageView, backText);
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
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		getweb(params, url);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(LuxianTopActivity.this,
						LuXianActivity.class);
				intent.putExtra("way", listwWays.get(arg2));
				startActivity(intent);
			}
		});
	}

	private void getweb(RequestParams params, String url2) {
		HttpUtils httpUtils1 = new HttpUtils();
		final CustomProgressDialog dialog=new CustomProgressDialog(LuxianTopActivity.this, "疯狂加载中。。。",R.anim.frame );
		dialog.show();
		
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanWay>>() {
						}.getType();
						List<BeanWay> list2 = gson.fromJson(result, typeOfT);

						for (int i = 0; i < list2.size(); i++) {
							boolean boo = true;
							BeanWay beanWay = list2.get(i);
							for (int j = 0; j < listwWays.size(); j++) {
								if (beanWay.equals(listwWays.get(j))) {
									boo = false;
								}
							}
							if (boo == true) {
								listwWays.add(tiaoshu, beanWay);
								tiaoshu++;
							}
						}
						if (lxadAdapter == null) {
							lxadAdapter = new SYLxtopAdapter(
									LuxianTopActivity.this, listwWays);

							listView.setAdapter(lxadAdapter);
						} else {
							lxadAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
					dialog.dismiss();
					Myappliction.show(LuxianTopActivity.this, "访问网络失败");
					}
				});
	}

	// 刷新加载
	private class myPullListener implements OnPullListener {

		@SuppressLint("HandlerLeak")
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					super.handleMessage(msg);
					// 刷新数据
					RequestParams params = new RequestParams();
					params.addBodyParameter("flg", "2");
					tiaoshu = 0;
					getweb(params, url);

					pullToRefreshLayout
							.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);

		}

		@SuppressLint("HandlerLeak")
		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			new Handler() {
				public void handleMessage(Message msg) {
					super.handleMessage(msg);

					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				};
			}.sendEmptyMessageDelayed(0, 0);
			Myappliction.show(LuxianTopActivity.this, "已加载全部");
		}

	}
	@Override
	protected void onDestroy() {
		finish();
		listwWays.clear();
		super.onDestroy();
	}
}
