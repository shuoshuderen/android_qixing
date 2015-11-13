package com.qixing.adapter;

//与TJLuXianAdapter大致相同,除了多排名和getCount();
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.qixingtianxia.R;
import com.qixing.bean.BeanWay;
import com.qixing.bean.BeanWayImage;
import com.qixing.my.utils.Myappliction;

public class SYLxtopAdapter extends BaseAdapter {
	Context context;
	List<BeanWay> list;
	List<BeanWayImage> lImages;
	LayoutInflater mInflater;

	public SYLxtopAdapter(Context context, List<BeanWay> list) {
		super();
		this.context = context;
		this.list = list;
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
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		arg1 = mInflater.inflate(R.layout.shouye_luxiantop_item, null);
		ImageView imageView, imageView2, start1, start2, start3;
		imageView = (ImageView) arg1.findViewById(R.id.imageview_lx_top);
		imageView2 = (ImageView) arg1.findViewById(R.id.image_top);
		start1 = (ImageView) arg1.findViewById(R.id.Start1_lx_top);
		start2 = (ImageView) arg1.findViewById(R.id.Start2_lx_top);
		start3 = (ImageView) arg1.findViewById(R.id.Start3_lx_top);
		TextView textView = (TextView) arg1.findViewById(R.id.textview_lx_top);
		textView.setText(list.get(arg0).getWayName());
		lImages = list.get(arg0).getList();
		String uri = Myappliction.photoaddress
				+ lImages.get(0).getWayimageroot();
		Myappliction.bitmapUtils.display(imageView, uri);
		// 排名相关

		//int zan = list.get(arg0).getWayZambia();

		int num = list.get(arg0).getStart1();
		ImageView iView = start1;
		getstar(num, iView);
		int num2 = list.get(arg0).getStart2();
		ImageView iView2 = start2;
		getstar(num2, iView2);
		int num3 = list.get(arg0).getStart3();
		ImageView iView3 = start3;
		getstar(num3, iView3);

		// 排名
		switch (arg0+1) {
		case 1:
			imageView2.setImageResource(R.drawable.num1);
			break;
		case 2:
			imageView2.setImageResource(R.drawable.num2);
			break;
		case 3:
			imageView2.setImageResource(R.drawable.num3);
			break;
		case 4:
			imageView2.setImageResource(R.drawable.num4);
			break;
		case 5:
			imageView2.setImageResource(R.drawable.num5);
			break;
		case 6:
			imageView2.setImageResource(R.drawable.num6);
			break;
		case 7:
			imageView2.setImageResource(R.drawable.num7);
			break;
		case 8:
			imageView2.setImageResource(R.drawable.num8);
			break;
		case 9:
			imageView2.setImageResource(R.drawable.num9);
			break;
		case 10:
			imageView2.setImageResource(R.drawable.num10);
			break;

		default:
			break;
		}
		return arg1;
	}

	private void getstar(int num1, ImageView iView1) {
		// 获取星星的数量
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
