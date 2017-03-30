package com.example.test.mytestdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.activity.AnimationActivity;
import com.example.test.mytestdemo.activity.HorizontalScrollViewActivity;
import com.example.test.mytestdemo.activity.SwipeBackActivity;
import com.example.test.mytestdemo.activity.ThreadActivity;
import com.example.test.mytestdemo.aidl.HelloSumAidlActivity;
import com.example.test.mytestdemo.broadcast.BroadcastActivity;
import com.example.test.mytestdemo.notifications.NotificationMainActivity;
import com.example.test.mytestdemo.service.ServiceTestActivity;
import com.example.test.mytestdemo.utils.LogUtils;
import com.example.test.mytestdemo.webview.WebViewTestActivity;

/**
 * @author fml
 *         created at 2016/6/20 13:41
 *         description：LazyFragment使用懒加载方法，避免切换fragment的时候造成其它fragment onstart方法运行,lazyLoad方法代替onstart方法
 */
public class OneFragment extends LazyFragment {
    // 标志fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    Button btn_notification;
    Button btn_horizontalscrollView;
    Button btn_webview;
    Button btn_thread,btn_swipeback,btn_animation;
    Button btn_broadcast, btn_aidl, btn_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_one, container, false);
            btn_notification = (Button) view.findViewById(R.id.btn_notification);
            btn_horizontalscrollView = (Button) view.findViewById(R.id.btn_horizontalscrollView);
            btn_webview = (Button) view.findViewById(R.id.btn_webview);
            btn_thread = (Button) view.findViewById(R.id.btn_thread);
            btn_aidl = (Button) view.findViewById(R.id.btn_aidl);
            btn_broadcast = (Button) view.findViewById(R.id.btn_broadcast);
            btn_service = (Button) view.findViewById(R.id.btn_service);
            btn_swipeback = (Button) view.findViewById(R.id.btn_swipeback);
            btn_animation = (Button) view.findViewById(R.id.btn_animation);
            isPrepared = true;
            lazyLoad();
        }
        return view;
    }


    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible) {
            return;
        }
        Log.e("TAG", "oneFragment--lazyLoad");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initListener();
    }


    private void initListener() {
        btn_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(NotificationMainActivity.class);
            }
        });
        btn_webview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), WebViewTestActivity.class);
                getinfo();
                it.putExtra("key", "123");
                startActivity(it);
            }
        });
        btn_horizontalscrollView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(HorizontalScrollViewActivity.class);
            }
        });
        btn_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(BroadcastActivity.class);
            }
        });
        btn_aidl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(HelloSumAidlActivity.class);
            }
        });
        btn_thread.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(ThreadActivity.class);
            }
        });
        btn_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(ServiceTestActivity.class);
            }
        });
        btn_swipeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(SwipeBackActivity.class);
            }
        });
        btn_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAc(AnimationActivity.class);
            }
        });
    }

    private void getinfo() {
        LogUtils.d("呵呵笑");
    }
}
