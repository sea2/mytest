package com.xcm91.relation.glide;

import android.util.DisplayMetrics;

import java.lang.reflect.Field;


/***
 *
 * @author yhl 下午3:46:38 2014-5-16 屏幕管理类
 */
public class ScreenUtil {

    private int height;
    private int width;
    private float density;
    private int densityDpi;
    private ScreenUtil mScreenUtil = null;


    public ScreenUtil() {
        DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        // 设置单位为像素单位
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0 /3.0）
        densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240 /320 /480）
    }

    /**
     * int 返回屏幕高度
     */
    public int getHeight() {
        if (height == 0) {
            DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
            // 设置单位为像素单位
            return metrics.heightPixels;
        } else {
            return height;
        }
    }

    /**
     * int 返回屏幕宽度
     */
    public int getWidth() {
        if (density == 0) {
            DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
            // 设置单位为像素单位
            return metrics.widthPixels;
        } else {
            return width;
        }
    }

    /**
     *
     */
    public float getDensity() {
        if (density == 0) {
            DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
            // 设置单位为像素单位
            return metrics.density;
        } else {
            return density;
        }
    }

    /**
     *
     */
    public int getDensityDpi() {
        if (densityDpi == 0) {
            DisplayMetrics metrics = MyApplication.getInstance().getResources().getDisplayMetrics();
            // 设置单位为像素单位
            return metrics.densityDpi;
        } else {
            return densityDpi;
        }
    }

    /**
     * 将dip转成px
     */
    public int dip2px(float dipValue) {
        return (int) (dipValue * getDensity() + 0.5f);
    }

    public int sp2px(float spValue) {
        return (int) (spValue * getDensity() + 0.5f);
    }

    /**
     * 获得屏幕宽度
     */
    public int getScreenHeight() {
        return getHeight() - getStatusBarHeight();// 天线高度
    }


    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = MyApplication.getInstance().getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            statusBarHeight = 25;
        }
        return statusBarHeight;
    }


}
