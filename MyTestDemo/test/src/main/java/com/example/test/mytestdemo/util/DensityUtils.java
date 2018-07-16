package com.example.test.mytestdemo.util;


import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.example.test.mytestdemo.utils.Utils;

/**
 * 如果修改了系统字体大小，sp和dp就不同了。注意要用getResources()获取。
 */
public class DensityUtils {
    private DensityUtils() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }


    /**
     * density
     *
     * @param context
     * @return
     */
    public static float density(Context context) {
        if (context != null) return context.getResources().getDisplayMetrics().density;
        else return 0;
    }


    /**
     * 根据设计稿标注得到等比例的尺寸，屏幕适配
     *
     * @param needDesignPxSize        需要设计宽度
     * @param designScreenWidthPxSize 设计稿宽度
     * @return
     */
    public static float getEndSize(float needDesignPxSize, float designScreenWidthPxSize) {
        float endSize = 0;
        WindowManager windowManager = (WindowManager) Utils.getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();// 创建了一张白纸
        if (windowManager != null) {
            windowManager.getDefaultDisplay().getMetrics(dm);// 给白纸设置宽高
            float widthPixels = dm.widthPixels;
            float rate = widthPixels / designScreenWidthPxSize;
            endSize = needDesignPxSize * rate;
        }
        return endSize;
    }


}
