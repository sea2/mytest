package com.example.test.mytestdemo.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;

import com.example.test.mytestdemo.application.MyApplication;
import com.example.test.mytestdemo.util.DensityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class BitmapUtils {



    /**
     * 将拍照得到的图片按照取景框（亮色区域）的范围进行裁剪.
     * 对于1280x720的屏幕，裁剪起始点为坐标(52, 80)，裁剪后，位图尺寸为896x588.（由layout xml定义的布局计算得到）
     * 以上参数将按照图片的实际大小，进行等比例换算。
     * 设备有无虚拟导航栏均能裁剪到正确的区域。
     *
     * @param originalBitmap 拍照得到的Bitmap
     * @return 裁剪之后的Bitmap
     */
    public static Bitmap crop(Bitmap originalBitmap) {
        double originalWidth = originalBitmap.getWidth();
        double originalHeight = originalBitmap.getHeight();

        int phoneHeight = ScreenUtils.getScreenHeight();
        int phoneWidth = ScreenUtils.getScreenWidth();
        float widthRate = (float) (originalWidth / phoneHeight);
        float hightRate = (float) (originalHeight / phoneWidth);

        int x = (int) (DensityUtils.dp2px(MyApplication.getInstance(), 80) * widthRate);
        int y = (int) (DensityUtils.dp2px(MyApplication.getInstance(), 45) * hightRate);
        //在1280x720的设计图上，裁剪区域大小为896x588
        int width = (int) originalWidth - 2 * x;
        int height = (int) originalHeight - 2 * y;
        return Bitmap.createBitmap(originalBitmap, x, y, width, height);
    }

    /**
     * 若图片宽小于高，则逆时针旋转90° ; 否则，返回原图片.
     * 适用于调用系统相机进行拍照，且希望图片总是横向的场景；
     * Bitmap占用内存较大，建议先压缩到一个分辨率级别，再进行旋转。
     *
     * @param sourceBitmap 拍照得到的Bitmap
     * @return 若图片宽小于高，返回逆时针旋转90°后的Bitmap ; 否则，返回原Bitmap.
     */
    public static Bitmap rotate(Bitmap sourceBitmap) {
        int sourceWidth = sourceBitmap.getWidth();
        int sourceHeight = sourceBitmap.getHeight();
        //若原图的宽大于高，不需要旋转，直接返回原图
        if (sourceWidth >= sourceHeight) return sourceBitmap;
        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        return Bitmap.createBitmap(sourceBitmap, 0, 0, sourceWidth, sourceHeight, matrix, true);
    }

    /**
     * 将图片文件压缩到所需的大小，返回位图对象.
     * 若原图尺寸小于需要压缩到的尺寸，则返回原图.
     * 该方法通过分辨率面积得到压缩比，因此不存在图片方向、宽高比的问题.
     *
     * @param filePath        File
     * @param reqSquarePixels 要压缩到的分辨率（面积）
     * @return 压缩后的Bitmap
     */
    public static Bitmap compressToResolution(File filePath, long reqSquarePixels) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath.toString(), options);
        double compressRatio = calculateCompressRatioBySquare(options, reqSquarePixels);
        //解析原图
        Bitmap fullBitmap = BitmapFactory.decodeFile(filePath.toString());
        //创建缩放的Bitmap。这里不用inSampleSize，inSampleSize基于向下采样，节省了Bitmap读入后占用的内存，但Bitmap本身的像素还是那么多，文件大小没有改变。
        return Bitmap.createScaledBitmap(fullBitmap, (int) (options.outWidth / compressRatio), (int) (options.outHeight / compressRatio), false);
    }

    /**
     * 计算压缩比.考虑了options的长宽比可能与req中的比例不同的情况.
     * 该方法通过分辨率面积得到压缩比，因此不存在图片方向、宽高比的问题.
     *
     * @param options         BitmapFactory.Options
     * @param reqSquarePixels 压缩后的分辨率面积
     * @return 计算得到的压缩比
     */
    public static double calculateCompressRatioBySquare(BitmapFactory.Options options, long reqSquarePixels) {
        //原图像素数
        long squarePixels = options.outWidth * options.outHeight;
        //若原图像素数少于需要压缩到的像素数，不压缩（压缩比返回1）
        if (squarePixels <= reqSquarePixels) return 1;
        //面积之比，即压缩比（长度之比）的平方
        double powwedScale = ((double) squarePixels) / ((double) reqSquarePixels);
        //开根号得压缩比
        return Math.sqrt(powwedScale);
    }

    /**
     * 将Bitmap写入文件，文件位于内置存储上该应用包名目录的cache子目录中.
     *
     * @param bitmap   要写入的Bitmap
     * @param fileName 文件名
     * @return 文件对应的File.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File writeBitmapToFile(Bitmap bitmap, String fileName) {
        //FileProvider中指定的目录
        File dir = new File(MyApplication.getInstance().getCacheDir(), "images");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, fileName);
        FileOutputStream fos;
        try {
            if (file.exists()) file.delete();
            fos = new FileOutputStream(file);
            //JPEG为硬件加速，O(1)时间复杂度，而PNG为O(n)，速度要慢很多，WEBP不常用
            //90%的品质已高于超精细(85%)的标准，已非常精细
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.flush();
            bitmap.recycle();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }


    /**
     * 压缩图片显示
     */
    public static Bitmap comp(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        if (baos.toByteArray().length / 1024 > 1024) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        //开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        newOpts.inJustDecodeBounds = false;
        int w = newOpts.outWidth;
        int h = newOpts.outHeight;
        float hh = 100f;//这里设置高度为100f
        float ww = 100f;//这里设置宽度为100f
        int be = 1;//be=1表示不缩放
        if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放
            be = (int) (newOpts.outWidth / ww);
        } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放
            be = (int) (newOpts.outHeight / hh);
        }
        if (be <= 0) be = 1;
        newOpts.inSampleSize = be;//设置缩放比例
        //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        isBm = new ByteArrayInputStream(baos.toByteArray());
        bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);
        return compressImage(bitmap);//压缩好比例大小后再进行质量压缩
    }

    public static Bitmap compressImage(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }


    /*1. 质量压缩
        设置bitmap options属性，降低图片的质量，像素不会减少
        第一个参数为需要压缩的bitmap图片对象，第二个参数为压缩后图片保存的位置
        设置options 属性0-100，来实现压缩*/
    public static void compressImageToFile(Bitmap bmp, File file) {
        // 0-100 100为不压缩
        int options = 100;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bmp.compress(Bitmap.CompressFormat.JPEG, options, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*2. 尺寸压缩
       通过缩放图片像素来减少图片占用内存大小*/
    public static void compressBitmapToFile(Bitmap bmp, File file) {
        // 尺寸压缩倍数,值越大，图片尺寸越小
        int ratio = 2;
        // 压缩Bitmap到对应尺寸
        Bitmap result = Bitmap.createBitmap(bmp.getWidth() / ratio, bmp.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, bmp.getWidth() / ratio, bmp.getHeight() / ratio);
        canvas.drawBitmap(bmp, null, rect, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        result.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*  设置图片的采样率，降低图片像素*/
    public static void compressBitmap(String filePath, File file) {
        // 数值越高，图片像素越低
        int inSampleSize = 2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        //采样率
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, options);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 把压缩后的数据存放到baos中
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(baos.toByteArray());
            fos.flush();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
