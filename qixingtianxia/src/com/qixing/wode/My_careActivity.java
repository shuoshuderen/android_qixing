package com.qixing.wode;

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
import com.qixing.adapter.MyCareAdapter;
import com.qixing.my.beans.BeanUser;
import com.qixing.my.utils.Myappliction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class My_careActivity extends Activity implements OnClickListener {

	ImageView backImageView, searchImageView;
	TextView backText, centertText, righText;
	 ProgressDialog dialog;
	MyCareAdapter careAdapter;
	ListView listView;
	ImageView imagesousuo;
	EditText editsousuo;
	TextView yincang;
	List<BeanUser> list = new ArrayList<BeanUser>();
	LinearLayout searchLayout;
	LinearLayout layout;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_care);
		initView();
		initData();
	}
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		careAdapter = null;
		initData();
	}
	@Override
	protected void onDestroy() {
		list.clear();
		finish();
		super.onDestroy();
	}
	private void initData() {
		getuser(Myappliction.id);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BeanUser beanUser=list.get(arg2);
				Myappliction.getuser(beanUser.getUserid(), My_careActivity.this);
			}
		});
	}

	private void initView() {
	dialog=new ProgressDialog(My_careActivity.this);
		imagesousuo=(ImageView) findViewById(R.id.case_ren_sousuo);
		editsousuo=(EditText) findViewById(R.id.case_ren_shuru);
		yincang=(TextView) findViewById(R.id.care_center_text);
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		searchImageView = (ImageView) findViewById(R.id.top_right_search);
		searchImageView.setImageResource(R.drawable.search);
		searchLayout = (LinearLayout) findViewById(R.id.ll_search);
		backText = (TextView) findViewById(R.id.backText);
		righText = (TextView) findViewById(R.id.top_right_text);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("我的关注");
		mySetOnClickListener(backImageView, backText, searchImageView, righText,imagesousuo);

		listView = (ListView) findViewById(R.id.wo_care_listview);
		
	}

	private void mySetOnClickListener(View... v) {
		for (View view : v) {
			view.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.backImage:
			finish();
			break;
		case R.id.backText:
			finish();
			break;
		case R.id.top_right_search:
			searchImageView.setVisibility(View.GONE);
			righText.setVisibility(View.VISIBLE);
			righText.setText("取消");
			searchLayout.setVisibility(View.VISIBLE);
			break;
		case R.id.top_right_text:
			searchLayout.setVisibility(View.GONE);
			righText.setVisibility(View.GONE);
			searchImageView.setVisibility(View.VISIBLE);
			break;
		case R.id.case_ren_sousuo:
			String name=editsousuo.getText().toString().trim();
			if (name.equals("")) {
				Myappliction.show(My_careActivity.this,"请输入要搜索人的姓名" );
				
			}else {
				getUserByName(name);
			}
			break;
		default:
			break;
		}
	}

	private void getUserByName(String name) {
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerDengLu";
		
		dialog.show();
		
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "5");
		params.addBodyParameter("username", name);
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanUser>>() {
						}.getType();
					 list = gson.fromJson(result, typeOfT);
					 yincang.setVisibility(View.GONE);
					 if (list.isEmpty()) {
						 yincang.setVisibility(View.VISIBLE);
					}
						 {
							 careAdapter = new MyCareAdapter(
									My_careActivity.this, list);
							listView.setAdapter(careAdapter);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(My_careActivity.this, "访问网络失败");
					}
				});
	}

	public void getuser(String userid) {
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerFriend";
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("userid", userid);
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanUser>>() {
						}.getType();
					 list = gson.fromJson(result, typeOfT);
						if (careAdapter == null) {
							careAdapter = new MyCareAdapter(
									My_careActivity.this, list);
							listView.setAdapter(careAdapter);
						} else {
							careAdapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(My_careActivity.this, "访问网络失败");
					}
				});

	}
}
