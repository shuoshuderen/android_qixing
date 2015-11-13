package com.qixing.gc.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.gc.beans.BeanPost;
import com.qixing.gc.beans.BeanPostPhoto;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GcTongchengAdapter extends BaseAdapter {

	int number = 0;
	List<BeanPostPhoto> list2 = new ArrayList<BeanPostPhoto>();
	Context context;
	List<BeanPost> list = new ArrayList<BeanPost>();
	LayoutInflater mInflater;
	
	public GcTongchengAdapter(Context context, List<BeanPost> list) {
		this.context = context;
		this.list = list;

		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		convertView = mInflater.inflate(R.layout.gc_tongcheng_item, null);
		TextView titlTextView = (TextView) convertView
				.findViewById(R.id.post1_title);
		TextView contentTextView = (TextView) convertView
				.findViewById(R.id.post1_content);
		TextView timeTextView = (TextView) convertView
				.findViewById(R.id.post1_time);
		final TextView goodsum = (TextView) convertView
				.findViewById(R.id.post1_good_sum);
		TextView commentsumtTextView = (TextView) convertView
				.findViewById(R.id.post1_comment_sum);

		list2 = list.get(position).getList();
		// 需点击的应放在外边，不然会出现异常
		// 头像
		ImageView userImageView = (ImageView) convertView
				.findViewById(R.id.userimage);
		userImageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Myappliction.getuser(list.get(position).getUserid(), context);
			}
		});
		// userImageView.setOnClickListener(this);

	/*	// 评论
		ImageView pinlu = (ImageView) convertView
				.findViewById(R.id.post1_comment_image);
		pinlu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Myappliction.show(context, "请到帖子中进行回复");
			}
		});*/


		String uri1 = Myappliction.photoaddress
				+ list.get(position).getUserimg();// 给imageview设置图片
		Myappliction.bitmapUtils.display(userImageView, uri1);
		// 设置文本
		titlTextView.setText(list.get(position).getPostname());
		contentTextView.setText(list.get(position).getPostaddress());
		timeTextView.setText(list.get(position).getPosttime());
		number = list.get(position).getPostpraise();
		goodsum.setText(number + "");
		commentsumtTextView.setText(list.get(position).getPlSum() + "");
		timeTextView.setText(list.get(position).getPosttime());

		/*// 赞
		final ImageView zan = (ImageView) convertView
				.findViewById(R.id.post1_good_image);
		zan.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Myappliction.show(context, "请进入帖子进行点赞");

			}

		});*/

		ImageView imageView = (ImageView) convertView
				.findViewById(R.id.post1_image);
		ImageView imageView2 = (ImageView) convertView
				.findViewById(R.id.post1_image2);
		ImageView imageView3 = (ImageView) convertView
				.findViewById(R.id.post1_image3);

		if (list2.size() == 1) {
			String uri = Myappliction.photoaddress + list2.get(0).getPhoto();
			Myappliction.bitmapUtils.display(imageView, uri);
		} else if (list2.size() == 2) {
			String uri = Myappliction.photoaddress + list2.get(0).getPhoto();
			Myappliction.bitmapUtils.display(imageView, uri);
			String uri11 = Myappliction.photoaddress + list2.get(1).getPhoto();
			Myappliction.bitmapUtils.display(imageView2, uri11);

		} else if (list2.size() > 2) {
			String uri = Myappliction.photoaddress + list2.get(0).getPhoto();
			Myappliction.bitmapUtils.display(imageView, uri);
			String uri11 = Myappliction.photoaddress + list2.get(1).getPhoto();
			Myappliction.bitmapUtils.display(imageView2, uri11);
			String uri12 = Myappliction.photoaddress + list2.get(2).getPhoto();
			Myappliction.bitmapUtils.display(imageView3, uri12);
		} else if (list2.size() == 0) {
			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.postImage_ll);
			layout.setVisibility(View.GONE);// 隐藏不可见
		}

		return convertView;
	}

}
