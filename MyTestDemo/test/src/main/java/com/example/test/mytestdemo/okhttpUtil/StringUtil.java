package com.example.test.mytestdemo.okhttpUtil;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * 
 * @author xiaocaimi@xcm.com
 *
 */
public class StringUtil {
	/**
	 * 判断一个字符串不是空的（服务可能会传null作为字符串内空，将null认为是空的）
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNotEmpty(String arg) {
		if (null == arg) {
			return false;
		}
		if ("".equals(arg) || "".equals(arg.trim())) {
			return false;
		}
		if ("NULL".equals(arg.toUpperCase(Locale.CHINA))) {
			return false;
		}
		return true;
	}

	public static boolean isEmpty(String str) {
		if (null == str || "".equals(str)) {
			return true;
		} else if ("".equals(str.trim())) {
			return true;
		} else if ("".equals(replaceBlank(str))) {
			return true;
		}
		return false;
	}

	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\\\\r|\\\\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 判断一个字符串不是空的（服务可能会传null作为字符串内空，将null认为是空的）
	 * 
	 * @param arg
	 * @return
	 */
	public static boolean isNULL(String arg) {
		if (null == arg) {
			return true;
		}
		if ("".equals(arg) || "".equals(arg.trim())) {
			return true;
		}
		if ("NULL".equals(arg.toUpperCase(Locale.CHINA))) {
			return true;
		}
		return false;
	}

	/**
	 * 去掉右边的零
	 * 
	 * @param str
	 * @return
	 */
	public static String trimRightZero(String str) {
		if (StringUtil.isNotEmpty(str) && str.indexOf(".") > 0) {
			String temp = str.trim();
			temp = temp.replaceAll("([0])*$", "");// 去掉末位为0
			temp = temp.replaceAll("(\\.)$", "");// 去掉末位为.
			return temp;
		}
		return str;
	}

	/**
	 * 去掉左边的零
	 * 
	 * @param strValue
	 * @return
	 */
	public static String trimLeftZero(String strValue) {
		if (null != strValue && strValue.matches("(0)(\\d)+")) {
			return strValue.replaceAll("^(0)+", "");
		}
		return strValue;
	}

	/**
	 * 在字符串判断是否存在匹配的字符
	 * 
	 * @param paramStr
	 *            要查找的字符串
	 * @param subStr
	 *            待匹配的字符
	 * @return
	 */
	public static boolean isExistSubString(String paramStr, String subStr) {
		if (null == paramStr || null == subStr) {
			return false;
		}
		if (paramStr.indexOf(subStr) >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * 在字符串判断是否存在匹配的字符
	 * 
	 * @param paramStr
	 *            要查找的字符串
	 * @param subStr
	 *            待匹配的字符
	 * @return
	 */
	public static boolean isExistSubStringOrBlank(String paramStr, String subStr) {
		if (null == paramStr || null == subStr) {
			return true;
		}
		if (paramStr.indexOf(subStr) >= 0) {
			return true;
		}
		return false;
	}

	public static boolean isEquals(String s1, String s2) {
		if (null == s1 && null == s2) {
			return true;
		}
		if (null != s1 && null != s2) {
			if (s1.trim().equals(s2.trim())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean isEqualsAndNotNull(String s1, String s2) {
		if (null == s1 || null == s2) {
			return false;
		}
		if (null != s1 && null != s2) {
			if (s1.trim().equals(s2.trim())) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
