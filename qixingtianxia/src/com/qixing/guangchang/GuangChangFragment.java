package com.qixing.guangchang;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.R;
import com.qixing.my.utils.Myappliction;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class GuangChangFragment extends Fragment {
	View guangchangvView;
	ViewPager viewPager;
	RadioGroup radioGroup;
	RadioButton radioButton;
	List<Fragment> fragments = new ArrayList<Fragment>();
	MyAdapter adapter;
	Context context;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initDate();
	}

	private void initDate() {
		// 初始化fragments，添加同城，好友 碎片
		Fragment tongchengfFragment = new TongChengFragment();
		// Fragment tongchengfFragment = new TcFragment();
		Fragment haoyoufFragment = new HaoYouFragment();
		fragments.add(tongchengfFragment);
		fragments.add(haoyoufFragment);
		// 不能传入getFragmentManager(),建议使用getChildFragmentManager，否则
		// 后台转前台，报错。
		adapter = new MyAdapter(getChildFragmentManager());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		guangchangvView = inflater.inflate(R.layout.guangchang, null);
		context = guangchangvView.getContext();
		initView();
		return guangchangvView;
	}

	private void initView() {
		radioButton = (RadioButton) guangchangvView
				.findViewById(R.id.radiobutton_gc_bianji);
		radioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Myappliction.id != null) {
					Intent intent = new Intent(context, GcAddActivity.class);
					startActivity(intent);
				} else {
					Myappliction.show(context, "请先登录");
				}

			}
		});
		viewPager = (ViewPager) guangchangvView
				.findViewById(R.id.guangchangviewpager);
		viewPager.setAdapter(adapter);
		// 监听viewPager状态改变时间
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 滑动监听，根据滑动，改变上方文字按钮
				switch (arg0) {
				// 说明viewpager滑动到了路线页面，同步修改推荐选项被选中
				case 0:
					radioGroup.check(R.id.gc_tongcheng);
					break;
				// 说明viewpager滑动到了装备页面，同步修改推荐选项被选中
				case 1:
					if (Myappliction.id != null) {
						radioGroup.check(R.id.gc_haoyou);
					} else {
						Myappliction.show(context, "请先登录");
					}

					break;

				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 初始化顶部的单选按钮组
		radioGroup = (RadioGroup) guangchangvView.findViewById(R.id.group_gc);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 根据按钮更换下方碎片
				switch (checkedId) {
				case R.id.gc_tongcheng:
					viewPager.setCurrentItem(0);
					break;
				case R.id.gc_haoyou:
					if (Myappliction.id != null) {
						viewPager.setCurrentItem(1);
					} else {
						Myappliction.show(context, "请先登录");
					}

					break;

				default:
					break;
				}

			}
		});
	}

	// 写一个适配器，继承FragmentPagerAdapter
	class MyAdapter extends FragmentPagerAdapter {

		public MyAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return fragments.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return fragments.size();
		}
	}
}
