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
import com.qixing.adapter.SyFengJingAdapter;
import com.qixing.bean.BeanScenery;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.util.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MirenfengjingActivity extends Activity  implements OnClickListener{
	int width;
	int ts = 0;
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;

	private PullToRefreshLayout ptr1;

	List<BeanScenery> list = new ArrayList<BeanScenery>();
	ListView listView;
	SyFengJingAdapter adapter;
	@Override
	protected void onDestroy() {
		list.clear();
		finish();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shouye_mirenfengjing);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.mieren_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initView();
		initData();
	}

	private void initView() {
		// 顶部栏初始化和监听
				backImageView = (ImageView) findViewById(R.id.backImage);
				backText = (TextView) findViewById(R.id.backText);
				centertText = (TextView) findViewById(R.id.top_center_text);
				centertText.setText("迷人风景");
				// 监听事件
				mySetOnClickListener(backImageView, backText);
		
		
		listView = (ListView) ptr1.findViewById(R.id.shouye_miren_listview);

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
			list.clear();
			finish();
			break;
		case R.id.backText:
			list.clear();
			finish();
			break;

		default:
			break;
		}
	}
	private void initData() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		getweb(params);

	}

	private void getweb(RequestParams params) {
		final CustomProgressDialog dialog = new CustomProgressDialog(
				MirenfengjingActivity.this, "努力加载中", R.anim.frame);
		dialog.show();
		HttpUtils httpUtils = new HttpUtils();
		String url = "http://"+Myappliction.ip+":8080/ZQXweb/SerFengjing";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						dialog.dismiss();
						Myappliction.show(MirenfengjingActivity.this, "联网失败");

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = arg0.result;
						Gson gson = new Gson();
						Type type = new TypeToken<List<BeanScenery>>() {
						}.getType();
						List<BeanScenery> list2 = gson.fromJson(result, type);

						for (int i = 0; i < list2.size(); i++) {
							boolean boo = true;
							BeanScenery beanScenery = list2.get(i);
							for (int j = 0; j < list.size(); j++) {
								if (list2.get(j).equals(beanScenery)) {
									boo = false;
								}
							}
							if (boo) {

								list.add(ts, beanScenery);
							}
						}
						if (adapter == null) {
							adapter = new SyFengJingAdapter(list,
									MirenfengjingActivity.this, width);
							listView.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}

					}
				});
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
					/*
					 * RequestParams params = new RequestParams();
					 * params.addBodyParameter("flg", "1"); tiaoshu = 0; String
					 * url = "http://" + Myappliction.ip +
					 * ":8080/ZQXweb/SerFreshonroot"; getweb(params, url);
					 */
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
			Myappliction.show(MirenfengjingActivity.this, "已加载全部");
		}

	}
}