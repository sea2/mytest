package com.example.test.mytestdemo.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.ui.MyButton;

public class TouchActionActivity extends Activity {

    private ListView listview;


    int count = 40;
    private ListAdapter mListAdapter;
    private ListView stickycontent;
    private MyButton btntest;
    private android.widget.RelativeLayout scrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_action);
        this.scrollview = (RelativeLayout) findViewById(R.id.scrollview);
        this.btntest = (MyButton) findViewById(R.id.btn_test);
        this.listview = (ListView) findViewById(R.id.sticky_content);
        mListAdapter = new ListAdapter();

        listview.setAdapter(mListAdapter);

        AlertDialog dialog = new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("确定退出?").create();
        dialog.show();



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
