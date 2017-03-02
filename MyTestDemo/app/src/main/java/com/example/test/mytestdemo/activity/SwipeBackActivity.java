package com.example.test.mytestdemo.activity;

import android.os.Bundle;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.SwipeBackBaseActivity;

/**
 * 右滑关闭
 */
public class SwipeBackActivity extends SwipeBackBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_back);
    }
}
