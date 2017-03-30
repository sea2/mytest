package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;
import com.example.test.mytestdemo.event.TestEvent;
import com.example.test.mytestdemo.ui.MyHorizontalScrollView;

import de.greenrobot.event.EventBus;

public class HorizontalScrollViewActivity extends BaseActivity {

    private android.widget.LinearLayout idgallery;
    private MyHorizontalScrollView activityhorizontalscrollview;
    private boolean isTouchOrRunning;
    private android.widget.Button btnevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_scroll_view);
        this.btnevent = (Button) findViewById(R.id.btn_event);
        this.activityhorizontalscrollview = (MyHorizontalScrollView) findViewById(R.id.activity_horizontal_scroll_view);
        this.idgallery = (LinearLayout) findViewById(R.id.id_gallery);

        btnevent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new TestEvent(109));
            }
        });

        for (int i = 0; i < 10; i++) {
            System.out.print(i + "");
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }
}
