package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;
import com.example.test.mytestdemo.util.AESUtil;
import com.example.test.mytestdemo.utils.AppUtils;

public class WelcomeActivity extends BaseActivity {
    private int counInt = 0;
    private Button btnstop;
    private android.widget.TextView tvtimeend;
    private final int delayedTime = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        String channl_name = AppUtils.getManifestMetaDataValue("CHANNEL_NAME_TEST");
        String channl_value = AppUtils.getManifestMetaDataValue("CHANNEL_VALUE_TEST");
        initView();
        initListener();
        initData();


        String  s = "站在大明门前守卫的禁卫军，事先没有接到\n" +
                "有关的命令，但看到大批盛装的官员来临，也就\n" +
                "以为确系举行大典，因而未加询问。进大明门即\n" +
                "为皇城。文武百官看到端门午门之前气氛平静，\n" +
                "城楼上下也无朝会的迹象，既无几案，站队点名\n" +
                "的御史和御前侍卫“大汉将军”也不见踪影，不免\n" +
                "心中揣测，互相询问：所谓午朝是否讹传？";
        System.out.println("s:" + s);

        String s1 = AESUtil.encrypt(s, "1234");
        System.out.println("s1:" + s1);

        System.out.println("s2:"+AESUtil.decrypt(s1, "1234"));

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

    private Handler myHandler = new Handler(Looper.getMainLooper()) {
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
