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
import com.qixing.denglu.LoginActivity;
import com.qixing.my.beans.BeanUser;
import com.qixing.my.utils.FragmentUtils;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class WoDeFragment extends Fragment implements OnClickListener {
	Context mcontext;

	Editor editor;
	SharedPreferences preferences;

	// 头像
	ImageView touxiang;
	TextView yonghuming;
	// 获取用户信息建立的对象
	BeanUser bUser;
	LinearLayout ll_unlogin;
	LinearLayout ll_logined;
	LinearLayout ll_care;
	LinearLayout ll_collect;
	LinearLayout ll_record;
	LinearLayout ll_create;
	LinearLayout ll_more;
	LinearLayout ll_cancleLogin;
	private View personalview;

	@SuppressWarnings("static-access")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		personalview = inflater.inflate(R.layout.wode, null);
		mcontext = personalview.getContext();

		preferences = mcontext.getSharedPreferences("userinfo",
				mcontext.MODE_PRIVATE);
		editor = preferences.edit();
		
		initView();
		intData();
		refreshView();

		return personalview;
	}

	/*@Override
	public void onResume() {
		
		super.onResume();
	}*/

	private void intData() {
		if (Myappliction.id != null) {
			String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerDengLu";
			getweb(url);
		}

	}

	private void getweb(String url) {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("userid", Myappliction.id);

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
						for (int i = 0; i < list.size(); i++) {
							bUser = list.get(i);
							yonghuming.setText(bUser.getUsername());
							String photoaddress = Myappliction.photoaddress
									+ bUser.getUserimg();

							Myappliction.bitmapUtils.display(touxiang,
									photoaddress);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});

	}

	private void initView() {
		// TODO Auto-generated method stu
		ll_unlogin = (LinearLayout) personalview.findViewById(R.id.unlogin);
		ll_logined = (LinearLayout) personalview.findViewById(R.id.logined);
		ll_care = (LinearLayout) personalview.findViewById(R.id.my_ll_care);
		ll_collect = (LinearLayout) personalview
				.findViewById(R.id.my_ll_collect);
		ll_record = (LinearLayout) personalview.findViewById(R.id.my_ll_record);
		ll_create = (LinearLayout) personalview.findViewById(R.id.my_ll_create);
		ll_more = (LinearLayout) personalview.findViewById(R.id.my_ll_more);
		ll_cancleLogin = (LinearLayout) personalview
				.findViewById(R.id.my_ll_out);
		mySetOnClickListener(ll_unlogin, ll_cancleLogin, ll_care, ll_collect,
				ll_create, ll_logined, ll_more, ll_record);
		// CircleImageView
		touxiang = (ImageView) personalview.findViewById(R.id.my_userimage);
		yonghuming = (TextView) personalview.findViewById(R.id.my_userText);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.unlogin:

			FragmentUtils.startActivity(this, LoginActivity.class);
			getActivity().finish();
			break;
		case R.id.logined:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, bUser,
						My_detail_Activity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;
		case R.id.my_ll_care:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, My_careActivity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;
		case R.id.my_ll_collect:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, My_CollectionActivity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;

		case R.id.my_ll_record:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, My_RecordActivity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;
		case R.id.my_ll_create:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, My_CheDuiActivity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;
		case R.id.my_ll_more:
			if (Myappliction.id != null) {
				FragmentUtils.startActivity(this, My_MoreActivity.class);
			} else {
				Myappliction.show(mcontext, "请先登录");
			}

			break;

		case R.id.my_ll_out:
			editor.remove("userId");
			editor.commit();
			getActivity().finish();
			getActivity().finishAffinity();
			// mcontext.geta

			break;

		default:
			break;
		}

	}

	private void mySetOnClickListener(View... v) {
		for (View view : v) {
			view.setOnClickListener(this);
		}
	}

	private void refreshView() {
		if (Myappliction.id != null) {// 已登录
			ll_logined.setVisibility(View.VISIBLE);// 登录界面
			ll_cancleLogin.setVisibility(View.VISIBLE);// 退出界面
			ll_unlogin.setVisibility(View.GONE);
		} else {
			ll_unlogin.setVisibility(View.VISIBLE);// 未登录界面
			ll_logined.setVisibility(View.GONE);// 登录界面
			ll_cancleLogin.setVisibility(View.GONE);// 退出界面
		}

	}
}
