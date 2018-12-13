package com.example.test.mytestdemo.socket;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by lhy on 2018/4/23.
 */
public class SocketActivity extends BaseActivity {
    private TextView tv_msg = null;
    private EditText ed_msg = null;
    private Button btn_send = null;
    private static final String HOST = "192.168.3.190";//服务器地址
    private static final int PORT = 8888;//连接端口号
    private Socket socket = null;
    private BufferedReader mBufferedReader = null;
    private PrintWriter mPrintWriter = null;
    private boolean mWorking = true;

    private Thread mThread;
    //接收线程发送过来信息，并用TextView追加显示
    public Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_msg.append((CharSequence) msg.obj);
        }
    };
    private Button btn_close;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        tv_msg = (TextView) findViewById(R.id.txt_1);
        ed_msg = (EditText) findViewById(R.id.et_talk);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_close = (Button) findViewById(R.id.btn_close);


        btn_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSocket();
            }
        });
        btn_send.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String msg = ed_msg.getText().toString();
                if (socket.isConnected()) {//如果服务器连接
                    if (!socket.isOutputShutdown()) {//如果输出流没有断开
                        new Thread(new Runnable() {//发送消息要在子线程
                            @Override
                            public void run() {
                                mPrintWriter.println(msg);//点击按钮发送消息
                            }
                        }).start();
                        ed_msg.setText("");//清空编辑框
                    }
                }
            }
        });


        //启动线程，连接服务器，并用死循环守候，接收服务器发送过来的数据
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                connection();// 连接到服务器
                try {
                    while (mWorking) {//死循环守护，监控服务器发来的消息
                        if (!socket.isClosed()) {//如果服务器没有关闭
                            if (socket.isConnected()) {//连接正常
                                if (!socket.isInputShutdown()) {//如果输入流没有断开

                                    // 步骤3：通过输入流读取器对象 接收服务器发送过来的数据
                                    String mLine;
                                    if ((mLine = mBufferedReader.readLine()) != null) {//读取接收的信息
                                        mLine += "\n";
                                        Message message = new Message();
                                        message.obj = mLine;
                                        mHandler.sendMessage(message);//通知UI更新
                                    } else {

                                    }
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        mThread.start();
    }

    /**
     * 连接服务器
     */
    private void connection() {
        try {
            // 步骤1：连接服务器
            socket = new Socket(HOST, PORT);
            // 步骤2：创建输入流对象InputStream //接收消息的流对象
            mBufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //发送消息的流对象
            mPrintWriter = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        } catch (IOException ex) {
            ex.printStackTrace();
            ShowDialog("连接服务器失败：" + ex.getMessage());
        }
    }

    /**
     * 如果连接出现异常，弹出AlertDialog！
     */
    public void ShowDialog(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new AlertDialog.Builder(SocketActivity.this).setTitle("通知").setMessage(msg)
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show();
            }
        });

    }


    /**
     * 关闭socket
     *
     * @throws IOException
     */
    public void closeSocket() {
        try {
            if (mBufferedReader != null) mBufferedReader.close();
            if (mPrintWriter != null) mPrintWriter.close();
            if (socket != null) socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        if (mWorking) {
            if (mThread != null && mThread.isAlive()) {
                mThread.interrupt();
                mThread = null;
            }
            mWorking = false;
        }
        super.onDestroy();
    }


}


