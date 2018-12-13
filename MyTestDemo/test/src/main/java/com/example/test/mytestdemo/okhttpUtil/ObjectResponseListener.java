package com.example.test.mytestdemo.okhttpUtil;

import com.google.gson.Gson;

import java.io.IOException;

/**
 * Created by lhy on 2018/11/29.
 */
public abstract class ObjectResponseListener<T> implements OkHttpUtil.ResponseListener {

    Class<?> mClass;

    @Override
    public void response(boolean isSuccess, String json, IOException e) {

        Gson gson = new Gson();
        responseObject(isSuccess, (T) gson.fromJson(json, mClass), e);
    }

    public abstract void responseObject(boolean isSuccess, T t, IOException e);


    public ObjectResponseListener(Class<?> mClass) {
        this.mClass = mClass;
    }


}
