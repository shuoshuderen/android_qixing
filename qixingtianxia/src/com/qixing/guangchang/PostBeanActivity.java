package com.qixing.guangchang;

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

import com.qixing.gc.adapter.PostImageAdapter;
import com.qixing.gc.adapter.PostListAdapter;
import com.qixing.gc.beans.BeanComments;
import com.qixing.gc.beans.BeanPost;
import com.qixing.gc.beans.BeanPostPhoto;
import com.qixing.gc.view.PostGridview;
import com.qixing.my.utils.Myappliction;
import com.qixing.util.CustomProgressDialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

public class PostBeanActivity extends Activity implements OnClickListener {
	int commid;
	int point;

	String url2 = "http://" + Myappliction.ip + ":8080/ZQXweb/SerZan";
	String url3 = "http://" + Myappliction.ip + ":8080/ZQXweb/SerPost";
	int sun = 0;
	// 判断是否赞过
	int number = 0;
	boolean boo = true;
	// 顶部栏
	ImageView backImageView, rightiImageView;
	TextView backText, centertText, righText;
	String url = "http://" + Myappliction.ip + ":8080/ZQXweb/SerCollect";
	// 判断是否收藏
	boolean b = true;
	TextView textView, textViewbt, zansum, pinglunsum;
	ImageView zanImageView, plimageImageView;
	EditText editText;
	Button button;
	ListView listView;
	// 显示图片适配器，屏幕宽
	PostGridview imaGridView;
	private int width;
	PostListAdapter adapter;
	PostImageAdapter imageAdapter;
	BeanPost beanPost;
	List<BeanPostPhoto> list = new ArrayList<BeanPostPhoto>();

	List<BeanComments> lComments = new ArrayList<BeanComments>();
	protected boolean num=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_bean);
		initView();
		initData();
	}

	private void initData() {
		// 接收从上个页面传来的数据
		Intent intent = getIntent();
		beanPost = (BeanPost) intent.getSerializableExtra("bean");
		// 判断是否收藏过这种帖子
		getwebcheckshoucang(url);
		// 判断是否赞过这个帖子
		getzanCheck();
		// 获取赞的数量
		getZannum();

		// 赋值
		textView.setText(beanPost.getPostaddress());
		textViewbt.setText(beanPost.getPostname());
		zansum.setText(beanPost.getPostpraise() + "");
		pinglunsum.setText(beanPost.getPlSum() + "");
		// 获取手机分辨率，这类写法只能放在activity中，其他地方搜索另外的方法
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		// list应为评论内容，从网上下载，现在是具体帖子中的图片集合
		list = beanPost.getList();
		imaGridView = (PostGridview) findViewById(R.id.post_gridview);
		List<String> lStrings = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			lStrings.add(list.get(i).getPhoto());
		}
		imageAdapter = new PostImageAdapter(lStrings, PostBeanActivity.this,
				imaGridView, width);
		imaGridView.setAdapter(imageAdapter);

		String urlpl = "http://" + Myappliction.ip + ":8080/ZQXweb/SerComments";
		getwebpinglun(urlpl);

	}

	private void initView() {

		// 修复了程序进入时GridView会滑动到顶端的小bug。
		ScrollView scrollView = (ScrollView) findViewById(R.id.post_scrollview);
		scrollView.requestChildFocus(imaGridView, null);
		// 顶部栏初始化和监听
		backImageView = (ImageView) findViewById(R.id.backImage);
		rightiImageView = (ImageView) findViewById(R.id.top_right_search);
		rightiImageView.setImageResource(R.drawable.shoucang);
		backText = (TextView) findViewById(R.id.backText);
		centertText = (TextView) findViewById(R.id.top_center_text);
		centertText.setText("帖子详情");

		// 内容和评论列表初始化
		textView = (TextView) findViewById(R.id.text_post_neirong);
		textViewbt = (TextView) findViewById(R.id.text_posttitle);
		listView = (ListView) findViewById(R.id.postdetail_listview);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				if (num) {
					arg2=arg2-1;
				}
				if (lComments.get(arg2).getUserid().equals(Myappliction.id)) {
					commid = lComments.get(arg2).getCommentsid();
					point = arg2;
					ShowPickDialog();
				}

			}
		});
		zanImageView = (ImageView) findViewById(R.id.post_image_zan);
		plimageImageView = (ImageView) findViewById(R.id.post_image_pinglun);
		zansum = (TextView) findViewById(R.id.post_text_zan);
		pinglunsum = (TextView) findViewById(R.id.post_text_pinlun);

		editText = (EditText) findViewById(R.id.post_edi_pl);
		button = (Button) findViewById(R.id.post_button_fabu);

		mySetOnClickListener(backImageView, backText, rightiImageView,
				zanImageView, plimageImageView, button);
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
		case R.id.top_right_search:// 收藏
			if (Myappliction.id == null) {
				Myappliction.show(PostBeanActivity.this, "请先登录");
			} else {
				if (b) {
					getwebshoucang(url);
					rightiImageView.setImageResource(R.drawable.shoucanghou);

					b = false;
				} else {
					getwebquxiao(url);
					rightiImageView.setImageResource(R.drawable.shoucang);

					b = true;
				}
			}

			break;
		case R.id.post_image_zan:// 赞图标
			if (Myappliction.id == null) {
				Myappliction.show(PostBeanActivity.this, "请先登录");
			} else {
				if (boo) {
					zanImageView.setImageResource(R.drawable.good_after);
					getaddzan();

					boo = false;
				} else {
					zanImageView.setImageResource(R.drawable.good_before);
					getsubzan();
					boo = true;

				}
			}

			break;
		case R.id.post_image_pinglun:// 评论图标
			editText.setFocusable(true);
			editText.requestFocus();
			break;
		case R.id.post_button_fabu:// 发送按钮
			if (Myappliction.id == null) {
				Myappliction.show(PostBeanActivity.this, "请先登录");
			} else {
				RequestParams params = new RequestParams();
				params.addBodyParameter("flg", "2");
				params.addBodyParameter("userid", Myappliction.id);
				String postid = beanPost.getPostid() + "";
				String comtent = editText.getText().toString().trim();
				params.addBodyParameter("comtent", comtent);
				params.addBodyParameter("postid", postid.trim());
				if (TextUtils.isEmpty(comtent)) {
					Myappliction.show(PostBeanActivity.this, "请输入内容");
				} else {
					setwebpinglun(params);
				}
				InputMethodManager imm = (InputMethodManager) this
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				editText.setCursorVisible(false);// 失去光标
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

				editText.setText("");

			}

			break;
		default:
			break;
		}
	}

	private void ShowPickDialog() {
		new AlertDialog.Builder(this).setTitle("确认删除？")
				.setNegativeButton("确认", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						getwebplshanchu();
						lComments.remove(point);
						sun=sun-1;
						num=true;
					}

				})
				.setPositiveButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.dismiss();
					}
				}).show();
	}

	private void getwebplshanchu() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("commentsid", commid + "");
		params.addBodyParameter("postid", beanPost.getPostid() + "");
		String wangzhi = "http://" + Myappliction.ip
				+ ":8080/ZQXweb/SerComments";
		httpUtils1.send(HttpMethod.POST, wangzhi, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);

						String urlpl = "http://" + Myappliction.ip
								+ ":8080/ZQXweb/SerComments";
						getwebpinglun(urlpl);
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});

	}

	private void setwebpinglun(RequestParams params) {
		HttpUtils httpUtils1 = new HttpUtils();
		String wangzhi = "http://" + Myappliction.ip
				+ ":8080/ZQXweb/SerComments";
		httpUtils1.send(HttpMethod.POST, wangzhi, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);

						String urlpl = "http://" + Myappliction.ip
								+ ":8080/ZQXweb/SerComments";
						getwebpinglun(urlpl);
						adapter.notifyDataSetChanged();
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	private void getsubzan() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);
						setZanNum(5);
						number = number - 1;
						zansum.setText(number + "");
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	private void getaddzan() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);
						setZanNum(4);
						number = number + 1;
						zansum.setText(number + "");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}

	private void setZanNum(int flg) {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", flg + "");
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url3, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
					}

					@Override
					public void onFailure(HttpException error, String msg) {
					}
				});
	}

	private void getZannum() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url3, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						number = Integer.parseInt(result);
						zansum.setText(number + "");

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});
	}

	private void getzanCheck() {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url2, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						if (result.equals("是")) {
							boo = false;
							zanImageView
									.setImageResource(R.drawable.good_after);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

	}

	// 判断帖子是否收藏过
	private void getwebcheckshoucang(final String url) {

		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "2");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						if (result.equals("是")) {
							b = false;
							rightiImageView
									.setImageResource(R.drawable.shoucanghou);
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

	}

	// 取消收藏
	private void getwebquxiao(String url) {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "3");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {

						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {

					}
				});
	}

	// 收藏
	private void getwebshoucang(String url) {
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		params.addBodyParameter("userid", Myappliction.id);
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub

						String result = responseInfo.result;
						Myappliction.show(PostBeanActivity.this, result);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub

					}
				});

	}

	private void getwebpinglun(String url) {
		final CustomProgressDialog dialog = new CustomProgressDialog(
				PostBeanActivity.this, "疯狂加载中。。。", R.anim.frame);
		dialog.show();
		HttpUtils httpUtils1 = new HttpUtils();
		RequestParams params = new RequestParams();
		params.addBodyParameter("flg", "1");
		String postid = beanPost.getPostid() + "";
		params.addBodyParameter("postid", postid.trim());
		httpUtils1.send(HttpMethod.POST, url, params,
				new RequestCallBack<String>() {

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						String result = responseInfo.result;

						Gson gson = new Gson();
						Type typeOfT = new TypeToken<List<BeanComments>>() {
						}.getType();
						List<BeanComments> listcBeanComments = gson.fromJson(
								result, typeOfT);
						for (int i = 0; i < listcBeanComments.size(); i++) {
							boolean bool = true;
							BeanComments beanCom = listcBeanComments.get(i);
							for (int j = 0; j < lComments.size(); j++) {
								if (beanCom.equals(lComments.get(j))) {
									bool = false;
								}
							}
							if (bool) {
								lComments.add(sun, beanCom);
								sun++;
							}
						}
						pinglunsum.setText(lComments.size() + "");
						// list集合只显示一条的解决方法
						int totalHeight = 0;
						{
							adapter = new PostListAdapter(
									PostBeanActivity.this, lComments);
							for (int i = 0; i < adapter.getCount(); i++) {
								View lViewitem = adapter.getView(i, null,
										listView);
								// 计算子项View 的宽高
								lViewitem.measure(0, 0);
								// 计算所有子项的高度和
								totalHeight += lViewitem.getMeasuredHeight();
							}
							ViewGroup.LayoutParams params = listView
									.getLayoutParams();
							// listView.getDividerHeight()获取子项间分隔符的高度
							// params.height设置ListView完全显示需要的高度
							params.height = totalHeight
									+ (listView.getDividerHeight() * (adapter
											.getCount()));
							listView.setLayoutParams(params);
							listView.setAdapter(adapter);
						}
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						dialog.dismiss();

					}
				});

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		finish();
	}

}
