package com.qixing.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {
	/**
	 * 保存Preference的name
	 */
	public static final String PREFERENCE_NAME = "UserInfo";
	private static SharedPreferences sharedPreferences;
	private static PreferenceUtil preferenceUtil;
	private static SharedPreferences.Editor editor;

	private String SHARE_USERID = "user_id";
	/*private String SHARE_USERNAME = "user_name";
	private String SHARE_PASSWORD = "user_password";
	private String SHARE_USERPIC = "user_pic";
	private String SHARE_PHONE = "user_phone";
	private String SHARE_GENDER = "user_gender";*/

	private PreferenceUtil(Context context) {
		sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME,
				Context.MODE_PRIVATE);
	}

	/**
	 * 单例模式，获取instance实例
	 * 
	 * @param cxt
	 * @return
	 */
	public static PreferenceUtil getInstance(Context context) {
		if (preferenceUtil == null) {
			preferenceUtil = new PreferenceUtil(context);
		}
		editor = sharedPreferences.edit();
		return preferenceUtil;
	}

	// 清除数据
	public void cleanUserInfo() {
		editor.clear();
		editor.commit();
	}

	/** share用户ID */
	public void setShareUserId(String userid) {
		editor.putString(SHARE_USERID, userid);
		editor.commit();
	}

	public String getShareUserId() {
		return sharedPreferences.getString(SHARE_USERID, "");
	}

/*	*//** share用户名 *//*
	public void setShareUserName(String UserName) {
		editor.putString(SHARE_USERNAME, UserName);
		editor.commit();
	}

	public String getShareUserName() {
		return sharedPreferences.getString(SHARE_USERNAME, "");
	}

	*//** share密码 *//*
	public void setSharePassWord(String PassWord) {
		editor.putString(SHARE_PASSWORD, PassWord);
		editor.commit();
	}

	public String getSharePassWord() {
		return sharedPreferences.getString(SHARE_PASSWORD, "");
	}

	*//** share头像 *//*
	public void setShareUserPic(String UserPic) {
		editor.putString(SHARE_USERPIC, UserPic);
		editor.commit();
	}

	public String getShareUserPic() {
		return sharedPreferences.getString(SHARE_USERPIC, "");
	}

	*//** share用户性别 *//*
	public void setShareUserGender(String UserGender) {
		editor.putString(SHARE_GENDER, UserGender);
		editor.commit();
	}

	public String getShareUserGender() {
		return sharedPreferences.getString(SHARE_GENDER, "");
	}

	*//** share用户手机 *//*
	public void setShareUserPhone(String Phone) {
		editor.putString(SHARE_PHONE, Phone);
		editor.commit();
	}

	public String getShareUserPhone() {
		return sharedPreferences.getString(SHARE_PHONE, "");
	}*/
}
