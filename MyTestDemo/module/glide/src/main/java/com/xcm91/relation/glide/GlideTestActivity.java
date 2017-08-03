package com.xcm91.relation.glide;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import static com.bumptech.glide.Glide.with;
import static com.xcm91.relation.glide.GlideUtil.getFileSize;

public class GlideTestActivity extends Activity {

    private android.widget.ImageView iv1;
    private android.widget.ImageView iv2;
    private android.widget.ImageView iv3;
    private android.widget.ImageView iv4;
    private android.widget.ImageView iv5;
    private android.widget.ImageView iv6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glide_test);
        this.iv6 = (ImageView) findViewById(R.id.iv6);
        this.iv5 = (ImageView) findViewById(R.id.iv5);
        this.iv4 = (ImageView) findViewById(R.id.iv4);
        this.iv3 = (ImageView) findViewById(R.id.iv3);
        this.iv2 = (ImageView) findViewById(R.id.iv2);
        this.iv1 = (ImageView) findViewById(R.id.iv1);


        String url2 = "http://imgsrc.baidu.com/image/c0%3Dshijue1%2C0%2C0%2C294%2C40/sign=ae4e87268d94a4c21e2eef68669d71a0/7c1ed21b0ef41bd5d5a88edd5bda81cb39db3d1b.jpg";
        String url3 = "http://pic49.nipic.com/file/20140927/19617624_230415502002_2.jpg";

        with(this).load("http://imgsrc.baidu.com/imgad/pic/item/267f9e2f07082838b5168c32b299a9014c08f1f9.jpg").into(iv1);
        with(this).load(R.drawable.widget_bar_user_press).into(iv2);
        // 设置加载中以及加载失败图片
        with(this).load(url2).placeholder(R.drawable.actionbar_add_icon).error(R.drawable.actionbar_add_icon).into(iv3);

        //设置跳过内存缓存
        with(this).load(url2).skipMemoryCache(true).into(iv4);
        //设置下载优先级
        /**  //设置缓存策略
         * all:缓存源资源和转换后的资源
         none:不作任何磁盘缓存
         source:缓存源资源
         result：缓存转换后的资源
         */
        with(this).load(url2).diskCacheStrategy(DiskCacheStrategy.ALL).into(iv5);

        new getImageCacheAsyncTask(this).execute(url2);

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
                return Glide.with(context).load(imgUrl).downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
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
            ;

        }
    }
}
