package com.example.test.mytestdemo.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.test.mytestdemo.ui.ToastShow;

import java.util.HashSet;

/**
 * Fragment基类
 *
 * @author xiaocaimi@xcm.com
 */
public class BaseFm extends Fragment {

    protected View fgView;
    // 圈圈提示框
    protected ToastShow toastShow;
    public String TAG = "";

    private HashSet<Integer> isShowingProDailogRequest = new HashSet<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 初始化提示框
        toastShow = new ToastShow(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null != fgView) {
            if (null != fgView.getParent()) {
                ViewGroup parent = (ViewGroup) fgView.getParent();
                parent.removeAllViews();
                parent = null;
            }
            return fgView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    // -------------------------生命周期-------------------------
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


}
