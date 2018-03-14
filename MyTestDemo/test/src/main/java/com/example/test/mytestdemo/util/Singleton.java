package com.example.test.mytestdemo.util;

/**
 * Created by lhy on 2017/6/30.
 */

public class Singleton {

  private   static Singleton instance = null;


    public static Singleton getInstance() {
        synchronized (Singleton.class) {
            if (instance == null) {
                instance = new Singleton();
            }
        }
        return instance;
    }

   /* public synchronized static Singleton getInstance() {
            if (instance == null) {
                instance = new Singleton();
            }
        return instance;
    }*/




}
