package com.qixing.my.utils;

import com.qixing.my.beans.BeanUser;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class ActivityUtils {
	public static void  startActivity(Context context,Class<?> cls){
		Intent intent=new Intent(context, cls);
		context.startActivity(intent);
	}
	public static void  startActivity(Context context,String a,Class<?> cls){
		Intent intent=new Intent(context, cls);
		intent.putExtra("yema", a);
		context.startActivity(intent);
	}
	public static void  startActivity(Context context,BeanUser beanUser,Class<?> cls){
		Intent intent=new Intent(context, cls);
		intent.putExtra("xiangqing", beanUser);
		context.startActivity(intent);
	}
	public static void setActionBarLayout(ActionBar actionBar, Context context,
			int layoutId) {
		if (null != actionBar) {
			actionBar.setDisplayShowHomeEnabled(false);
			actionBar.setDisplayShowCustomEnabled(true);
			actionBar.setDisplayShowTitleEnabled(false);
			LayoutInflater inflator = LayoutInflater.from(context);
			View v = inflator.inflate(layoutId, null);
			ActionBar.LayoutParams layout = new ActionBar.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
			actionBar.setCustomView(v, layout);
		}
	}

}
