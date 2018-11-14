package com.example.test.mytestdemo.activity;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.BackgroundColorSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.app.BaseActivity;
import com.example.test.mytestdemo.utils.CustomClickableSpan;


public class SpannableStringActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spannable_string_builder);
        mode2();
        mode3();
        mode4();
        mode5();
        mode6();
        mode7();
        mode8();
        mode9();
        mode10();


    }



    /**
     * 使用SpannableStringBuilder设置样式——字体颜色
     */
    private void mode2() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置样式——字体颜色");
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#009ad6"));
        spannableString.setSpan(colorSpan, 5, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        ((TextView) findViewById(R.id.mode2)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——背景颜色
     */
    private void mode3() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置样式——背景颜色");
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(Color.parseColor("#009ad6"));
        spannableString.setSpan(bgColorSpan, 6, 10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        ((TextView) findViewById(R.id.mode3)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——字体大小
     */
    private void mode4() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置样式——字体大小");
        AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(20);
        spannableString.setSpan(absoluteSizeSpan, 6,10, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        ((TextView) findViewById(R.id.mode4)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——粗体\斜体
     */
    private void mode5() {
        SpannableStringBuilder spannableStringBuilder  = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置样式——粗体\\斜体\\粗斜体");
        //setSpan可多次使用
        StyleSpan styleSpan = new StyleSpan(Typeface.BOLD);//粗体
        spannableString.setSpan(styleSpan, 6, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan styleSpan2 = new StyleSpan(Typeface.ITALIC);//斜体
        spannableString.setSpan(styleSpan2, 9, 11, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        StyleSpan styleSpan3 = new StyleSpan(Typeface.BOLD_ITALIC);//粗斜体
        spannableString.setSpan(styleSpan3, 12, 15, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);


        ((TextView) findViewById(R.id.mode5)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——删除线
     */
    private void mode6() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置样式——删除线");
        StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
        spannableString.setSpan(strikethroughSpan, 0, 8, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        ((TextView) findViewById(R.id.mode6)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——下划线
     */
    private void mode7() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        UnderlineSpan underlineSpan = new UnderlineSpan();
        SpannableString spannableString = new SpannableString("设置样式——下划线");
        spannableString.setSpan(underlineSpan, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        ((TextView) findViewById(R.id.mode7)).setText(spannableStringBuilder);
    }

    /**
     * 使用SpannableStringBuilder设置样式——图片
     */
    private void mode8() {
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        spannableString.append("设置样式——图片");
        ImageSpan imageSpan = new ImageSpan(this, R.mipmap.ic_launcher);
        //也可以这样
        //Drawable drawable = getResources().getDrawable(R.mipmap.ic_launcher);
        //drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        //ImageSpan imageSpan1 = new ImageSpan(drawable);
        //将index为6、7的字符用图片替代

        spannableString.setSpan(imageSpan, 4, 6, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        ((TextView) findViewById(R.id.mode8)).setText(spannableString);
    }

    /**
     * 使用SpannableStringBuilder设置点击事件
     * 去除下划线可以自定义ClickableSpan
     */
    private void mode9() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        SpannableString spannableString = new SpannableString("设置点击事件");
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SpannableStringActivity.this, "请不要点我", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                ds.setUnderlineText(false);
            }
        };
        spannableString.setSpan(clickableSpan, 0,spannableString.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableString);

        TextView textView = (TextView) findViewById(R.id.mode9);
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /**
     * 使用SpannableStringBuilder事件组合使用
     */
    private void mode10() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();


        String text="点击事件";
        SpannableString spannableString = new SpannableString(text);
        spannableString.setSpan(new CustomClickableSpan() {
            @Override
            public void onClick(View widget) {
                Toast.makeText(SpannableStringActivity.this, "点我我看到了", Toast.LENGTH_SHORT).show();
            }
        },0,text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#215865")),0,text.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableStringBuilder.append(spannableString);



        //文字颜色
        String textColor="文字颜色";
        SpannableString spannableStringColor = new SpannableString(textColor);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#cccccc"));
        spannableStringColor.setSpan(colorSpan, 0, textColor.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableStringColor);


        //文字背景颜色
        String textBgColor="文字背景颜色";
        SpannableString spannableStringBGColor = new SpannableString(textBgColor);
        BackgroundColorSpan bgColorSpan = new BackgroundColorSpan(Color.parseColor("#009ad6"));
        spannableStringBGColor.setSpan(bgColorSpan, 0,textBgColor.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        spannableStringBuilder.append(spannableStringBGColor);


        TextView textView = (TextView) findViewById(R.id.mode10);
        textView.setText(spannableStringBuilder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());

    }



}
