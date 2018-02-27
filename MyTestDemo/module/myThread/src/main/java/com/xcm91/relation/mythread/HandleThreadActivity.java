package com.xcm91.relation.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class HandleThreadActivity extends Activity {

    private android.widget.TextView tvinfo;
    StringBuffer sb = new StringBuffer();
    int TIME = 1000;
    Timer timer;
    private Button btnthreadstop;
    private Button btnhandlerstop;
    private Button btntimerstop;
    int thread_count = 0;
    int handler_count = 0;
    int timer_count = 0;
    boolean thread_flag = true;
    private Button btnlooper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_thread);
        this.btnlooper = (Button) findViewById(R.id.btn_looper);
        this.btntimerstop = (Button) findViewById(R.id.btn_timer_stop);
        this.btnhandlerstop = (Button) findViewById(R.id.btn_handler_stop);
        this.btnthreadstop = (Button) findViewById(R.id.btn_thread_stop);
        this.tvinfo = (TextView) findViewById(R.id.tv_info);

        //-------------------Handler的发起-----------------------------------------------------------------------------------------
        mhandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //此处是UI主线程不能耗时操作
                sb.append("postDelayed\n");
                tvinfo.setText(sb.toString());
            }
        }, 1000);


        mhandler.post(new Runnable() {
            @Override
            public void run() {
                sb.append("post\n");
                tvinfo.setText(sb.toString());
            }
        });

        mhandler.sendEmptyMessageDelayed(100, 2000);
        mhandler.sendEmptyMessage(200);

        Message message = new Message();
        message.what = 250;
        Bundle mb = new Bundle();
        mb.putString("mbData", "通过message的setData传递Bundle");
        message.setData(mb);
        mhandler.sendMessage(message);

        mhandler.sendEmptyMessageAtTime(300, 2000);

        //Thread循环
        mThread.start();

        //handler循环
        handler.postDelayed(runnable, TIME); //每隔1s执行

        //timer循环
        timer = new Timer();
        timer.schedule(mTimerTask, 0, 1000); // 1s后执行task,经过1s再次执行


        initListener();
    }


    private Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg == null) return;
            switch (msg.what) {
                case 100:
                    sb.append("sendEmptyMessageDelayed\n");
                    tvinfo.setText(sb.toString());
                    break;
                case 200:
                    sb.append("sendEmptyMessage\n");
                    tvinfo.setText(sb.toString());
                case 300:
                    sb.append("sendEmptyMessageAtTime\n");
                    tvinfo.setText(sb.toString());
                    break;
                case 250:
                    String info_message = msg.getData().getString("mbData");
                    sb.append(info_message + "info_message\n");
                    tvinfo.setText(sb.toString());
                    break;
                case 1000:
                    thread_count++;
                    btnthreadstop.setText("thread" + thread_count);
                    break;
            }
        }
    };


    Thread mThread = new Thread(new Runnable() {
        @Override
        public void run() {

            while (thread_flag) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (mhandler != null) mhandler.sendEmptyMessage(1000);
            }
        }
    });


    private static class MainLooperHandler extends Handler {
        public MainLooperHandler() {
            super(Looper.getMainLooper());
        }

        @Override
        public void handleMessage(Message msg) {
        }
    }


    Handler handler = new Handler();
    Runnable runnable = new Runnable() {

        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, TIME);
                handler_count++;
                btnhandlerstop.setText("handler循环" + handler_count);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            //里面不是UI主线程，要返回到UI主线程执行UI更新
            btntimerstop.post(new Runnable() {
                @Override
                public void run() {
                    timer_count++;
                    btntimerstop.setText("TimerTask循环" + handler_count);
                }
            });
        }
    };


    private void initListener() {
        btnthreadstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mThread != null) {
                    thread_flag = false;
                    mThread.interrupt();
                    mThread = null;
                }

            }
        });
        btntimerstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer != null) {
                    timer.cancel();
                    if (mTimerTask != null) mTimerTask.cancel();
                }
            }
        });
        btnhandlerstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);
                }
            }
        });
        btnlooper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (handler != null && runnable != null) {
                    handler.removeCallbacks(runnable);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mThread != null) {
            mThread.interrupt();
            mThread = null;
        }


        if (mhandler != null) {
            // mhandler.removeCallbacks();
            mhandler.removeMessages(100);
            mhandler = null;
        }
    }


}
