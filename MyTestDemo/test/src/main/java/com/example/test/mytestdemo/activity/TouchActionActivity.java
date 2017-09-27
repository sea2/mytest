package com.example.test.mytestdemo.activity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.ui.MyListView;
import com.example.test.mytestdemo.ui.MyScrollView;

public class TouchActionActivity extends AppCompatActivity {

    private com.example.test.mytestdemo.ui.MyListView listview;
    private com.example.test.mytestdemo.ui.MyScrollView scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_action);
        this.scrollview = (MyScrollView) findViewById(R.id.scrollview);
        this.listview = (MyListView) findViewById(R.id.listview);


        listview.setAdapter(new ListAdapter() {
            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int position) {
                return false;
            }

            @Override
            public void registerDataSetObserver(DataSetObserver observer) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver observer) {

            }

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
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView tv=new TextView(TouchActionActivity.this);
                tv.setText(""+position);
                return tv;
            }

            @Override
            public int getItemViewType(int position) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 1;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }
        });


    }







}
