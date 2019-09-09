package com.xcm91.relation.kotlin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by lhy on 2017/6/14.
 */

public class TestEmpleActivty extends Activity {
    String tag = this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TextView tv = new TextView(this);
        Intent it = new Intent(TestEmpleActivty.this, MainActivity.class);
        startActivity(it);

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

        Log.e(tag, "" + get1(1));

        StringUtils.INSTANCE.isEmpty("");


    }


    private int get1(int c) {

        for (int i = 0; i < 10; i++) {

        }

        return c + 1;
    }
}
