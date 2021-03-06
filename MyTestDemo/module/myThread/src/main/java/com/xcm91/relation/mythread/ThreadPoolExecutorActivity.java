package com.xcm91.relation.mythread;

import android.app.Activity;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;

import com.xcm91.relation.mythread.utils.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolExecutorActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_pool_executor);


       ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(3000);
                    Log.d("google_lenve_fb", "run: " + finalI);
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
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(2000);
                    Log.d("google_lenve_fb", "run: " + Thread.currentThread().getName() + "----" + finalI);
                }
            };
            cachedThreadPool.execute(runnable);
        }


        for (int i = 0; i < 30; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    SystemClock.sleep(1000);
                    Log.e("ThreadPoolManager", "run: " + Thread.currentThread().getName() + "----" + finalI);
                }
            };
            ThreadPoolManager.getInstance().execute(runnable);
        }


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.e("Handler", "run: " + Thread.currentThread().getName() + "----");
            }
        }, 100);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.e("runOnUiThread", "run: " + Thread.currentThread().getName() + "----");
            }
        });




    }


    public void stopThreadPool(ExecutorService threadPool, long timeout, TimeUnit unit)  {
        try {
            threadPool.shutdown();
            // (所有的任务都结束的时候，返回TRUE)
            if (!threadPool.awaitTermination(1000L, TimeUnit.MILLISECONDS)) {
                // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                threadPool.shutdownNow();
            }
        } catch (InterruptedException e) {
            // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
            threadPool.shutdownNow();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    ScheduledExecutorService executorService;

    public void open(View view) {
        beginExecutorScan();
    }


    private void beginExecutorScan() {
        //先取消上一个任务，防止重复的任务
        endExecutorScan();
        executorService = Executors.newSingleThreadScheduledExecutor();
            executorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Log.e("GMSDK", "getName" + Thread.currentThread().getName());

                }
            }, 0,5, TimeUnit.SECONDS);
    }

    private void endExecutorScan() {
        if (executorService != null) {
            executorService.shutdownNow();
            executorService = null;//非单例模式，置空防止重复的任务
        }
    }



}
