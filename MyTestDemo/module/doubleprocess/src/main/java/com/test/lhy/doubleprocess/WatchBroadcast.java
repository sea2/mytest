package com.test.lhy.doubleprocess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by lhy on 2018/12/28.
 */
public class WatchBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, "广播启动LocalService服务!", Toast.LENGTH_SHORT).show();

        Intent service = new Intent(context, LocalService.class);
        //在广播中启动服务必须加上startService(intent)的修饰语。Context是对象
        context.startService(service);


    }

}
