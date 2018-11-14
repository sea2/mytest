package com.lhy.ndk.keyboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 特殊情况不能使用的时候可以考虑控制中间组件显隐实现效果
 */
public class EditTextUpAndButtonDownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_text_up_and_button_down);
    }
}
