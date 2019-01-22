package com.xcm91.relation.mythread.handlerthread;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import com.xcm91.relation.mythread.utils.FileDirectoryUtil;
import com.xcm91.relation.mythread.utils.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.List;

/**
 * Created by lhy on 2018/3/26.
 * HandlerThread 它就是一个帮我们创建 Looper 的线程，让我们可以直接在线程中创建 Handler 来处理异步任务,
 * 因为new Handler必须有Loop,省去了Loop.prepare   Loop.looper步骤，和主线程main里的操作类似主线程创建Handler不需要Loop.prepare。
 *  串行
 */

public class HandlerThreadDownLoad extends HandlerThread implements Handler.Callback {

    private final String TAG = this.getClass().getSimpleName();
    private final String KEY_URL = "url";
    public static final int TYPE_START = 1;
    public static final int TYPE_FINISHED = 2;
    public static final int TYPE_ING = 3;
    private final String commonPath;
    //工作线程-即子线程
    private Handler mWorkerHandler;
    //UI线程
    private Handler mUIHandler;
    private List<String> mDownloadUrlList;

    public HandlerThreadDownLoad(final String name, Context context) {
        super(name);
        commonPath = FileDirectoryUtil.getOwnCacheDirectory(context) + "/down/";
        Log.i(TAG, "下载地址" + commonPath);

    }

    @Override
    protected void onLooperPrepared() {    //执行初始化任务
        super.onLooperPrepared();
        mWorkerHandler = new Handler(getLooper(), this);    //使用子线程中的 Looper
        if (mUIHandler == null) {
            throw new IllegalArgumentException("Not set UIHandler!");
        }

        //将接收到的任务消息挨个添加到消息队列中
        for (String url : mDownloadUrlList) {
            Message message = mWorkerHandler.obtainMessage();
            Bundle bundle = new Bundle();
            bundle.putString(KEY_URL, url);
            message.setData(bundle);
            mWorkerHandler.sendMessage(message);
        }
    }

    public void setDownloadUrls(String... urls) {
        mDownloadUrlList = Arrays.asList(urls);
    }

    public Handler getUIHandler() {
        return mUIHandler;
    }

    //注入主线程 Handler
    public HandlerThreadDownLoad setUIHandler(final Handler UIHandler) {
        mUIHandler = UIHandler;
        return this;
    }

    /**
     * 子线程中执行任务，完成后发送消息到主线程
     *
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(final Message msg) {
        if (msg == null || msg.getData() == null) {
            return false;
        }

        String urlStr = (String) msg.getData().get(KEY_URL);
        if (StringUtils.isEmpty(urlStr)) {
            Log.e(TAG, "下载链接为空");
            return false;
        }

        if (StringUtils.isEmpty(commonPath)) {
            Log.e(TAG, "下载文件存放路径异常");
            return false;
        }

        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1);
        if (StringUtils.isEmpty(fileName)) {
            Log.e(TAG, "下载文件名格式错误");
            return false;
        }
        String allFilename = commonPath.concat(fileName);

        //下载开始，通知主线程
        if (mUIHandler != null) {
            Message startMsg = mUIHandler.obtainMessage(TYPE_START, "\n 开始下载 @" + "\n" + urlStr);
            mUIHandler.sendMessage(startMsg);
        }


        try {

            File file = new File(allFilename);
            Log.i(TAG, "开始下载：" + urlStr);
            // 构造URL
            URL url = new URL(urlStr);
            // 打开连接
            URLConnection con = url.openConnection();
            if (con != null) {
                // 输入流
                InputStream is = con.getInputStream();
                File filePath = new File(commonPath);
                if (!filePath.exists()) {
                    boolean bool = filePath.mkdirs();
                    if (bool) Log.i(TAG, "创建文件夹");
                }


                //如果目标文件已经存在，则删除。产生覆盖旧文件的效果
                if (file.exists()) {
                    boolean bool = file.delete();
                    if (bool) Log.i(TAG, "删除文件");
                }

                //下载文件大小
                float fileSize = con.getContentLength();
                float count = 0;
                int oldDown = -100;
                // 1K的数据缓冲
                byte[] bs = new byte[1024];
                // 读取到的数据长度
                int len;
                // 输出的文件流
                if (!StringUtils.isEmpty(allFilename)) {
                    OutputStream os = new FileOutputStream(allFilename);
                    // 开始读取
                    while ((len = is.read(bs)) != -1) {
                        os.write(bs, 0, len);
                        if (mUIHandler != null) {
                            count += len;
                            int progress = (int) ((count / fileSize) * 100);
                            if (progress > oldDown) {
                                Message finishMsg = mUIHandler.obtainMessage(TYPE_ING, "\n 下载中: @" + "\n" + urlStr);
                                Bundle mBundle = new Bundle();
                                mBundle.putInt("progress", progress);
                                finishMsg.setData(mBundle);
                                mUIHandler.sendMessage(finishMsg);
                            }
                            oldDown = progress;
                        }
                    }
                    // 完毕，关闭所有链接
                    os.close();
                }
                is.close();
                Log.i(TAG, "下载完毕");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "下载异常");
            return false;
        }


        //下载完成，通知主线程
        if (mUIHandler != null) {
            Message finishMsg = mUIHandler.obtainMessage(TYPE_FINISHED, "\n 下载完成 @" + "\n" + urlStr);
            Bundle mBundle = new Bundle();
            mBundle.putString("allFilename", allFilename);
            finishMsg.setData(mBundle);
            mUIHandler.sendMessage(finishMsg);
        }
        return true;
    }

    @Override
    public boolean quitSafely() {
        mUIHandler = null;
        return super.quitSafely();
    }
}
