package com.xcm91.relation.glide;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

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


        String url = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ae4e87268d94a4c21e2eef68669d71a0/7c1ed21b0ef41bd5d5a88edd5bda81cb39db3d1b.jpg";
        String url2 = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ae4e87268d94a4c21e2eef68669d71a0/7c1ed21b0ef41bd5d5a88edd5bda81cb39db3d1b.jpg";

        String url3 = "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg";

        with(this).load("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg").into(iv1);

        with(this).load(R.drawable.widget_bar_user_press).into(iv2);

        // 设置加载中以及加载失败图片
        with(this).load(url2).placeholder(R.drawable.actionbar_add_icon).error(R.drawable.actionbar_add_icon).into(iv3);

        //设置跳过内存缓存
        with(this).load(url2).skipMemoryCache(true).into(iv4);

        Glide.with(this).load(url).placeholder(R.drawable.actionbar_add_icon).into(new GlideDrawableImageViewTarget(iv4) {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                // 开始加载图片
                // progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                //   progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                super.onResourceReady(resource, glideAnimation);
                // 图片加载完成
                iv4.setImageDrawable(resource);
                //  progressBar.setVisibility(View.GONE);
            }
        });


        //设置下载优先级
        /**  //设置缓存策略
         * all:缓存源资源和转换后的资源
         none:不作任何磁盘缓存
         source:缓存源资源
         result：缓存转换后的资源
         */
        with(this).load(url2).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv5);


        for (int i = 1; i <= 5; i++) {
            getImageCacheAsyncTask mgetImageCacheAsyncTask = new getImageCacheAsyncTask(this);
            mgetImageCacheAsyncTask.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, url2);
        }


        Log.e("tag", GlideUtil.getCacheSize(this));
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
                Thread.sleep(3000);
                return with(context).load(imgUrl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
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
  /*
   http://blog.csdn.net/wbwjx/article/details/51239627
   with():指定了声明周期
    load():加载资源，String/Uri/File/Integer/URL/byte[]/T,或者 loadFromMediaStore(Uri uri)
    placeholder(resourceId/drawable)： 设置资源加载过程中的占位Drawable。
    error()：load失败时显示的Drawable。
    crossFade()/crossFade(int duration)：imageView改变时的动画，version 3.6.1后默认开启300毫秒
    dontAnimate()：移除所有的动画。
    override() ：调整图片大小
    centerCrop()：图片裁剪，ImageView 可能会完全填充，但图像可能不会完整显示。
    fitCenter()： 图像裁剪，图像将会完全显示，但可能不会填满整个 ImageView。
    animate(): 指定加载动画。
    transform():图片转换。
    bitmapTransform(): bitmap转换，不可以和(centerCrop() 或 fitCenter())共用。
    priority(Priority priority):当前线程的优先级,Priority.IMMEDIATE，Priority.HIGH，Priority.NORMAL(default)，Priority.LOW
    thumbnail(): 缩略图.
            listener():异常监听
    preload(int width, int height): 预加载resource到缓存中（单位为pixel）
    fallback(Drawable drawable):设置model为空时显示的Drawable。
    using() ：为单个的请求指定一个 model
    asGif()：Gif 检查，如果是图片且加了判断，则会显示error占位图，否则会显示图片
    asBitmap()：bitmap转化，如果是gif，则会显示第一帧*/


    private SimpleTarget target = new SimpleTarget<Bitmap>() {
        @Override
        public void onResourceReady(Bitmap bitmap, GlideAnimation glideAnimation) {
            //图片加载完成
        }

        @Override
        public void onStart() {
            super.onStart();
        }
    };


}




