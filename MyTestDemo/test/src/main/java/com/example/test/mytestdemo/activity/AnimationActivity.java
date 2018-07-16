package com.example.test.mytestdemo.activity;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.test.mytestdemo.R;

public class AnimationActivity extends AppCompatActivity {

    private android.widget.ImageView ivanimation;
    private android.widget.Button btnstart;
    private android.widget.Button btnstop;
    private android.widget.LinearLayout activityanimation;
    private AnimationDrawable anim;
    private ValueAnimator mAnimator;

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


/*-------------------------------------------补间动画-----------------------------------------------*/

        Animation mRotateAnimation = AnimationUtils.loadAnimation(this, R.anim.refresh_coin_rotate_animation);
        mRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (btnstart != null) {//清除动画
                    btnstart.clearAnimation();
                    btnstart.setAnimation(null);
                    btnstart.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mRotateAnimation.setFillEnabled(true);
        mRotateAnimation.setFillAfter(true);
        btnstart.startAnimation(mRotateAnimation);




    }







    //值变化   属性动画
    private void setObjectAnimator(float last, float current) {
        ValueAnimator progressAnimator = ValueAnimator.ofFloat(last, current);
        progressAnimator.setDuration(300);
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
            }
        });
        progressAnimator.start();

    }


    /**
     * translationX 和 translationY：这两个属性控制了View所处的位置，它们的值是由layout容器设置的，是相对于坐标原点（0，0左上角）的一个偏移量。
     rotation, rotationX 和 rotationY：控制View绕着轴点（pivotX和pivotY）旋转。
     scaleX 和 scaleY：控制View基于pivotX和pivotY的缩放。
     pivotX 和 pivotY：旋转的轴点和缩放的基准点，默认是View的中心点。
     x 和 y：描述了view在其父容器中的最终位置，是左上角左标和偏移量（translationX，translationY）的和。
     aplha：透明度，1是完全不透明，0是完全透明。


     */
    private void setValueAnimation() {
        //属性动画
        ObjectAnimator animator = ObjectAnimator.ofFloat(ivanimation, "translationX", 1000, 0f);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animator.setDuration(400);
        animator.start();

      /*  ObjectAnimator animX = ObjectAnimator.ofFloat(this,
                "translationX", x, 0f);
        ObjectAnimator animY = ObjectAnimator.ofFloat(this,
                "translationY", y, 0f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(animX).with(animY);
        //  animSet.play(anim4).after(anim3);
        animSet.setDuration(1000);
        animSet.start();*/
      /*  animator.cancel();*/





    }


    /**
     * 取消
     */
    public void cancel() {
        if (mAnimator != null) {
            mAnimator.removeAllUpdateListeners();
            mAnimator.cancel();
            mAnimator = null;
        }
    }


    /**
     * 暂停
     */
    public void stop() {
        if (mAnimator != null && mAnimator.isRunning()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                mAnimator.pause();
            }
        }
    }

    /**
     * 恢复
     */
    public void restart() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (mAnimator != null && mAnimator.isPaused()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    mAnimator.resume();
                }
            }
        }
    }


}
