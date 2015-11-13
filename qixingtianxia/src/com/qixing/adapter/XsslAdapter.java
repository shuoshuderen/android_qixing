package com.qixing.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanFreshonroot;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class XsslAdapter extends BaseAdapter{

	Context context;
	LayoutInflater mInflater;
	List<BeanFreshonroot> list =new ArrayList<BeanFreshonroot>();
	List<String> list2=new ArrayList<String>();
	public XsslAdapter(Context context, List<BeanFreshonroot> list) {
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
	public Object getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView=mInflater.inflate(R.layout.shouye_xinshoushanglu_item,null);
		TextView title=(TextView) convertView.findViewById(R.id.texttitle_xssl);
		TextView shuoming=(TextView)convertView.findViewById(R.id.textshuoming_xssl);
		ImageView image1=(ImageView) convertView.findViewById(R.id.image4);
		ImageView image2=(ImageView) convertView.findViewById(R.id.image5);
		ImageView image3=(ImageView) convertView.findViewById(R.id.image6);
				
		title.setText(list.get(position).getFreshtitle());
		shuoming.setText(list.get(position).getFreshmaincontent());
		
		list2=list.get(position).getList();
		if (list2.size() == 1) {
			String uri = Myappliction.photoaddress + list2.get(0);
			Myappliction.bitmapUtils.display(image1, uri);
		} else if (list2.size() == 2) {
			String uri = Myappliction.photoaddress + list2.get(0);
			Myappliction.bitmapUtils.display(image1, uri);
			String uri11 = Myappliction.photoaddress + list2.get(1);
			Myappliction.bitmapUtils.display(image2, uri11);

		} else if (list2.size() > 2) {
			String uri = Myappliction.photoaddress + list2.get(0);
			Myappliction.bitmapUtils.display(image1, uri);
			String uri11 = Myappliction.photoaddress + list2.get(1);
			Myappliction.bitmapUtils.display(image2, uri11);
			String uri12 = Myappliction.photoaddress + list2.get(2);
			Myappliction.bitmapUtils.display(image3, uri12);
		} else if (list2.size() == 0) {
			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.lian_xinshou);
			layout.setVisibility(View.GONE);// 隐藏不可见
		}
		return convertView;
	}

}
