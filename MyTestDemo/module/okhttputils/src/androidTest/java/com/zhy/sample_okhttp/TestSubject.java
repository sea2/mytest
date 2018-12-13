package com.zhy.sample_okhttp;

import android.test.InstrumentationTestCase;
import android.util.Log;

import com.lhy.http.okhttp.OkHttpUtils;
import com.lhy.http.okhttp.callback.StringCallback;
import com.lhy.http.okhttp.https.HttpsUtils;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * Created by lhy on 2018/11/29.
 */
public class TestSubject extends InstrumentationTestCase {




    public void testPublishSubject() {

        HttpsUtils.SSLParams sslParams = HttpsUtils.getSslSocketFactory(null, null, null);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
              //  .addInterceptor(new LoggerInterceptor("TAG"))
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .sslSocketFactory(sslParams.sSLSocketFactory, sslParams.trustManager)
                .build();
        // 网络接口配置信息初始化
        OkHttpUtils.getInstance(okHttpClient).debug("OkHttpUtils");

        OkHttpUtils.get().url("http://192.168.5.188:8081/fund/getListInfo").build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                Log.i("okhttp",response);
            }
        });

    }



}
