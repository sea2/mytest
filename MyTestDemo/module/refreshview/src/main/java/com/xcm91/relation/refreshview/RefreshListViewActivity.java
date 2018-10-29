package com.xcm91.relation.refreshview;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

public class RefreshListViewActivity extends AppCompatActivity {
    List<Integer> list = new ArrayList<>();
    PullToRefreshListView pullToRefreshListView;
    Adapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setScrollLoadEnabled(true,true);
        listView = pullToRefreshListView.getRefreshableView();
        // 进入页面就显示刷新
        pullToRefreshListView.doPullRefreshing(true, 100);

        adapter = new Adapter();
        listView.setAdapter(adapter);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list.clear();
                        for (int i = 1; i <= 10; i++) {
                            list.add(i);
                        }
                        adapter.notifyDataSetChanged();
                        pullToRefreshListView.onPullDownRefreshComplete();
                        pullToRefreshListView.onPullUpRefreshComplete();
                        pullToRefreshListView.setHasMoreData(true);
                    }
                }, 1000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                SystemClock.sleep(300);
                int oldSize = list.size();
                for (int i = oldSize; i <= oldSize + 10; i++) {
                    list.add(i);
                }
                adapter.notifyDataSetChanged();
                pullToRefreshListView.onPullUpRefreshComplete();
                pullToRefreshListView.onPullDownRefreshComplete();
                pullToRefreshListView.onPullUpRefreshComplete();
                if (list.size() < 40)
                    pullToRefreshListView.setHasMoreData(true);
                else pullToRefreshListView.setHasMoreData(false);
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
            TextView textView = new TextView(RefreshListViewActivity.this);
            textView.setPadding(30, 30, 30, 30);
            textView.setText(list.get(i) + "-");
            return textView;
        }
    }

}
