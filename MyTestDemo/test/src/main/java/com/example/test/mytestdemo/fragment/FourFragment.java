package com.example.test.mytestdemo.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.mytestdemo.R;

/**
 * @author fml
 * created at 2016/6/20 13:41
 * description：LazyFragment使用懒加载方法，避免切换fragment的时候造成其它fragment onstart方法运行,lazyLoad方法代替onstart方法
 */
public class FourFragment extends LazyFragment{
    // 标志fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_four , container , false);
            Log.e("TAG" , "fourFragment--onCreateView");
            isPrepared = true;
            lazyLoad();
        }
        return view;
    }



    @Override
    protected void lazyLoad() {
        if(!isPrepared || !isVisible) {
            return;
        }
        Log.e("TAG" , "fourFragment--lazyLoad");
    }



}
