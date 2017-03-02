package com.example.test.mytestdemo.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.activity.HorizontalScrollViewActivity;
import com.example.test.mytestdemo.app.BaseActivity;

public class WebViewTestActivity extends BaseActivity {

    private WebView webview;
    private String TAG = this.getClass().getSimpleName();
    private android.widget.Button btnjs;
    private android.widget.RelativeLayout activitywebviewtest;
    String url;
    private Button btnnextpage;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
        this.btnnextpage = (Button) findViewById(R.id.btn_next_page);
        this.activitywebviewtest = (RelativeLayout) findViewById(R.id.activity_web_view_test);
        this.btnjs = (Button) findViewById(R.id.btn_js);
        this.webview = (WebView) findViewById(R.id.webview);
        Intent it = getIntent();
        String str = it.getStringExtra("key");
        Log.e("WebViewTestActivity", str + "");

        url = "file:///android_asset/test.html";
        //支持Html5标签……等
        webview.loadUrl(url);
        //      webview.loadUrl("http://192.168.2.63:8080/v1.0/html/index.html");
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webview.addJavascriptInterface(new JsInteration(), "android");//支持本地js


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.equals("file:///android_asset/test2.html")) {
                    Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                    startActivity(new Intent(WebViewTestActivity.this, WebViewTestActivity.class));
                    return true;
                } else {
                    webview.loadUrl(url);
                    return false;
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                url = "http://baidu.com";
                webview.loadUrl(url);
            }
        }, 3000);

        //加载本地网页
        // if (webview != null) webview.loadData("file:///android_asset/test.html", "text/html", "utf-8");

       /* // 返回上个页面
        if (webview.canGoBack()) {【
            webview.goBack();
        }

        //去刚才浏览的页面
        if (webview.canGoForward()) {
            webview.goForward();
        }

        //刷新当前页面
        webview.reload();*/


        btnjs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(HorizontalScrollViewActivity.class);
                /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webview.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.e(TAG, "onReceiveValue value=" + value);
                        }
                    });
                }*/
            }
        });






        btnnextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //绘制白色空页面
                webview.loadUrl("about:blank");
                // 12.清除返回栈
                webview.clearHistory();
                //13.获得访问历史列表
                webview.copyBackForwardList();
            }
        });

    }


    public class JsInteration {

        @JavascriptInterface
        public String back() {
            return "hello world";
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }




}
