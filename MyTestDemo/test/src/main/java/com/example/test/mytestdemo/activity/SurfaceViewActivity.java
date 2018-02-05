package com.example.test.mytestdemo.activity;

import android.app.Activity;
import android.os.Bundle;

import com.example.test.mytestdemo.ui.MySurfaceView;

/**
 * Created by lhy on 2018/1/30.
 * SurfaceView内部实现双缓冲机制很好的解决这个问题，具体是使用主线程来负责UI的显示和渲染线程做UI的绘制，
 * 两个线程交替进行，这样两个线程显示界面的效率非常快，当然对内存和cpu的开销也是非常大的。
 */

public class SurfaceViewActivity extends Activity {


    private MySurfaceView mMySurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //实例化SurfaceView
        mMySurfaceView = new MySurfaceView(this);
        setContentView(mMySurfaceView);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
