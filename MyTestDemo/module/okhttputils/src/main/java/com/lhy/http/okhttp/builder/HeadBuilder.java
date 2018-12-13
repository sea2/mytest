package com.lhy.http.okhttp.builder;

import com.lhy.http.okhttp.OkHttpUtils;
import com.lhy.http.okhttp.request.OtherRequest;
import com.lhy.http.okhttp.request.RequestCall;

/**
 * Created by lhy on 16/3/2.
 */
public class HeadBuilder extends GetBuilder
{
    @Override
    public RequestCall build()
    {
        return new OtherRequest(null, null, OkHttpUtils.METHOD.HEAD, url, tag, params, headers).build();
    }
}
