package com.xcm91.relation.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolExecutorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);



        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    SystemClock.sleep(3000);
                    Log.d("google_lenve_fb", "run: "+ finalI);
                }
            };
            fixedThreadPool.execute(runnable);
        }



        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    Log.d("google_lenve_fb", "run: " + Thread.currentThread().getName() + "-----" + finalI);
                    SystemClock.sleep(1000);
                }
            };
            singleThreadExecutor.execute(runnable);
        }


        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable(){
                @Override
                public void run() {
                    Log.d("google_lenve_fb", "run: " + Thread.currentThread().getName() + "----" + finalI);
                }
            };
            cachedThreadPool.execute(runnable);
            SystemClock.sleep(2000);
        }




    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
