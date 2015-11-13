package com.qixing.my.utils;

import com.qixing.my.beans.BeanUser;
import com.qixing.wode.WoDeFragment;

import android.content.Intent;


public class FragmentUtils {
	public  static void startActivity(WoDeFragment woDeFragment,Class<?> activity){
		Intent intent=new Intent(woDeFragment.getActivity().getApplicationContext(), activity);
		woDeFragment.startActivity(intent);

}
	public  static void startActivity(WoDeFragment woDeFragment,BeanUser beanUser,Class<?> activity){
		Intent intent=new Intent(woDeFragment.getActivity().getApplicationContext(), activity);
		intent.putExtra("wode",beanUser );
		woDeFragment.startActivity(intent);

}
}
