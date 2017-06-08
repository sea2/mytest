package com.example.test.mytestdemo.inject;

import android.os.Bundle;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

@ContentView(R.layout.activity_inject)
public class InjectActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtil.injectContentView(this);
        ViewUtil.injectViews(this);
    }


}
