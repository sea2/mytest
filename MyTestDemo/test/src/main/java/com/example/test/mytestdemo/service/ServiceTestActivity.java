package com.example.test.mytestdemo.service;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.activity.MainActivity;
import com.example.test.mytestdemo.app.BaseActivity;

public class ServiceTestActivity extends BaseActivity {

    private android.widget.Button btnstartservice;
    private android.widget.Button btnstopservice;
    private android.widget.TextView tvservice1;
    private android.widget.Button btnbindservice;
    private android.widget.Button btnunbindservice;
    private android.widget.TextView tvservice2;
    private android.widget.LinearLayout activityservicetest;
    private MyHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_test);
        this.activityservicetest = (LinearLayout) findViewById(R.id.activity_service_test);
        this.tvservice2 = (TextView) findViewById(R.id.tv_service2);
        this.btnunbindservice = (Button) findViewById(R.id.btn_unbindservice);
        this.btnbindservice = (Button) findViewById(R.id.btn_bindservice);
        this.tvservice1 = (TextView) findViewById(R.id.tv_service1);
        this.btnstopservice = (Button) findViewById(R.id.btn_stopservice);
        this.btnstartservice = (Button) findViewById(R.id.btn_startservice);
        myHandler = new MyHandler(this);

        btnstartservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//开启   //要是service启动过不在调用onCtreate,调用onStartCommon
                /*Intent it = new Intent(ServiceTestActivity.this, MyService.class);
                startService(it);*/
                Intent it = new Intent(ServiceTestActivity.this, MainActivity.class);
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startService(it);
            }
        });
        btnstopservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭
                Intent it = new Intent(ServiceTestActivity.this, MyService.class);
                stopService(it);
            }
        });


        btnbindservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//开启  bindService多次调用无效  调用者一旦退出，服务也就终止
                Intent it = new Intent(ServiceTestActivity.this, MyService2.class);
                bindService(it, connection, Context.BIND_AUTO_CREATE);
            }
        });
        btnunbindservice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//关闭
              /*  unbindService(connection);
                connection = null;*/
                myBinder.startDialog();
            }
        });


    }


    private MyService2.MyBinder myBinder;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myBinder = (MyService2.MyBinder) service;
            myBinder.setCallBackHandler(myHandler);
        }
    };


    public class MyHandler extends BaseHandler {
        public MyHandler(Object o) {
            super(o);
        }

        @Override
        public void handleMessage(Message msg, Object o) {

        }
    }


}
