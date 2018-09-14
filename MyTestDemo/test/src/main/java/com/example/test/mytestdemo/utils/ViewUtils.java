package com.example.test.mytestdemo.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.test.mytestdemo.application.MyApplication;

import java.text.DecimalFormat;

public class ViewUtils {



    /**
     * 计算图片要显示的高度
     *
     * @param pixel 原始分辨率
     * @param width 要显示的宽度
     * @return
     */
    public static int calcPhotoHeight(String pixel, int width) {
        int height = -1;
        int index = pixel.indexOf("*");
        if (index != -1) {
            try {
                int widthPixel = Integer.parseInt(pixel.substring(0, index));
                int heightPixel = Integer.parseInt(pixel.substring(index + 1));
                height = (int) (heightPixel * (width * 1.0f / widthPixel));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return -1;
            }
        }

        return height;
    }


    /**
     * 获取控件宽度（适用于LinearLayout,View）
     *
     * @param view
     * @return
     */
    public static int getWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        try {
            view.measure(w, h);
        } catch (Exception e) {
            return 0;
        }
        return (view.getMeasuredWidth());
    }

    /**
     * 获取控件高度（适用于LinearLayout,View）
     *
     * @param view
     * @return
     */
    public static int getHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        try {
            view.measure(w, h);
        } catch (Exception e) {
            return 0;
        }
        return (view.getMeasuredHeight());
    }

    /****************************
     * 获取屏幕宽高
     ***************************/
    private static DisplayMetrics displayMetrics = null;

    /**
     * 获取屏幕高
     *
     * @param activity
     * @return
     */
    public static int getScreenHeight(Activity activity) {
        if (null == displayMetrics) {
            displayMetrics = new DisplayMetrics();
        }
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenHeight() {
        if (null == displayMetrics) {
            displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        }
        return displayMetrics.heightPixels;
    }

    //物理尺寸
    public static String getDeviceScreenSizeOf() {
        try {
            DisplayMetrics dm = MyApplication.getInstance().getResources().getDisplayMetrics();
            DecimalFormat df = new DecimalFormat("######0.0");
            double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
            double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
            double screenInches = Math.sqrt(x + y);
            return df.format(screenInches);
        } catch (Exception e) {
            return "0";
        }
    }

    public static double getScreenSizeOfDevice() {
        DisplayMetrics dm = MyApplication.getInstance().getResources().getDisplayMetrics();
        //        int width=dm.widthPixels;
        //        int height=dm.heightPixels;
        //        double x = Math.pow(width,2);
        //        double y = Math.pow(height,2);
        //        double diagonal = Math.sqrt(x+y);

        int dens = dm.densityDpi;
        return /*diagonal/(double)*/dens;
    }

    /**
     * 获取屏幕宽
     *
     * @param activity
     * @return
     */
    public static int getScreenWidth(Activity activity) {
        if (null == displayMetrics) {
            displayMetrics = new DisplayMetrics();
        }
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

    public static int getScreenWidth() {
        if (null == displayMetrics) {
            displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        }
        return displayMetrics.widthPixels;
    }

    /**************************** 以上是获取屏幕宽高 ***************************/
    /**
     * 手机天线高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        java.lang.reflect.Field field = null;
        int x = 0;
        int statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = MyApplication.getInstance().getResources().getDimensionPixelSize(x);
            return statusBarHeight;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusBarHeight;
    }

    /**
     * 重置view高度
     *
     * @param view
     */
    public static void resetStatusTitle(View view) {
        int statusHeight = getStatusBarHeight();
        view.setPadding(0, statusHeight, 0, 0);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        if (null == displayMetrics) {
            displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        }
        final float scale = displayMetrics.density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        if (null == displayMetrics) {
            displayMetrics = MyApplication.getInstance().getResources().getDisplayMetrics();
        }
        final float scale = displayMetrics.density;
        return (int) (pxValue / scale + 0.5f);
    }
    public static void setImageLayout(ImageView imageView) {
        if (imageView != null){

            ViewGroup.LayoutParams imageParams = imageView.getLayoutParams();
            if (imageParams != null){
                int screenWidth = ViewUtils.getScreenWidth();

                imageParams.width = screenWidth;
                imageParams.height = (int) (screenWidth / /*(scale <= 1  ? */4.464f/*:scale)*/);
                imageView.setLayoutParams(imageParams);
            }

        }

    }

    public static void setImageLayout(ImageView imageView, Bitmap loadedImage) {

        if (imageView != null){

            ViewGroup.LayoutParams imageParams = imageView.getLayoutParams();
            if (imageParams != null){
                int screenWidth = ViewUtils.getScreenWidth();

                imageParams.width = screenWidth;
                imageParams.height = (int) (screenWidth / /*(scale <= 1  ? */((loadedImage.getWidth()*1f)/loadedImage.getHeight()*1f)/*:scale)*/);
                imageView.setLayoutParams(imageParams);
            }

        }
    }
}
