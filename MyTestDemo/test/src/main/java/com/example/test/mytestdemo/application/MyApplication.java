package com.example.test.mytestdemo.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.multidex.MultiDex;

import com.example.test.mytestdemo.notifications.NotificationUtil;

import java.util.List;


public class MyApplication extends Application {

    private static MyApplication appContext;
    private boolean isBackground;

    public static MyApplication getInstance() {
        return appContext;
    }

    public static long t1;


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        t1 = System.currentTimeMillis(); // 取得当前时间
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());


        listenForForeground();
        listenForScreenTurningOff();

    }


    private void listenForForeground() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
                if (isBackground) {
                    notifyForeground();
                }
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                isApplicationBroughtToBackground(activity);
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });
    }

    private void listenForScreenTurningOff() {//锁屏
        IntentFilter screenStateFilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // notifyBackground();
            }
        }, screenStateFilter);
    }


    private void notifyForeground() {
        isBackground = false;
        NotificationUtil.getInstance().clearAllNotification();
    }

    private void notifyBackground(Activity activity) {
        isBackground = true;
        NotificationUtil _notifyUtils = NotificationUtil.getInstance();
        _notifyUtils.init(this.getApplicationContext(), activity.getClass());
        _notifyUtils.showNotification("退到后台");
    }


    /**
     * 判断当前应用程序处于前台还是后台
     */
    public boolean isApplicationBroughtToBackground(Activity activity) {
        try {
            ActivityManager am = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
            if (am != null) {
                List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
                if (!tasks.isEmpty()) {
                    ComponentName topActivity = tasks.get(0).topActivity;
                    if (!topActivity.getPackageName().equals(activity.getPackageName())) {
                        notifyBackground(activity);
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public boolean isBackground() {
        return isBackground;
    }


}
