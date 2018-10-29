package com.example.test.mytestdemo.Interface;

import android.view.View;

/**
 * 重复点击，按钮的重复点击
 * Created by lhy on 2018/9/28.
 */
public class IClickListener implements View.OnClickListener {

    private View.OnClickListener origin;
    private long lastclick = 0;
    private long timems = 1000; //ms
    private IAgain mIAgain;

    public IClickListener(View.OnClickListener origin, long timems, IAgain again) {
        this.origin = origin;
        this.mIAgain = again;
        this.timems = timems;
    }

    public IClickListener(View.OnClickListener origin) {
        this.origin = origin;
    }



    @Override
    public void onClick(View v) {
        if (System.currentTimeMillis() - lastclick >= timems) {
            origin.onClick(v);
            lastclick = System.currentTimeMillis();
        } else {
            if (mIAgain != null) mIAgain.onAgain();
        }
    }

    public interface IAgain {
        void onAgain();//重复点击
    }

}

