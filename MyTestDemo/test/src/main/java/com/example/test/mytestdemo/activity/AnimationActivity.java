package com.example.test.mytestdemo.activity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.test.mytestdemo.R;

public class AnimationActivity extends AppCompatActivity {

    private android.widget.ImageView ivanimation;
    private android.widget.Button btnstart;
    private android.widget.Button btnstop;
    private android.widget.LinearLayout activityanimation;
    AnimationDrawable anim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        this.activityanimation = (LinearLayout) findViewById(R.id.activity_animation);
        this.btnstop = (Button) findViewById(R.id.btn_stop);
        this.btnstart = (Button) findViewById(R.id.btn_start);
        this.ivanimation = (ImageView) findViewById(R.id.iv_animation);


/*--------------------逐帧-----------------------------*/
       /* void start()：开始播放逐帧动画。
        void stop()：停止播放逐帧动画。
        void addFrame(Drawable frame,int duration)：为AnimationDrawable添加一帧，并设置持续时间。
        int getDuration(int i)：得到指定index的帧的持续时间。
        Drawable getFrame(int index)：得到指定index的帧Drawable。
        int getNumberOfFrames()：得到当前AnimationDrawable的所有帧数量。
        boolean isOneShot()：当前AnimationDrawable是否执行一次，返回true执行一次，false循环播放。
        boolean isRunning()：当前AnimationDrawable是否正在播放。
        void setOneShot(boolean oneShot)：设置AnimationDrawable是否执行一次，true执行一次，false循环播放*/
        ivanimation.setBackgroundResource(R.drawable.frame_anmation);
        anim = (AnimationDrawable) ivanimation.getBackground();
        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim.start();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim.stop();
            }
        });


    }
}
