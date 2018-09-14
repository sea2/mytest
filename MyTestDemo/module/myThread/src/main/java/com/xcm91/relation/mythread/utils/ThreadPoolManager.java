package com.xcm91.relation.mythread.utils;

import android.util.Log;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Jenly <a href="mailto:jenly1314@gmail.com">Jenly</a>
 * @since 2017/3/13
 */

public class ThreadPoolManager {

    private static ThreadPoolProxy mInstance;
    //CPU核心数
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    //核心线程数
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;

    //最大线程数
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

    public static ThreadPoolProxy getInstance() {
        if (mInstance == null) {
            synchronized (ThreadPoolManager.class) {
                if (mInstance == null) {
                    Log.i("ThreadPoolManager", "corePoolSize: " + CORE_POOL_SIZE + "----maximumPoolSize:" + MAX_POOL_SIZE);
                    mInstance = new ThreadPoolProxy(CORE_POOL_SIZE, MAX_POOL_SIZE);
                }
            }
        }

        return mInstance;
    }


    /**
     * 线程池代理
     * <p>
     * 线程池初始化方法
     * corePoolSize 核心线程池大小----2--- 线程池维护线程的最少数量
     * maximumPoolSize 最大线程池大小----5---线程池维护线程的最大数量
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit--线程池维护线程所允许的空闲时间
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES--线程池维护线程所允许的空闲时间的单位
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(5)==线程池所使用的缓冲队列==5容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 线程池对拒绝任务的处理策略，当提交任务数超过maxmumPoolSize+workQueue之和时,
     * 即当提交第41个任务时(前面线程都没有执行完),
     * 任务会交给RejectedExecutionHandler来处理
     */
    public static class ThreadPoolProxy {

        private ThreadPoolExecutor mThreadPoolExecutor;


        public ThreadPoolProxy(int corePoolSize, int maximumPoolSize) {
            initThreadPoolExecutor(corePoolSize, maximumPoolSize);
        }

        private void initThreadPoolExecutor(int corePoolSize, int maximumPoolSize) {

            if (mThreadPoolExecutor == null) {
//                ExecutorService executorService = Executors.newFixedThreadPool(3);
                //阻塞缓冲队列
                BlockingDeque<Runnable> workQueue = new LinkedBlockingDeque<>();

                //线程工厂
                ThreadFactory threadFactory = Executors.defaultThreadFactory();
                //拒绝任务处理策略（抛弃旧的任务）
                RejectedExecutionHandler handler = new ThreadPoolExecutor.DiscardPolicy();

                mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 0, TimeUnit.MICROSECONDS, workQueue, threadFactory, handler);
            }
        }

        public void submit(Runnable task) {
            if (mThreadPoolExecutor != null) {
                mThreadPoolExecutor.submit(task);
            }
        }

        public void execute(Runnable task) {
            if (mThreadPoolExecutor != null) {
                mThreadPoolExecutor.execute(task);
            }
        }

        public void remove(Runnable task) {
            if (mThreadPoolExecutor != null && !mThreadPoolExecutor.isShutdown()) {
                mThreadPoolExecutor.remove(task);
            }
        }


        /**
         * 线程关闭
         * 　shutdown()：不会立即终止线程池，而是要等所有任务缓存队列中的任务都执行完后才终止，但再也不会接受新的任务
         * 　　shutdownNow()：立即终止线程池，并尝试打断正在执行的任务，并且清空任务缓存队列，返回尚未执行的任务
         */
        public void destroy() {
            if (mThreadPoolExecutor != null) {
                try {
                    mThreadPoolExecutor.shutdown();
                    // (所有的任务都结束的时候，返回TRUE)
                    if (!mThreadPoolExecutor.awaitTermination(1000L, TimeUnit.MILLISECONDS)) {
                        // 超时的时候向线程池中所有的线程发出中断(interrupted)。
                        mThreadPoolExecutor.shutdownNow();
                    }
                } catch (InterruptedException e) {
                    // awaitTermination方法被中断的时候也中止线程池中全部的线程的执行。
                    mThreadPoolExecutor.shutdownNow();
                }
            }
        }


    }

}
