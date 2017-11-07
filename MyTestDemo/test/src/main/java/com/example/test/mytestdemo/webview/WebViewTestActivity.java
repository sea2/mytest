package com.example.test.mytestdemo.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
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


       //支持获取手势焦点，输入用户名、密码或其他
        webview.requestFocusFromTouch();



        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings. setLoadWithOverviewMode(true); // 缩放至屏幕的大小


        webSettings. setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.  setBuiltInZoomControls(true); //设置内置的缩放控件。
       // 若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        webSettings.  setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings. setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.  supportMultipleWindows();  //多窗口
        webSettings.  setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //关闭webview中缓存
        webSettings. setAllowFileAccess(true);  //设置可以访问文件
        webSettings.  setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings. setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.  setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式




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




/*
7、使用独立进程跑WebView
    有一定使用WebView经验的老司机可能都把项目中的WebView模块抽取出来，并跑在独立的进程中去。例如在manifest文件中使用属性process指定独立的进程。

<!-- Web 页面 -->
<activity
    android:name=".UI.CommonUI.Activity.WebBrowserActivity"
    android:configChanges="orientation|screenSize|keyboardHidden"
    android:hardwareAccelerated="true"
    android:label=""
    android:process=":web"
    android:screenOrientation="portrait" />
    这样做的是因为WebView在以前的版本的底层实现中会发生内存泄漏，导致页面关闭但是依然没有释放内存，而在独立进程中的WebView模块就可以很好解决此问题，在关闭WebView的时候就关闭进程，这样就可以释放相关的内存了。

    但是使用多进程架构，进程间数据共享就是一个问题了。例如进程A设置了cookie，同样我也要在进程B共享这个cookie。目前AC认为可行的解决方案是使用ContentProvider来共享数据。此问题AC没有写相应的Demo，希望有老司机可以带路。

            8、WebView生命周期回调

    WebView也有生命周期回调方法，这些方法需要在Activity或Fragment相应的生命方法中回调。主要是onResume(),onPasuse()和onDestory()（或者onDestoryView()）这几个方法的回调实现。
*/

    @Override
    public void onResume() {
        if (webview != null) {
            webview.resumeTimers();
            webview.onResume();
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (webview != null) {
            webview.pauseTimers();
            webview.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (webview != null) {
            webview.stopLoading();
            ((ViewGroup) webview.getParent()).removeView(webview);
            webview.removeAllViews();
            webview.setWebChromeClient(null);
            webview.setWebViewClient(null);
            unregisterForContextMenu(webview);
            webview.destroy();
        }
        super.onDestroy();
    }


}
