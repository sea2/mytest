package com.example.test.mytestdemo.inject;

import android.app.Activity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by lhy on 2017/5/26.
 */

public class ViewUtil {


    public static void injectContentView(Activity activity) {
        Class a = activity.getClass();
        if (a.isAnnotationPresent(ContentView.class)) {
            // 得到activity这个类的ContentView注解
            ContentView contentView = (ContentView) a.getAnnotation(ContentView.class);
            // 得到注解的值
            int layoutId = contentView.value();
            // 使用反射调用setContentView
            try {
                Method method = a.getMethod("setContentView", int.class);
                method.setAccessible(true);
                method.invoke(activity, layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


    public static void injectViews(Activity activity) {
        Class a = activity.getClass();
        // 得到activity所有字段
        Field[] fields = a.getDeclaredFields();
        // 得到被ViewInject注解的字段
        for (Field field : fields) {
            if (field.isAnnotationPresent(ViewInject.class)) {
                // 得到字段的ViewInject注解
                ViewInject viewInject = field.getAnnotation(ViewInject.class);
                // 得到注解的值
                int viewId = viewInject.value();
                // 使用反射调用findViewById，并为字段设置值
                try {
                    Method method = a.getMethod("findViewById", int.class);
                    method.setAccessible(true);
                    Object resView = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity, resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }


}
