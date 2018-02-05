package com.example.test.mytestdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lhy on 2018/1/30.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder holder;
    private boolean runFlag;
    private Paint paint = new Paint();

    //重写构造方法
    public MySurfaceView(Context context) {
        this(context, null);
    }

    public MySurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        //获取Surface句柄对象，通过该对象获取画布
        holder = this.getHolder();
        //添加回调接口
        holder.addCallback(this);
//      setFocusable(true);
    }

    //实现SurfaceHolder.Callback接口的方法
    @Override//当切换横竖屏时调用
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    //当SurfaceView创建时调用
    @Override
    public void surfaceCreated(SurfaceHolder arg0) {
        //创建时启动线程
        runFlag = true;
        new Thread(this).start();
    }

    //当SurfaceView结束时调用
    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {
        //退出时结束线程
        runFlag = false;
        holder = null;
    }

    //重写run方法
    @Override
    public void run() {
        int x = 0;
        int y = 0;
        int dx = 2;
        int dy = 3;
        int width = 30;
        int height = 30;
        while (runFlag) {
            Canvas canvas = null;
            try {

                if (holder != null) {
                    //锁定画笔对象
                    canvas = holder.lockCanvas();
                    //设置画笔
                    paint.setColor(Color.WHITE);
                    paint.setStyle(Paint.Style.FILL);
                    //填充背景颜色
                    Rect rect = new Rect(0, 0, this.getWidth(), this.getHeight());
                    canvas.drawRect(rect, paint);

                    //绘制小球，设置小球颜色为红
                    paint.setColor(Color.RED);
                    RectF rf = new RectF(x, y, x + width, y + height);
                    canvas.drawOval(rf, paint);

                    //小球移动
                    x += dx;
                    y += dy;
                    //边界判断
                    if (x <= 0 || x >= this.getWidth()) {
                        dx = -dx;
                    }
                    if (y <= 0 || y >= this.getHeight()) {
                        dy = -dy;
                    }

                    Thread.sleep(40);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //画布用完要释放
                if (canvas != null&&holder!=null) {
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}