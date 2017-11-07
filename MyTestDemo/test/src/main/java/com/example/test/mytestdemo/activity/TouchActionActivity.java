package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.test.mytestdemo.R;

public class TouchActionActivity extends AppCompatActivity {

    private ListView listview;


    int count = 40;
    private ListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_action);
        this.listview = (ListView) findViewById(R.id.sticky_content);
        mListAdapter = new ListAdapter();

        listview.setAdapter(mListAdapter);

    }


    private int getCount1() {
        return count;
    }


    private class ListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return getCount1();
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
            TextView tv = new TextView(TouchActionActivity.this);
            tv.setText(String.valueOf(position));
            return tv;
        }
    }

}
