package com.xcm91.relation.mvp;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

/**
 * Created by lhy on 2017/10/18.
 */

public class MyApplication  extends Application{




    @Override
    public void onCreate() {
        super.onCreate();

        LeakCanary.install(this);
    }
}
