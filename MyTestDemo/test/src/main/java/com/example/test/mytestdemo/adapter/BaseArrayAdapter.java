package com.example.test.mytestdemo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import com.example.test.mytestdemo.model.SerializableInfo;

/**
 * Created by lhy on 2018/4/18.
 */
public class BaseArrayAdapter extends ArrayAdapter<SerializableInfo> {


    public BaseArrayAdapter(@NonNull Context context, int resource, @NonNull SerializableInfo[] objects) {
        super(context, resource, objects);
    }


    @Nullable
    @Override
    public SerializableInfo getItem(int position) {
        return super.getItem(position);
    }


}
