package com.qixing.fujin;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.qixingtianxia.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.bean.BeanHelp;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class QiuZhuFragment extends Fragment{
	Context context;
	View view;
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	@SuppressWarnings("unused")
	private LocationMode mCurrentMode;
	BitmapDescriptor mCurrentMarker;
	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Marker mMarkerA;
	List<BeanHelp> list=new ArrayList<BeanHelp>();

	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bdA = BitmapDescriptorFactory
			.fromResource(R.drawable.icon_gcoding);
	
	Button help;
	boolean isFirstLoc = true;// 是否首次定位
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fj_qiuzhu, null);
		context = view.getContext();
		
		initMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		initOverlay();//加载marker图标,并从web端获取数据
		MarkerClick();
		initView();
		return view;
	}
	private void MarkerClick() {
		// TODO Auto-generated method stub
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			
			@Override
			public boolean onMarkerClick(Marker arg0) {
				// TODO Auto-generated method stub
				Bundle bundle2=arg0.getExtraInfo();
				
				if (bundle2!=null) {
					Intent intent=new Intent(getActivity(),HelpActivity.class);
					intent.putExtras(bundle2);
					startActivity(intent);
				}
				
				return false;
			}
		});
	}
	private void initView() {
		// TODO Auto-generated method stub
		help=(Button) view.findViewById(R.id.button_help);
		help.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {//求助书写页面
				Intent intent=new Intent(getActivity(),SeekHelpActivity.class);
				startActivity(intent);
			}
		});
	}
	private void initMap() {
		mMapView = (MapView) view.findViewById(R.id.bmapView);
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 定位初始化
		mLocClient = new LocationClient(context);
		mLocClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(10000);
		mLocClient.setLocOption(option);
		mLocClient.start();

	}
	
	/**
	 * 定位SDK监听函数
	 */
	public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
  
        	if (location == null || mMapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);  
           
            if (isFirstLoc) {
//                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
               // addMarker();
            }
        }

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}
	//按钮  我要求助 的单击事件
	
		@Override
		public void onPause() {
			mMapView.onPause();
			super.onPause();
		}

		@Override
		public void onResume() {
			mMapView.onResume();
			super.onResume();
		}

		@Override
		public void onDestroy() {
			// 退出时销毁定位
			mLocClient.stop();
			// 关闭定位图层
			mBaiduMap.setMyLocationEnabled(false);
			mMapView.onDestroy();
			mMapView = null;
			super.onDestroy();
			bdA.recycle();
		}
		public void initOverlay() {
			//加载marker图标,并从web端获取数据
			HttpUtils httpUtils=new HttpUtils();
			RequestParams params = new RequestParams();
			params.addBodyParameter("flg", "1");
			params.addBodyParameter("helpaddress", Myappliction.city);
			String url="http://" + Myappliction.ip +":8080/ZQXweb/SerHelp";
			httpUtils.send(HttpMethod.POST,
					url, params, 
					new RequestCallBack<String>() {


						@Override
						public void onFailure(HttpException arg0, String arg1) {
							// TODO Auto-generated method stub
							
						}

						@Override
						public void onSuccess(ResponseInfo<String> arg0) {
							// TODO Auto-generated method stub
							//Log.e("求助成功", "成功");
							
							String result = arg0.result;
							
							Gson gson = new Gson();
							Type typeOfSrc = new TypeToken<List<BeanHelp>>(){}.getType();
							
							list = gson.fromJson(result, typeOfSrc);
							
							BeanHelp beanHelp=null;
							for (int i = 0; i < list.size(); i++) {
								beanHelp=list.get(i);
								
								double lan=beanHelp.getHelpLatitude();
								double lon=beanHelp.getHelpLongitude();
								
								LatLng llA=new LatLng(lan, lon);
								Bundle extraInfo=new Bundle();
						        extraInfo.putSerializable("help", beanHelp);
								OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
										.zIndex(9);
								mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
								mMarkerA.setExtraInfo(extraInfo);
							}
						}
					});
		}
}
