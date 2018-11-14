package com.spidertool;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.dl7.recycler.helper.RecyclerViewHelper;
import com.dl7.recycler.listener.OnRequestDataListener;
import com.xcm91.relation.glide.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


/**
 * <pre>
 *     author : lhy
 *     e-mail : 18141924293@163.com
 *     time   : 2018/09/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class MainAct extends AppCompatActivity {

    private List<ImgInfo> list = new ArrayList<ImgInfo>();
    private HashSet<String> urlList = new HashSet<String>();
    private ProgressDialog pd;
    private RecyclerView rv_photo_list;
    private long mExitTime = 0;
    private CustomPopWindow mCustomPopWindow = null;
    private String url;
    private WelfarePhotoAdapter mAdapter;
    private int page = 0;
    private int pageNum = 1;
    private final int pageSize = 20;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        this.rv_photo_list = (RecyclerView) findViewById(R.id.rv_photo_list);
        url = "http://www.mzitu.com/";
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);
        pd.setMessage("数据初始化中...");
        initUrlData();


        mAdapter = new WelfarePhotoAdapter(this);
        SlideInBottomAdapter slideAdapter = new SlideInBottomAdapter(mAdapter);
        RecyclerViewHelper.initRecyclerViewG(this, rv_photo_list, slideAdapter, 2);
        mAdapter.setRequestDataListener(new OnRequestDataListener() {
            @Override
            public void onLoadMore() {
                pageNum++;
                addUrlData();
            }
        });
    }


    private void initUrlData() {
        pageNum = 1;
        page = 0;
        urlList.clear();
        list.clear();
        urlList.add(url);
        pd.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //获取页码
                try {
                    Document doc = Jsoup.connect(url).get();
                    Elements a_page = doc.select(".page-numbers");

                    for (Element element : a_page) {
                        String hrefStr = element.attr("href");
                        String pageStr = hrefStr.replace(url + "page/", "").replace("/", "");
                        try {
                            if (pageStr != null && !pageStr.equals("")) {
                                if (page < Integer.parseInt(pageStr)) page = Integer.parseInt(pageStr);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    Log.i("MainAct_page", page + "");
                    if (page <= 0) page = 50;
                    for (int i = 2; i <= page; i++) {
                        urlList.add(url + "page/" + i + "/");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                int count = 0;
                for (String url : urlList) {
                    count++;
                    try {
                        Document doc = Jsoup.connect(url).get();
                        Elements a = doc.select(".lazy");
                        for (Element element : a) {
                            Element parentElement = element.parent();
                            String hrefStr = parentElement.attr("href");
                            String imgStr = element.attr("data-original");
                            ImgInfo mImgInfo = new ImgInfo(hrefStr, imgStr);
                            list.add(mImgInfo);
                            Log.i("tag", mImgInfo.toString());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (count >= pageSize) break;
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    private void addUrlData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int count = 0;
                for (String url : urlList) {
                    count++;
                    if (count > 20 * (pageNum - 1)) {
                        try {
                            Document doc = Jsoup.connect(url).get();
                            Elements a = doc.select(".lazy");
                            for (Element element : a) {
                                Element parentElement = element.parent();
                                String hrefStr = parentElement.attr("href");
                                String imgStr = element.attr("data-original");
                                ImgInfo mImgInfo = new ImgInfo(hrefStr, imgStr);
                                list.add(mImgInfo);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (count >= 20 * pageNum) break;
                    }
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }


    Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pd.dismiss();
            mAdapter.updateItems(list);
        }
    };


    public void showDialog(View view) {
        if (mCustomPopWindow == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.popwindow_main_rank, null);
            RadioGroup rg = (RadioGroup) contentView.findViewById(R.id.rg);
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.btn_new:
                            url = "http://www.mzitu.com/";
                            break;

                        case R.id.btn_hot:
                            url = "http://www.mzitu.com/hot/";
                            break;
                        case R.id.btn_best:
                            url = "http://www.mzitu.com/best/";
                            break;
                        case R.id.btn_xinggan:
                            url = "http://www.mzitu.com/tag/xinggan/";
                            break;
                        case R.id.btn_baoru:
                            url = "http://www.mzitu.com/tag/baoru/";
                            break;
                        case R.id.btn_leg:
                            url = "http://www.mzitu.com/tag/leg/";
                            break;
                        case R.id.btn_youhuo:
                            url = "http://www.mzitu.com/tag/youhuo/";
                            break;
                        case R.id.btn_zouguang:
                            url = "http://www.mzitu.com/tag/zouguang/";
                            break;
                        case R.id.btn_zhifu:
                            url = "http://www.mzitu.com/tag/zhifu/";
                            break;
                        case R.id.btn_meitun:
                            url = "http://www.mzitu.com/tag/btn_meitun/";
                            break;
                        case R.id.btn_shishen:
                            url = "http://www.mzitu.com/tag/shishen/";
                            break;
                        case R.id.btn_taiwan:
                            url = "http://www.mzitu.com/taiwan/";
                            break;
                        case R.id.btn_mm:
                            url = "http://www.mzitu.com/mm/";
                            break;

                        default:
                            break;
                    }
                    initUrlData();
                    mCustomPopWindow.dissmiss();
                }
            });
            mCustomPopWindow = new CustomPopWindow.PopupWindowBuilder(this)
                    .setView(contentView)
                    .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                    .setBgDarkAlpha(1f) // 控制亮度
                    .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                        @Override
                        public void onDismiss() {
                            Log.e("TAG", "onDismiss");
                        }
                    })
                    .create();
        }
        mCustomPopWindow.showAsDropDown(view, 0, 20);
    }


    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (null == convertView) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(MainAct.this).inflate(R.layout.item_common_linetogo, null);
                viewHolder.iv_show = (ImageView) convertView.findViewById(R.id.iv_show);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            final ImgInfo mImgInfo = list.get(position);
            GlideUrl glideUrl = new GlideUrl(mImgInfo.getImgUrl(), new LazyHeaders.Builder()
                    .addHeader("Referer", "http://www.mzitu.com")
                    .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:55.0) Gecko/20100101 Firefox/55.0")
                    .addHeader("Accept", "  text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
                    .addHeader("Accept-Language", "zh-cn,zh;q=0.5")
                    .addHeader("Accept-Charset", "  GB2312,utf-8;q=0.7,*;q=0.7")
                    .addHeader("Connection", "keep-alive")
                    .build());
            Glide.with(MainAct.this).load(glideUrl).into(viewHolder.iv_show);

            viewHolder.iv_show.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent it = new Intent(MainAct.this, ViewPageActivity.class);
                    it.putExtra("url", mImgInfo.getUrl());
                    startActivity(it);
                }
            });

            return convertView;
        }
    }

    private static class ViewHolder {
        ImageView iv_show;
    }


    @Override
    public void onBackPressed() {
        _exit();
    }

    /**
     * 退出
     */
    private void _exit() {
        if (System.currentTimeMillis() - mExitTime > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            mExitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }

}

