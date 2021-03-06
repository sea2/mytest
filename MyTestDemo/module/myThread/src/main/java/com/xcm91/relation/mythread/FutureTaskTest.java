package com.xcm91.relation.mythread;

import java.util.concurrent.*;

public class FutureTaskTest {

    //同步关键字特殊域
    static volatile int num = 0;


    public static void main(String[] args) {
     /*    for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int n = 0; n < 10; n++) {
                        num++;
                        System.out.println(Thread.currentThread().getName() + "=" + num);
                    }
                }
            }).start();
        }*/

        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (this) {
                        for (int n = 0; n < 10; n++) {
                            num++;
                            System.out.println(Thread.currentThread().getName() + "=" + num);
                        }
                    }
                }
            }).start();
        }


        /**

         * ExecutorService

         */

        ExecutorService mExecutor = Executors.newSingleThreadExecutor();
        Future<Integer> result2 = mExecutor.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });
        try {
            Integer it1 = result2.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        /**

         * FutureTask则是一个RunnableFuture<v>，即实现了Runnbale又实现了Futrue<v>这两个接口，
         * 另外它还可以包装Runnable(实际上会转换为Callable)和Callable
         * <v>，所以一般来讲是一个符合体了，它可以通过Thread包装来直接执行，也可以提交给ExecuteService来执行
         * ，并且还可以通过v get()返回执行结果，在线程体没有执行完成的时候，主线程一直阻塞等待，执行完则直接返回结果。
         */
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return fibc(20);
            }
        });
        // 提交futureTask
        mExecutor.submit(futureTask);


        /**
         * 通过简单的测试程序来试验Runnable、Callable通过Executor来调度的时候与Future的关系
         */
        // 创建一个执行任务的服务
        ExecutorService executor = Executors.newFixedThreadPool(3);
        try {
            //1.Runnable通过Future返回结果为空
            //创建一个Runnable，来调度，等待任务执行完毕，取得返回结果
            Future<?> runnable1 = executor.submit(new Runnable() {
                @Override
                public void run() {
                    System.out.println("runnable1 running.");
                }
            });
            System.out.println("Runnable1:" + runnable1.get());

            // 2.Callable通过Future能返回结果
            //提交并执行任务，任务启动时返回了一个 Future对象，
            // 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
            Future<String> future1 = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    // TODO Auto-generated method stub
                    return "result=task1";
                }
            });
            // 获得任务的结果，如果调用get方法，当前线程会等待任务执行完毕后才往下执行
            System.out.println("task1: " + future1.get());

            //3. 对Callable调用cancel可以对对该任务进行中断
            //提交并执行任务，任务启动时返回了一个 Future对象，
            // 如果想得到任务执行的结果或者是异常可对这个Future对象进行操作
            Future<String> future2 = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    try {
                        while (true) {
                            System.out.println("task2 running.");
                            Thread.sleep(50);
                        }
                    } catch (InterruptedException e) {
                        System.out.println("Interrupted task2.");
                    }
                    return "task2=false";
                }
            });

            // 等待5秒后，再停止第二个任务。因为第二个任务进行的是无限循环
            Thread.sleep(10);
            System.out.println("task2 cancel: " + future2.cancel(true));

            // 4.用Callable时抛出异常则Future什么也取不到了
            // 获取第三个任务的输出，因为执行第三个任务会引起异常
            // 所以下面的语句将引起异常的抛出
            Future<String> future3 = executor.submit(new Callable<String>() {

                @Override
                public String call() throws Exception {
                    throw new Exception("task3 throw exception!");
                }

            });
            System.out.println("task3: " + future3.get());
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // 停止任务执行服务
        executor.shutdownNow();


        Callable<String> task = new Callable<String>() {
            public String call() {
                System.out.println("Sleep start.");
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("Sleep end.");
                return "time=" + System.currentTimeMillis();
            }
        };

        //直接使用Thread的方式执行
        FutureTask<String> ft = new FutureTask<String>(task);
        Thread t = new Thread(ft);
        t.start();
        try {
            System.out.println("waiting execute result");
            System.out.println("result = " + ft.get());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //使用Executors来执行
        System.out.println("=========");
        FutureTask<String> ft2 = new FutureTask<String>(task);
        Executors.newSingleThreadExecutor().submit(ft2);
        try {
            System.out.println("waiting execute result");
            System.out.println("result = " + ft2.get());
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    /**
     * 效率底下的斐波那契数列, 耗时的操作
     *
     * @param num
     * @return
     */

    static int fibc(int num) {
        if (num == 0) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fibc(num - 1) + fibc(num - 2);
    }

}
