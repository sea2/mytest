package com.example.test.mytestdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * @author fml
 *         created at 2016/6/24 15:17
 *         description：懒加载
 */
public abstract class LazyFragment extends Fragment {
    //用于标记视图是否初始化
    protected boolean isVisible;

    //在onCreate方法之前调用，用来判断Fragment的UI是否是可见的
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
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


}
