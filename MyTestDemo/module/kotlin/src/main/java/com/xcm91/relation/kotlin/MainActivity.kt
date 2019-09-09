package com.xcm91.relation.kotlin

import android.app.Activity
import android.os.Bundle
import android.view.Window

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        get1();
        get1(1);

        StringUtils.isEmpty("")


        var mUserInfo = UserInfo()
        mUserInfo.age = 1
        mUserInfo.name="12"
    }

    private fun get1() {


    }


    private fun get1(int1: Int): Int {
        for (i in 0..int1) {

        }

        return int1 + 1
    }


}
