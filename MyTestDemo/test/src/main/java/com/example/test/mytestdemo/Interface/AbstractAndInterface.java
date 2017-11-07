package com.example.test.mytestdemo.Interface;

/**
 * Created lhy Administrator on 2017/3/3.
 * 1、抽象类和接口都不能直接实例化，如果要实例化，抽象类变量必须指向实现所有抽象方法的子类对象，接口变量必须指向实现所有接口方法的类对象。

 2、抽象类要被子类继承，接口要被类实现。

 3、接口只能做方法申明，抽象类中可以做方法申明，也可以做方法实现

 4、接口里定义的变量只能是公共的静态的常量，抽象类中的变量是普通变量。

 5、抽象类里的抽象方法必须全部被子类所实现，如果子类不能全部实现父类抽象方法，那么该子类只能是抽象类。同样，一个实现接口的时候，如不能全部实现接口方法，那么该类也只能为抽象类。

 6、抽象方法只能申明，不能实现。abstract void abc();不能写成abstract void abc(){}。

 7、抽象类里可以没有抽象方法

 8、如果一个类里有抽象方法，那么这个类只能是抽象类

 9、抽象方法要被实现，所以不能是静态的，也不能是私有的。

 10、接口可继承接口，并可多继承接口，但类只能单根继承。
 */

public class AbstractAndInterface extends TestAbstract implements TestInterface {

    /**
     * 接口和抽象类不能实例化，就是实例化也要指向他们的子类
     */
    TestAbstract mTestAbstract = new AbstractAndInterface();
    TestInterface mTestInterface = new AbstractAndInterface();

    /**
     * 接口里的方法必须实现
     */
    @Override
    public void testInter() { }

    @Override
    public void testInter2() {

    }

    @Override
    void testBas2() {

    }
}




