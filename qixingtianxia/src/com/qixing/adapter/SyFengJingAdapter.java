package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanScenery;
import com.qixing.bean.BeanSceneryPhoto;
import com.qixing.gc.view.PostGridview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class SyFengJingAdapter extends BaseAdapter {

	List<BeanScenery> list = new ArrayList<BeanScenery>();
	Context context;
	LayoutInflater mInflater;
	int width;
	GridviewAdapter gridviewAdapter;

	public SyFengJingAdapter(List<BeanScenery> list, Context context, int width) {
		super();
		this.list = list;
		this.context = context;
		this.width = width;
		mInflater = LayoutInflater.from(context);
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
		List<BeanSceneryPhoto> listphoto = list.get(arg0).getList();
		listphoto = list.get(arg0).getList();
		arg1 = mInflater.inflate(R.layout.shouye_fengjing_item, null);
		TextView textView = (TextView) arg1.findViewById(R.id.shouye_fengjing_text);
		textView.setText(list.get(arg0).getSceneryname());
		GridView gridView = (PostGridview) arg1.findViewById(R.id.shouye_fengjig_gridview);
		gridviewAdapter = new GridviewAdapter(listphoto, context, gridView,
				width);
		gridView.setAdapter(gridviewAdapter);
		return arg1;
	}

}
