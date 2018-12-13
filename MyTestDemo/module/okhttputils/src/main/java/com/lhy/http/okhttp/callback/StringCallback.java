package com.lhy.http.okhttp.callback;

import okhttp3.Response;

import java.io.IOException;

/**
 * Created by lhy on 15/12/14.
 */
public abstract class StringCallback extends Callback<String>
{
    @Override
    public String parseNetworkResponse(Response response) throws IOException
    {
        return response.body().string();
    }

}
