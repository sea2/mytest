package com.example.test.mytestdemo.config;


import com.example.test.mytestdemo.BuildConfig;

/**
 * Created by Administrator on 2016/3/21.
 */
public class Constants {
    /**
     * 测试模式  DEV,
     * 生产模式  PRO,
     * 测试模式 TES;
     */
    public static RunModel AppRunModel = ("release".equals(BuildConfig.BUILD_TYPE) && !"内部测试_1".equals(BuildConfig.FLAVOR)) ? RunModel.PRO : RunModel.DEV;


    public enum RunModel {
        /**
         * 测试模式
         */
        DEV,
        /**
         * 预发布
         */
        UAT,
        /**
         * 生产模式
         */
        PRO,
        TES;
    }


}
