package com.spidertool;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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
public class Main2Activity extends AppCompatActivity {

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
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (String url : urlList) {
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
        Toast.makeText(this, "请联系管理员", Toast.LENGTH_LONG).show();
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

