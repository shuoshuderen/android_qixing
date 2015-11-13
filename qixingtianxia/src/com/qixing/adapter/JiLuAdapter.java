package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanJiLu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class JiLuAdapter extends BaseAdapter {
	Context context;
	List<BeanJiLu> list = new ArrayList<BeanJiLu>();
	LayoutInflater mInflater;
	ViewHolder mHolder;

	public JiLuAdapter(Context context, List<BeanJiLu> list) {
		super();
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
		// TODO Auto-generated method stub
		if (arg1 == null) {
			arg1 = mInflater.inflate(R.layout.jilu_item, null);
			mHolder = new ViewHolder();
			mHolder.licheng = (TextView) arg1.findViewById(R.id.jilu_luche);
			mHolder.time = (TextView) arg1.findViewById(R.id.jilu_alltime);
			mHolder.btime = (TextView) arg1.findViewById(R.id.jilu_begin_time);
			mHolder.cishu = (TextView) arg1.findViewById(R.id.jilu_cishu);
			arg1.setTag(mHolder);

		} else {
			mHolder = (ViewHolder) arg1.getTag();
		}
		mHolder.cishu.setText("第"+(list.size()-arg0)+"次骑行");
		mHolder.licheng.setText(list.get(arg0).getMileage() + "公里");
		mHolder.time.setText(list.get(arg0).getAlltime() + "小时");
		mHolder.btime.setText(list.get(arg0).getTime());
		return arg1;
	}

	class ViewHolder {
		TextView licheng, time, btime,cishu;

	}

}
