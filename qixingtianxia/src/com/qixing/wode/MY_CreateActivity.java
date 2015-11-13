package com.qixing.wode;

import java.io.File;

import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.utils.Myappliction;
import com.qixing.my.utils.ToastUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MY_CreateActivity extends Activity implements OnClickListener {
	ContentResolver resolver;
	private File tempFile;
	
	ImageView backImageView;
	TextView backText, centertText, righText;
	private static final String PHOTO_FILE_NAME = "headImage2.jpg";
	//用到的控件
	ImageView touxiang;
	EditText name,kouhao,jieshao,gonggao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my__create);
		initView();
	}
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		righText=(TextView) findViewById(R.id.top_right_text);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("创建车队");
		righText.setText("提交");
		
		touxiang=(ImageView) findViewById(R.id.tjtouxiang);
		name=(EditText) findViewById(R.id.cd_name);
		kouhao=(EditText) findViewById(R.id.kouhao_shuru);
		jieshao=(EditText) findViewById(R.id.jieshaoshuru);
		gonggao=(EditText) findViewById(R.id.gonggaoshuru);
		mySetOnClickListener(backImageView, backText,righText,touxiang);
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
			 String teamname=	name.getText().toString().trim();
			if (tempFile==null||teamname==null) {
				Myappliction.show(MY_CreateActivity.this, "请输入完整信息");
			}else {
				String url = "http://"+Myappliction.ip+":8080/ZQXweb/CreatTeam";
				downLoad(url);
				ToastUtil.show(MY_CreateActivity.this, "创建车队信息已提交");
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finish();
			}
			
			break;
		case R.id.tjtouxiang:
			//添加图片
			ShowPickDialog();
			break;
		default:
			break;
		}
		
	}

	private void ShowPickDialog() {
		new AlertDialog.Builder(this)
		.setTitle("设置头像...")
		.setNegativeButton("相册", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(
						MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
						"image/*");
				startActivityForResult(intent, 1);

			}
		})
		.setPositiveButton("拍照", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				dialog.dismiss();
				Intent intent = new Intent(
						MediaStore.ACTION_IMAGE_CAPTURE, null);
				// 下面这句指定调用相机拍照后的照片存储路的径
				if (hasSdcard()) {
					intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
							.fromFile(new File(Environment
									.getExternalStorageDirectory(),
									PHOTO_FILE_NAME)));
				}
				startActivityForResult(intent, 2);
			}
		}).show();
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// 直接从相册获取时
		case 1:
			if (data != null) {
				// 得到图片的全路径
				Uri uri = data.getData();
				tempFile = new File(getAbsoluteImagePath(uri));
				Bitmap bitmap = BitmapFactory
						.decodeFile(getAbsoluteImagePath(uri));
				touxiang.setImageBitmap(bitmap);
	
			}

			break;
		// 调用相机拍照时
		case 2:

			if (resultCode == RESULT_OK) {
				if (hasSdcard()) {
					String path = Environment.getExternalStorageDirectory()
							.getPath() + "/" + PHOTO_FILE_NAME;
					tempFile = new File(path);
					Bitmap bitmap = BitmapFactory.decodeFile(path);
					touxiang.setImageBitmap(bitmap);

				} else {
					Myappliction
							.show(MY_CreateActivity.this, "未找到存储卡，无法存储照片！");
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	private void downLoad(String url) {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", Myappliction.id);
	    String teamname=	name.getText().toString().trim();
		params.addBodyParameter("teamname", teamname);
		//口号
		String kohao=kouhao.getText().toString().trim();
		params.addBodyParameter("teamslogan", kohao+"");
		//介绍
		String jiesha=jieshao.getText().toString().trim();
		params.addBodyParameter("teamintroduce", jiesha+"");
		//公告
		String gg=gonggao.getText().toString().trim();
		params.addBodyParameter("teaminform", gg+"");
		params.addBodyParameter("photo", tempFile);

		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});
	}
		// 取到相册照片的绝对路径
		protected String getAbsoluteImagePath(Uri uri) {
			resolver = getContentResolver();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = resolver.query(uri,// 地址
					proj, // Which columns to return 自己建的数组，里边存放需查询的数据
					null, // WHERE clause; which rows to return (all rows)where语句
					null, // WHERE clause selection arguments (none)where中？占位符组成的数组
					null); // Order-by clause (ascending by name)排序
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		}

	private boolean hasSdcard() {
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}
}
