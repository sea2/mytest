package com.example.test.mytestdemo.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;
import com.example.test.mytestdemo.utils.StringUtils;
import com.example.test.mytestdemo.utils.ToastUtils;

/**
 * 总结:

 1)静态注册：在AndroidManifest.xml注册，android不能自动销毁广播接收器，也就是说当应用程序关闭后，还是会接收广播。
 2)动态注册：在代码中通过registerReceiver()手工注册.当程序关闭时,该接收器也会随之销毁。当然，也可手工调用unregisterReceiver()进行销毁。

 android:enabled:
 这个属性用于定义系统是否能够实例化这个广播接收器，如果设置为true，则能够实例化，如果设置为false，则不能被实例化。默认值是true。

 android:exported:
 这个属性用于指示该广播接收器是否能够接收来自应用程序外部的消息，
 */
public class BroadcastActivity extends BaseActivity {

    private android.widget.Button btnbroadcast;
    private android.widget.RelativeLayout activitybroadcast;
    private Button btnbroadcast1;
    private Button btnbroadcast2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast);
        this.btnbroadcast2 = (Button) findViewById(R.id.btn_broadcast2);
        this.btnbroadcast1 = (Button) findViewById(R.id.btn_broadcast1);
        this.activitybroadcast = (RelativeLayout) findViewById(R.id.activity_broadcast);
        this.btnbroadcast = (Button) findViewById(R.id.btn_broadcast);

        btnbroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //与清单文件的receiver的anction对应
                intent.setAction("com.broadcast.test");
                intent.putExtra("info", "测试静态注册广播");
                //发送广播
                sendBroadcast(intent);
            }
        });


        btnbroadcast1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //与清单文件的receiver的anction对应
                intent.setAction("com.test.login");
                intent.putExtra("name", "测试动态注册广播--登录");
                //发送广播
                sendBroadcast(intent);
            }
        });
        btnbroadcast2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                //与清单文件的receiver的anction对应
                intent.setAction("com.test.logout");
                intent.putExtra("name", "测试动态注册广播--退出");
                //发送广播
                sendBroadcast(intent);
            }
        });

        //动态注册广播
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.test.logout");// 退出动作
        filter.addAction("com.test.login");// 登录动作
        registerReceiver(acReceiver, filter);

    }


    // -------------------------广播接收器-------------------------
    private BroadcastReceiver acReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (!StringUtils.isEmpty(action)) {
                if (action.equals(getPackageName())) {
                    finish();
                } else if (action.equals("com.test.login")) {
                    ToastUtils.showLongToast("登录" + intent.getStringExtra("name"));
                } else if (action.equals("com.test.logout")) {
                    ToastUtils.showLongToast("退出" + intent.getStringExtra("name"));
                }
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除广播
        if (acReceiver != null) unregisterReceiver(acReceiver);
    }
}
