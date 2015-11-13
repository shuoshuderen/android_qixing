package com.qixing.fujin;


import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.bean.BeanTeam;
import com.qixing.my.utils.Myappliction;
import com.qixing.util.MyDialog;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;

public class JtTeamActivity extends Activity implements OnClickListener {
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerTeamPeople";
	// 顶部栏
	ImageView backImageView;
	TextView backText, centertText, righText;
	// 接收到的数据
	BeanTeam beanTeam;
	// 控件
	LinearLayout dz, dy, qunliaoLayout;
	ImageView imageView;
	TextView name, id, kouhao, jieshao, duizhang, duiyuan, licheng, gonggao,
			didian, time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jt_team);
		initView();
		initData();
	}

	private void initView() {
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("车队详情");
		righText = (TextView) findViewById(R.id.top_right_text);

		// 控件
		imageView = (ImageView) findViewById(R.id.cd_touxiang_jt);
		name = (TextView) findViewById(R.id.cd_name);
		id = (TextView) findViewById(R.id.cd_ID);
		kouhao = (TextView) findViewById(R.id.cd_kouhao);
		jieshao = (TextView) findViewById(R.id.cd_jieshao);
		duizhang = (TextView) findViewById(R.id.cd_duizhang);
		duiyuan = (TextView) findViewById(R.id.cd_duiyuan);
		licheng = (TextView) findViewById(R.id.cd_licheng);
		gonggao = (TextView) findViewById(R.id.cd_gonggao);
		didian = (TextView) findViewById(R.id.cd_address);
		time = (TextView) findViewById(R.id.CD_time);
		dz = (LinearLayout) findViewById(R.id.cd_duizhang_lin);
		dy = (LinearLayout) findViewById(R.id.cd_duiyuan_lin);
		qunliaoLayout = (LinearLayout) findViewById(R.id.cd_qunliao_lin);

		mySetOnClickListener(backImageView, backText, dz, dy, qunliaoLayout);
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
		case R.id.cd_duizhang_lin:// 队长
			Myappliction.getuser(beanTeam.getUserid(), JtTeamActivity.this);
			break;
		case R.id.cd_duiyuan_lin:// 队员
			Intent intent = new Intent(JtTeamActivity.this,
					Team_PeopleActivity.class);
			intent.putExtra("teamid", beanTeam.getTeamid() + "");
			startActivity(intent);
			break;
		case R.id.cd_qunliao_lin:// 群聊
			Myappliction.show(JtTeamActivity.this, "还未使用");
			break;

		default:
			break;
		}
	}

	private void initData() {
		Intent intent = getIntent();
		beanTeam = (BeanTeam) intent.getSerializableExtra("team");

		if (Myappliction.id.equals(beanTeam.getUserid())) {
			righText.setText("删除车队");
			righText.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					getwebshanchu();

				}

			});
		} else {
			// 判断是否加入该车队

			righText.setText("加入车队");
			getwebcheck();
			righText.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					if (righText.getText().toString().equals("加入车队")) {
						gerwebAdd();
					} else {
						getwebshanSub();
					}

				}

			});
		}

		String uri = Myappliction.photoaddress + beanTeam.getTeamimage();
		Myappliction.bitmapUtils.display(imageView, uri);
		name.setText(beanTeam.getTeamname());
		id.setText("车队ID ：   " + beanTeam.getTeamid());
		kouhao.setText(beanTeam.getTeamslogan());
		jieshao.setText(beanTeam.getTeamintroduce());
		duizhang.setText(beanTeam.getUsername());
		duiyuan.setText(beanTeam.getTeamnumber() + "人");
		licheng.setText(beanTeam.getTeamdistance() + "公里");
		gonggao.setText(beanTeam.getTeaminform());
		didian.setText(beanTeam.getTeamaddress());
		time.setText(beanTeam.getTeamtime());
	}

	// 判断
	private void getwebcheck() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("teamid", beanTeam.getTeamid() + "");
		final MyDialog dialog=	MyDialog.show(JtTeamActivity.this, "加载中...请等待", true, null);
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						if (result.equals("true")) {
							righText.setText("退出车队");
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(JtTeamActivity.this, "访问网络失败");
					}
				});

	}

	// 删除车队
	private void getwebshanchu() {
		final MyDialog dialog=	MyDialog.show(JtTeamActivity.this, "加载中...请等待", true, null);
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("teamid", beanTeam.getTeamid() + "");
		String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerTeam";
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						if (result.equals("解散成功")) {
							Myappliction.show(JtTeamActivity.this, result);
							finish();
						} else {
							Myappliction.show(JtTeamActivity.this, result);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(JtTeamActivity.this, "访问网络失败");
					}
				});

	}

	// 退出车队
	private void getwebshanSub() {
		final MyDialog dialog=	MyDialog.show(JtTeamActivity.this, "加载中...请等待", true, null);
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "4");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("teamid", beanTeam.getTeamid() + "");

		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;
						Myappliction.show(JtTeamActivity.this, result);
						righText.setText("加入车队");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						Myappliction.show(JtTeamActivity.this, "访问网络失败");
					}
				});

	}

	// 加入车队
	private void gerwebAdd() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("teamid", beanTeam.getTeamid() + "");

		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String result = responseInfo.result;
						Myappliction.show(JtTeamActivity.this, result);
						righText.setText("退出车队");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						Myappliction.show(JtTeamActivity.this, "访问网络失败");
					}
				});

	}
}
