package com.qixing.config;

import java.io.File;

import com.qixing.my.utils.FileUtils;



import android.content.Context;

/**
 * 
 * @ClassName: FilePath
 * @Description: TODO(文件存储路径配置)
 */
public class FilePathConfig {

	public static String getCameraStore(Context c) {

		return FileUtils.getPicturesStorageDir(c, "CameraStore").getAbsolutePath();
	}

	/**
	 * 获取临时图片目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getTemp(Context c) {

		return FileUtils.getPicturesStorageDir(c, "temp").getAbsolutePath();
	}

	/**
	 * 获取外部缓存目录
	 * 
	 * @param context
	 * @return
	 */
	public static String getExternalCachePath(Context context) {
		return FileUtils.getExternalCacheDir(context);
	}
	/**
	 * 获取头像目录
	 * 
	 * @param context
	 * 
	 */
	public static String getHeadFaceParh(Context context) {
		return getExternalCachePath(context) + File.separator + "hd";

	}
}

