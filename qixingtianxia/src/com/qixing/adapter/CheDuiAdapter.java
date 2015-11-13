package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanTeam;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CheDuiAdapter extends BaseAdapter {

	Context context;
	List<BeanTeam> list=new ArrayList<BeanTeam>();
	LayoutInflater mInflater;
	ViewHolder mHolder;
	
	public CheDuiAdapter(Context context, List<BeanTeam> list) {
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
			convertView=mInflater.inflate(R.layout.wo_chedui_item, null);
			mHolder=new ViewHolder();
			mHolder.imageView=(ImageView) convertView.findViewById(R.id.cd_image);
			mHolder.nameTextView=(TextView) convertView.findViewById(R.id.cd_name);
			mHolder.addressTextView=(TextView) convertView.findViewById(R.id.address);
			mHolder.total_disTextView=(TextView) convertView.findViewById(R.id.zonglicheng);
			mHolder.total_peopTextView=(TextView) convertView.findViewById(R.id.zongrenshu);
			convertView.setTag(mHolder);
			
		}else {
			mHolder=(ViewHolder) convertView.getTag();
		}
		mHolder.nameTextView.setText(list.get(position).getTeamname());
		mHolder.addressTextView.setText(list.get(position).getTeamaddress());
		mHolder.total_disTextView.setText(list.get(position).getTeamdistance()+"");
		mHolder.total_peopTextView.setText(list.get(position).getTeamnumber()+"");
		String uri=Myappliction.photoaddress+list.get(position).getTeamimage();
		Myappliction.bitmapUtils.display(mHolder.imageView, uri);
		return convertView;
	}
	class ViewHolder{
		TextView nameTextView;
		ImageView imageView;
		TextView addressTextView;
		TextView total_disTextView,total_peopTextView;
	}

}
