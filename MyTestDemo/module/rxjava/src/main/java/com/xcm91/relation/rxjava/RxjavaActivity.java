package com.xcm91.relation.rxjava;

import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class RxjavaActivity extends AppCompatActivity implements PullListener {


    private TextView tv_send;
    String TAG = this.getClass().getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        this.tv_send = (TextView) findViewById(R.id.tv_send);


        /**
         * 线程要是没有subscribeOn，observeOn切换都是默认按照上一级执行的线程环境
         * */
        Observable.create(new Observable.OnSubscribe<String>() {   //要是没有subscribeOn控制，执行在代码调用的环境的线程，次此调用在主线程，所以在主线程执行
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i("test1", "Observable------" + Thread.currentThread().getName());
                subscriber.onNext("http://baidu.com");
            }
        }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Action0() {//执行在被观察者（订阅者）之前，比如访问网络的dialog加载框，受紧跟的subscribeOn控制线程执行环境
                    @Override
                    public void call() {
                        Log.i("test1", "doOnSubscribe------" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        Log.i("test1", "map------" + Thread.currentThread().getName());

                        return s.length();
                    }
                })
                .subscribeOn(Schedulers.io())//控制被观察者（订阅者）执行的线程
                .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("test1", "Observer--onCompleted----" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test1", "Observer--onError----" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onNext(Integer s) {
                        Log.i("test1", "Observer--onNext----" + Thread.currentThread().getName());
                    }
                });


       /*<-------------------------------------------------from操作符    just----------------------------------------------------------------------------------->*/

        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("12");
        list.add("123");
        list.add("1234");

        String[] arrat = new String[]{"1", "23", "345"};

        Observable.from(list)
                .map(new Func1<String, Integer>() {
                    @Override
                    public Integer call(String s) {
                        return s.length();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {
                        Log.i("test_from", "Observer--onCompleted----" + Thread.currentThread().getName());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }


                    @Override
                    public void onNext(Integer s) {
                        Log.i("test_from", "Observer--onNext----" + Thread.currentThread().getName());
                    }
                });


        Observable.just("1", "12", "123").subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String strings) {

            }
        });


    /*<-------------------------------------------------timer延时 interval循环执行 操作符----------------------------------------------------------------------------------->*/

        Observable.timer(5, TimeUnit.SECONDS).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<Long>() {
                            @Override
                            public void onCompleted() {
                                Log.i("test_timer", "Observer----" + Thread.currentThread().getName());
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onNext(Long aLong) {
                                Log.i("test_timer", aLong + "--Observer----" + Thread.currentThread().getName());
                            }
                        }

                );

        Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //每隔1秒发送数据项，从0开始计数
                        Log.i("test_interval", aLong + "--Observer----" + Thread.currentThread().getName());

                    }
                });


        Observable.range(2, 5).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.d("test_range", integer.toString());// 2,3,4,5,6 从2开始发射5个整数数据
            }
        });


        final int count = 5;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .subscribeOn(Schedulers.io())
                .map(new Func1<Long, Long>() {
                    @Override
                    public Long call(Long aLong) {
                        Log.i("test2", "map------" + Thread.currentThread().getName());
                        return count - aLong;
                    }
                })

                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        Log.i("test2", "Observer--onCompleted---" + Thread.currentThread().getName());
                        tv_send.setText("获取验证码");//数据发送完后设置为原来的文字
                        tv_send.setTextColor(Color.BLACK);
                        tv_send.setBackgroundColor(Color.parseColor("#f97e7e"));//数据发送完后设置为原来背景色
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Long aLong) { //接受到一条就是会操作一次UI
                        tv_send.setText("剩余时间" + aLong + "秒");
                        tv_send.setEnabled(true);
                        tv_send.setTextColor(Color.WHITE);

                    }
                });









            /*<-------------------------------------------------组合concat顺序执行 操作符----------------------------------------------------------------------------------->*/


        Observable<String> ob = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ob");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> ob1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(1000);
                subscriber.onNext("ob1_1");
                SystemClock.sleep(1000);
                subscriber.onNext("ob1_2");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> ob2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(2000);
                subscriber.onNext("ob2_1");
                SystemClock.sleep(2000);
                subscriber.onNext("ob2_1");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.concat(ob, ob1, ob2).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("test_concat", "Observer--onCompleted---" + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.i("test_concat", "Observer--onError---" + e.toString() + "----" + Thread.currentThread().getName());

            }

            @Override
            public void onNext(String str) {
                Log.i("test_concat", "Observer--onNext---" + str + "----" + Thread.currentThread().getName());

            }
        });

    /*<-------------------------------------------------merge并行 操作符----------------------------------------------------------------------------------->*/
        Observable<String> obm = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ob");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.io());
        Observable<String> obm1 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                SystemClock.sleep(1000);
                subscriber.onNext("ob1_1");
                SystemClock.sleep(1000);
                subscriber.onNext("ob1_2");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> obm2 = Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("ob2_1");
                SystemClock.sleep(2000);
                subscriber.onNext("ob2_1");
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.merge(obm, obm1, obm2).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i("test_merge", "Observer--onCompleted---" + Thread.currentThread().getName());

            }

            @Override
            public void onError(Throwable e) {
                Log.i("test_merge", "Observer--onError---" + e.toString() + "----" + Thread.currentThread().getName());

            }

            @Override
            public void onNext(String str) {
                Log.i("test_merge", "Observer--onNext---" + str + "----" + Thread.currentThread().getName());
            }
        });



        Observable.just(3,4,5,6)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer>4;
                    }
                })
                .subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i("test_filter", "Observer--onNext---" + integer + "----" + Thread.currentThread().getName());
                    }
                });




    }


    @Override
    public void doSomething() {
        Log.e("test", "doSomething");
    }


}
