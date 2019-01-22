package com.xcm91.relation.mythread;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class ThreadLocalActivity extends AppCompatActivity {
    private ThreadLocal<Integer> integerThreadLocal = new ThreadLocal<Integer>();
    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv=new TextView(this);
        setContentView(tv);

        Log.e(TAG, "[Thread--main] 初始值integerThreadLocal=" + integerThreadLocal.get());
        Log.e(TAG, "[Thread--main] 设置integerThreadLocal=0");
        integerThreadLocal.set(0);
        Log.e(TAG, "[Thread--main] 获取get值integerThreadLocal=" + integerThreadLocal.get());

        new Thread("Thread--1") {
            @Override
            public void run() {
                Log.e(TAG, "[Thread--1] 初始值integerThreadLocal=" + integerThreadLocal.get());
                Log.e(TAG, "[Thread--1] 设置integerThreadLocal=1");
                integerThreadLocal.set(1);
                Log.e(TAG, "[Thread--1] 获取get值integerThreadLocal=" + integerThreadLocal.get());
            }
        }.start();


        new Thread("Thread--2") {
            @Override
            public void run() {
                Log.e(TAG, "[Thread--2] 初始值integerThreadLocal=" + integerThreadLocal.get());
                Log.e(TAG, "[Thread--2] 设置integerThreadLocal=2");
                integerThreadLocal.set(2);
                Log.e(TAG, "[Thread--2] 获取get值integerThreadLocal=" + integerThreadLocal.get());
            }
        }.start();
    }


}

