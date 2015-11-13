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
import com.qixing.adapter.CheDuiAdapter;
import com.qixing.bean.BeanTeam;
import com.qixing.fujin.JtTeamActivity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class ZuiniucheduiActivity extends Activity implements OnClickListener {

	ListView myListView;
	ImageView backImageView;
	TextView title, backtTextView;
	private PullToRefreshLayout ptr1;
	CheDuiAdapter mAdapter;
	List<BeanTeam> list = new ArrayList<BeanTeam>();
	@Override
	protected void onDestroy() {
		finish();
		list.clear();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// 取消标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__che_dui);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.chedui_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initView();
		initData();
	}

	private void initData() {
		getweb();

	}

	private void getweb() {
		final CustomProgressDialog dialog=new CustomProgressDialog(ZuiniucheduiActivity.this, "疯狂加载中。。。",R.anim.frame );
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerTeam";
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanTeam>>() {
						}.getType();

						list = gson.fromJson(result, typeOfT);
						if (mAdapter == null) {
							mAdapter = new CheDuiAdapter(
									ZuiniucheduiActivity.this, list);
							myListView.setAdapter(mAdapter);
						} else {
							mAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(ZuiniucheduiActivity.this, "访问网络失败");
					}
				});
	}

	private void initView() {
		backImageView = (ImageView) findViewById(R.id.backImage);
		title = (TextView) findViewById(R.id.top_center_text);
		title.setText("最牛车队");
		backtTextView = (TextView) findViewById(R.id.backText);

		backImageView.setOnClickListener(this);

		backtTextView.setOnClickListener(this);

		myListView = (ListView) ptr1.findViewById(R.id.wo_chedui_listview);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BeanTeam beanTeam = list.get(position);
				Intent intent = new Intent(ZuiniucheduiActivity.this,
						JtTeamActivity.class);
				intent.putExtra("team", beanTeam);
				startActivity(intent);

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
			Myappliction.show(ZuiniucheduiActivity.this, "已加载全部");
		}

	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
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
