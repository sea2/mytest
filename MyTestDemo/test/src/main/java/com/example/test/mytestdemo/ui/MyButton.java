package com.example.test.mytestdemo.ui;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.AccelerateInterpolator;

import com.example.test.mytestdemo.utils.NavigationBarUtil;
import com.example.test.mytestdemo.utils.ScreenUtils;

/**
 * Created by lhy on 2018/1/8.
 */

public class MyButton extends android.support.v7.widget.AppCompatButton {


    private int lastX;
    private int lastY;
    int screenWidth;
    int screenHeight;
    int screenWidthBan;
    int screenHeightBan;

    public MyButton(Context context) {
        this(context, null);
    }

    public MyButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = ScreenUtils.getScreenHeightDisplay() - NavigationBarUtil.getBottomStatusHeight(getContext());
        screenWidthBan = screenWidth / 2;
        screenHeightBan = screenHeight / 2;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();// 获取触摸事件触摸位置的原始X坐标
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:

                int dx = (int) event.getRawX() - lastX;
                int dy = (int) event.getRawY() - lastY;
                int leftInt = this.getLeft() + dx;
                int rightInt = this.getRight() + dx;
                int bottomInt = this.getBottom() + dy;
                int topInt = this.getTop() + dy;

                if (leftInt < 0) {
                    leftInt = 0;
                    rightInt = leftInt + this.getWidth();
                }
                if (topInt < 0) {
                    topInt = 0;
                    bottomInt = topInt + this.getHeight();
                }
                if (rightInt > screenWidth) {
                    rightInt = screenWidth;
                    leftInt = rightInt - this.getWidth();
                }
                if (bottomInt > screenHeight) {
                    bottomInt = screenHeight;
                    topInt = bottomInt - this.getHeight();
                }


                this.layout(leftInt, topInt, rightInt, bottomInt);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                  this.postInvalidate();

                break;
            case MotionEvent.ACTION_CANCEL://超出布局
            case MotionEvent.ACTION_UP:
                int resetY = this.getTop();
                int resetX = this.getLeft();

              /*  this.layout(0, 0, getWidth(), getHeight());
                this.invalidate();*/
                if (resetX > screenWidthBan) {
                    reset2(resetX, resetY);
                } else {
                    reset(resetX, resetY);
                }


                break;
            default:
                break;
        }


        return true;
    }

    /**
     * 恢复
     *
     * @param x
     * @param y
     */
    private void reset(final int x, final int y) {

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(1, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int resetX = (int) (x * (float) animation.getAnimatedValue());
                layout(resetX, y, getWidth() + resetX, getHeight() + y);
                invalidate();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }

    private void reset2(final int x, final int y) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(x, screenWidth - getWidth());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int resetX = (int) animation.getAnimatedValue();
                layout(resetX, y, resetX + getWidth(), getHeight() + y);
                invalidate();
            }
        });
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.start();
    }


}
