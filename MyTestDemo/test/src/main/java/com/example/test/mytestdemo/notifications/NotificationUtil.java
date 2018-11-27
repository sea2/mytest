package com.example.test.mytestdemo.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.utils.AppUtils;

/**
 * desc:NotificationUtils
 * Author: znq
 * Date: 2016-09-19 14:45
 */
public class NotificationUtil {
    private NotificationManager mNotificationManager;
    private Notification mNotification;
    private Intent mNotificationIntent;

    /**
     * 饿汉式单例
     */
    private static NotificationUtil _instance = new NotificationUtil();
    private Context mContext;

    public static NotificationUtil getInstance() {
        return _instance;
    }

    public void init(Context context, @NonNull Class<?> intentActivity) {
        mContext = context;
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationIntent = new Intent(mContext, intentActivity);
        mNotificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

    }

    public void showNotification(@NonNull String contentText) {
        int notifyId = 1;
        PendingIntent _pendingIntent = PendingIntent.getActivity(mContext, notifyId, mNotificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(AppUtils.getAppName(mContext))
                    .setContentText(contentText)
                    .setSubText(mContext.getString(R.string.notice_click_into))
                    .setPriority(Notification.PRIORITY_HIGH)
                    .setVibrate(new long[]{0, 0, 0, 10})
                    //.setFullScreenIntent(_pendingIntent, true)
                    .setContentIntent(_pendingIntent);
        } else {
            builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(AppUtils.getAppName(mContext))
                    .setContentText(contentText)
                    .setSubText(mContext.getString(R.string.notice_click_into))
                    .setVibrate(new long[]{0, 0, 0, 10})
                    //.setFullScreenIntent(_pendingIntent, true)
                    .setContentIntent(_pendingIntent);
        }
        //在什么状态下显示通知,比如锁屏状态
        if (Build.VERSION.SDK_INT >= 21) {
            builder.setVisibility(Notification.VISIBILITY_PUBLIC);
            builder.setCategory(Notification.CATEGORY_MESSAGE);
        }
        mNotification = builder.build();
        mNotificationManager.notify(notifyId, mNotification);
    }


    //清除所有推送通知
    public void clearAllNotification() {
        if (mNotificationManager != null)
            mNotificationManager.cancelAll();
    }

}
