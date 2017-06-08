package com.xcm91.relation.mythread;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LooperActivity extends AppCompatActivity {

    private android.widget.Button btnloopermain;
    private android.widget.Button btnlooperchild;
    private android.widget.TextView tvmain;
    private android.widget.TextView tvchild;
    private Handler handler;
    private MyThread.ThreadHandler mThreadHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_looper);
        this.tvchild = (TextView) findViewById(R.id.tv_child);
        this.tvmain = (TextView) findViewById(R.id.tv_main);
        this.btnlooperchild = (Button) findViewById(R.id.btn_looper_child);
        this.btnloopermain = (Button) findViewById(R.id.btn_looper_main);


        btnloopermain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Looper looper = Looper.getMainLooper(); //主线程的Looper对象
                //这里以主线程的Looper对象创建了handler，
                //所以，这个handler发送的Message会被传递给主线程的MessageQueue。
                handler = new MainHandler(looper);
                handler.removeMessages(0);
                //构建Message对象
                //第一个参数：是自己指定的message代号，方便在handler选择性地接收*-+999999999
                //第二三个参数没有什么意义
                //第四个参数需要封装的对象
                Message msg = handler.obtainMessage(1,1,1,"主线程发消息了");

                handler.sendMessage(msg); //发送消息
            }
        });

        btnlooperchild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //可以看出这里启动了一个线程来操作消息的封装和发送的工作
                //这样原来主线程的发送就变成了其他线程的发送，简单吧？呵呵
                new MyThread().start();
            }
        });
    }


    class MainHandler extends Handler{

        public MainHandler(Looper looper){
            super(looper);
        }

        public void handleMessage(Message msg){
            super.handleMessage(msg);
            tvmain.setText("我是主线程的Handler，收到了消息："+(String)msg.obj);
        }
    }


    class MyThread extends Thread{

        public void run(){
            Looper.prepare(); //创建该线程的Looper对象，用于接收消息  

            //注意了：这里的handler是定义在主线程中的哦，呵呵，  
            //前面看到直接使用了handler对象，是不是在找，在什么地方实例化的呢？  
            //现在看到了吧？？？呵呵，开始的时候实例化不了，因为该线程的Looper对象  
            //还不存在呢。现在可以实例化了  
            //这里Looper.myLooper()获得的就是该线程的Looper对象了  
            mThreadHandler = new ThreadHandler(Looper.myLooper());

            //这个方法，有疑惑吗？  
            //其实就是一个循环，循环从MessageQueue中取消息。  
            //不经常去看看，你怎么知道你有新消息呢？？？  
            Looper.loop();

        }

        //定义线程类中的消息处理类  
        class ThreadHandler extends Handler{

            public ThreadHandler(Looper looper){
                super(looper);
            }

            public void handleMessage(Message msg){
                Looper looper = Looper.getMainLooper(); //主线程的Looper对象
                //这里以主线程的Looper对象创建了handler，
                //所以，这个handler发送的Message会被传递给主线程的MessageQueue。
                handler = new MainHandler(looper);

                //构建Message对象
                //第一个参数：是自己指定的message代号，方便在handler选择性地接收
                //第二三个参数没有什么意义
                //第四个参数需要封装的对象
                Message msgs = handler.obtainMessage(1,1,1,"其他线程发消息了");

                handler.sendMessage(msgs); //发送消息
            }
        }
    }







}
