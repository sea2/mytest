package com.example.test.mytestdemo.Interface;

import android.view.View;

/**
 * 处理重复点击，代理的方式
 * Created by lhy on 2018/11/12.
 * <p>
 * mButton.setOnClickListener(new ClickProxy(new View.OnClickListener() {
 *
 * @Override public void onClick(View v) {
 * //to do
 * }
 * }));
 */
public class ClickProxy implements View.OnClickListener {

    private View.OnClickListener origin;
    private long last_click = 0;
    private long time_ms = 1000; //ms
    private IAgain mIAgain;


    public ClickProxy(View.OnClickListener origin, long time_ms, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.time_ms = time_ms;
    }

    public ClickProxy(View.OnClickListener origin) {
        this.origin = origin;
    }

    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - last_click >= time_ms) {
            origin.onClick(v);
            last_click = System.currentTimeMillis();
        } else {//重复点击
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }


}
