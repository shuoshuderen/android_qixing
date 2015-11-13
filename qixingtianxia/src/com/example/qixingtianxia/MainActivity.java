package com.example.qixingtianxia;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.GeoCodeResult;
import com.baidu.mapapi.search.geocode.GeoCoder;
import com.baidu.mapapi.search.geocode.OnGetGeoCoderResultListener;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeOption;
import com.baidu.mapapi.search.geocode.ReverseGeoCodeResult;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.fujin.FuJinFragment;
import com.qixing.guangchang.GuangChangFragment;
import com.qixing.my.utils.Myappliction;
import com.qixing.shouye.ShouYeFragment;
import com.qixing.tuijian.TuiJianFragment;
import com.qixing.wode.WoDeFragment;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.util.Log;
import android.view.Display;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements
		OnGetGeoCoderResultListener {
	// 地图主要定位类
	private LocationClient mLocClient;
	// 监听当前位置类
	MyLocationListenner myListener = new MyLocationListenner();
	private double latitude;
	private double longitude;
	LatLng userLatLng;

	GeoCoder mSearch = null; // 搜索模块，也可去掉地图模块独立使用

	Fragment fragment;
	FragmentManager manager;
	FragmentTransaction transaction;
	RadioGroup group;
	RadioButton radioButton;
	SharedPreferences preferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		preferences = getSharedPreferences("userinfo", MODE_PRIVATE);
		Myappliction.id = preferences.getString("userId", null);
		initView();
		getLocation();// 获取经纬度
		initData();
		getwindth();
		// 第一次进入页面
		firstShow();
	}

	private void getwindth() {
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		Myappliction.width = width;
	}

	private void firstShow() {
		String aString = getIntent().getStringExtra("yema");
		if (aString == null) {
			fragment = new ShouYeFragment();
			
			replace(fragment);
		} else {
			fragment = new WoDeFragment();
			replace(fragment);
		}

	}

	private void initData() {
		// 赋初值
		manager = getSupportFragmentManager();
	}

	private void initView() {
		radioButton = (RadioButton) findViewById(R.id.woderedio);

		group = (RadioGroup) findViewById(R.id.bottomtab);
		group.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.shouyeredio:
					fragment = new ShouYeFragment();
					break;
				case R.id.tuijianredio:
					fragment = new TuiJianFragment();
					break;
				case R.id.guangchangredio:
					fragment = new GuangChangFragment();
					break;
				case R.id.fujinredio:
					if (Myappliction.id != null) {
						fragment = new FuJinFragment();
					} else {
						Myappliction.show(MainActivity.this, "请先登录");
					}

					break;
				case R.id.woderedio:
					fragment = new WoDeFragment();
					// 允许回调,即按返回按钮返回当前碎片
					transaction.addToBackStack(null);
					break;

				default:
					break;
				}
				replace(fragment);
			}
		});
	}

/*	// 添加编辑的单击事件
	public void bianji(View view) {
		if (Myappliction.id != null) {
			Intent intent = new Intent(MainActivity.this, GcAddActivity.class);
			startActivity(intent);
		} else {
			Myappliction.show(MainActivity.this, "请先登录");
		}

	}*/

	// 替换fragment的封装方法
	private void replace(Fragment fragment) {
		transaction = manager.beginTransaction();
		// 布局碎片，上部
		transaction.replace(R.id.container, fragment);
		transaction.commit();
	}

	// 此方法用来回调图赏右上角按钮单击事件，并调用社区fragment
	public void test() {
		fragment = new FuJinFragment();
		replace(fragment);
	}

	/**
	 * 定位SDK监听函数
	 */
	class MyLocationListenner implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 获取用户当前经纬度
			latitude = location.getLatitude();// 纬度
			longitude = location.getLongitude();// 经度
			userLatLng = new LatLng(latitude, longitude);

			Myappliction.latitude = latitude;
			Myappliction.longitude = longitude;
			// 反Geo搜索,根据经纬度获取城市
			mSearch.reverseGeoCode(new ReverseGeoCodeOption()
					.location(userLatLng));
			if (Myappliction.id != null) {
				setJWweb();
			}
			// 定位成功后及时修改当前用户的经纬度值
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	private void setJWweb() {
		HttpUtils httpUtils = new HttpUtils();
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerDengLu";

		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "6");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("jingdu", longitude + "");
		params.addBodyParameter("weidu", latitude + "");

		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {

					}
				});

	}

	public void getLocation() {
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		// 定位配置类
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型 ：经纬度类型
		option.setScanSpan(1000000); // 设置扫描间隔，单位是ms，这里假设10s钟定位一次
		mLocClient.setLocOption(option);
		mLocClient.start(); // 开始定位
		mSearch = GeoCoder.newInstance();
		mSearch.setOnGetGeoCodeResultListener(this);

	}

	@Override
	public void onGetGeoCodeResult(GeoCodeResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGetReverseGeoCodeResult(ReverseGeoCodeResult arg0) {
		// TODO Auto-generated method stub
		// 把当前城市存储起来
		Myappliction.city = arg0.getAddressDetail().city;
		Log.e("cs", Myappliction.city);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mLocClient.stop();
		mSearch.destroy();
		super.onDestroy();
	}
}
