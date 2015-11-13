package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanSceneryPhoto;
import com.qixing.my.utils.Myappliction;
import com.qixing.shouye.ImagePagerActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class GridviewAdapter extends BaseAdapter {

	List<BeanSceneryPhoto> list = new ArrayList<BeanSceneryPhoto>();
	Context context;
	LayoutInflater mInflater;
	GridView gridView;
	int width, count;

	LayoutParams params;

	public GridviewAdapter(List<BeanSceneryPhoto> list, Context context,
			GridView gridView, int width) {
		super();
		this.list = list;
		this.context = context;
		this.gridView = gridView;
		this.width = width;
		mInflater = LayoutInflater.from(context);

	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(final int arg0, View contextView, ViewGroup parent) {
		contextView = mInflater.inflate(R.layout.post_image_item, null);
		ImageView imageView = (ImageView) contextView
				.findViewById(R.id.post_imageview);
		/*
		 * imageView.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { Intent intent=new
		 * Intent(context,SkipActivity.class); BeanSceneryPhoto
		 * beanSceneryPhoto=new BeanSceneryPhoto(); intent.putExtra("photo",
		 * beanSceneryPhoto); Toast.makeText(context, "��Ҫ����"+arg0, 0).show();
		 * } });
		 */
		if (count == 1) {
			// 设置gridView每行只显示一张图片
			gridView.setNumColumns(1);
			// 只有一张图片时，图片宽与手机屏幕分辨率宽一致，高为宽分辨率的一半
			params = new LayoutParams(width, width / 2);
			imageView.setLayoutParams(params);
		} else if (count == 2) {
			// 设置gridView每行只显示一张图片
			gridView.setNumColumns(2);
			// 只有两张图片时，图片宽度几乎为手机分辨率宽的一半，因为gridView设置了
			// 两张图片间距为1dp，所以这里减去了一个值3，可以自行调节此值大小
			params = new LayoutParams(width / 2 - 3, width / 3);
			imageView.setLayoutParams(params);
		} else {
			gridView.setNumColumns(3);
			params = new LayoutParams(width / 3 - 3, width / 4);
			imageView.setLayoutParams(params);
		}
		contextView.setTag(imageView);
		// 使用XUtils框架下载图片
		String uri = Myappliction.photoaddress
				+ list.get(arg0).getPhoto();
		
		Myappliction.bitmapUtils.display(imageView, uri);

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (list.size() != 0) {
					// 当图片路径不为0时，传给数组
					String[] images = new String[list.size()];
					for (int i = 0; i < list.size(); i++) {
						images[i] = Myappliction.photoaddress
								+ list.get(i).getPhoto();
					
					}
					Intent intent = new Intent(context,
							ImagePagerActivity.class);
					// 图片url,一般从数据库中或网络中获取
					intent.putExtra("images", images);
					context.startActivity(intent);
				}
			}
		});
		return contextView;
	}

}
