package com.example.test.mytestdemo.webview;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

public class WebViewTestActivity extends BaseActivity implements View.OnClickListener {

    private WebView webview;
    private String TAG = this.getClass().getSimpleName();
    private String url;
    private android.widget.LinearLayout llbottomtext;
    private android.widget.Button btnsubmit;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_test);
        this.llbottomtext = (LinearLayout) findViewById(R.id.ll_bottom_text);
        this.btnsubmit = (Button) findViewById(R.id.btn_submit);
        this.btnsubmit.setOnClickListener(this);
        this.webview = (WebView) findViewById(R.id.webview);
        Intent it = getIntent();
        String str = it.getStringExtra("key");
        Log.e("WebViewTestActivity", str + "");

        url = "http://192.168.2.36:8080/html/抽奖/index.html";

        //支持Html5标签……等
        webview.loadUrl(url);
        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);//支持js调用
        webview.addJavascriptInterface(new JsInteration(), "android");//支持js调用android方法

        //支持获取手势焦点，输入用户名、密码或其他
        webview.requestFocusFromTouch();

        webSettings.setUseWideViewPort(true);  //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

        webSettings.setSupportZoom(true);  //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。
        // 若上面是false，则该WebView不可缩放，这个不管设置什么都不能缩放。

        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN); //支持内容重新布局
        webSettings.supportMultipleWindows();  //多窗口
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);  //不使用缓存
        webSettings.setAllowFileAccess(true);  //设置可以访问文件
        webSettings.setNeedInitialFocus(true); //当webview调用requestFocus时为webview设置节点
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true);  //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式


        webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "shouldOverrideUrlLoading:old" + url);
                if (url.equals("file:///android_asset/test2.html")) {
                    Log.e(TAG, "shouldOverrideUrlLoading: " + url);
                    startActivity(new Intent(WebViewTestActivity.this, WebViewTestActivity.class));
                    return true;
                } else {
                    webview.loadUrl(url);
                    return false;
                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.i(TAG, "shouldOverrideUrlLoading:new" + request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                //用javascript隐藏系统定义的404页面信息
                String data = "出错了哦~\n您的页面找不到了！";
                view.loadUrl("javascript:document.body.innerHTML=\"" + data + "\"");
            }
        });


        //加载本地网页
        //  webview.loadData("file:///android_asset/test.html", "text/html", "utf-8");

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


        webview.setWebChromeClient(mWebChromeClient);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                callJs();
                break;
        }
    }


    /**
     * 调用js
     */
    private void callJs() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webview.evaluateJavascript("fromAndroid()", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    Log.i("js", "" + value);
                }
            });
        } else
            webview.loadUrl("javascript:callJS()");
    }


    public class JsInteration {

        @JavascriptInterface
        public String back() {
            return "hello world";
        }

        @JavascriptInterface
        public void showMessage(String message) {
            Toast toast = Toast.makeText(WebViewTestActivity.this, message, Toast.LENGTH_SHORT);
            toast.show();
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


    WebChromeClient mWebChromeClient = new WebChromeClient() {


        //获得网页的加载进度，显示在右上角的TextView控件中
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress < 100) {
                String progress = newProgress + "%";
            } else {
            }
        }

        //获取Web页中的title用来设置自己界面中的title
        //当加载出错的时候，比如无网络，这时onReceiveTitle中获取的标题为 找不到该网页,
        //因此建议当触发onReceiveError时，不要使用获取到的title
        @Override
        public void onReceivedTitle(WebView view, String title) {

        }

        @Override
        public void onReceivedIcon(WebView view, Bitmap icon) {
            //
        }

        @Override
        public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
            //
            return true;
        }

        @Override
        public void onCloseWindow(WebView window) {
        }

        //处理alert弹出框，html 弹框的一种方式
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            //
            return true;
        }

        //处理confirm弹出框
        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult
                result) {
            //
            return true;
        }

        //处理prompt弹出框
        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            //
            return true;
        }
    };

/*
7、使用独立进程跑WebView
    有一定使用WebView经验的老司机可能都把项目中的WebView模块抽取出来，并跑在独立的进程中去。例如在manifest文件中使用属性process指定独立的进程。
               //绘制白色空页面
                webview.loadUrl("about:blank");
                // 12.清除返回栈
                webview.clearHistory();
                //13.获得访问历史列表
                webview.copyBackForwardList();

                 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    webview.evaluateJavascript("sum(1,2)", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.e(TAG, "onReceiveValue value=" + value);
                        }
                    });
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
