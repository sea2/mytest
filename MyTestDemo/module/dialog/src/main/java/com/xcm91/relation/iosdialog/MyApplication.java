package com.xcm91.relation.iosdialog;

import android.app.Application;
import android.util.Log;

/**
 * Created by lhy on 2018/3/12.
 */

public class MyApplication extends Application {


    public static MyApplication instance;

    @Override
    public void onCreate() {
        Log.i("time", "application_start");
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        if (instance == null)
            instance = new MyApplication();
        return instance;
    }


}
