package com.example.test.mytestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService2 extends Service {

    public static final int MAX_PROGRESS = 100;

    private int progress = 0;

    private MyHandler myHandler;

    public void setHandler(MyHandler handler) {
        myHandler = handler;
    }

    public int getProgress() {
        return progress;
    }

    public static final String TAG = "MyService2";
    private MyBinder mBinder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate() executed");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() executed");
        return mBinder;
    }


    public class MyBinder extends Binder {

        public MyService2 getService() {
            return MyService2.this;
        }


        public void startDownLoad() {
            Log.d("", "startDownLoad() inBinder-->");
            new Thread(new Runnable() {
                public void run() {
                    while (progress < MAX_PROGRESS) {
                        progress += 5;
                        Log.d("", "startDownLoad() run-->");
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

        public void setCallBackHandler(ServiceTestActivity.MyHandler myHandler) {
            myHandler = myHandler;
        }
    }


}