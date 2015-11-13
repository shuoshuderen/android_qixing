package com.qixing.fujin;

import java.util.ArrayList;
import java.util.List;

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

public class FuJinFragment extends Fragment {
	View fujinView;
	ViewPager viewPager;
	RadioGroup radioGroup;
	List<Fragment> fragments = new ArrayList<Fragment>();
	MyAdapter adapter;
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
		// 初始化fragments，添加路线，装备 碎片
		Fragment renFragment = new RenFragment();
		Fragment cheduifFragment = new CheDuiFragment();
		Fragment qiuzhuFragment = new QiuZhuFragment();
		fragments.add(renFragment);
		fragments.add(cheduifFragment);
		fragments.add(qiuzhuFragment);
		// 不能传入getFragmentManager(),建议使用getChildFragmentManager，否则
		// 后台转前台，报错。
		adapter = new MyAdapter(getChildFragmentManager());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		fujinView=inflater.inflate(R.layout.fujin, null);
		initView();
		return fujinView;
	}
	private void initView() {
		viewPager=(ViewPager) fujinView.findViewById(R.id.fujinviewpager);
		viewPager.setAdapter(adapter);
		// 监听viewPager状态改变时间
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// 滑动监听，根据滑动，改变上方文字按钮
				switch (arg0) {
				// 说明viewpager滑动到了路线页面，同步修改推荐选项被选中
				case 0:
					radioGroup.check(R.id.fj_ren);
					break;
				// 说明viewpager滑动到了装备页面，同步修改推荐选项被选中
				case 1:
					radioGroup.check(R.id.fj_chedui);
					break;
				case 2:
					radioGroup.check(R.id.fj_qiuzhu);
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
		radioGroup = (RadioGroup) fujinView.findViewById(R.id.group_fj);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 根据按钮更换下方碎片
				switch (checkedId) {
				case R.id.fj_ren:
					viewPager.setCurrentItem(0);
					break;
				case R.id.fj_chedui:
					viewPager.setCurrentItem(1);
					break;
				case R.id.fj_qiuzhu:
					viewPager.setCurrentItem(2);
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
