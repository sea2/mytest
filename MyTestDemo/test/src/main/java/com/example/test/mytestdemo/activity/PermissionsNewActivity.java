package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.test.mytestdemo.app.BaseActivity;

/**
 * Created by lhy on 2017/3/29.
 */

public class PermissionsNewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout ll_main = new LinearLayout(this);
        ll_main.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ll_main.setLayoutParams(params);

        for (int i = 0; i < 10; i++) {
            Button mbt = new Button(this);
            mbt.setText("测试");
            ll_main.addView(mbt);

        }
        setContentView(ll_main);
    }

}
