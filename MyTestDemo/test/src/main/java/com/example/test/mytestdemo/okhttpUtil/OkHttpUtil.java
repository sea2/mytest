package com.example.test.mytestdemo.okhttpUtil;


import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.test.mytestdemo.application.MyApplication;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by lhy on 2018/11/29.
 */
public class OkHttpUtil {


    public static final int HTTP_GET = 1;
    public static final int HTTP_POST = 2;
    public static final int HTTP_POST_BODY = 3;

    private Handler handler;
    public static OkHttpUtil mOkHttpUtil = null;

    public OkHttpUtil() {
        handler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtil getIntance() {
        if (mOkHttpUtil == null) {
            synchronized (OkHttpUtil.class) {
                if (mOkHttpUtil == null) {
                    mOkHttpUtil = new OkHttpUtil();
                }
            }
        }
        return mOkHttpUtil;
    }

    private static OkHttpClient okHttpClient = null;

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new LoggerInterceptor("OkHttpUtil"))
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }


    public interface ResponseListener {
        void response(boolean isSuccess, String json, IOException e);
    }


    public void requestData(String url, int method, Map<String, String> params, final ResponseListener mResponseListener) {
        Map<String, String> paramsAll = addCommonParams(params);
        String urlTag = getUrl(url, paramsAll);
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        Request request = null;
        switch (method) {
            case HTTP_GET:
                request = new Request.Builder().url(urlTag).tag(urlTag).build();
                break;
            case HTTP_POST:
                FormBody.Builder builder = new FormBody.Builder();
                //遍历集合
                for (String key : paramsAll.keySet()) {
                    builder.add(key, params.get(key));
                }
                request = new Request.Builder().url(url).post(builder.build()).tag(urlTag).build();
                break;
            case HTTP_POST_BODY:
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(paramsAll));
                request = new Request.Builder().url(url).post(requestBody).build();
                break;
            default://默认get
                request = new Request.Builder().url(urlTag).tag(urlTag).build();
        }
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (mResponseListener != null) {
                    IOException e2;
                    if (!NetworkUtils.IsNetWorkEnable(MyApplication.getInstance())) {
                        e2 = new IOException("当前网络不可用，请检查您的网络设置");
                    } else {
                        e2 = new IOException("网络错误");
                    }
                    setBack(false, "", e2, mResponseListener);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (mResponseListener != null) {
                    if (call.isCanceled()) {
                        IOException e = new IOException("请求被取消");
                        setBack(false, "", e, mResponseListener);
                        return;
                    }
                    if (response.body() != null)
                        setBack(true, response.body().string(), null, mResponseListener);
                    else setBack(true, "", null, mResponseListener);
                }
            }
        });
    }


    private void setBack(final boolean isSuccess, final String json, final IOException e, final ResponseListener mResponseListener) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                mResponseListener.response(isSuccess, json, e);
            }
        });
    }


    /**
     * post请求上传文件
     * 参数1 url
     * 参数2 回调Callback
     */
    public void uploadPic(String url, File file, String fileName) {
        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getOkHttpClient();
        //创建RequestBody 封装file参数
        RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
        //创建RequestBody 设置类型等
        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("file", fileName, fileBody).build();
        //创建Request
        Request request = new Request.Builder().url(url).post(requestBody).build();

        //得到Call
        Call call = okHttpClient.newCall(request);
        //执行请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {

            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                //上传成功回调 目前不需要处理
            }
        });
    }



    /**
     * 参数一：请求Url
     * 参数二：保存文件的路径名
     * 参数三：保存文件的文件名
     */
    public void download(final String url, final String fileName, final DownLoadListener mDownLoadListener) {
        Request request = new Request.Builder().url(url).build();
        Call call = getOkHttpClient().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                if (mDownLoadListener != null) mDownLoadListener.onError(call, e);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (!call.isCanceled()) {
                    String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/down_file/";
                    String file_name = new SimpleDateFormat("yyyyMMdd-HHmmss", Locale.CHINA).format(new Date()) + fileName;
                    InputStream is = null;
                    byte[] buf = new byte[2048];
                    int len = 0;
                    FileOutputStream fos = null;
                    try {
                        if (response != null && response.body() != null) {
                            is = response.body().byteStream();
                            final long total = response.body().contentLength();
                            long sum = 0;

                            File dir = new File(destFileDir);
                            if (!dir.exists()) {
                                dir.mkdirs();
                            }
                            File file = new File(dir, file_name);
                            fos = new FileOutputStream(file);
                            while ((len = is.read(buf)) != -1) {
                                sum += len;
                                fos.write(buf, 0, len);
                                final long finalSum = sum;
                                if (mDownLoadListener != null) mDownLoadListener.inProgress(finalSum * 1.0f / total, total);
                            }
                            fos.flush();
                            if (mDownLoadListener != null) mDownLoadListener.onResponse(file);
                        }
                        if (mDownLoadListener != null) mDownLoadListener.onResponse(null);
                    } finally {
                        try {
                            if (is != null) is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (fos != null) fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

    }

    interface DownLoadListener {
        void onError(Call call, Exception e);

        void onResponse(File response);

        void inProgress(float progress, long total);
    }


    /**
     * @param saveDir
     * @return
     * @throws IOException 判断下载目录是否存在
     */
    public static String isExistDir(String saveDir) throws IOException {
        // 下载位置
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {

            File downloadFile = new File(Environment.getExternalStorageDirectory(), saveDir);
            if (!downloadFile.mkdirs()) {
                downloadFile.createNewFile();
            }
            String savePath = downloadFile.getAbsolutePath();
            Log.e("savePath", savePath);
            return savePath;
        }
        return null;
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }


    /**
     * Get地址拼接
     *
     * @param uri
     * @return
     */
    private static String getUrl(String uri, Map<String, String> map) {
        StringBuilder mStringBuilder = new StringBuilder(uri);
        if (uri != null && map != null && map.size() > 0) {
            if (!uri.contains("?")) mStringBuilder.append("?");
            else mStringBuilder.append("&");
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, String> entry = iterator.next();
                mStringBuilder.append(entry.getKey());
                mStringBuilder.append("=");
                if (iterator.hasNext()) {
                    mStringBuilder.append(entry.getValue());
                    mStringBuilder.append("&");
                } else {
                    mStringBuilder.append(entry.getValue());
                }
            }
        }
        return mStringBuilder.toString();
    }


    /**
     * 公共参数
     *
     * @param map
     */
    public static Map<String, String> addCommonParams(Map<String, String> map) {
        //1.app_version 2. version 3. channel 4. current_time 5. sign
      /*  map.put("app_version", MyApplication.APP_VERSION);
        map.put("market", ManifestMetaDataHelper.getMetaDataChannelKey());
        map.put("spm", SpmUtil.getInstance().getSpmStr());
        map.put("mac", Utils.getMacAddress(MyApplication.getInstance()));
        map.put("imei", NetworkUtils.getImei());
        map.put("version", MyApplication.VERSION);
        map.put("channel", MyApplication.CHANNEL);*/
        if (map != null) {
            map.put("current_time", System.currentTimeMillis() + "");
            map.put("sign", "sign");// 所有的参数都需要验签
        }
        return map;
    }


    /**
     * 取消所有tag网络
     */
    public void cancelTag(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                call.cancel();
            }
        }
    }

    /**
     * 取消所有网络
     */
    public void cancelAll() {
        getOkHttpClient().dispatcher().cancelAll();
    }

    /**
     * 是否正在排队或者请求进行时
     */
    public boolean isInRequestQueue(Object tag) {
        for (Call call : getOkHttpClient().dispatcher().queuedCalls()) {
            if (tag.equals(call.request().tag())) {
                return true;
            }
        }
        for (Call call : getOkHttpClient().dispatcher().runningCalls()) {
            if (tag.equals(call.request().tag())) {
                return true;
            }
        }
        return false;
    }


}
