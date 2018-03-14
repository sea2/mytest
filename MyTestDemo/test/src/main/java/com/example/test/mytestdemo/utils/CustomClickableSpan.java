package com.example.test.mytestdemo.utils;

import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

/**
 * Created by lhy on 2018/3/7.
 */

public abstract class CustomClickableSpan extends ClickableSpan {


    @Override
    public void onClick(View widget) {
    }


    /**
     * Makes the text underlined and in the link color.
     */
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
