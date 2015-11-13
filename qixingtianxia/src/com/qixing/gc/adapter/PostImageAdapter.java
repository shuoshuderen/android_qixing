package com.qixing.gc.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.gc.view.PostGridview;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class PostImageAdapter extends BaseAdapter {
	List<String> list = new ArrayList<String>();
	Context context;
	LayoutInflater mInflater;
	PostGridview gridView;
	ViewHolder2 mHolder;

	int count, width;
	// 这里注意LayoutParams是属于AbsListView类的，因为本例中图片父布局是gridview
	LayoutParams params;
/*	BitmapUtils bitmapUtils;*/

	public PostImageAdapter(List<String> list, Context context,
			 PostGridview gridView, int width) {
		super();
		this.list = list;
		this.context = context;
		this.gridView = gridView;
		this.width = width;
		
		mInflater=LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
class ViewHolder2{
	ImageView imageView;
}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.post_image_item, null);
			mHolder=new ViewHolder2();
			mHolder.imageView=(ImageView) convertView.findViewById(R.id.post_imageview);
	        gridView.setNumColumns(1);
	        params=new LayoutParams(width, width/2);
	        mHolder.imageView.setLayoutParams(params);
	        convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder2) convertView.getTag();
		}
		String uriString=Myappliction.photoaddress+list.get(position);
		Myappliction.bitmapUtils.display(mHolder.imageView,uriString );
		return convertView;
	}

}
