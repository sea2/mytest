package com.example.test.mytestdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

public class ScrollViewActivity extends Activity {

    private android.widget.ListView lvdatascrollviewactivity;
    private android.widget.TextView tvshowinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview_test);
        this.tvshowinfo = (TextView) findViewById(R.id.tv_show_info);
        this.lvdatascrollviewactivity = (ListView) findViewById(R.id.lv_data_scrollview_activity);

        String channl_name = AppUtils.getManifestMetaDataValue("CHANNEL_NAME_TEST");
        String channl_value = AppUtils.getManifestMetaDataValue("CHANNEL_VALUE_TEST");
        tvshowinfo.setText(channl_name + "--" + channl_value);

        List<String> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(String.valueOf(i));
        }
        MyAdapter myAdapter = new MyAdapter();
        lvdatascrollviewactivity.setAdapter(myAdapter);


    }


    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 50;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = LayoutInflater.from(ScrollViewActivity.this).inflate(R.layout.item_common_text, null);
            TextView tv_item_scrollview_activity = (TextView) view.findViewById(R.id.tv_item_scrollview_activity);
            tv_item_scrollview_activity.setText(String.valueOf(position));
            return view;
        }
    }

}
