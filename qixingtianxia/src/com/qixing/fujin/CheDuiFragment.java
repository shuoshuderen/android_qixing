package com.qixing.fujin;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class CheDuiFragment extends Fragment{
	View View;
	Context context;
	private PullToRefreshLayout ptr1;
	private boolean isFirstIn = true;
	ListView myListView;
	HttpUtils httpUtils;
	CheDuiAdapter maAdapter;
	
	private List<BeanTeam> list = new ArrayList<BeanTeam>();//存储从网络上下载数据
	@Override
	public void onDestroyView() {
		list.clear();
		super.onDestroyView();
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View = inflater.inflate(R.layout.fj_chedui, null);
		context = View.getContext();
		ptr1 = (PullToRefreshLayout) View.findViewById(R.id.fj_chedui_frsh);
		ptr1.setOnPullListener(new MyPullListener());
		initView();
		initData();
		return View;
	}
	private void initData() {
		// TODO Auto-generated method stub
				httpUtils = new HttpUtils();
				String url="http://" + Myappliction.ip +":8080/ZQXweb/SerFujin_Team";
				RequestParams params = new RequestParams();
				params.addBodyParameter("teamaddress", Myappliction.city);
				httpUtils.send(
					HttpMethod.POST, 
					url, params,
					new RequestCallBack<String>() {

						@Override
						public void onSuccess(ResponseInfo<String> responseInfo) {
						
							String result = responseInfo.result;
							Gson gson = new Gson();
							Type typeOfSrc = new TypeToken<List<BeanTeam>>(){}.getType();
							list = gson.fromJson(result, typeOfSrc);
						
							
							if (maAdapter==null) {
								maAdapter=new CheDuiAdapter(context, list);
								myListView.setAdapter(maAdapter);
							}else {
								maAdapter.notifyDataSetChanged();
							}
								
							
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							Myappliction.show(context, "联网失败");
						}
					});
				
			
	}
	private void initView() {
		// TODO Auto-generated method stub
	      myListView=(ListView) ptr1.findViewById(R.id.listview_fj_chedui);
	     /* maAdapter=new CheDuiAdapter(context, list2);
	      myListView.setAdapter(maAdapter);*/
	      myListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				BeanTeam beanTeam=list.get(position);
				Intent intent = new Intent(context,
						JtTeamActivity.class);
				intent.putExtra("team", beanTeam);
				startActivity(intent);
			}
		});
	}
	public void onWindowFocusChanged(boolean hasFocus) {
		// TODO Auto-generated method stub
		if (isFirstIn) {
			ptr1.autoRefresh();
			isFirstIn = false;
		}
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
					// 千万别忘了告诉控件刷新完毕了哦！
					
					initData();
					
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

