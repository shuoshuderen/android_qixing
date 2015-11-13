package com.qixing.tuijian;


import com.example.qixingtianxia.R;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class ZhuangBeiFragment extends Fragment implements OnClickListener {
	View zhuangbeiView;
	LinearLayout l1, l2, l3, l4;
	Context context;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		zhuangbeiView = inflater.inflate(R.layout.tj_zhuangbei, null);
		context = zhuangbeiView.getContext();

		initView();
		
		return zhuangbeiView;
	}
	private void initView() {
		l1 = (LinearLayout) zhuangbeiView.findViewById(R.id.tj_zb_lin1);
		l2 = (LinearLayout) zhuangbeiView.findViewById(R.id.tj_zb_lin2);
		l3 = (LinearLayout) zhuangbeiView.findViewById(R.id.tj_zb_lin3);
		l4 = (LinearLayout) zhuangbeiView.findViewById(R.id.tj_zb_lin4);

		l1.setOnClickListener(this);
		l2.setOnClickListener(this);
		l3.setOnClickListener(this);
		l4.setOnClickListener(this);

	}

	@Override
	public void onClick(View arg0) {
		Intent intent = new Intent(getActivity(), TjZbjtActivity.class);
		switch (arg0.getId()) {
		case R.id.tj_zb_lin1:
			
			String a = "单车";
			intent.putExtra("lei", a);
			break;
		case R.id.tj_zb_lin2:
			
			String b = "护具";
			intent.putExtra("lei", b);
			break;
		case R.id.tj_zb_lin3:
			
			String c = "服饰";
			intent.putExtra("lei", c);
			break;
		case R.id.tj_zb_lin4:
			
			String d = "工具";
			intent.putExtra("lei", d);
			break;
		default:
			break;
		}
		startActivity(intent);
	}


}
