package com.spidertool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.xcm91.relation.glide.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ViewPageActivity extends AppCompatActivity {

    private ViewPager viewpagercontainer;
    private ProgressDialog pd;
    private List<String> list = new ArrayList<>();
    private int page = 0;
    private TextView tvnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_page);
        this.tvnum = (TextView) findViewById(R.id.tv_num);
        this.viewpagercontainer = (ViewPager) findViewById(R.id.viewpager_container);
        Intent it = getIntent();
        final String url = it.getStringExtra("url");
        Log.i(this.getLocalClassName(), "" + page);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements div = doc.select(".main-image");
                    Elements spans = doc.select("span");

                    for (Element element : spans) {
                        try {
                            if (element.parent().attr("href").contains(url)) {
                                String pageStr = element.text();
                                if (page < Integer.parseInt(pageStr)) page = Integer.parseInt(pageStr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.e("tag", "" + page);

                    for (Element element : div) {
                        String url = element.child(0).child(0).child(0).attr("src");
                        String startUrl = url.substring(0, url.length() - 6);
                        if (page <= 0) page = 30;
                        for (int i = 1; i < page; i++) {
                            if (page < 100) {
                                if (i < 10)
                                    list.add(startUrl + "0" + i + ".jpg");
                                else list.add(startUrl + "" + i + ".jpg");
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();


        pd = new ProgressDialog(this);
        pd.setMessage("数据初始化中...");
        pd.setCanceledOnTouchOutside(false);
        pd.show();


        viewpagercontainer.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int num = position + 1;
                int totle = page - 1;
                tvnum.setText(num + "/" + totle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pd.dismiss();
            ViewPagerAdapter adapter = new ViewPagerAdapter(list);
            viewpagercontainer.setAdapter(adapter);
        }
    };


    /**
     * ViewPager的适配器
     *
     * @author guolin
     */
    class ViewPagerAdapter extends PagerAdapter {
        List<String> list;

        public ViewPagerAdapter(List<String> lists) {
            this.list = lists;
        }



        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(ViewPageActivity.this).inflate(R.layout.zoom_image_layout, null);
            PhotoView iv_photo = (PhotoView) view.findViewById(R.id.iv_photo);
            final Button btn_down = (Button) view.findViewById(R.id.btn_down);
            final String mImgInfo = list.get(position);
            final GlideUrl glideUrl = new GlideUrl(mImgInfo, new LazyHeaders.Builder()
                    .addHeader("Referer", "https://www.mzitu.com")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0")
                    .addHeader("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .addHeader("Accept-Language", "zh-cn,zh;q=0.5")
                    .addHeader("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7")
                    .addHeader("Connection", "keep-alive")
                    .build());
            Glide.with(ViewPageActivity.this).load(glideUrl).into(iv_photo);
            btn_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownLoadImage mDownLoadImage=new  DownLoadImage(ViewPageActivity.this,glideUrl);
                    new Thread(mDownLoadImage).start();
                    btn_down.setVisibility(View.GONE);
                }
            });

            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            if (list == null) return 0;
            else return list.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }


    private void downImg(String imgUrl) {

        Glide.with(this).asBitmap().load(imgUrl).into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                try {
                    String filename = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + ".png";
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }






}
