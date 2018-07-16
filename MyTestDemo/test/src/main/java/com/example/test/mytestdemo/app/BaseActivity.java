package com.example.test.mytestdemo.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;

import com.example.test.mytestdemo.ui.ToastShow;


public abstract class BaseActivity extends FragmentActivity {
    public ToastShow toastShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(this.getClass().getSimpleName(), "onCreate");
        toastShow = new ToastShow(this);





    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.i(this.getClass().getSimpleName(), "onStart");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.i(this.getClass().getSimpleName(), "onResume");

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(this.getClass().getSimpleName(), "onRestart");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.i(this.getClass().getSimpleName(), "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(), "onDestroy");
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


}
