package com.qixing.guangchang;

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
import com.qixing.gc.adapter.GcTongchengAdapter;
import com.qixing.gc.beans.BeanPost;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class HaoYouFragment extends Fragment {
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerPost";
	int yeshu1 = 1;
	int tiaoshu = 0;
	ListView myListView;
	private PullToRefreshLayout ptr1;
	private boolean isFirstIn = true;
	GcTongchengAdapter mAdapter;
	List<BeanPost> list = new ArrayList<BeanPost>();
	private View view;
	Context context;

	@Override
	public void onDestroyView() {
		list.clear();
		super.onDestroyView();
	}

	@Override
	public void onResume() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("yema", 1 + "");

		tiaoshu = 0;
		getweb(params, url);
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.gc_haoyou, null);
		context = view.getContext();
		ptr1 = (PullToRefreshLayout) view.findViewById(R.id.haoyou_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initView();
		initData();
		return view;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (isFirstIn) {
			ptr1.autoRefresh();
			isFirstIn = false;
		}
	}

	private void initData() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("yema", 1 + "");
		getweb(params, url);
	}

	private void initView() {
		// TODO Auto-generated method stub
		myListView = (ListView) ptr1.findViewById(R.id.gc_haoyou_listview);
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BeanPost beanPost = list.get(position);
				Intent intent = new Intent(getActivity(),
						PostBeanActivity.class);
				intent.putExtra("bean", beanPost);
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

					RequestParams params = new RequestParams();
					params.addBodyParameter("flg", "2");
					params.addBodyParameter("userid", Myappliction.id);
					params.addBodyParameter("yema", 1 + "");

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
			// 加载操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);

					RequestParams params = new RequestParams();
					int yeshu = mAdapter.getCount() / 5 + 1;
					if (yeshu == yeshu1) {
						Myappliction.show(context, "已全部加载");
					} else {
						yeshu1 = yeshu1 + 1;
						tiaoshu = mAdapter.getCount();
						params.addBodyParameter("flg", "2");
						params.addBodyParameter("userid", Myappliction.id);
						params.addBodyParameter("yema", yeshu1 + "");
						getweb(params, url);
					}

					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);

		}

	}

	private void getweb(RequestParams params, String url2) {
		HttpUtils httpUtils1 = new HttpUtils();

		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanPost>>() {
						}.getType();
						List<BeanPost> list2 = gson.fromJson(result, typeOfT);

						for (int i = 0; i < list2.size(); i++) {
							boolean boo = true;
							BeanPost beanPost = list2.get(i);
							for (int j = 0; j < list.size(); j++) {
								if (beanPost.equals(list.get(j))) {
									boo = false;
								}
							}
							if (boo == true) {
								list.add(tiaoshu, beanPost);
								tiaoshu++;
							}
						}
						if (mAdapter == null) {
							mAdapter = new GcTongchengAdapter(context, list);
							myListView.setAdapter(mAdapter);
						} else {
							mAdapter.notifyDataSetChanged();
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

						Myappliction.show(context, "访问网络失败");
					}
				});

	}
}
