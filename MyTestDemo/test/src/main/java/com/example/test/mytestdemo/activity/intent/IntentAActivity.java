package com.example.test.mytestdemo.activity.intent;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.util.ToastUtils;

public class IntentAActivity extends IntentBaseActivity {

    private android.widget.Button btnopen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intent_a);
        this.btnopen = (Button) findViewById(R.id.btn_open);
        btnopen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(IntentAActivity.this, FullscreenActivity.class);
                startActivity(it);
            }
        });
    }


    /**
     * 打开别的app
     * 打开的activity应该设置android:exported="true"
     */
    private void openOther() {
        try {
            ComponentName componentName = new ComponentName("com.tangguo.tangguoxianjin", "com.tangguo.tangguoxianjin.activity.MainActivity");
            Intent intent = new Intent();
            //  Intent intent = new Intent("chroya.foo");
            Bundle bundle = new Bundle();
            bundle.putString("args", "我就是跳转过来的");
            intent.putExtras(bundle);
            intent.setComponent(componentName);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            //判断是否安装B应用，提供下载链接
            ToastUtils.showShortToast("请下载----" + "com.example.intentActivity2");
            e.printStackTrace();
        }
    }

}
