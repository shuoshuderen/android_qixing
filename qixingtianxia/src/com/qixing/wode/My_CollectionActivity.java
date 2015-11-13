package com.qixing.wode;

import java.lang.reflect.Type;
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
import com.qixing.gc.adapter.GcTongchengAdapter;
import com.qixing.gc.beans.BeanPost;
import com.qixing.guangchang.PostBeanActivity;
import com.qixing.my.utils.Myappliction;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class My_CollectionActivity extends Activity implements OnClickListener {
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerCollect";
	ProgressDialog dialog;
	ImageView backImageView;
	TextView backText, centertText, righText;

	List<BeanPost> list;
	ListView listView;
	GcTongchengAdapter adapter;

	@Override
	protected void onDestroy() {
		finish();
		list.clear();
		super.onDestroy();
	}

	@Override
	protected void onRestart() {
		adapter = null;
		initData();
		super.onRestart();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__collection);
		dialog = new ProgressDialog(My_CollectionActivity.this);
		initView();
		initData();
	}

	private void initData() {
		getweb(url);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				BeanPost beanPost = list.get(arg2);
				Intent intent = new Intent(My_CollectionActivity.this,
						PostBeanActivity.class);
				intent.putExtra("bean", beanPost);
				startActivity(intent);
			}
		});
	}

	private void getweb(String url2) {
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "4");
		params.addBodyParameter("userid", Myappliction.id);
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanPost>>() {
						}.getType();
						list = gson.fromJson(result, typeOfT);
						if (adapter == null) {
							adapter = new GcTongchengAdapter(
									My_CollectionActivity.this, list);
							listView.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(My_CollectionActivity.this, "访问网络失败");
					}
				});

	}

	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("我的收藏");
		mySetOnClickListener(backImageView, backText);
		listView = (ListView) findViewById(R.id.wo_collect_listview);
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
		default:
			break;
		}

	}
}
