package com.example.test.mytestdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.utils.ArraysUtils;

import java.util.ArrayList;

/**
 * Created by lhy on 2018/12/26.
 */
public class CommonAdapter extends BaseAdapter {

    private ArrayList<String> list;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public CommonAdapter(Context context, ArrayList<String> list) {
        this.list = list;
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        if (ArraysUtils.isEmpty(list)) {
            return 0;
        }
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_common_text, null);
            viewHolder.tv_info = (TextView) convertView.findViewById(R.id.tv_item_scrollview_activity);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String str = list.get(position);
        viewHolder.tv_info.setText(str);

        return convertView;
    }


    private static class ViewHolder {
        TextView tv_info;
    }

}
