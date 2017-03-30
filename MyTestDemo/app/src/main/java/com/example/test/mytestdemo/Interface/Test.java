package com.example.test.mytestdemo.Interface;

/**
 * Created lhy Administrator on 2017/3/3.
 */

public class Test extends TestAbstract implements TestInterface {

    /**
     * 接口和抽象类不能实例化，就是实例化也要指向他们的子类
     */
    TestAbstract mTestAbstract = new Test();
    TestInterface mTestInterface = new Test();

    /**
     * 接口里的方法必须实现
     */
    @Override
    public void testInter() {

    }

    @Override
    public void testInter2() {

    }

    @Override
    void testBas2() {

    }
}




