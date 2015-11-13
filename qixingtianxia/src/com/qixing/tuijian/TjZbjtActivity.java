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
import com.qixing.adapter.TjZhuangbeiAdapter;
import com.qixing.bean.BeanEqudetail;
import com.qixing.bean.Beanequdetailphoto;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.util.CustomProgressDialog;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;

public class TjZbjtActivity extends Activity implements OnClickListener {
	// 顶部栏
		ImageView backImageView;
		TextView backText, centertText, righText;
		boolean b = true;
	
	
	int yeshu1 = 1;
	int tiaoshu = 0;
	private PullToRefreshLayout ptr1;

	String fenlei=null;
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerEqudetail";
	TjZhuangbeiAdapter zbAdapter;
	ListView listView;
	List<BeanEqudetail> lEqudetails = new ArrayList<BeanEqudetail>();
	List<Beanequdetailphoto> lphoto;
BeanEqudetail beanEqudetail;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tj_zbjt);
		ptr1 = (PullToRefreshLayout) findViewById(R.id.jtzhuangbei_fresh);
		ptr1.setOnPullListener(new MyPullListener());
		initview();
		initData();
	}

	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}

	private void initData() {
		Intent intent = getIntent();
	    fenlei = intent.getStringExtra("lei");

		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("yema", 1 + "");
		params.addBodyParameter("lei", fenlei);
		getweb(params, fenlei, url);

	}

	private void getweb(RequestParams params, String fenlei, String url2) {
		HttpUtils httpUtils1 = new HttpUtils();
		final CustomProgressDialog dialog=new CustomProgressDialog(TjZbjtActivity.this, "疯狂加载中。。。",R.anim.frame );
		dialog.show();
		
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanEqudetail>>() {
						}.getType();
						List<BeanEqudetail> list2 = gson.fromJson(result,
								typeOfT);
						
						for (int i = 0; i < list2.size(); i++) {
							boolean boo = true;
							BeanEqudetail beanEqudetail = list2.get(i);
							for (int j = 0; j < lEqudetails.size(); j++) {
								if (beanEqudetail.equals(lEqudetails.get(j))) {
									boo = false;
								}
							}
							if (boo == true) {
								lEqudetails.add(tiaoshu, beanEqudetail);
								tiaoshu++;
							}
						}
						
						if (zbAdapter == null) {
							zbAdapter = new TjZhuangbeiAdapter(lEqudetails,
									TjZbjtActivity.this);
							listView.setAdapter(zbAdapter);
						} else {
							zbAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(TjZbjtActivity.this, "访问网络失败");
					}
				});
	}

	private void initview() {
		// 顶部栏初始化和监听
				backImageView = (ImageView) findViewById(R.id.backImage);
			
				backText = (TextView) findViewById(R.id.backText);
				centertText = (TextView) findViewById(R.id.top_center_text);
				centertText.setText("装备列表");
				// 监听事件
				mySetOnClickListener(backImageView, backText);
				
				
		listView = (ListView) findViewById(R.id.listview_tj_jtzb);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent=new Intent(TjZbjtActivity.this,JtZbActivity.class);
				beanEqudetail=lEqudetails.get(arg2);
                 intent.putExtra("bean",beanEqudetail);
                 startActivity(intent);
			}
		});

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
	public class MyPullListener implements OnPullListener {

		@SuppressLint("HandlerLeak")
		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// TODO Auto-generated method stub
			// 下拉刷新操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					//刷新数据
					RequestParams params = new RequestParams();
					params.addBodyParameter("flg", "1");
					params.addBodyParameter("yema", 1 + "");
					params.addBodyParameter("lei", fenlei);
					tiaoshu=0;
					getweb(params,fenlei, url);
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
					
					RequestParams params = new RequestParams();
					int yeshu = zbAdapter.getCount() / 5 + 1;
					if (yeshu == yeshu1) {
						Myappliction.show(TjZbjtActivity.this, "已全部加载");
					} else {
						yeshu1 = yeshu1 + 1;
						tiaoshu=zbAdapter.getCount();
						params.addBodyParameter("flg", "1");
						params.addBodyParameter("yema", yeshu1 + "");
						params.addBodyParameter("lei", fenlei);
						getweb( params,fenlei,url);
					}
					// 千万别忘了告诉控件加载完毕了哦！
					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
		}
	}
}
