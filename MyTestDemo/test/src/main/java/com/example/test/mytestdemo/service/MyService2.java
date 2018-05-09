package com.example.test.mytestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

public class MyService2 extends Service {

    public static final int MAX_PROGRESS = 100;

    private int progress = 0;

    private ServiceTestActivity.MyHandler myHandler;


    public int getProgress() {
        return progress;
    }

    public static final String TAG = "MyService2";
    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, "onCreate() executed");


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(TAG, "onBind() executed");
        return mBinder;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestroy() executed");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    public class MyBinder extends Binder {

        public MyService2 getService() {
            return MyService2.this;
        }


        public void startDownLoad() {
            Logger.d("", "startDownLoad() inBinder-->");
            new Thread(new Runnable() {
                public void run() {
                    while (progress < MAX_PROGRESS) {
                        progress += 5;
                        Logger.d("", "startDownLoad() run-->");
                        //进度发生变化通知调用方
                        if (myHandler != null) {
                            myHandler.sendEmptyMessage(progress);
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

        public void setCallBackHandler(ServiceTestActivity.MyHandler mHandler) {
            myHandler = mHandler;
        }

        public void startDialog() {
            //自定义一个dialog属性的activty

        }
    }


}