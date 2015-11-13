package com.qixing.shouye;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ViewFlipper;
//重写ViewFlipper，因为代码中ViewFlipper是嵌入到ViewPager中
//而ViewPager拦截了事件，导致ViewFlipper无法监听到用户触摸屏幕事件
public class MyFillper extends ViewFlipper {

	public MyFillper(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//通知所有父布局不要拦截当前视图的事件处理方法
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

}
