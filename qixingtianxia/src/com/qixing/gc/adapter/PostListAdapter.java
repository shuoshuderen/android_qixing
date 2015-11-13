package com.qixing.gc.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.gc.beans.BeanComments;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PostListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	List<BeanComments> list=new ArrayList<BeanComments>();
	ViewHolder h;

	public PostListAdapter(Context context, List<BeanComments> list) {
		super();
		this.context = context;
		this.list = list;
		inflater=LayoutInflater.from(context);
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
		// TODO Auto-generated method stub
		if (convertView == null || convertView.getTag() == null) {
			convertView = inflater.inflate(R.layout.postdetail_item, null);
			h = new ViewHolder();
			h.time=(TextView) convertView.findViewById(R.id.textpost_pl_neirong);
			h.nr = (TextView) convertView.findViewById(R.id.item_textview);
			h.name=(TextView) convertView.findViewById(R.id.textpost_pl_username);
			
			convertView.setTag(h);
		} else {
			h = (ViewHolder) convertView.getTag();
		}
		
		h.imageView=(ImageView) convertView.findViewById(R.id.post_pl_view);
		h.imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			Myappliction.getuser(list.get(position).getUserid(), context);
			}
		});
		
		h.nr.setText(list.get(position).getContent());
		h.time.setText(list.get(position).getTIME());
		h.name.setText(list.get(position).getUsername());
		String uri=Myappliction.photoaddress+list.get(position).getTouxiang();
		Myappliction.bitmapUtils.display(h.imageView, uri);
		return convertView;
	}

	class ViewHolder {
		TextView nr,time,name;
		ImageView imageView;
	}

}
