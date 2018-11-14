package com.example.test.mytestdemo.app;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.test.mytestdemo.ui.ToastShow;
import com.example.test.mytestdemo.util.SystemBarTintManager;
import com.example.test.mytestdemo.utils.StatusBarUtils;


public abstract class BaseActivity extends FragmentActivity {
    public ToastShow toastShow;
    private boolean isUserDefinedColorForStatusBar = true;
    private SystemBarTintManager tintManager;
    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");
        toastShow = new ToastShow(this);


        //黑色状态栏
        StatusBarUtils.setStatusTextColor(true, this);


    }


    /**
     * 设置状态栏颜色
     *
     * @param colorInt
     */
    public void setStatusBarTintResource(int colorInt) {
        if (isUserDefinedColorForStatusBar) {//自定义状态栏的颜色
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                setLopStatBar(colorInt);
            } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                initSystemBar(colorInt);
            }
        }
    }

    /**
     * 安卓5.0以上版本设置状态栏颜色配合如下两条属性使用
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     */
    private void setLopStatBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(this.getResources().getColor(color));
            // window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * 状态栏半透明 4.4 以上有效配合如下两条属性使用
     * android:clipToPadding="true"
     * android:fitsSystemWindows="true"
     */
    private void initSystemBar(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        if (null == tintManager) {
            tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        } else tintManager.setStatusBarTintResource(color);
    }

    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }


    @Override
    public Resources getResources() {  //不受系统字体影响
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");

    }



    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }


    /**
     * 弹出提示框
     *
     * @param conent 提示语句
     */
    public void showToast(String conent) {
        if (toastShow == null) toastShow = new ToastShow(this);
        if (!TextUtils.isEmpty(conent)) {
            toastShow.show(conent);
        }
    }

    /**
     * 无参数跳转Activity
     *
     * @param classAc
     */
    public void startAc(Class<?> classAc) {
        startAc(classAc, null);
    }

    public void startAc(Class<?> classAc, Bundle bundle) {
        Intent it = new Intent();
        it.setClass(this, classAc);
        if (bundle != null) {
            it.putExtras(bundle);
        }
        startActivity(it);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.i(TAG, "onNewIntent");

    }
}
