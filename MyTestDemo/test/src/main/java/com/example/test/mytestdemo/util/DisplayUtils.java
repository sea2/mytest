package com.example.test.mytestdemo.util;

import android.content.Context;

/**
 * Created by lhy on 2017/12/26.
 */

public class DisplayUtils {


    private static  DisplayUtils instance = null;
    private Context mContext;

    private DisplayUtils(Context context) {        //这里变化了，把当前Context指向个应用程序的Context
        this.mContext = context.getApplicationContext();
    }

    public static DisplayUtils getInstance(Context context) {
        if (instance != null) {
            synchronized (DisplayUtils.class) {
                if (instance != null) {
                    instance = new DisplayUtils(context);
                }
            }
        }
        return instance;
    }

    public int dip2px(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
