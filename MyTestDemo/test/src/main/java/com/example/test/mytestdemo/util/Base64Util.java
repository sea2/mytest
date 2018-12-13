package com.example.test.mytestdemo.util;

import android.util.Base64;

import java.io.UnsupportedEncodingException;


/**
 * Created by lhy on 2017/7/18.
 */

public class Base64Util {


    // 加密
    public static String encodeBase64(byte[] strByte) {
        String result = "";
        if (strByte != null) {
            try {
                result = new String(Base64.encode(strByte, Base64.DEFAULT), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String encodeBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.encode(str.getBytes("utf-8"), Base64.DEFAULT), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // 解密
    public static String decodeBase64(String str) {
        String result = "";
        if (str != null) {
            try {
                result = new String(Base64.decode(str, Base64.DEFAULT), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static byte[] decodeBase64ToBytes(String str) {
        if (str != null) {
            try {
                return Base64.decode(str, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        } else return null;
    }


}
