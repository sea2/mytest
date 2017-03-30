package com.example.test.mytestdemo.app;

import android.os.Bundle;

import com.jude.swipbackhelper.SwipeBackHelper;

/**
 * Created by Administrator on 2017/2/15.
 */

public class SwipeBackBaseActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(true).setSwipeSensitivity(0.5f).setSwipeRelateEnable(true).setSwipeRelateOffset(300);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    public void onResume() {
        super.onResume();
    }
}