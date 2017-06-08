package com.example.test.mytestdemo.xmlPull;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.test.mytestdemo.R;

import java.io.InputStream;
import java.util.List;

public class PullTestActivity extends Activity {

    //装载Beauty类型的链表，其内容由XML文件解析得到
    private List<Beauty> beautyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_test);

        try {
            //通过assertmanager的open方法获取到beauties.xml文件的输入流
            InputStream is = this.getAssets().open("beauties.xml");
            //初始化自定义的实现类BeautyParserImpl
            BeautyParserImpl pbp = new BeautyParserImpl();
            //调用pbp的parse()方法，将输入流传进去解析，返回的链表结果赋给beautyList
            beautyList = pbp.parse(is);
        } catch (Exception e) {
            e.printStackTrace();
        }

        setupViews();
    }

    /**
     * 将数据显示到手机界面上
     */
    private void setupViews() {
        String result = "";

        for (Beauty b : beautyList) {
            result += b.toString();
        }

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(result);
    }

}
