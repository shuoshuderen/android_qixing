package com.qixing.tuijian;

import java.util.ArrayList;
import java.util.List;

import com.example.qixingtianxia.MainActivity;
import com.example.qixingtianxia.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class TuiJianFragment extends Fragment {
	View tuijianView;
	ViewPager viewPager;
	RadioGroup radioGroup;
	List<Fragment> fragments = new ArrayList<Fragment>();
	MainActivity activity;
	MyAdapter adapter;

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.activity = (MainActivity) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		initDate();
	}

	private void initDate() {
		// 初始化fragments，添加路线，装备 碎片
		Fragment lunxianFragment = new LunXianFragment();
		Fragment zhuangbeiFragment = new ZhuangBeiFragment();
		fragments.add(lunxianFragment);
		fragments.add(zhuangbeiFragment);
		// 不能传入getFragmentManager(),建议使用getChildFragmentManager，否则
		// 后台转前台，报错。
		adapter = new MyAdapter(getChildFragmentManager());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		tuijianView = inflater.inflate(R.layout.tuijian, null);
		initView();
		return tuijianView;
	}

	private void initView() {
		viewPager = (ViewPager) tuijianView.findViewById(R.id.tuijianviewpager);
		viewPager.setAdapter(adapter);
		// 监听viewPager状态改变时间
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// 滑动监听，根据滑动，改变上方文字按钮
				switch (arg0) {
				// 说明viewpager滑动到了路线页面，同步修改推荐选项被选中
				case 0:
					radioGroup.check(R.id.tj_luxian);
					break;
				// 说明viewpager滑动到了装备页面，同步修改推荐选项被选中
				case 1:
					radioGroup.check(R.id.tj_zhuanbei);
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
		radioGroup = (RadioGroup) tuijianView.findViewById(R.id.group_tj);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				//根据按钮更换下方碎片
				switch (checkedId) {
				case R.id.tj_luxian:
                 viewPager.setCurrentItem(0);
					break;
				case R.id.tj_zhuanbei:
					viewPager.setCurrentItem(1);
					break;

				default:
					break;
				}

			}
		});
		//碎片之间跳转，需在activity中声明跳转到的碎片
	/*	public void test(){
			fragment = new FuJinFragment();
			replace(fragment);
		}*/
	/*	button = (Button) homeView.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 假设单击此按钮由图赏碎片跳转到社区碎片
				// fragment是不可以直接跳转到另外一个fragment，需要借助activity实现
				// 一般是写一个回调函数，当单击了当前的button，回调activity中的某个方法
				// 由此方法来调用另外一个碎片
				activity.test();
			}
		});*/
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
		/*
		 * @Override 获取标题 public CharSequence getPageTitle(int position) { //
		 * TODO Auto-generated method stub return titles.get(position); }
		 */
	}
}
