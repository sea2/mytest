package com.xcm91.relation.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
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

    public ScreenUtil(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // 设置单位为像素单位
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0 /3.0）
        densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240 /320 /480）
    }

    public ScreenUtil(Context ct) {
        DisplayMetrics metrics = ct.getResources().getDisplayMetrics();
        // 设置单位为像素单位
        height = metrics.heightPixels;
        width = metrics.widthPixels;
        density = metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0 /3.0）
        densityDpi = metrics.densityDpi; // 屏幕密度DPI（120 / 160 / 240 /320 /480）
    }

    /**
     * int 返回屏幕高度
     */
    public static int getHeight(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 设置单位为像素单位
        return metrics.heightPixels;
    }

    /**
     * int 返回屏幕宽度
     */
    public static int getWidth(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 设置单位为像素单位
        return metrics.widthPixels;
    }

    /**
     *
     */
    public float getDensity(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 设置单位为像素单位
        return metrics.density; // 屏幕密度（0.75 / 1.0 / 1.5 /2.0 /3.0）
    }

    /**
     *
     */
    public int getDensityDpi(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        // 设置单位为像素单位
        return metrics.densityDpi;
    }

    /**
     * 将dip转成px
     *
     * @param dipValue
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {

        final float scale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * scale + 0.5f);
    }

    /**
     * 获得屏幕宽度
     *
     * @return
     */
    public static int getScreenHeight(Context context) {
        return getHeight(context) - getStatusBarHeight(context);// 天线高度
    }


    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            statusBarHeight = 25;
        }
        return statusBarHeight;
    }


    /**
     * 用于获取状态栏的高度。
     *
     * @return 返回状态栏高度的像素值。
     */
    public static int getStatusBarHeight2(Context context) {
        int statusBarHeight = 0;
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object o = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(o);
            statusBarHeight = context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
            Rect frame = new Rect();
            ((Activity) context).getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
            statusBarHeight = frame.top;
            if (statusBarHeight == 0) statusBarHeight = 25;
        }
        return statusBarHeight;
    }


}
