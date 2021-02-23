package com.xju.memorize.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatchStringUtil {

	public static boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

	public static boolean isChinese(String text) {
		// 只能输入汉字
		if (!text.matches("[\u4e00-\u9fa5]+")) {
			return false;
		}
		return true;
	}

	// 座机判断
	public static boolean isTelePhoneNum(String text) {
		// 只能输入汉字
		if (text.length() != 11) {
			return false;
		}
		return true;
	}

	/**
	 * 验证手机号码
	 * 
	 * @param phoneNumber
	 *            手机号码
	 * @return boolean
	 */
	public static boolean checkPhoneNumber(String phoneNumber) {
		if (phoneNumber.length() != 11) {
			return false;
		}else {
			return true;
		}
		// Pattern pattern = Pattern.compile("^1[0-9]{10}$");
//		Pattern pattern = Pattern
//				.compile("^(0\\d{2,3}-\\d{7,8}(-\\d{3,5}){0,1})|(((13[0-9])|(15([0-3]|[5-9]))|(1[7-8][0,3,5-9]))\\d{8})$");
//		Matcher matcher = pattern.matcher(phoneNumber);
//		return matcher.matches();
	}

	public static boolean isUrl(String text){
		if(!text.matches("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")){
			return false;
		}
		return true;
	}


	/**
	 * //获取完整的域名
	 *
	 * @param text 获取浏览器分享出来的text文本
	 */
	public static String getCompleteUrl(String text) {
		Pattern p = Pattern.compile("((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&%_\\./-~-]*)?", Pattern.CASE_INSENSITIVE);
		Matcher matcher = p.matcher(text);
		matcher.find();
		return matcher.group();
	}
}
