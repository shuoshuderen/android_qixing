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
import com.qixing.adapter.XsslAdapter;
import com.qixing.bean.BeanFreshonroot;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
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

public class XinshoushangluActivity extends Activity implements OnClickListener {
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText;

	XsslAdapter xsslAdapter;
	private PullToRefreshLayout ptr1;
	ListView listView;
	int tiaoshu = 0;
	List<BeanFreshonroot> lBeanFreshonroots = new ArrayList<BeanFreshonroot>();
	@Override
	protected void onDestroy() {
		finish();
		lBeanFreshonroots.clear();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shouye_xinshoushanglu);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.xinshou_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initView();
		initData();
	}

	private void initData() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		String url = "http://" + Myappliction.ip
				+ ":8080/ZQXweb/SerFreshonroot";
		getweb(params, url);

	}

	private void getweb(RequestParams params, String url) {
		HttpUtils httpUtils1 = new HttpUtils();
		final CustomProgressDialog dialog=new CustomProgressDialog(XinshoushangluActivity.this, "疯狂加载中。。。",R.anim.frame );
		dialog.show();
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanFreshonroot>>() {
						}.getType();
						List<BeanFreshonroot> list2 = gson.fromJson(result,
								typeOfT);
						
						for (int i = 0; i < list2.size(); i++) {
							boolean boo = true;
							BeanFreshonroot beanFreshonroot = list2.get(i);
							for (int j = 0; j < lBeanFreshonroots.size(); j++) {
								if (beanFreshonroot.equals(lBeanFreshonroots
										.get(j))) {
									boo = false;
								}
							}
							if (boo == true) {
								lBeanFreshonroots.add(tiaoshu, beanFreshonroot);
								tiaoshu++;
							}
						}
						if (xsslAdapter == null) {
							xsslAdapter = new XsslAdapter(
									XinshoushangluActivity.this,
									lBeanFreshonroots);

							listView.setAdapter(xsslAdapter);
						} else {
							xsslAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();
						Myappliction.show(XinshoushangluActivity.this, "访问网络失败");
					}
				});
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.shouye_xinshou_listview);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				BeanFreshonroot beanFreshonroot=lBeanFreshonroots.get(arg2);
				Intent intent = new Intent(XinshoushangluActivity.this,
						JtXinshouslActivity.class);
				intent.putExtra("xinshou", beanFreshonroot);
				startActivity(intent);
			}
		});
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("新手须知");
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

	// 刷新加载后调用的方法
	public class myPullListener implements OnPullListener {

		@SuppressLint("HandlerLeak")
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// TODO Auto-generated method stub
			// 下拉刷新操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					// 千万别忘了告诉控件刷新完毕了哦！
					RequestParams params = new RequestParams();
					params.addBodyParameter("flg", "1");
					tiaoshu = 0;
					String url = "http://" + Myappliction.ip
							+ ":8080/ZQXweb/SerFreshonroot";
					getweb(params, url);

					pullToRefreshLayout
							.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
		}

		@SuppressLint("HandlerLeak")
		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					// 千万别忘了告诉控件加载完毕了哦！
					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
			Myappliction.show(XinshoushangluActivity.this, "已加载全部");
		}

	}

}
