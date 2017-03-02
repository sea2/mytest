package com.example.test.mytestdemo.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {


    public static final String TAG = "MyService";

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
        return null;
    }

}