package com.xcm91.relation.glide.imageload;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;

/**
 * Created by lhy on 2018/3/27.
 */

public class ImageResizer {

    public ImageResizer() {
    }

    public Bitmap resizeBitmapFromResources(Resources res, int resId, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        //设置为true之后，不为bitmap分配内存，返回null，但是options会被赋值，以此来获取图片大小
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //计算出合适的缩放比例
        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public Bitmap resizeBitmapFromFileDescriptor(FileDescriptor fd, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd, null, options);
        options.inSampleSize = caculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFileDescriptor(fd, null, options);
    }

    public int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        if (reqWidth == 0 || reqHeight == 0) {
            return 1;
        }

        int inSampleSize = 1;
        //图片的原始宽高
        final int originWidth = options.outWidth;
        final int originHeight = options.outHeight;

        if (originWidth > reqWidth || originHeight > reqHeight) {
            final int halfWidth = originWidth / 2;
            final int halfHeight = originHeight / 2;
            //计算宽高都达到预期标准时的inSampleSize，根据文档描述，一般为2的倍数
            while ((halfWidth / inSampleSize) > reqWidth && (halfHeight / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }
}
