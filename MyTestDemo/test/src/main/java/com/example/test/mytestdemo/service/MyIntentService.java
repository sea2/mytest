package com.example.test.mytestdemo.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import static com.example.test.mytestdemo.application.CrashHandler.TAG;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class MyIntentService extends IntentService {
    /**
     * 是否正在运行
     */
    private boolean isRunning;

    /**
     * 进度
     */
    private int count;


    public MyIntentService() {
        super("MyIntentService");
        Log.e("TAG", "MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.e("TAG", "onHandleIntent");
        try {
            Thread.sleep(1000);
            isRunning = true;
            count = 0;
            while (isRunning) {
                count++;
                if (count >= 100) {
                    isRunning = false;
                }
                Thread.sleep(50);
                sendThreadStatus("线程运行中...", count);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送进度消息
     */
    private void sendThreadStatus(String status, int progress) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "线程结束运行..." + count);
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand() executed");
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "onBind() executed");
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }


    private MyBinder mBinder = new MyBinder();


    public class MyBinder extends Binder {

        public MyIntentService getService() {
            return MyIntentService.this;
        }




        public void setCallBackHandler(ServiceTestActivity.MyHandler myHandler) {
            myHandler = myHandler;
        }

        public void startDialog() {
            //自定义一个dialog属性的activty

        }
    }

}
