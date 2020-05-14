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
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
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

/**
 * Created by lhy on 2020/3/17.
 */
public class KaKaViewPageActivity extends AppCompatActivity {

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
                    Elements div = doc.select(".dede_pages");
                    String pageNum = div.get(0).child(0).child(0).child(0).html();

                    int pageInt = Integer.parseInt(Utils.getNumbers(pageNum));
                    for (int i = 1; i <= pageInt; i++) {
                        String urlImg;
                        if (i == 1) urlImg = url;
                        else urlImg = url.replace(".html", "_" + i + ".html");
                        Document docImg = Jsoup.connect(urlImg).get();
                        Elements divs = docImg.select(".content");
                        Elements imgs = divs.get(0).children();
                        for (Element element : imgs) {
                            try {
                                page++;
                                list.add(element.attr("src"));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("tag", "" + list.toString());
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
                int totle = page;
                tvnum.setText(num + "/" + totle);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    float startDown = 0;
    float endDown = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                startDown = event.getRawY();
                break;
            case (MotionEvent.ACTION_MOVE):
                endDown = event.getRawY();
                float moveFloat = endDown - startDown;
                if (moveFloat > 800) {
                    finish();
                    return true;
                }
                break;
            case (MotionEvent.ACTION_UP):
            case (MotionEvent.ACTION_CANCEL):
                endDown = event.getRawY();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pd.dismiss();
            ViewPagerAdapter adapter = new ViewPagerAdapter(list);
            viewpagercontainer.setAdapter(adapter);
            tvnum.setText(1 + "/" + page);
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

        //https://www.mm13.cc/xinggan/2.html
//        https://www.xibixibi.com
//        http://www.ylzzd.com/xgmn/
//        https://www.ku66.net
//        https://www.pic12345.net
//        https://www.kaka1234.com

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = LayoutInflater.from(KaKaViewPageActivity.this).inflate(R.layout.zoom_image_layout, null);
            PhotoView iv_photo = (PhotoView) view.findViewById(R.id.iv_photo);
            final Button btn_down = (Button) view.findViewById(R.id.btn_down);
            final String mImgInfo = list.get(position);
            iv_photo.enable();
            Glide.with(KaKaViewPageActivity.this).load(mImgInfo).into(iv_photo);
            btn_down.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DownLoadImage mDownLoadImage = new DownLoadImage(KaKaViewPageActivity.this, mImgInfo);
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

