package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.People1;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PeopleAdapter extends BaseAdapter {

	Context context;
	List<People1> list=new ArrayList<People1>();
	LayoutInflater mInflater;
	ViewHolder mHolder;
	
	public PeopleAdapter(Context context, List<People1> list) {
		super();
		this.context = context;
		this.list = list;
		mInflater=LayoutInflater.from(context);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView==null) {
			convertView=mInflater.inflate(R.layout.fujin_ren_item, null);
			mHolder=new ViewHolder();
			mHolder.imageView=(ImageView) convertView.findViewById(R.id.care_image);
			mHolder.nameTextView=(TextView) convertView.findViewById(R.id.care_name);
			mHolder.sexTextView=(TextView) convertView.findViewById(R.id.care_sex);
			mHolder.ageTextView=(TextView) convertView.findViewById(R.id.care_age);
			mHolder.distanceTextView=(TextView) convertView.findViewById(R.id.care_distance);
			mHolder.total_disTextView=(TextView) convertView.findViewById(R.id.care_dis);
			mHolder.total_timeTextView=(TextView) convertView.findViewById(R.id.care_time);
		    convertView.setTag(mHolder);
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		String uri=Myappliction.photoaddress+list.get(position).getUserimg();//给imageview设置图片
		Myappliction.bitmapUtils.display(mHolder.imageView, uri);
		mHolder.nameTextView.setText(list.get(position).getUsername());
		mHolder.sexTextView.setText(list.get(position).getGender());
		mHolder.ageTextView.setText(list.get(position).getAge()+"");
		mHolder.total_disTextView.setText(list.get(position).getTotaldistance()+"");
		mHolder.total_timeTextView.setText(list.get(position).getTotaltime());
		//把距离转换为千米和米的形式，去掉小数点
				int distance =  (int)list.get(position).getDistance();
				int km = distance/1000;//千米
				int m = distance%1000;//米
				if (km > 0) {
					mHolder.distanceTextView.setText(km + "千米" + m + "米");
				} else {
					mHolder.distanceTextView.setText(m + "米");
				}
		return convertView;
	}
	class ViewHolder{
		TextView nameTextView;
		ImageView imageView;
		TextView sexTextView,distanceTextView;
		TextView ageTextView,total_disTextView,total_timeTextView;
	}

}
