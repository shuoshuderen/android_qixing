package com.qixing.wode;

import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.beans.BeanUser;
import com.qixing.my.utils.Myappliction;
import com.qixing.my.views.CircleImageView;
import com.qixing.util.MyDialog;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserInfoActivity extends Activity implements OnClickListener {

	ImageView backImageView;
	TextView backText, centertText, righText;

	// 头像
	ImageView touxinag;
	// 信息框
	TextView username, usid, gen, age, address, licheng, time;
	// 从上个界面传来的信息
		BeanUser beanUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		initView();
		initData();
	}
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	private void initView() {
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("个人资料");
		righText = (TextView) findViewById(R.id.top_right_text);
		righText.setText("关注");
		mySetOnClickListener(backImageView, backText,righText);
		
		
		touxinag = (CircleImageView) findViewById(R.id.userinfo_image);
		username = (TextView) findViewById(R.id.userinfo_name);
		usid = (TextView) findViewById(R.id.userinfo_phone);
		gen = (TextView) findViewById(R.id.userinfo_xingbie);
		age = (TextView) findViewById(R.id.userinfo_age);
		address = (TextView) findViewById(R.id.userinfo_address);
		licheng = (TextView) findViewById(R.id.userinfo_licheng);
		time = (TextView) findViewById(R.id.userinfo_time);
		
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
		case R.id.top_right_text:
		String  gz=	righText.getText().toString().trim();
		Log.e("关注", gz);
			if (gz.equals("关注")) {
				//还未关注
				RequestParams params=new RequestParams();
				params.addBodyParameter("flg","3");
				params.addBodyParameter("userid",Myappliction.id);
				params.addBodyParameter("friendid",beanUser.getUserid());
				getweb(params);
			}else {
				RequestParams params=new RequestParams();
				params.addBodyParameter("flg","4");
				params.addBodyParameter("userid",Myappliction.id);
				params.addBodyParameter("friendid",beanUser.getUserid());
				getweb(params);
			}
			break;
		default:
			break;
		}
	}


	private void initData() {
		Intent intent = getIntent();
		beanUser = (BeanUser) intent.getSerializableExtra("wode");
		
		RequestParams params=new RequestParams();
		params.addBodyParameter("flg","2");
		params.addBodyParameter("userid",Myappliction.id);
		params.addBodyParameter("friendid",beanUser.getUserid());
		getweb(params);
		username.setText(beanUser.getUsername());
		usid.setText(beanUser.getUserid());
		gen.setText(beanUser.getGender());
		age.setText(beanUser.getAge() + "岁");
		address.setText(beanUser.getAddress());
		licheng.setText(beanUser.getTotaldistance() + "公里");
		time.setText(beanUser.getTotaltime() + "小时");
		Myappliction.bitmapUtils.display(touxinag, Myappliction.photoaddress
				+ beanUser.getUserimg());
	}
	private void getweb(RequestParams params) {
		final MyDialog dialog=	MyDialog.show(UserInfoActivity.this, "加载中...请等待", true, null);
		HttpUtils httpUtils1 = new HttpUtils();
		
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerFriend";
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						if (result.equals("true")) {
							righText.setText("已关注");
						} else if (result.equals("关注成功")) {
							righText.setText("已关注");
							Myappliction.show(UserInfoActivity.this, result);
						} else if (result.equals("取消关注")) {
							righText.setText("关注");
							Myappliction.show(UserInfoActivity.this, result);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(UserInfoActivity.this, "访问网络失败");
					}
				});
	}
}
