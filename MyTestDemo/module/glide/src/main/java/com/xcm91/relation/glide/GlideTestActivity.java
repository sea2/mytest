package com.xcm91.relation.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BaseTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.SizeReadyCallback;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.xcm91.relation.glide.imageload.ImageLoader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.bumptech.glide.Glide.with;
import static com.xcm91.relation.glide.GlideUtil.getFileSize;


/**
 * 参考  http://blog.csdn.net/dickyqie/article/details/65935993
 */
public class GlideTestActivity extends Activity {

    private ImageView iv2;
    private ImageView iv1;
    private ImageView iv3;
    private ImageView iv4;
    private ImageView iv5;
    private ImageView iv6;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_glide_test);

        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);

        ViewTreeObserver vto2 = iv6.getViewTreeObserver();
        vto2.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Log.e("glide", iv6.getMeasuredWidth() + "----" + iv6.getMeasuredHeight());
            }
        });
        iv6.post(new Runnable() {
            @Override
            public void run() {
                Log.e("glide", iv6.getWidth() + "----");
            }
        });


        String url = "";

        String url2 = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ae4e87268d94a4c21e2eef68669d71a0/7c1ed21b0ef41bd5d5a88edd5bda81cb39db3d1b.jpg";

        String url3 = "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg";

        ImageLoader.getIntance(this).bindBitmap(url3, iv1);

        //加入header的Url
        GlideUrl glideUrl = new GlideUrl("http://i.meizitu.net/thumbs/2018/10/154998_18a30_236.jpg", new LazyHeaders.Builder()
                .addHeader("Referer", "http://www.mzitu.com")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0")
                .addHeader("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                .addHeader("Accept-Language", "zh-cn,zh;q=0.5")
                .addHeader("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7")
                .addHeader("Connection", "keep-alive")
                .build());
        Glide.with(this).load(glideUrl).into(iv1);


        // 加载本地图片
        File file = new File(getExternalCacheDir() + "/image.jpg");
        // 加载应用资源
        int resource = R.drawable.abc_ab_share_pack_mtrl_alpha;
        // 加载二进制流
        byte[] image;
        // 加载Uri对象
        Uri imageUri;
        // 加载网络  GlideUrl 或者String url;

        with(this).
                load(R.drawable.widget_bar_user_press).
                listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }
                }).into(iv2);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.actionbar_add_icon)                //加载成功之前占位图
                .error(R.drawable.actionbar_add_icon)                    //加载错误之后的错误图
                .override(400, 400)                                //指定图片的尺寸
                .override(Target.SIZE_ORIGINAL)
                //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
                .fitCenter()
                //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
                .centerCrop()
                .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
                .skipMemoryCache(true)                            //跳过内存缓存
                //.diskCacheStrategy(DiskCacheStrategy.ALL)        //缓存所有版本的图像
                //.diskCacheStrategy(DiskCacheStrategy.NONE)        //跳过磁盘缓存
                //.diskCacheStrategy(DiskCacheStrategy.DATA)        //只缓存原来分辨率的图片
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);    //只缓存最终的图片
        // 设置加载中以及加载失败图片
        with(this).
                load(url2).apply(options).
                into(iv3);


        //加载监听
        with(this).
                load(url).
                into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                    }
                });


        for (int i = 1; i <= 5; i++) {
            getImageCacheAsyncTask mgetImageCacheAsyncTask = new getImageCacheAsyncTask(this);
            mgetImageCacheAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, url2);
        }


        Log.e("tag", GlideUtil.getCacheSize(this));

        //图片等比展示
        bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.widget_bar_user_press);
        float height = bitmap.getHeight();
        float width = bitmap.getWidth();
        float screenWidth = new ScreenUtil().getWidth();
        float rale = screenWidth / width;
        float drawableHeigthEnd = rale * height;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) drawableHeigthEnd);
        iv5.setLayoutParams(params);
        iv5.setScaleType(ImageView.ScaleType.FIT_XY);
        iv5.setImageBitmap(bitmap);
        /***
         *  Glide默认 asDrawable()  SimpleTarget<>默和Glide默认的保持一致
         *  */
        //图片适配，等比例放大缩小
        Glide.with(this).
                asBitmap().
                load(url3).
                into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        // 取 drawable 的长宽
                        int width = resource.getWidth();
                        int height = resource.getHeight();
                        //图片加载完成
                        float screenWidth = new ScreenUtil().getWidth();
                        float rale = screenWidth / width;
                        float drawableHeigthEnd = rale * height;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, (int) drawableHeigthEnd);
                        iv6.setLayoutParams(params);
                        iv6.setScaleType(ImageView.ScaleType.FIT_XY);
                        iv6.setImageBitmap(resource);
                    }

                });
        
        
        
        
   /* 4.7.1版本    RequestOptions options = new RequestOptions()
        .placeholder(R.mipmap.ic_launcher)				//加载成功之前占位图
        .error(R.mipmap.ic_launcher)					//加载错误之后的错误图
        .override(400,400)								//指定图片的尺寸
        //指定图片的缩放类型为fitCenter （等比例缩放图片，宽或者是高等于ImageView的宽或者是高。）
        .fitCenter()
        //指定图片的缩放类型为centerCrop （等比例缩放图片，直到图片的狂高都大于等于ImageView的宽度，然后截取中间的显示。）
        .centerCrop()
        .circleCrop()//指定图片的缩放类型为centerCrop （圆形）
        .skipMemoryCache(true)							//跳过内存缓存
        .diskCacheStrategy(DiskCacheStrategy.ALL)		//缓存所有版本的图像
        .diskCacheStrategy(DiskCacheStrategy.NONE)		//跳过磁盘缓存
        .diskCacheStrategy(DiskCacheStrategy.DATA)		//只缓存原来分辨率的图片
        .diskCacheStrategy(DiskCacheStrategy.RESOURCE);	//只缓存最终的图片

        Glide.with(this)
        .load(url)
        .apply(options)
        .into(imageView);
*/

    }


    @Override
    protected void onDestroy() {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
            bitmap = null;
        }
        super.onDestroy();
    }


    /**
     * 获取指定url路径
     */
    private class getImageCacheAsyncTask extends AsyncTask<String, Void, File> {

        private final Context context;

        public getImageCacheAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected File doInBackground(String... params) {
            String imgUrl = params[0];
            try {
                FutureTarget<File> target = Glide.with(context)
                        .asFile()
                        .load(imgUrl)
                        .submit();
                FutureTarget<Bitmap> targetBitmap = Glide.with(context)
                        .asBitmap()
                        .load(imgUrl)
                        .submit();
                final File imageFile = target.get();
                return imageFile;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(File result) {
            if (result == null) {
                return;
            }
            //此path就是对应文件的缓存路径
            String path = result.getPath();
            try {
                Log.e("path", path + "--" + String.valueOf(getFileSize(result)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 获取指定url路径
     */
    private class getBitmapAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private final Context context;

        public getBitmapAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String imgUrl = params[0];
            try {
                return null;
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) {
                return;
            }
        }
    }


    private void downImg(String imgUrl) {

        Glide.with(this).asBitmap().load(imgUrl).into(new BaseTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {

            }

            @Override
            public void getSize(@NonNull SizeReadyCallback cb) {

            }

            @Override
            public void removeCallback(@NonNull SizeReadyCallback cb) {

            }
        });


    }


    // 保存图片到手机指定目录
    public void savaBitmap(String imgName, byte[] bytes) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = null;
            FileOutputStream fos = null;
            try {
                filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyImg";
                File imgDir = new File(filePath);
                if (!imgDir.exists()) {
                    imgDir.mkdirs();
                }
                imgName = filePath + "/" + imgName;
                fos = new FileOutputStream(imgName);
                fos.write(bytes);
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


  /*  3.7.0
   http://blog.csdn.net/wbwjx/article/details/51239627
   with():指定了声明周期
    load():加载资源，String/Uri/File/Integer/URL/byte[]/T,或者 loadFromMediaStore(Uri uri)
    placeholder(resourceId/drawable)： 设置资源加载过程中的占位Drawable。
    error()：load失败时显示的Drawable。
    crossFade()/crossFade(int duration)：imageView改变时的动画，version 3.6.1后默认开启300毫秒
    dontAnimate()：移除所有的动画。
    override() ：调整图片大小  Target.SIZE_ORIGINAL默认尺寸
    centerCrop()：图片裁剪，ImageView 可能会完全填充，但图像可能不会完整显示。
    fitCenter()： 图像裁剪，图像将会完全显示，但可能不会填满整个 ImageView。
    animate(): 指定加载动画。
    transform():图片转换。
    bitmapTransform(): bitmap转换，不可以和(centerCrop() 或 fitCenter())共用。
    priority(Priority priority):当前线程的优先级,Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL(default)，Priority.LOW
    thumbnail(): 缩略图.
            listener():异常监听

    preload(int width, int height): 预加载resource到缓存中（单位为pixel）
    Glide.with(this).load("https://www.baidu.com/img/bd_logo1.png")     .preload();

    fallback(Drawable drawable):设置model为空时显示的Drawable。
    using() ：为单个的请求指定一个 model
    asGif()：Gif 检查，如果是图片且加了判断，则会显示error占位图，否则会显示图片
    asBitmap()：bitmap转化，如果是gif，则会显示第一帧*/


}




