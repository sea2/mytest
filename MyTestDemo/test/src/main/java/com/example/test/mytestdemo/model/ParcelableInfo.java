package com.example.test.mytestdemo.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lhy on 2017/9/27.
 */

public class ParcelableInfo implements Parcelable {

    String name;
    int id;

    protected ParcelableInfo(Parcel in) {
        name = in.readString();
        id = in.readInt();
    }

    public static final Creator<ParcelableInfo> CREATOR = new Creator<ParcelableInfo>() {
        @Override
        public ParcelableInfo createFromParcel(Parcel in) {
            return new ParcelableInfo(in);
        }

        @Override
        public ParcelableInfo[] newArray(int size) {
            return new ParcelableInfo[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(id);
    }


}
