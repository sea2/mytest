package com.example.test.mytestdemo.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

/**
 * Created by lhy on 2017/1/13.
 */

public class ThreadActivity extends BaseActivity {


    private android.widget.Button btnnotification;
    private android.widget.Button btnwebview;
    private android.widget.Button btnhorizontalscrollView;
    private android.widget.Button btnthread;
    private android.widget.ProgressBar pbtest;
    private int progressInt = 0;
    private DialogHelper task;
    private boolean flag;
    private Button btnbroadcast;
    private Button btnaidl;
    private Button btnservice;
    private Button btnswipeback;
    private Button btnanimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_layout);
        this.btnanimation = (Button) findViewById(R.id.btn_animation);
        this.btnswipeback = (Button) findViewById(R.id.btn_swipeback);
        this.btnservice = (Button) findViewById(R.id.btn_service);
        this.btnaidl = (Button) findViewById(R.id.btn_aidl);
        this.btnbroadcast = (Button) findViewById(R.id.btn_broadcast);
        this.pbtest = (ProgressBar) findViewById(R.id.pb_test);
        pbtest.setVisibility(View.VISIBLE);
        this.btnthread = (Button) findViewById(R.id.btn_thread);
        this.btnhorizontalscrollView = (Button) findViewById(R.id.btn_horizontalscrollView);
        this.btnwebview = (Button) findViewById(R.id.btn_webview);
        this.btnnotification = (Button) findViewById(R.id.btn_notification);
        Log.e("tag", Thread.currentThread().getName());


        btnthread.setText("开启线程");
        btnhorizontalscrollView.setText("关闭线程");
        btnthread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mthread.start();
              /*  mthread2.start();
                mthread2.start();*/
            }
        });

        btnhorizontalscrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mhandler = null;
                mthread.interrupt();
            }
        });

     /*   task = new DialogHelper();
        task.execute(1);*/


    }


    //终止线程的办法
    Thread mthread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted() && flag) {
                try {
                    Log.e("tag", Thread.currentThread().getName());
                    Thread.sleep(3000);
                } catch (Exception err) {
                    err.printStackTrace();
                }


                Log.e("tag", mthread.isInterrupted() + "");
                // 通过Handler获得Message对象
                if (mhandler != null) {
                    Message msg = mhandler.obtainMessage();
                    msg.obj = "任务执行完毕";
                    // 发送到Handler，在UI线程里处理Message
                    mhandler.sendEmptyMessage(1);
                }
            }
        }
    });


    //同步和非同步线程测试
    Thread mthread2 = new Thread(new Runnable() {
        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("tag", i + "--未同步-");
            }


            synchronized (this) {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Log.e("tag", i + "--同步-");
                }
            }
        }
    });


    Handler mhandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg != null) {
                switch (msg.what) {
                    case 1:
                        showToast("呵呵" + 1);
                        break;
                }
            }
        }
    };

    /**
     * 1、 AsyncTask的本质是一个静态的线程池，AsyncTask派生出的子类可以实现不同的异步任务，这些任务都是提交到静态的线程池中执行。
     * 2、线程池中的工作线程执行doInBackground(mParams)方法执行异步任务
     * 3、当任务状态改变之后，工作线程会向UI线程发送消息，AsyncTask内部的InternalHandler响应这些消息，并调用相关的回调函数
     */
    private class DialogHelper extends AsyncTask<Integer, Void, Integer> {

        @Override
        protected void onPreExecute() {
            // 该方法是运行在主线程中的,执行早于下面的方法
            // showToast("开始进度");
        }


        @Override
        protected Integer doInBackground(Integer... params) {

            if (isCancelled()) return null;

            switch (params[0]) {
                case 1:
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    progressInt++;
                    return 1;

                default:
                    return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                }
            });
            switch (result) {
                case 1:
                    if (progressInt < 100) {
                        task = new DialogHelper();
                        task.execute(1);
                    } else {
                        showToast("完成进度");
                    }

                    Log.e("进度", "" + progressInt);
                    pbtest.setProgress(progressInt);
                    break;

                default:
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mhandler = null;
        mthread.interrupt();

        if (task != null && task.getStatus() == AsyncTask.Status.RUNNING) {
            task.cancel(true); // 如果Task还在运行，则先取消它
        }
    }


}
