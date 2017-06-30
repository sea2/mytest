package com.xcm91.relation.kotlin;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by lhy on 2017/6/14.
 */

public class TestEmpleActivty extends Activity{
     String tag=this.getClass().getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        TextView tv=new TextView(this);
      
    }
}
