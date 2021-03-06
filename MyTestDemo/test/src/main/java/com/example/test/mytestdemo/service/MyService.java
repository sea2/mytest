package com.example.test.mytestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.orhanobut.logger.Logger;

public class MyService extends Service {


    public static final String TAG = "MyService";

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.i(TAG, "onCreate() executed");
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.i(TAG, "onStartCommand() executed");
        Intent mintent = new Intent();
        //与清单文件的receiver的anction对应
        intent.setAction("com.test.login");
        intent.putExtra("name", "测试动态注册广播--登录");
        //发送广播
        sendBroadcast(mintent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i(TAG, "onDestroy() executed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Logger.i(TAG, "onCreate() executed");
        return null;
    }

}