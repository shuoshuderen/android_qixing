package com.qixing.my.utils;

import java.lang.reflect.Type;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
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
import com.qixing.my.beans.BeanUser;
import com.qixing.wode.My_detail_Activity;
import com.qixing.wode.UserInfoActivity;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class Myappliction extends Application{
	public static String id=null;
	public static double latitude;//纬度
	public static double longitude;//经度
	public static String city;//当前用户所定位的城市
	public static String ip="10.204.1.57";
	
	public static String photoaddress="http://10.204.1.57:8080/photo/";
	public static int width;
	public static BitmapUtils bitmapUtils;
	
	public static  void show(Context context, String text){
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	public static  void getuser(final String userid,final Context context){
		String url = "http://" + Myappliction.ip
				+ ":8080/ZQXweb/SerDengLu";
		
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("userid", userid);
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanUser>>() {
						}.getType();
						List<BeanUser> list = gson.fromJson(result, typeOfT);
					  BeanUser beanUser = null;
						for (int i = 0; i < list.size(); i++) {
							beanUser = list.get(i);
						}
						if (userid.equals(id)) {
							Intent intent = new Intent(context,
									My_detail_Activity.class);
							intent.putExtra("wode", beanUser);
							context.startActivity(intent);
						}else {
							Intent intent = new Intent(context,
									UserInfoActivity.class);
							intent.putExtra("wode", beanUser);
							context.startActivity(intent);
						}
						
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stu
					}
				});

	}
	@Override
	public void onCreate() {
		//application的onCreate只会调用一次，加之bitmapUtils是
		//static修饰的，表示所有对象共享
		bitmapUtils = new BitmapUtils(getApplicationContext());
		bitmapUtils.configDefaultLoadFailedImage(R.drawable.photofail);
		bitmapUtils.configDefaultLoadingImage(R.drawable.photowite);
		
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext());
		
		super.onCreate();
	}

}
