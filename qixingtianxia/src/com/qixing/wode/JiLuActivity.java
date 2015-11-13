package com.qixing.wode;

import java.text.NumberFormat;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.utils.Myappliction;

import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

public class JiLuActivity extends Activity implements OnClickListener {
	boolean boo =false;
	int i = 0;
	double zong;
	private LocationClient mLocClient;
	// 监听当前位置类
	MyLocationListenner2 myListener = new MyLocationListenner2();

	private double latitude;
	private double longitude;
	// 用户当前经纬度和其他人的经纬度
	LatLng firstLatLng, secondLatLng;

	private Chronometer timer;

	ImageView backImageView;
	TextView title, backtTextView;

	TextView licheng, dw;
	Button start, end;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ji_lu);
		initView();
	}

	private void initView() {
		backImageView = (ImageView) findViewById(R.id.backImage);
		title = (TextView) findViewById(R.id.top_center_text);
		title.setText("骑行");
		backtTextView = (TextView) findViewById(R.id.backText);

		licheng = (TextView) findViewById(R.id.text_licheng);
		dw = (TextView) findViewById(R.id.text_danwei);
		start = (Button) findViewById(R.id.button_start);
		end = (Button) findViewById(R.id.button_end);
		timer = (Chronometer) findViewById(R.id.chronometer);
		mySetOnClickListener(backImageView, backtTextView, start, end);
	}

	private void mySetOnClickListener(View... v) {
		for (View view : v) {
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
		case R.id.button_start:
			timer.start();
			// 获取经纬度
			getLocation();
			break;
		case R.id.button_end:
			timer.stop();
			// 退出应用的时候停止定位,这个时候其实还应该把经纬度置0，自己去实现
			mLocClient.stop();
			if (boo) {
				getweb();
			}
			else {
				Myappliction.show(JiLuActivity.this, "路程或时间过短，无法生成记录");
				finish();
			}
			break;
		default:
			break;
		}

	}

	private void getweb() {
		final ProgressDialog dialog = new ProgressDialog(JiLuActivity.this);
		dialog.show();
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		String li = licheng.getText().toString().trim();
		String time = timer.getText().toString().trim();

		params.addBodyParameter("mileage", li);
		params.addBodyParameter("alltime", time);

		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerJiLu";
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						dialog.dismiss();
						Myappliction.show(JiLuActivity.this, "联网失败");

					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						dialog.dismiss();
						String rString = arg0.result;
						Myappliction.show(JiLuActivity.this, rString);
						finish();
					}
				});

	}
	private void getLocation() {
		// 定位初始化
		mLocClient = new LocationClient(getApplicationContext());
		mLocClient.registerLocationListener(myListener);
		// 定位配置类
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型 ：经纬度类型
		option.setScanSpan(10000); // 设置扫描间隔，单位是ms，这里假设10s钟定位一次
		mLocClient.setLocOption(option);
		mLocClient.start(); // 开始定位
	}

	/**
	 * 定位SDK监听函数
	 */
	class MyLocationListenner2 implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// 获取用户当前经纬度
			latitude = location.getLatitude();// 纬度
			longitude = location.getLongitude();// 经度
			firstLatLng = new LatLng(latitude, longitude);

			if (i != 0) {
				// 获取两点之间距离，单位为m
				double distance = DistanceUtil.getDistance(firstLatLng,
						secondLatLng);
				NumberFormat nf = NumberFormat.getNumberInstance();
				nf.setMaximumFractionDigits(1);

				zong = zong + distance;
				double km = (zong / 1000);// 千米

				int m = (int) (zong % 1000);// 米
				if (km > 1) {
					licheng.setText(nf.format(km) + "");
					boo=true;
					dw.setText("千米");
				} else {
					licheng.setText(m + "");
				}
			} else {
				i++;
			}
			secondLatLng = new LatLng(latitude, longitude);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

}
