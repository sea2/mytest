package com.xcm91.relation.refreshview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;

import java.util.ArrayList;
import java.util.List;

public class RefreshGridViewActivity extends AppCompatActivity {

    private List<Integer> list = new ArrayList<>();
    private PullToRefreshGridView pullToRefreshGridView;
    private Adapter adapter;
    private GridView gridView;
    private TextView tv_loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_grid_view);
        pullToRefreshGridView = (PullToRefreshGridView) findViewById(R.id.pullToRefreshGridView);
        tv_loading = (TextView) findViewById(R.id.tv_loading);

        pullToRefreshGridView.setScrollLoadEnabled(true, true);
        gridView = pullToRefreshGridView.getRefreshableView();
        // 进入页面就显示刷新
        pullToRefreshGridView.doPullRefreshing(true, 100);

        gridView.setNumColumns(2);

        adapter = new Adapter();
        gridView.setAdapter(adapter);
        pullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 1; i <= 10; i++) {
                            list.add(i);
                        }
                        adapter.notifyDataSetChanged();
                        pullToRefreshGridView.onPullDownRefreshComplete();
                        pullToRefreshGridView.onPullUpRefreshComplete();
                        pullToRefreshGridView.setHasMoreData(true);
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                tv_loading.setVisibility(View.VISIBLE);

                int oldSize = list.size();
                for (int i = oldSize; i <= oldSize + 10; i++) {
                    list.add(i);
                }
                adapter.notifyDataSetChanged();
                pullToRefreshGridView.onPullUpRefreshComplete();
                pullToRefreshGridView.onPullDownRefreshComplete();
                pullToRefreshGridView.onPullUpRefreshComplete();
                if (list.size() < 40) {
                    pullToRefreshGridView.setHasMoreData(true);
                } else pullToRefreshGridView.setHasMoreData(false);

                tv_loading.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_loading.setVisibility(View.GONE);
                    }
                }, 1000);
            }
        });
    }

    class Adapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(RefreshGridViewActivity.this);
            textView.setPadding(30, 30, 30, 30);
            textView.setText(list.get(i) + "-");
            return textView;
        }
    }

}
