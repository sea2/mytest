package com.test.lhy.doubleprocess;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

public class LocalService extends Service {


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            IMyAidlInterface iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
            try {
                Log.i("LocalService", "connected with " + iMyAidlInterface.getServiceName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(LocalService.this, "链接断开，重新启动 RemoteService", Toast.LENGTH_LONG).show();
            startService(new Intent(LocalService.this, RemoteService.class));
            bindService(new Intent(LocalService.this, RemoteService.class), connection, Context.BIND_IMPORTANT);
        }
    };

    public LocalService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //设置为前台进程
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            notification = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setTicker("有通知到来")
                    .setContentTitle("这是通知的标题")
                    .setContentText("这是通知的内容")
                    .setOngoing(true)
                    .setContentIntent(pendingIntent)
                    .build();
        }
        /*使用startForeground,如果id为0，那么notification将不会显示*/
        startForeground(1, notification);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "LocalService 启动", Toast.LENGTH_LONG).show();
        startService(new Intent(LocalService.this, RemoteService.class));
        bindService(new Intent(this, RemoteService.class), connection, Context.BIND_IMPORTANT);
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        RemoteService.MyBinder mBinder = new RemoteService.MyBinder();
        return mBinder;
    }

    private class MyBinder extends IMyAidlInterface.Stub {

        @Override
        public String getServiceName() throws RemoteException {
            return LocalService.class.getName();
        }

    }


    @Override
    public void onDestroy() {
        stopForeground(true);

        //发起广播启动服务
        sendBroadcast(new Intent("com.broadcast.watch_service"));

        super.onDestroy();
    }


}
