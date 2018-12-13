package com.zhy.sample_okhttp;

import android.test.InstrumentationTestCase;

import com.example.test.mytestdemo.util.Base64Util;

/**
 * Created by lhy on 2018/11/29.
 */
public class TestSubject extends InstrumentationTestCase {




    public void testPublishSubject() {

        String strBase64 = Base64Util.encodeBase64("123");

        System.out.println(strBase64);


        System.out.println( Base64Util.decodeBase64(strBase64));

    }



}
