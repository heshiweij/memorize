package com.xju.memorize.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 
 * 本地存储工具
 * @author Yunlongx Luo
 * 
 */
public class SharedPreferencesUtil {

	/** SharedPreferences */
	private static SharedPreferences mSharedPreferences;

	
	public static void setSharedPreferencesData(Context mContext, String mKey,
                                                String mValue) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putString(mKey, mValue).commit();
	}

	public static void setSharedPreferencesData(Context mContext, String mKey, int mValue){
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putInt(mKey, mValue).commit();
	}
	public static void setSharedPreferencesData(Context mContext, String mKey, boolean mValue) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		mSharedPreferences.edit().putBoolean(mKey, mValue).commit();
	}
	
	public static String getSharedPreferencesData(Context mContext, String mKey) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getString(mKey, "");
	}
	
	public static Integer getIntSharedPreferencesData(Context mContext, String mKey) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getInt(mKey, -1);
	}
	public static boolean getBooleanSharedPreferencesData(Context mContext, String mKey, boolean defaultValue) {
		if (null == mSharedPreferences) {
			mSharedPreferences = mContext.getSharedPreferences(
					"memorize", Context.MODE_PRIVATE);
		}
		return mSharedPreferences.getBoolean(mKey, defaultValue);
	}

}
