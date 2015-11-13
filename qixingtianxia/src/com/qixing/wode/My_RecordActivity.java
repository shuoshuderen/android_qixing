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
import com.qixing.adapter.JiLuAdapter;
import com.qixing.bean.BeanJiLu;
import com.qixing.my.utils.Myappliction; 

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class My_RecordActivity extends Activity implements OnClickListener {
	ListView viListView;
	JiLuAdapter adapter;

	List<BeanJiLu> list = new ArrayList<BeanJiLu>();
	ProgressDialog dialog;
	ImageView backImageView;
	TextView backText, centertText, righText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__record);
		dialog = new ProgressDialog(My_RecordActivity.this);
		initView();
		iitData();
	}

	private void iitData() {
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("userid", Myappliction.id);
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerJiLu";
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanJiLu>>() {
						}.getType();
						list = gson.fromJson(result, typeOfT);

						if (adapter == null) {
							adapter = new JiLuAdapter(My_RecordActivity.this,
									list);
							viListView.setAdapter(adapter);
						} else {
							adapter.notifyDataSetChanged();
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(My_RecordActivity.this, "访问网络失败");
					}
				});

	}

	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("骑行记录");
		righText = (TextView) findViewById(R.id.top_right_text);
		righText.setText("新的骑行");
		mySetOnClickListener(backImageView, backText, righText);

		viListView = (ListView) findViewById(R.id.my_jilu);
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
		case R.id.top_right_text:
			Intent intent = new Intent(My_RecordActivity.this,
					JiLuActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
