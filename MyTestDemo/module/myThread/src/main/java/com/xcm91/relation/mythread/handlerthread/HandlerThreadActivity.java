package com.xcm91.relation.mythread.handlerthread;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xcm91.relation.mythread.R;

/**
 * Created by lhy on 2018/3/26.
 */

public class HandlerThreadActivity extends Activity implements Handler.Callback {


    private Handler mUIHandler;
    private HandlerThreadDownLoad mDownloadThread;
    private TextView tvstartmsg;
    private TextView tvfinishmsg;
    private Button btnstartdownload;
    private android.widget.LinearLayout llmain;
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_thread_test);
        this.llmain = (LinearLayout) findViewById(R.id.ll_main);
        this.btnstartdownload = (Button) findViewById(R.id.btn_start_download);
        this.tvfinishmsg = (TextView) findViewById(R.id.tv_finish_msg);
        this.tvstartmsg = (TextView) findViewById(R.id.tv_start_msg);
        btnstartdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDownload();
            }
        });
        init();
    }

    private void init() {
        mUIHandler = new Handler(this);
        mDownloadThread = new HandlerThreadDownLoad("下载线程", this);
        mDownloadThread.setUIHandler(mUIHandler);
      /*  mDownloadThread.setDownloadUrls("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg",
                "http://img3.redocn.com/tupian/20150312/haixinghezhenzhubeikeshiliangbeijing_3937174.jpg");*/
        mDownloadThread.setDownloadUrls("http://img.taopic.com/uploads/allimg/120727/201995-120HG1030762.jpg","http://img.tuku.cn/file_big/201502/89448ed96e524552a46abce14fab2eb8.jpg");
    }

    public void startDownload() {
        mDownloadThread.start();
        btnstartdownload.setEnabled(false);
    }

    //主线程中的 Handler 处理消息的方法
    @Override
    public boolean handleMessage(final Message msg) {
        switch (msg.what) {
            case HandlerThreadDownLoad.TYPE_FINISHED:
                stringBuilder.append(msg.obj).append("\n ");
                String filePath = msg.getData().getString("allFilename");
                Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                ImageView iv = new ImageView(this);
                iv.setImageBitmap(bitmap);
                llmain.addView(iv);
                break;
            case HandlerThreadDownLoad.TYPE_START:
                tvstartmsg.setText(tvstartmsg.getText().toString() + "\n " + msg.obj);
                break;
            case HandlerThreadDownLoad.TYPE_ING:
                int progress = msg.getData().getInt("progress");
                stringBuilder.append(progress).append("-");
                tvstartmsg.setText(stringBuilder);
                break;
        }
        return true;
    }


}