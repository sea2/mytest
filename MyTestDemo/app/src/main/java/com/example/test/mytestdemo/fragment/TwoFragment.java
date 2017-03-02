package com.example.test.mytestdemo.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.util.ArraysUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fml
 *         created at 2016/6/20 13:41
 *         description：LazyFragment使用懒加载方法，避免切换fragment的时候造成其它fragment onstart方法运行,lazyLoad方法代替onstart方法
 */
public class TwoFragment extends LazyFragment {
    // 标志fragment是否初始化完成
    private boolean isPrepared;
    private View view;
    private ListView mListView;
    private List<Integer> intList;
    Context context;
    Button btn_refersh;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_two, container, false);
            mListView = (ListView) view.findViewById(R.id.listview);
            btn_refersh = (Button) view.findViewById(R.id.btn_refersh);
            Log.e("TAG", "twoFragment--onCreateView");
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
        Log.e("TAG", "twoFragment--lazyLoad");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        intList = new ArrayList<>();
        for (int i = 1; i < 100; i++)
            intList.add(i);
        MyAdapter adapter = new MyAdapter();
        mListView.setAdapter(adapter);
        btn_refersh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateItem(88);
            }
        });



    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (ArraysUtils.isNotEmpty(intList)) {
                return intList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int i) {
            return intList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @SuppressLint("ViewHolder")
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            view = LayoutInflater.from(context).inflate(R.layout.list_item_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.tv_position);
            tv.setText(String.valueOf(intList.get(position)));
            return view;
        }
    }


    private void updateItem(int position) {
        int visiblePosition = mListView.getFirstVisiblePosition();
        if (position - visiblePosition >= 0) {
            View view = mListView.getChildAt(position - visiblePosition);
            if (view != null) {
                TextView tv = (TextView) view.findViewById(R.id.tv_position);
                tv.setText("更新完成");
            }
        }
    }


}
