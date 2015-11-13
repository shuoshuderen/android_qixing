package com.qixing.wode;

import java.util.Calendar;
import java.util.Date;

import com.example.qixingtianxia.MainActivity;
import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.beans.BeanUser;
import com.qixing.my.beans.StringUtils;
import com.qixing.my.utils.ActivityUtils;
import com.qixing.my.utils.KeyBoardUtils;
import com.qixing.my.utils.Myappliction;
import com.qixing.my.views.ArrayWheelAdapter;
import com.qixing.my.views.CustomDatePicker;
import com.qixing.my.views.MyEditText;
import com.qixing.my.views.OnWheelChangedListener;
import com.qixing.my.views.WheelView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.Toast;

public class My_Edit_detail_Activity extends CascadeCityActivity implements
		OnClickListener, OnWheelChangedListener {

	My_detail_Activity mContext;
	ImageView backImageView;
	TextView backText, centertText, righText;
	MyEditText name;
	TextView sexchoose;
	TextView agechoose;
	TextView addresschoose;
	WheelView proWheelView;
	WheelView cityWheelView;
	WheelView townwWheelView;
	Dialog dialog;
	private String maddress;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__edit_detail_);
		initView();
		initdatashuju();
	}

	private void initdatashuju() {
		Intent intent = getIntent();
		BeanUser beanUser = (BeanUser) intent.getSerializableExtra("xiangqing");
		name.setText(beanUser.getUsername());
		sexchoose.setText(beanUser.getGender());
		agechoose.setText(beanUser.getAge() + "");
		addresschoose.setText(beanUser.getAddress());
	}

	@SuppressLint("HandlerLeak")
	final Handler handler = new Handler() {
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			Calendar year = (Calendar) msg.obj;
			switch (msg.what) {
			case 1:
				String age = calculateDatePoor(year);
				agechoose.setText(age);
				break;
			default:
				break;
			}

		};
	};

	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		// backText.setText("取消");
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("编辑资料");
		righText = (TextView) findViewById(R.id.top_right_text);
		righText.setText("保存");
		name = (MyEditText) findViewById(R.id.name);
		sexchoose = (TextView) findViewById(R.id.sex);
		agechoose = (TextView) findViewById(R.id.age);
		addresschoose = (TextView) findViewById(R.id.address);
		sexchoose.setOnClickListener(this);
		agechoose.setOnClickListener(this);
		addresschoose.setOnClickListener(this);
		mySetOnClickListener(backImageView, backText, righText, name);
		setTextChangedListener(name, agechoose, sexchoose, addresschoose);
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
		case R.id.top_right_text:
			String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerDengLu";
			getweb(url);
			Toast.makeText(My_Edit_detail_Activity.this, "资料修改成功",
					Toast.LENGTH_SHORT).show();
			 ActivityUtils.startActivity(this,"4", MainActivity.class);
			//My_Edit_detail_Activity.this.finish();
			break;
		case R.id.name:
			KeyBoardUtils.openKeybord(name, My_Edit_detail_Activity.this);
			break;
		case R.id.sex:
			showSexDialog();
			break;
		case R.id.age:
			showAgeDialog();
			break;
		case R.id.address:
			showAddressDialog();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		return super.onKeyDown(keyCode, event);
	}

	private void getweb(String url) {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "4");
		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("username", name.getText().toString().trim());
		params.addBodyParameter("gen", sexchoose.getText().toString().trim());
		params.addBodyParameter("age", agechoose.getText().toString().trim());
		params.addBodyParameter("address", addresschoose.getText().toString()
				.trim());
	
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
					
						String result = responseInfo.result;
						Myappliction.show(My_Edit_detail_Activity.this, result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
					
					}
				});
	}

	@SuppressWarnings("deprecation")
	private void showAddressDialog() {
		// 地址对话框
		View view = getLayoutInflater().inflate(R.layout.dialog_address_choose,
				null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams w1 = window.getAttributes();
		w1.x = 0;
		w1.y = getWindowManager().getDefaultDisplay().getHeight();
		w1.width = ViewGroup.LayoutParams.MATCH_PARENT;
		w1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		proWheelView = (WheelView) view.findViewById(R.id.province);
		cityWheelView = (WheelView) view.findViewById(R.id.city);
		townwWheelView = (WheelView) view.findViewById(R.id.town);
		Button button = (Button) view.findViewById(R.id.address_yes);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				maddress = mCurrentProviceName + "-" + mCurrentCityName + "-"
						+ mCurrentDistrictName;
				
				if (!TextUtils.isEmpty(maddress)) {

					addresschoose.setText(maddress);

				}
				dialog.dismiss();
			}
		});
		initdata();
		setUpListener();
		setUpData();
		dialog.onWindowAttributesChanged(w1);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
	}

	@SuppressWarnings("deprecation")
	private void showAgeDialog() {
		// 年龄对话框
		View view = getLayoutInflater().inflate(R.layout.dialog_age_choose,
				null);
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		CustomDatePicker datePicker = (CustomDatePicker) view
				.findViewById(R.id.datepicker_age);
		datePicker.setMaxDate(new Date().getTime());
		final Calendar inintTime = Calendar.getInstance();
		inintTime.setTimeInMillis(System.currentTimeMillis());
		int year1 = inintTime.get(Calendar.YEAR);
		int month1 = inintTime.get(Calendar.MONTH);
		int day1 = inintTime.get(Calendar.DAY_OF_MONTH);
		datePicker.init(year1, month1 + 1, day1, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				inintTime.set(year, monthOfYear, dayOfMonth);
				Message message = new Message();
				message.what = 1;
				message.obj = inintTime;
				handler.sendMessage(message);

			}
		});
		view.findViewById(R.id.age_finish).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});
		Window window = dialog.getWindow();
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams w1 = window.getAttributes();
		w1.x = 0;
		w1.y = getWindowManager().getDefaultDisplay().getHeight();
		w1.width = ViewGroup.LayoutParams.MATCH_PARENT;
		w1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		dialog.onWindowAttributesChanged(w1);
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	@SuppressWarnings("deprecation")
	private void showSexDialog() {
		// TODO Auto-generated method stub
		View view = getLayoutInflater().inflate(R.layout.dialog_sex_choose,
				null);
		view.findViewById(R.id.b_man).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sexchoose.setText("男");
			}
		});
		view.findViewById(R.id.b_woman).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						sexchoose.setText("女");
					}
				});
		view.findViewById(R.id.b_cancle).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams w1 = window.getAttributes();
		w1.x = 0;
		w1.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下按钮保证按钮可以水平铺满
		w1.width = ViewGroup.LayoutParams.MATCH_PARENT;
		w1.height = ViewGroup.LayoutParams.WRAP_CONTENT;
		// 设置显示位置
		dialog.onWindowAttributesChanged(w1);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();

	}

	/**
	 * 
	 * @Title: calculateDatePoor
	 * 
	 * @Description: TODO(计算年龄)
	 * 
	 * @param birthyear
	 * 
	 * @return String 返回类型
	 */
	public static final String calculateDatePoor(Calendar birthyear) {
		int age = 0;
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(System.currentTimeMillis());
		final int nowYear = c.get(Calendar.YEAR);
		int nowMonth = c.get(Calendar.MONTH) + 1;
		int nowday = c.get(Calendar.DAY_OF_MONTH);
		int year = birthyear.get(Calendar.YEAR);
		int mounth = birthyear.get(Calendar.MONTH) + 1;
		int day = birthyear.get(Calendar.DAY_OF_MONTH);
		if (year >= nowYear)
			age = 0;
		else {
			if (year < nowYear) {
				age = nowYear - year;
				if (mounth < nowMonth)
					age = nowYear - year + 1;
				else if (mounth == nowMonth) {
					if (day <= nowday)
						age = nowYear - year + 1;
				}
			}
		}
	
		return String.valueOf(age);
	}

	private void initdata() {
		initProvinceDatas();

		if (!TextUtils.isEmpty(maddress)) {
			String[] address1 = StringUtils.convertStrToArry(maddress);
			// System.out.println("所在地"+address);
			pPosition = StringUtils.getPosition(address1[0], mProvinceDatas);
			cPosition = StringUtils.getPosition(address1[1],
					mCitisDatasMap.get(address1[0]));
			aPosition = StringUtils.getPosition(address1[2],
					mDistrictDatasMap.get(address1[1]));
			// System.out.println("所在地"+pPosition+"--->"+cPosition+"--->"+cPosition);

		}
	}

	private void setUpListener() {
		// 添加change事件
		proWheelView.addChangingListener(this);
		// 添加change事件
		cityWheelView.addChangingListener(this);
		// 添加change事件
		townwWheelView.addChangingListener(this);
	}

	private void setUpData() {

		proWheelView.setViewAdapter(new ArrayWheelAdapter<String>(
				My_Edit_detail_Activity.this, mProvinceDatas));
		// 设置可见条目数量
		proWheelView.setVisibleItems(7);
		cityWheelView.setVisibleItems(7);
		townwWheelView.setVisibleItems(7);
		proWheelView.setCurrentItem(pPosition);
		updateCities(cPosition, aPosition);
	}

	/**
	 * 根据当前的省，更新市WheelView的信息
	 */
	private void updateCities(int cPosition, int aPosition) {
		int pCurrent = proWheelView.getCurrentItem();
		mCurrentProviceName = mProvinceDatas[pCurrent];

		String[] cities = mCitisDatasMap.get(mCurrentProviceName);
		if (cities == null) {
			cities = new String[] { "" };
		}
		cityWheelView.setViewAdapter(new ArrayWheelAdapter<String>(
				My_Edit_detail_Activity.this, cities));
		cityWheelView.setCurrentItem(cPosition);
		updateAreas(aPosition);
	}

	/**
	 * 根据当前的市，更新区WheelView的信息
	 */
	private void updateAreas(int aPosition) {
		int pCurrent = cityWheelView.getCurrentItem();
		mCurrentCityName = mCitisDatasMap.get(mCurrentProviceName)[pCurrent];
		
		String[] areas = mDistrictDatasMap.get(mCurrentCityName);
		if (areas == null) {
			areas = new String[] { "" };
		}
		townwWheelView.setViewAdapter(new ArrayWheelAdapter<String>(
				My_Edit_detail_Activity.this, areas));
		townwWheelView.setCurrentItem(aPosition);
		int dCurrent = townwWheelView.getCurrentItem();
		mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[dCurrent];

	}

	@Override
	public void onChanged(WheelView wheel, int oldValue, int newValue) {
		// TODO Auto-generated method stub
		if (wheel == proWheelView) {
			updateCities(0, 0);
		} else if (wheel == cityWheelView) {
			updateAreas(0);
		} else if (wheel == townwWheelView) {
			int n = townwWheelView.getCurrentItem();
			mCurrentDistrictName = mDistrictDatasMap.get(mCurrentCityName)[n];
			mCurrentZipCode = mZipcodeDatasMap.get(mCurrentDistrictName);
		}
	}

	private void setTextChangedListener(TextView... v) {
		for (TextView textView : v) {
			textView.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub

				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub

				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub

				}
			});
		}

	}

}
