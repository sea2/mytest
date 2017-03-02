package com.example.test.mytestdemo.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.example.test.mytestdemo.test.IAdditionService;


/*
 * This class exposes the service to client
 */
public class AdditionService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myIBinder;
    }


    //中间人
    IBinder myIBinder = new IAdditionService.Stub() {
        @Override
        public boolean callPay(String name, String pwd, int money) throws RemoteException {
            return pay(name, pwd, money);
        }
    };


    //支付宝支付的方法
    public boolean pay(String name, String pwd, int money) {
        System.out.println("1验证用户名和密码 ");
        System.out.println("2验证手机是否携带病毒");
        System.out.println("3调用C语言 做一些加密处理 ");

        if ("abc".equals(name) && "123".equals(pwd) && money < 5000 && money > 0) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
