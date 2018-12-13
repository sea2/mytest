package com.zhy.sample_okhttp;


import com.example.test.mytestdemo.util.Base64Util;

import org.junit.Test;

/**
 * Created by lhy on 2018/12/13.
 */
public class SafeTest {


    @Test
    public void testBase64() {

        String strBase64 = Base64Util.encodeBase64("123");

        System.out.println(strBase64);


        System.out.println( Base64Util.decodeBase64(strBase64));

    }


}
