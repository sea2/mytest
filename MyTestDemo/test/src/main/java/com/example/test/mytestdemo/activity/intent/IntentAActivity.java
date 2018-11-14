package com.example.test.mytestdemo.activity.intent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;

public class IntentAActivity extends BaseActivity {

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
                it.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(it);
            }
        });
    }




}
