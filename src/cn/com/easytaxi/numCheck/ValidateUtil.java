package cn.com.easytaxi.numCheck;

import java.text.ParseException;
import java.util.regex.Pattern;

public class ValidateUtil {

	public static String REGEX_MOBILE = "^1(3|4|5|7|8)[0-9]{9}$";
	public static String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	public static String REGEX_NAME = "^\\w{1,10}$";
	public static CheckIDFormat checkIdCard = new CheckIDFormat();
	private static Pattern PATTERN_MOBILE = Pattern.compile(REGEX_MOBILE);
	private static Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);

	/**
	 * 验证是否是手机
	 */
	public static boolean isMobile(String mobile) {
		return PATTERN_MOBILE.matcher(mobile).matches();
	}

	/**
	 * 是否是邮箱
	 * 
	 * @param email
	 * @return
	 */
	public static boolean isEmail(String email) {
		return PATTERN_EMAIL.matcher(email).matches();
	}

	/**
	 * 身份证验证
	 * 
	 * @return 有效：true 无效：false
	 */
	public static boolean isIdCardNumber(String idcard) {
		try {
			return checkIdCard.IDCardValidate(idcard);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static boolean len(String m, int min, int max) {
		return m.length() >= min && m.length() <= max;
	}

	public static boolean minValue(String m, int minValue) {
		return Integer.parseInt(m) >= minValue;
	}

	public static boolean maxValue(String m, int maxValue) {
		return Integer.parseInt(m) <= maxValue;
	}

	/**
	 * 验证是否为空
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.trim().length() == 0 || str.trim().toLowerCase().equals("null");
	}

	/**
	 * 验证是否为空
	 */
	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

}
