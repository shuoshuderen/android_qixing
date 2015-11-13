package com.qixing.fujin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.qixingtianxia.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.adapter.PeopleAdapter;
import com.qixing.bean.People;
import com.qixing.bean.People1;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.util.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.content.Context;
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

public class RenFragment extends Fragment {
   
	// 用户当前经纬度和其他人的经纬度
	LatLng userLatLng, otherLatLng;
	PeopleAdapter maAdapter;
	private List<People> list = new ArrayList<People>();// 存储从网络上下载数据
	private List<People1> list2 = new ArrayList<People1>();// 存储从网络上下载数据

	ListView myListView;
	HttpUtils httpUtils;
	People people;

	private PullToRefreshLayout ptr1;
	private boolean isFirstIn = true;
	View renView;
	Context context;
	@Override
	public void onDestroyView() {
		list.clear();
		list2.clear();
		super.onDestroyView();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		renView = inflater.inflate(R.layout.fj_ren, null);
		context = renView.getContext();
		ptr1 = (PullToRefreshLayout) renView.findViewById(R.id.ren_fresh);
		ptr1.setOnPullListener(new MyPullListener());
		initView();
		inotData();
		return renView;
	}

	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (isFirstIn) {
			ptr1.autoRefresh();
			isFirstIn = false;
		}
	}

	private void inotData() {
		 final CustomProgressDialog dialog=new CustomProgressDialog(context, "疯狂加载中。。。",R.anim.frame);
		dialog.show();
		// TODO Auto-generated method stub
		userLatLng = new LatLng(Myappliction.latitude, Myappliction.longitude);
		httpUtils = new HttpUtils();
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerPeople";
		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", Myappliction.id);
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						dialog.dismiss();
						Gson gson = new Gson();
						Type typeOfSrc = new TypeToken<List<People>>() {
						}.getType();

						list = gson.fromJson(result, typeOfSrc);

						for (int i = 0; i < list.size(); i++) {

							people = list.get(i);
							String userid = people.getUserid();
							String username = people.getUsername();
							String gender = people.getGender();
							int age = people.getAge();
							String userimg = people.getUserimg();
							double totaldistance = people.getTotaldistance();
							String totaltime = people.getTotaltime();

							double longitud = people.getLongitude();// 其他用户经度
							double latiudt = people.getLatiudt();// 其他用户纬度

							otherLatLng = new LatLng(latiudt, longitud);

							// 获取两点之间距离，单位为m
							double distance = DistanceUtil.getDistance(
									userLatLng, otherLatLng);

							if (distance < 5000) {
								People1 people1 = new People1(userid, username,
										gender, age, userimg, totaldistance,
										totaltime, latiudt, longitud, distance);
								boolean boo = true;
								for (int j = 0; j < list2.size(); j++) {

									if (people1.equals(list2.get(j))) {
										boo = false;
									}

								}
								if (boo) {
									list2.add(people1);
								}
							}

						}

						{
							maAdapter = new PeopleAdapter(context, list2);
							myListView.setAdapter(maAdapter);
						}

						/* maAdapter.notifyDataSetChanged(); */
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(context, "联网失败");
					}
				});

	}

	private void initView() {
		
		myListView = (ListView) ptr1.findViewById(R.id.listview_fj_ren);
		/*
		 * maAdapter = new PeopleAdapter(context, list2);
		 * myListView.setAdapter(maAdapter);
		 */
		myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String userid = list2.get(position).getUserid();
				Myappliction.getuser(userid, context);
			}
		});
	}

	public class MyPullListener implements OnPullListener {

		@SuppressLint("HandlerLeak")
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// TODO Auto-generated method stub
			// 下拉刷新操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// maAdapter = new PeopleAdapter(context, list2);
					list2.clear();
					inotData();

					// 千万别忘了告诉控件刷新完毕了哦！
					pullToRefreshLayout
							.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
		}

		@SuppressLint("HandlerLeak")
		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// TODO Auto-generated method stub
			// 加载操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// 千万别忘了告诉控件加载完毕了哦！
					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
			Myappliction.show(context, "已加载全部");
		}

	}
}
