package com.qixing.tuijian;

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
import com.qixing.adapter.TjLuXianAdapter;
import com.qixing.bean.BeanWay;
import com.qixing.bean.BeanWayImage;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.util.CustomProgressDialog;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class LunXianFragment extends Fragment {

	int yeshu1 = 1;
	int tiaoshu = 0;
	private PullToRefreshLayout ptr1;
	private boolean isFirstIn = true;
	View luxianView;
	Context context;

	ListView listView;
	TjLuXianAdapter lxadAdapter;
	List<BeanWay> listwWays = new ArrayList<BeanWay>();
	List<BeanWayImage> lImages;
	private String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerWay";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		luxianView = inflater.inflate(R.layout.tj_luxian, null);
		context = luxianView.getContext();
		ptr1 = (PullToRefreshLayout) luxianView.findViewById(R.id.luxian_fresh);
		ptr1.setOnPullListener(new myPullListener());
		initview();
		initData();
		return luxianView;
	}
	@Override
	public void onDestroyView() {
		listwWays.clear();
		super.onDestroyView();
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
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("yema", 1 + "");
		getweb(params, url);
	}

	private void initview() {
		listView = (ListView) ptr1.findViewById(R.id.listview_tj_lx);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> aparent, View view,
					int position, long id) {
				BeanWay beanWay = listwWays.get(position);
				Intent intent = new Intent(getActivity(), LuXianActivity.class);
				intent.putExtra("way", beanWay);
				startActivity(intent);
			}
		});
	}

	private void getweb(RequestParams params, String url2) {
		HttpUtils httpUtils1 = new HttpUtils();
		final CustomProgressDialog dialog = new CustomProgressDialog(context,
				"疯狂加载中。。。", R.anim.frame);
		dialog.show();

		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
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
							lxadAdapter = new TjLuXianAdapter(context,
									listwWays);
							listView.setAdapter(lxadAdapter);
						} else {
							lxadAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(context, "访问网络失败");
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
					// TODO Auto-generated method stub
					super.handleMessage(msg);
					// 刷新数据
					RequestParams params = new RequestParams();
					params.addBodyParameter("flg", "1");
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
			new Handler() {
				public void handleMessage(Message msg) {
					super.handleMessage(msg);

					RequestParams params = new RequestParams();
					int yeshu = lxadAdapter.getCount() / 5 + 1;
					if (yeshu == yeshu1) {
						Myappliction.show(context, "已全部加载");
					} else {
						yeshu1 = yeshu1 + 1;
						tiaoshu = lxadAdapter.getCount();
						params.addBodyParameter("flg", "1");
						params.addBodyParameter("yema", yeshu1 + "");
						getweb(params, url);
					}
					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				};
			}.sendEmptyMessageDelayed(0, 0);

		}

	}

}
