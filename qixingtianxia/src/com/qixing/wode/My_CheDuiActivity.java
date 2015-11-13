package com.qixing.wode;

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
import com.qixing.my.utils.ActivityUtils;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class My_CheDuiActivity extends Activity implements OnClickListener {
	ProgressDialog dialog;
	ListView myListView;
	ImageView backImageView, rightImageView;
	TextView title, backtTextView;
	private PullToRefreshLayout ptr1;
	CheDuiAdapter mAdapter;
	List<BeanTeam> list = new ArrayList<BeanTeam>();

	@Override
	protected void onDestroy() {
		list.clear();
		finish();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my__che_dui);
		dialog = new ProgressDialog(My_CheDuiActivity.this);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.chedui_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initView();
		initData();
	}

	@Override
	protected void onRestart() {
		mAdapter=null;
		list.clear();
		getweb();
		super.onRestart();
		
	}

	private void initData() {
		getweb();
	}

	private void getweb() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		dialog.show();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("userid", Myappliction.id);
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
									My_CheDuiActivity.this, list);
							myListView.setAdapter(mAdapter);
							Log.e("list", list.toString());
							Log.e("adapter", "null");
						} else {
							mAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(My_CheDuiActivity.this, "访问网络失败");
					}
				});

	}

	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		title = (TextView) findViewById(R.id.top_center_text);
		title.setText("我的车队");
		backtTextView = (TextView) findViewById(R.id.backText);
		rightImageView = (ImageView) findViewById(R.id.top_right_search);
		rightImageView.setImageResource(R.drawable.create);
		backImageView.setOnClickListener(this);
		rightImageView.setOnClickListener(this);
		backtTextView.setOnClickListener(this);

		myListView = (ListView) ptr1.findViewById(R.id.wo_chedui_listview);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BeanTeam beanTeam = list.get(position);
				Intent intent = new Intent(My_CheDuiActivity.this,
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
			Myappliction.show(My_CheDuiActivity.this, "已加载全部");
		}

	}

	/*
	 * public void onWindowFocusChanged(boolean hasFocus) { // TODO
	 * Auto-generated method stub if (isFirstIn) { ptr1.autoRefresh(); isFirstIn
	 * = false; } }
	 */

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.backText:
			finish();
			break;
		case R.id.top_right_search:
			ActivityUtils.startActivity(My_CheDuiActivity.this,
					MY_CreateActivity.class);
			break;
		default:
			break;
		}
	}

}
