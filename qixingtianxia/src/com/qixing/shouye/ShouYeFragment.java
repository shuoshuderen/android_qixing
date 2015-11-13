package com.qixing.shouye;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.DetailActivity;
import com.example.qixingtianxia.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.adapter.SyListAdapter;
import com.qixing.bean.BeanWay;
import com.qixing.my.utils.Myappliction;
import com.qixing.refresh_utils.PullToRefreshLayout;
import com.qixing.refresh_utils.PullToRefreshLayout.OnPullListener;
import com.qixing.tuijian.LuXianActivity;
import com.qixing.util.CustomProgressDialog;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

public class ShouYeFragment extends Fragment implements OnClickListener {
	private PullToRefreshLayout ptr1;
	ListView listView;
	View shouyeView;
	View shouyelist;

	Context context;
	List<BeanWay> listwWays = new ArrayList<BeanWay>();
	int tiaoshu = 0;
	SyListAdapter listAdapter;
	// SYLxtopAdapter lxadAdapter;

	ViewFlipper flipper;
	// 默认有三张图片控件
	ImageView imageView1, imageView2, imageView3;
	// 使用XUtils框架下载图片，需要先导入XUtils库文件
	// 注意在AndroidManifest文件中添加访问网络权限
	BitmapUtils bitmapUtils;
	// 存储网址的集合
	List<String> list = new ArrayList<String>();
	// 用户在屏幕上滑动时在起止坐标值
	float startX, stopX;
	// 点击中间按钮跳到新的界面
	private Button buttontop;
	private Button buttonzncd;
	private Button buttonmrfj;
	private Button buttonxssl;
	ImageButton sosuobuttonButton;
	AutoCompleteTextView shuruTextView;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		bitmapUtils = new BitmapUtils(activity);
		// 设置加载中图片
		bitmapUtils.configDefaultLoadingImage(R.drawable.ic_launcher);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initData();
	}

	private void initData() {
		list.add("http://pic2.nipic.com/20090424/1468853_230119053_2.jpg");
		list.add("http://pic12.nipic.com/20110101/2531170_154321544422_2.jpg");
		list.add("http://pic4.nipic.com/20091106/3453122_014101584797_2.jpg");
	}

	@Override
	public void onDestroyView() {
		listwWays.clear();
		super.onDestroyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		shouyeView = inflater.inflate(R.layout.shouye, null);
		shouyelist = inflater.inflate(R.layout.shouye2, null);
		context = shouyelist.getContext();
		ptr1 = (PullToRefreshLayout) shouyelist.findViewById(R.id.shouye_fresh);
		ptr1.setOnPullListener(new myPullListener());
		listView = (ListView) ptr1.findViewById(R.id.listview_sy_list);
		listView.addHeaderView(shouyeView);
		initView();
		buttonmrfj = (Button) shouyeView.findViewById(R.id.symrfj);
		buttontop = (Button) shouyeView.findViewById(R.id.sylxtop);
		buttonzncd = (Button) shouyeView.findViewById(R.id.syzn);
		buttonxssl = (Button) shouyeView.findViewById(R.id.syfresh);
		sosuobuttonButton = (ImageButton) shouyeView
				.findViewById(R.id.buttonSearch);
		shuruTextView = (AutoCompleteTextView) shouyeView
				.findViewById(R.id.zhuyesousuo);

		buttonmrfj.setOnClickListener(this);
		buttonxssl.setOnClickListener(this);
		buttontop.setOnClickListener(this);
		buttonzncd.setOnClickListener(this);
		sosuobuttonButton.setOnClickListener(this);
		getweb();
		return shouyelist;
	}

	private void getweb() {
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		HttpUtils httpUtils1 = new HttpUtils();
		final CustomProgressDialog dialog = new CustomProgressDialog(context,
				"疯狂加载中。。。", R.anim.frame);
		dialog.show();
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerWay";
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						Log.e("路线top", "成功");
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
						if (listAdapter == null) {
							listAdapter = new SyListAdapter(context, listwWays);

							listView.setAdapter(listAdapter);
						} else {
							listAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();
						Myappliction.show(context, "访问网络失败");
					}
				});

	}

	private void initView() {
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent(context, LuXianActivity.class);
				intent.putExtra("way", listwWays.get(arg2-1));
				startActivity(intent);
			}
		});
		flipper = (ViewFlipper) shouyeView.findViewById(R.id.flipper);
		imageView1 = (ImageView) flipper.findViewById(R.id.i1);
		imageView2 = (ImageView) flipper.findViewById(R.id.i2);
		imageView3 = (ImageView) flipper.findViewById(R.id.i3);
		// 从网络上下载图片
		bitmapUtils.display(imageView1, list.get(0));
		bitmapUtils.display(imageView2, list.get(1));
		bitmapUtils.display(imageView3, list.get(2));
		// 监听viewFlipper触摸事件，此处如果不是重写了ViewFlipper
		// 屏幕触摸事件是被viewPager拦截的，即监听不到触摸图片事件
		flipper.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				case MotionEvent.ACTION_DOWN:
					// 按下的时候取消动画，不然自动轮播和手势滑动有冲突
					flipper.setInAnimation(null);
					flipper.setOutAnimation(null);
					// 获取按下屏幕瞬间的x轴坐标值
					startX = event.getX();
					if (flipper.isFlipping()) {
						// 手指按下屏幕的时候停止自动轮播，否则到时间会轮播到下一张图片
						flipper.stopFlipping();
					}
					/* return true */break;
				case MotionEvent.ACTION_UP:
					stopX = event.getX();
					if (stopX - startX > 100) {
						// 从左往右滑动，显示上一张图片
						flipper.showPrevious();
					} else if (startX - stopX > 100) {
						// 从右向左滑动，显示下一张图片
						flipper.showNext();
					} else if (Math.abs(stopX - startX) < 10) {
						// 滑动范围太小，默认为单击状态
						// 获取当前显示图片id
						int id = flipper.getCurrentView().getId();
						// 针对图片id，获取其对应的图片网址
						String url = getUrlById(id);
						Intent intent = new Intent(getActivity(),
								DetailActivity.class);
						intent.putExtra("url", url);
						startActivity(intent);
					}
					if (!flipper.isActivated()) {
						// 此时viewFlipper停止了轮播，恢复自动轮播状态
						flipper.startFlipping();
					}
					// 用户手指离开了屏幕，可以轮播了，并添加动画效果
					flipper.setInAnimation(getActivity(), R.anim.right_in);
					flipper.setOutAnimation(getActivity(), R.anim.lift_out);
					break;
				default:
					break;
				}
				return true;
			}
		});
	}

	private String getUrlById(int id) {
		// 获取指定id图片对应的网址
		String url = "";
		switch (id) {
		case R.id.i1:
			url = list.get(0);
			break;
		case R.id.i2:
			url = list.get(1);
			break;
		case R.id.i3:
			url = list.get(2);
			break;
		default:
			break;
		}
		return url;
	}

	@Override
	public void onClick(View v) {
		Intent intent = null;
		switch (v.getId()) {
		case R.id.sylxtop:
			// 定义一个意图,跳转到下一个界面
			intent = new Intent(getActivity(), LuxianTopActivity.class);
			// 转到详情界面获取数据
			break;
		case R.id.syfresh:
			// 定义一个意图,跳转到下一个界面
			intent = new Intent(getActivity(), XinshoushangluActivity.class);
			// 转到详情界面获取数据
			break;
		case R.id.symrfj:
			intent = new Intent(getActivity(), MirenfengjingActivity.class);
			// 转到详情界面获取数据
			break;
		case R.id.syzn:
			intent = new Intent(getActivity(), ZuiniucheduiActivity.class);
			// 转到详情界面获取数据
			break;
		case R.id.buttonSearch:
			String wayname = shuruTextView.getText().toString().trim();
			if (wayname.equals("")) {
				Myappliction.show(context, "请输入你要搜索的内容");
				return;
			} else {
				intent = new Intent(getActivity(), SousuoLxActivity.class);
				intent.putExtra("wayname", wayname);
			}

			break;
		default:
			break;
		}
		startActivity(intent);
	}

	// 刷新加载后调用的方法，刷新
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

					pullToRefreshLayout
							.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 0);
		}

		// 加载
		@SuppressLint("HandlerLeak")
		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			// 加载操作
			new Handler() {
				@Override
				public void handleMessage(Message msg) {
					// TODO Auto-generated method stub
					super.handleMessage(msg);

					pullToRefreshLayout
							.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				}

			}.sendEmptyMessageDelayed(0, 0);

		}

	}
}
