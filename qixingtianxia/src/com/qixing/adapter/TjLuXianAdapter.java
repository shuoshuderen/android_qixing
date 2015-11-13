package com.qixing.adapter;

import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanWay;
import com.qixing.bean.BeanWayImage;
import com.qixing.my.utils.Myappliction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TjLuXianAdapter extends BaseAdapter {
	Context context;
	List<BeanWay> list;
	List<BeanWayImage> lImages;
	LayoutInflater mInflater;

	public TjLuXianAdapter(Context context, List<BeanWay> list) {
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

		arg1 = mInflater.inflate(R.layout.tj_luxian_item, null);

		ImageView imageView, start1, start2, start3;
		imageView = (ImageView) arg1.findViewById(R.id.imageview_lx);
		start1 = (ImageView) arg1.findViewById(R.id.Start1_lx);
		start2 = (ImageView) arg1.findViewById(R.id.Start2_lx);
		start3 = (ImageView) arg1.findViewById(R.id.Start3_lx);

		TextView textView = (TextView) arg1.findViewById(R.id.textview_lx);
		
		textView.setText(list.get(arg0).getWayName());
		lImages = list.get(arg0).getList();
		String uri = Myappliction.photoaddress
				+ lImages.get(0).getWayimageroot();
		Myappliction.bitmapUtils.display(imageView, uri);

		int num = list.get(arg0).getStart1();
		ImageView iView = start1;
		getstar(num, iView);
		int num2 = list.get(arg0).getStart2();
		ImageView iView2 =start2;
		getstar(num2, iView2);
		int num3 = list.get(arg0).getStart3();
		ImageView iView3 = start3;
		getstar(num3, iView3);
		return arg1;

	}

	public void getstar(int num1, ImageView iView1) {
		switch (num1) {
		case 1:
			iView1.setImageResource(R.drawable.star1);
			break;
		case 2:
			iView1.setImageResource(R.drawable.star2);
			break;
		case 3:
			iView1.setImageResource(R.drawable.star3);
			break;
		case 4:
			iView1.setImageResource(R.drawable.star4);
			break;
		case 5:
			iView1.setImageResource(R.drawable.star5);
			break;

		default:
			break;
		}

	}

}
