package com.example.test.mytestdemo.util;

/**
 * 工具类
 *
 * @author TangWei 2014-1-13上午10:59:15
 */
public class Utils {

    /**
     * 判断 一个字段的值否为空
     *
     * @param s
     * @return
     * @author Michael.Zhang 2013-9-7 下午4:39:00
     */
    public static boolean isNull(String s) {
        if (null == s || s.equals("") || s.equalsIgnoreCase("null")) {
            return true;
        }

        return false;
    }


}
