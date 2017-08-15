package com.example.test.mytestdemo.service;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Created by lhy on 2016/8/16.
 */
public abstract class BaseHandler<T> extends Handler {

    private WeakReference<T> mWeakReference;

    public BaseHandler(T t) {
        super();
        mWeakReference = new WeakReference<T>(t);
    }

    @Override
    public void handleMessage(Message msg) {
        T t = mWeakReference.get();
        if (t != null) {
            handleMessage(msg, t);
        }
    }

    public abstract void handleMessage(Message msg, T t);
}