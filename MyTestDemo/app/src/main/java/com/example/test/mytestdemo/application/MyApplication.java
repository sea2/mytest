package com.example.test.mytestdemo.application;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;


public class MyApplication extends Application {

    private static MyApplication appContext;

    public static MyApplication getInstance() {
        return appContext;
    }

    public static long t1;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        t1 = System.currentTimeMillis(); // 取得当前时间
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }


}
