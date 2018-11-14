package com.spidertool;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by lhy on 2018/11/1.
 */
public class DownLoadImage implements Runnable {
    private String url;
    private GlideUrl glideUrl;
    private Context context;
    private File currentFile;
    private Bitmap bitmap = null;

    public DownLoadImage(Context context, String url) {
        this.url = url;
        this.context = context;
    }

    public DownLoadImage(Context context, GlideUrl glideUrl) {
        this.glideUrl = glideUrl;
        this.context = context;
    }

    @Override
    public void run() {
        try {

            FutureTarget<File> target = Glide.with(context)
                    .asFile()
                    .load(glideUrl)
                    .submit();
            final File file = target.get();
            Log.i("tag", file.getAbsolutePath());
            copy(file);

            if (bitmap != null) {
                // 在这里执行图片保存方法
                saveImageToGallery(context, bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void copy(File source) {
        File file = Environment.getExternalStorageDirectory().getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "DCIM";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);


        FileInputStream fileInputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            fileInputStream = new FileInputStream(source);
            fileOutputStream = new FileOutputStream(currentFile);
            byte[] buffer = new byte[1024];
            while (fileInputStream.read(buffer) > 0) {
                fileOutputStream.write(buffer);
            }

            MediaStore.Images.Media.insertImage(context.getContentResolver(), currentFile.getAbsolutePath(), fileName, null);
            MediaScannerConnection.scanFile(context, new String[]{file.getAbsolutePath()}, null, null);

            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "ningjing";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            //保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
