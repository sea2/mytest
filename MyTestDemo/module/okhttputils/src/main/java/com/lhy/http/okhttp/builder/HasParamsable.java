package com.lhy.http.okhttp.builder;

import java.util.Map;

/**
 * Created by lhy on 16/3/1.
 */
public interface HasParamsable
{
    public abstract OkHttpRequestBuilder params(Map<String, String> params);

    public abstract OkHttpRequestBuilder addParams(String key, String val);

}
