package com.qixing.guangchang;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.qixingtianxia.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.qixing.my.utils.Myappliction;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.SimpleAdapter.ViewBinder;

public class GcAddActivity extends Activity implements OnClickListener {
	private GridView gridView1;
	private final int IMAGE_OPEN = 1; // 打开图片标记
	private String pathImage; // 选择图片路径
	private Bitmap bmp; // 导入临时图片
	private ArrayList<HashMap<String, Object>> imageItem;
	private SimpleAdapter simpleAdapter; // 适配器
	List<String> list = new ArrayList<String>();
	EditText namEditText, neirEditText;
	TextView textView, fanhui, numbertext;
	private int selectstart, selectend;
	private CharSequence temp;
	String titname, neirong, lei = "灌水";
	RadioGroup radioGroup;
	@Override
	protected void onDestroy() {
		finish();
		super.onDestroy();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gcadd);

		initView();
		initdata();
	}

	private void initdata() {
		textView = (TextView) findViewById(R.id.tv_publish);
		fanhui = (TextView) findViewById(R.id.tv_publish_cancel);
		numbertext = (TextView) findViewById(R.id.text_addtz_number);
		numbertext.setText("已输入0个字");
		textView.setOnClickListener(this);
		fanhui.setOnClickListener(this);
		neirEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				temp = arg0;

			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable editable) {
				int num = editable.length();
				numbertext.setText("还可输入" + (500 - num) + "字");
				selectstart = neirEditText.getSelectionStart();
				selectend = neirEditText.getSelectionEnd();
				if (temp.length() > num) {
					editable.delete(selectstart - 1, selectend);
					int temoselection = selectend;
					neirEditText.setText(editable);
					neirEditText.setSelection(temoselection);// 设置光标在最后
				}
			}
		});
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.tv_publish:
			Myappliction.show(GcAddActivity.this, "开始上传");
			String url = "http://10.204.1.57:8080/ZQXweb/MorePhotosc";
			upweb(url);
			
			break;
		case R.id.tv_publish_cancel:
			finish();
			break;

		default:
			break;
		}

	}

	private void initView() {
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup_tzclass);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				switch (arg1) {
				case R.id.radio0:
					lei = "攻略";
					break;
				case R.id.radio1:
					lei = "分享";
					break;
				case R.id.radio2:
					lei = "灌水";
					break;
				default:
					break;
				}
			}
		});
		namEditText = (EditText) findViewById(R.id.edtext_name_addtz);
		neirEditText = (EditText) findViewById(R.id.edtext_neirong_addtz);
		gridView1 = (GridView) findViewById(R.id.gridView_addtz);
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.create1);
		imageItem = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", bmp);
		imageItem.add(map);
		simpleAdapter = new SimpleAdapter(this, imageItem,
				R.layout.activity_gcaddd_item, new String[] { "itemImage" },
				new int[] { R.id.image_addtz_tb });
		/*
		 * HashMap载入bmp图片在GridView中不显示,但是如果载入资源ID能显示 如 map.put("itemImage",
		 * R.drawable.img); 解决方法: 1.自定义继承BaseAdapter实现 2.ViewBinder()接口实现 参考
		 * http://blog.csdn.net/admin_/article/details/7257901
		 */
		simpleAdapter.setViewBinder(new ViewBinder() {

			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		gridView1.setAdapter(simpleAdapter);
		/*
		 * 监听GridView点击事件 报错:该函数必须抽象方法 故需要手动导入import android.view.View;
		 */
		gridView1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (imageItem.size() == 4) { // 第一张为默认图片
					if (position == 0) {
						Myappliction.show(GcAddActivity.this, "图片数3张已满,不能添加");
					} else {
						dialog(position);
					}

				} else if (position == 0) { // 点击图片位置为+ 0对应0张图片
					Myappliction.show(GcAddActivity.this, "添加图片");
					// 选择图片
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, IMAGE_OPEN);
					// 通过onResume()刷新数据
				} else {
					dialog(position);
					Myappliction.show(GcAddActivity.this, "点击第" + (position)
							+ " 号图片");
				}

			}
		});

	}

	// 获取图片路径 响应startActivityForResult
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// 打开图片
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK && requestCode == IMAGE_OPEN) {
			Uri uri = data.getData();
			if (!TextUtils.isEmpty(uri.getAuthority())) {
				// 查询选择图片
				Cursor cursor = getContentResolver().query(uri,
						new String[] { MediaStore.Images.Media.DATA }, null,
						null, null);
				// 返回 没找到选择图片
				if (null == cursor) {
					return;
				}
				// 光标移动至开头 获取图片路径
				cursor.moveToFirst();
				pathImage = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
				list.add(pathImage);
			}
		} // end if 打开图片

	}

	// 刷新图片
	@Override
	protected void onResume() {
		super.onResume();
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("itemImage", addbmp);
			imageItem.add(map);
			simpleAdapter = new SimpleAdapter(this, imageItem,
					R.layout.activity_gcaddd_item,
					new String[] { "itemImage" },
					new int[] { R.id.image_addtz_tb });
			simpleAdapter.setViewBinder(new ViewBinder() {
				@Override
				public boolean setViewValue(View view, Object data,
						String textRepresentation) {
					// TODO Auto-generated method stub
					if (view instanceof ImageView && data instanceof Bitmap) {
						ImageView i = (ImageView) view;
						i.setImageBitmap((Bitmap) data);
						return true;
					}
					return false;
				}
			});
			gridView1.setAdapter(simpleAdapter);
			simpleAdapter.notifyDataSetChanged();
			// 刷新后释放防止手机休眠后自动添加
			pathImage = null;
		}
	}

	// 联网上传图片
	private void upweb(String url) {
		titname = namEditText.getText().toString().trim();
		neirong = neirEditText.getText().toString().trim();

		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();

		params.addBodyParameter("userid", Myappliction.id);
		params.addBodyParameter("postname", titname);
		params.addBodyParameter("postnr", neirong);
		params.addBodyParameter("lei", lei);
		for (int i = 0; i < list.size(); i++) {
			File file = new File(list.get(i));
			params.addBodyParameter(i + "", file);
		}
	
		httpUtils.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
					
						finish();
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					

					}
				});

	}

	/*
	 * Dialog对话框提示用户删除操作 position为删除图片位置
	 */
	protected void dialog(final int position) {
		AlertDialog.Builder builder = new Builder(GcAddActivity.this);
		builder.setMessage("确认移除已添加图片吗？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				imageItem.remove(position);
				list.remove(position - 1);
				simpleAdapter.notifyDataSetChanged();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

}
