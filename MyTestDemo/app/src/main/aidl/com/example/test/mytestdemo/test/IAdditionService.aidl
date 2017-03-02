// IAdditionService.aidl
package com.example.test.mytestdemo.test;

// Declare any non-default types here with import statements

interface IAdditionService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    boolean callPay(String name, String pwd, int money) ;
}
