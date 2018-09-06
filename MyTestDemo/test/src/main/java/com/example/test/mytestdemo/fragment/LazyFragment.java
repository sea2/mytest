package com.example.test.mytestdemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author fml
 * created at 2016/6/24 15:17
 * description：懒加载
 */
public abstract class LazyFragment extends Fragment {
    //用于标记视图是否初始化
    protected boolean isVisible;
    private String TAG = this.getClass().getSimpleName();

    //在onCreate方法之前调用，用来判断Fragment的UI是否是可见的
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        Log.i(TAG, "setUserVisibleHint:"+isVisibleToUser);

        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 视图可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 自定义抽象加载数据方法
     */
    protected abstract void lazyLoad();

    /**
     * 视图不可见
     */
    protected void onInvisible() {
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
        it.setClass(getActivity(), classAc);
        if (bundle != null) {
            it.putExtras(bundle);
        }
        startActivity(it);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate");

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.i(TAG, "onStart");

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");


    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i(TAG, "onDestroyView");


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i(TAG, "onDetach");

    }
}
