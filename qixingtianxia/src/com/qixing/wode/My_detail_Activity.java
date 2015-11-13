package com.qixing.wode;

import java.io.File;

import com.example.qixingtianxia.R;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import com.qixing.my.beans.BeanUser;
import com.qixing.my.utils.ActivityUtils;
import com.qixing.my.utils.Myappliction;
import com.qixing.my.views.CircleImageView;

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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class My_detail_Activity extends Activity implements OnClickListener {
	// 获取图片地址
	ContentResolver resolver;

	ImageView backImageView;
	TextView backText, centertText, righText;
	// 头像
	ImageView touxinag;
	// 信息框
	TextView username, usid, gen, age, address, licheng, time;
	// 从上个界面传来的信息
	BeanUser beanUser;
	LinearLayout imageLayout;
	CircleImageView imageView;
	// private My_detail_Activity mContext;
	/* 头像名称 */
	private static final String PHOTO_FILE_NAME = "headImage.jpg";
	private File tempFile;
	// private Bitmap bitmap;
	HttpUtils httpUtils;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_detail_);
		initView();
		initData();

	}
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	private void initData() {
		Intent intent = getIntent();
		beanUser = (BeanUser) intent.getSerializableExtra("wode");
		username.setText(beanUser.getUsername());
		usid.setText(beanUser.getUserid());
		gen.setText(beanUser.getGender());
		age.setText(beanUser.getAge() + "岁");
		address.setText(beanUser.getAddress());
		licheng.setText(beanUser.getTotaldistance() + "公里");
		time.setText(beanUser.getTotaltime() + "小时");
		Myappliction.bitmapUtils.display(imageView, Myappliction.photoaddress
				+ beanUser.getUserimg());
	}

	private void downLoad(String url) {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("userid", Myappliction.id);
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

	private void initView() {
		// TODO Auto-generated method stub
		backImageView = (ImageView) findViewById(R.id.backImage);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("详细资料");
		righText = (TextView) findViewById(R.id.top_right_text);
		righText.setText("编辑资料");
		imageLayout = (LinearLayout) findViewById(R.id.llimage);
		imageView = (CircleImageView) findViewById(R.id.user_default_image);
		mySetOnClickListener(backImageView, backText, righText, imageLayout);
		username = (TextView) findViewById(R.id.tv_username_detail);
		usid = (TextView) findViewById(R.id.tv_userid_detail);
		gen = (TextView) findViewById(R.id.tv_gean_detail);
		age = (TextView) findViewById(R.id.tv_age_detail);
		address = (TextView) findViewById(R.id.tv_address_detail);
		licheng = (TextView) findViewById(R.id.tv_licheng_detail);
		time = (TextView) findViewById(R.id.tv_time_detail);
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
			ActivityUtils.startActivity(this, beanUser,
					My_Edit_detail_Activity.class);
			My_detail_Activity.this.finish();
			break;
		case R.id.llimage:
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
				imageView.setImageBitmap(bitmap);
				String url = "http://" + Myappliction.ip
						+ ":8080/ZQXweb/Touxiangphoto";
				downLoad(url);
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
					imageView.setImageBitmap(bitmap);
					String url = "http://" + Myappliction.ip
							+ ":8080/ZQXweb/Touxiangphoto";
					downLoad(url);
				} else {
					Myappliction
							.show(My_detail_Activity.this, "未找到存储卡，无法存储照片！");
				}
			}
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
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
