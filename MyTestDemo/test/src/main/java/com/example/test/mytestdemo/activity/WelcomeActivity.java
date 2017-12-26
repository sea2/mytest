package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    private int counInt = 0;
    private Button btnstop;
    private android.widget.TextView tvtimeend;
    private int delayedTime = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        initListener();
        initData();
    }

    protected void initView() {
        this.tvtimeend = (TextView) findViewById(R.id.tv_time_end);
        this.btnstop = (Button) findViewById(R.id.btn_stop);
    }

    protected void initListener() {
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(MainActivity.class);
                finish();
            }
        });

        


    }

    protected void initData() {
        myHandler.sendEmptyMessageDelayed(1, 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (null != msg) {
                switch (msg.what) {
                    case 1:
                        counInt++;
                        tvtimeend.setText(String.valueOf(counInt));
                        if (counInt >= delayedTime) {
                            if (counInt == delayedTime) {
                                startAc(MainActivity.class);
                                finish();
                            }
                        } else myHandler.sendEmptyMessageDelayed(1, 1000);
                        break;
                    default:
                        break;
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (myHandler != null) {
            myHandler.removeMessages(1);
        }
    }
}
