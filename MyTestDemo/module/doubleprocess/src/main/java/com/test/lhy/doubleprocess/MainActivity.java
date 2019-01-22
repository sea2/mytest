package com.test.lhy.doubleprocess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**  启动
     * @param view
     */
    public void start(View view) {
        startService(new Intent(this, LocalService.class));
    }

    /**停止本地服务
     * @param view
     */
    public void stop_local(View view) {
        stopService(new Intent(this, LocalService.class));
    }

    /**停止守护服务
     * @param view
     */
    public void stop_remote(View view) {
        stopService(new Intent(this, RemoteService.class));
    }
}
