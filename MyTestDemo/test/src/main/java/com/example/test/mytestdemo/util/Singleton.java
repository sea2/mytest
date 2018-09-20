package com.example.test.mytestdemo.util;

/**
 * Created by lhy on 2017/6/30.
 */


/**
 * 1、饿汉模式：
 */
public class Singleton {

    private static Singleton singleton = new Singleton();

    private Singleton() {

    }

    public static Singleton getInstance() {
        return singleton;
    }


}


/**
 * 2、饱汉模式：很饱不着急，延迟加载，啥时候用啥时候创建实例，存在线程安全问题
 */

class Singleton2 {

    private static Singleton2 singleton;

    private Singleton2() {

    }

    public static synchronized Singleton2 getInstance() {
        if (singleton == null)
            singleton = new Singleton2();
        return singleton;
    }
}


/**
 * 3、双重锁模式：饱汉模式的双重锁模式，提高效率
 */

class Singleton3 {
    private static Singleton3 singleton;

    private Singleton3() {

    }

    public static Singleton3 getInstance() {
        if (singleton == null) {
            synchronized (Singleton3.class) {
                if (singleton == null) {
                    singleton = new Singleton3();
                }
            }
        }
        return singleton;
    }
}
