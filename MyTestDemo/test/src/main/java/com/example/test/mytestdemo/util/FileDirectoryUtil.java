package com.example.test.mytestdemo.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/2/22.
 */

public class FileDirectoryUtil {

    private static String myfilepath = "myfile";

    public static File getOwnFileDirectory(Context context, String fileDir) {
        File appFileDir = null;
        if ("mounted".equals(Environment.getExternalStorageState()) && hasExternalStoragePermission(context)) {
            appFileDir = new File(Environment.getExternalStorageDirectory(), fileDir);
        }

        if (appFileDir == null || !appFileDir.exists() && !appFileDir.mkdirs()) {
            if (Build.VERSION.SDK_INT >= 19) appFileDir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            else {
                if (hasExternalStoragePermission(context)) {
                    appFileDir = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileDir);
                } else appFileDir = context.getFilesDir();
            }
        }

        if (appFileDir == null || !appFileDir.exists() && !appFileDir.mkdirs()) {
            appFileDir = context.getFilesDir();
        }

        return appFileDir;
    }

    public static File getOwnFileDirectory(Context context) {
        return getOwnFileDirectory(context, myfilepath);
    }


    public static File getOwnCacheDirectory(Context context, String cacheDir) {
        File appCacheDir = null;
        if (Build.VERSION.SDK_INT >= 19) appCacheDir = context.getExternalCacheDir();
        else {
            if (hasExternalStoragePermission(context)) {
                appCacheDir = new File(context.getExternalCacheDir(), cacheDir);
            } else appCacheDir = context.getCacheDir();
        }

        if (appCacheDir == null || !appCacheDir.exists() && !appCacheDir.mkdirs()) {
            appCacheDir = context.getCacheDir();
        }
        return appCacheDir;
    }

    public static File getOwnCacheDirectory(Context context) {
        return getOwnCacheDirectory(context, myfilepath);
    }


    private static boolean hasExternalStoragePermission(Context context) {
        int perm = context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE");
        return perm == 0;
    }






    /**
     * 将输入流写入文件
     */
    private static void writeFile(InputStream inputString, String pathDirectorStr, String fileName) {
        try {
            //创建目录
            File fileDirector = new File(pathDirectorStr);
            if (!fileDirector.exists()) {
                fileDirector.mkdirs();
            }

            //创建文件
            File file = new File(pathDirectorStr.concat(fileName));
            if (file.exists()) {
                file.delete();
            }

            FileOutputStream fos = null;

            fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = inputString.read(b)) != -1) {
                fos.write(b, 0, len);
            }
            inputString.close();
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
