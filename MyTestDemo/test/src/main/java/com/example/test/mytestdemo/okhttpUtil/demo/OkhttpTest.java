package com.example.test.mytestdemo.okhttpUtil.demo;


import android.support.v4.util.ArrayMap;
import android.util.Log;

import com.example.test.mytestdemo.okhttpUtil.ObjectResponseListener;
import com.example.test.mytestdemo.okhttpUtil.OkHttpUtil;

import java.io.IOException;

/**
 * Created by lhy on 2018/11/30.
 */
public class OkhttpTest {


    public static void main(String[] arg) {
        String url = "http://www.zhiyun-tech.com/App/Rider-M/changelog-zh.txt";
        url = "http://192.168.3.240:9080/v2.0/app/product/subsetSubjectList";

        ArrayMap<String, String> map =new ArrayMap<>();
        map.put("user", "1234");
        map.put("password", "1234");
        OkHttpUtil.getIntance().requestData(url, 3, map, new ObjectResponseListener<Result>(Result.class) {

            @Override
            public void responseObject(boolean isSuccess, Result user, IOException e) {
                Log.e("tag", user.toString());
            }
        });
        OkHttpUtil.getIntance().requestData(url, 3, map, new OkHttpUtil.ResponseListener() {

            @Override
            public void response(boolean isSuccess, String json, IOException e) {
                Log.e("tag", json);
            }

        });
    }


}
