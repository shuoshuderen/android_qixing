package com.qixing.refresh_utils;

import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;

import android.os.Handler;
import android.os.Message;

public class MyPullListener implements OnPullListener {


	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// 下拉刷新操作
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// 千万别忘了告诉控件刷新完毕了哦！
						pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 3000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
		// TODO Auto-generated method stub
		// 加载操作
				new Handler() {
					@Override
					public void handleMessage(Message msg) {
						// 千万别忘了告诉控件加载完毕了哦！
						pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					}
				}.sendEmptyMessageDelayed(0, 3000);
	}

}
