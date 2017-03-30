package com.example.test.mytestdemo.Interface;

/**
 * Created by Administrator on 2017/3/3.
 */

public interface TestInterface {

    /**
     * 接口定义的 变量 是必须是 public static final,所以必须赋值
     * 接口里定义的变量只能是公共的静态的常量
     */
    int testInter = 0;


    /**
     * 接口--只能方法声明，不能方法实现
     * //实现报错
     * void test2() { }
     */
    //声明
    abstract void testInter();

    void testInter2();


}
