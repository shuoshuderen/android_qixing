package com.qixing.adapter;

import java.util.List;


import com.example.qixingtianxia.R;
import com.qixing.bean.BeanEqudetail;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TjZhuangbeiAdapter extends BaseAdapter {
	List<BeanEqudetail> list;
	Context context;
LayoutInflater mInflater;
	public TjZhuangbeiAdapter(List<BeanEqudetail> list, Context context) {
		super();
		this.list = list;
		this.context = context;
		
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

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		arg1=mInflater.inflate(R.layout.activity_tj_zbjtitem, null);
		ImageView imageView1;
		TextView textView;
		imageView1=(ImageView) arg1.findViewById(R.id.img_zbjtitem);
		textView=(TextView) arg1.findViewById(R.id.text_zbjtitem);
		
		textView.setText(list.get(arg0).getEqudetailName());
		
		String uri = Myappliction.photoaddress
				+ list.get(arg0).getList().get(0).getPhoto();

		Myappliction.bitmapUtils.display(imageView1, uri);
		return arg1;
	}

}
