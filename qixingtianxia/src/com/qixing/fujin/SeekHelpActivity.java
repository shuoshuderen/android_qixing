package com.qixing.fujin;

import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.utils.Myappliction;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.ProgressDialog;

public class SeekHelpActivity extends Activity implements OnClickListener {

	// 顶部栏
		ImageView backImageView;
		TextView backText, centertText, righText;
	
		
		EditText name,neirong,address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seek_help);
		initView();

	}
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	private void initView() {
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("我要求助");
		righText=(TextView) findViewById(R.id.top_right_text);
		righText.setText("发布");
		// 监听事件
		mySetOnClickListener(backImageView, backText,righText);
		
		name=(EditText) findViewById(R.id.seek_name);
		neirong=(EditText) findViewById(R.id.seek_content);
		address=(EditText) findViewById(R.id.seek_address);


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
			String qiuzhuming=name.getText().toString().trim();
			if (qiuzhuming==null) {
				Myappliction.show(SeekHelpActivity.this, "信息不能为空");
			}else {
				getweb();
			}
			
			break;
		default:
			break;
		}
	}

	private void getweb() {
		/*final Dialog dialog=new Dialog(SeekHelpActivity.this);*/
		final ProgressDialog dialog=new ProgressDialog(SeekHelpActivity.this);
		dialog.show();
		HttpUtils httpUtils=new HttpUtils();
		RequestParams params=new RequestParams();
		params.addBodyParameter("flg","2");
		params.addBodyParameter("userid",Myappliction.id);
		String nam=name.getText().toString().trim();
		String nei=neirong.getText().toString().trim();
		String add=address.getText().toString().trim();
		params.addBodyParameter("name",nam);
		params.addBodyParameter("neirong",nei);
		params.addBodyParameter("address",add);
		
		params.addBodyParameter("long",Myappliction.longitude+"");
		
		params.addBodyParameter("la",Myappliction.latitude+"");
		
		String url="http://" + Myappliction.ip +":8080/ZQXweb/SerHelp";
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
			dialog.dismiss();
			Myappliction.show(SeekHelpActivity.this, "联网失败");
				
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				dialog.dismiss();
				String rString=arg0.result;
				Myappliction.show(SeekHelpActivity.this,rString );
				finish();
			}
		});
		
	}

}
