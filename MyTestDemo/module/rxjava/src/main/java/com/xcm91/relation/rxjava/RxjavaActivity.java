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

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.ResourceObserver;
import io.reactivex.schedulers.Schedulers;


public class RxjavaActivity extends AppCompatActivity implements PullListener {


    private TextView tv_send;
    private String TAG = this.getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);
        this.tv_send = (TextView) findViewById(R.id.tv_send);


        /**
         * 线程要是没有subscribeOn，observeOn切换都是默认按照上一级执行的线程环境
         * */


        Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("test1", "Observable------" + Thread.currentThread().getName());
                e.onNext("http://baidu.com");
            }

        }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {//执行在被观察者（订阅者）之前，比如访问网络的dialog加载框，受紧跟的subscribeOn控制线程执行环境
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i("test1", "doOnSubscribe------" + Thread.currentThread().getName());
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Long>() {

                    @Override
                    public Long apply(String s) throws Exception {
                        Log.i("test1", "map------" + Thread.currentThread().getName());
                        return Long.valueOf(s.length());
                    }
                })
                .subscribeOn(Schedulers.io())//控制被观察者（订阅者）执行的线程
                .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Disposable disposable2 =  Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("test1", "Observable------" + Thread.currentThread().getName());
                e.onNext("http://baidu.com");
            }

        }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {//执行在被观察者（订阅者）之前，比如访问网络的dialog加载框，受紧跟的subscribeOn控制线程执行环境
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i("test1", "doOnSubscribe------" + Thread.currentThread().getName());
                        mCompositeDisposable.add(disposable);
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Long>() {

                    @Override
                    public Long apply(String s) throws Exception {
                        Log.i("test1", "map------" + Thread.currentThread().getName());
                        return Long.valueOf(s.length());
                    }
                })
                .subscribeOn(Schedulers.io())//控制被观察者（订阅者）执行的线程
                .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                .subscribeWith(new ResourceObserver<Long>() {
                                   @Override
                                   public void onNext(Long aLong) {

                                   }

                                   @Override
                                   public void onError(Throwable e) {

                                   }

                                   @Override
                                   public void onComplete() {

                                   }
                               }

                      );





        Disposable disposable22 = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("test1", "Observable------" + Thread.currentThread().getName());
                e.onNext("http://baidu.com");
            }

        }).observeOn(Schedulers.newThread())//控制下面流程的（doOnSubscribe除外）执行的线程
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {//执行在被观察者（订阅者）之前，比如访问网络的dialog加载框，受紧跟的subscribeOn控制线程执行环境
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        Log.i("test1", "doOnSubscribe------" + Thread.currentThread().getName());
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .map(new Function<String, Long>() {

                    @Override
                    public Long apply(String s) throws Exception {
                        Log.i("test1", "map------" + Thread.currentThread().getName());
                        return Long.valueOf(s.length());
                    }
                })
                .subscribeOn(Schedulers.io())//控制被观察者（订阅者）执行的线程
                .observeOn(AndroidSchedulers.mainThread())//控制观察者执行的线程
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable2);





        /*<-------------------------------------------------from操作符    just----------------------------------------------------------------------------------->*/


        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("12");
        list.add("123");
        list.add("1234");

        String[] arrat = new String[]{"1", "23", "345"};

        Disposable disposable3 = Observable.fromIterable(list)
                .map(new Function<String, Integer>() {
                    //这个就是转换的函数，返回的是转换结果
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {

                    }
                });
        mCompositeDisposable.add(disposable3);

        Observable.fromArray(arrat)
                .map(new Function<String, Integer>() {
                    //这个就是转换的函数，返回的是转换结果
                    @Override
                    public Integer apply(String s) throws Exception {
                        return s.length();
                    }
                })
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("test_from", "Observer--onCompleted----" + Thread.currentThread().getName());

                    }
                });


        Disposable disposableJust = Observable.just("1", "12", "123").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) {
            }
        });
        mCompositeDisposable.add(disposableJust);


        /*<-------------------------------------------------timer延时 interval循环执行 操作符-------take也属于过滤操作符，过滤前几个执行---------------------------------------------------------------------------->*/

        Observable.timer(5, TimeUnit.SECONDS).
                observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i("test_timer", "Observer----" + Thread.currentThread().getName());

                    }
                });

        Disposable disposableInterval = Observable.interval(1, TimeUnit.SECONDS)
                .take(5)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.i("test_interval", aLong + "--Observer----" + Thread.currentThread().getName());

                    }
                });
        mCompositeDisposable.add(disposableInterval);


        Disposable disposablerange = Observable.range(2, 5).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d("test_range", integer.toString());// 2,3,4,5,6 从2开始发射5个整数数据
            }
        });
        mCompositeDisposable.add(disposablerange);


        final int count = 5;
        Observable.interval(0, 1, TimeUnit.SECONDS)//设置0延迟，每隔一秒发送一条数据
                .take(count + 1) //设置循环11次
                .subscribeOn(Schedulers.io())
                .map(new Function<Long, Long>() {
                    @Override
                    public Long apply(Long aLong) throws Exception {
                        Log.i("test2", "map------" + Thread.currentThread().getName());
                        return count - aLong;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())//操作UI主要在UI线程
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        tv_send.setText("剩余时间".concat(aLong + "秒"));
                        tv_send.setEnabled(true);
                        tv_send.setTextColor(Color.WHITE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("test2", "Observer--onCompleted---" + Thread.currentThread().getName());
                        tv_send.setText("获取验证码");//数据发送完后设置为原来的文字
                        tv_send.setTextColor(Color.BLACK);
                        tv_send.setBackgroundColor(Color.parseColor("#f97e7e"));//数据发送完后设置为原来背景色
                    }
                });









        /*<-------------------------------------------------组合concat顺序执行 操作符----------------------------------------------------------------------------------->*/


        Observable<String> ob = Observable.create(
                new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
                        e.onNext("ob");
                        e.onComplete();
                    }
                }).subscribeOn(Schedulers.io());
        Observable<String> ob1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                SystemClock.sleep(1000);
                e.onNext("ob1_1");
                SystemClock.sleep(1000);
                e.onNext("ob1_2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> ob2 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                SystemClock.sleep(2000);
                e.onNext("ob2_1");
                SystemClock.sleep(2000);
                e.onNext("ob2_1");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.concat(ob, ob1, ob2).

                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("test_concat", "Observer--onCompleted---" + Thread.currentThread().getName());
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("test_concat", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test_concat", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        Log.i("test_concat", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }
                });

        /*<-------------------------------------------------merge并行 操作符----------------------------------------------------------------------------------->*/
        Observable<String> obm = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("ob");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.io());

        Observable<String> obm1 = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                SystemClock.sleep(1000);
                e.onNext("ob1_1");
                SystemClock.sleep(1000);
                e.onNext("ob1_2");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());
        Observable<String> obm2 = Observable.create(new ObservableOnSubscribe<String>() {

            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("ob2_1");
                SystemClock.sleep(2000);
                e.onNext("ob2_1");
                e.onComplete();
            }
        }).subscribeOn(Schedulers.newThread());

        Observable.merge(obm, obm1, obm2).

                subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("test_merge", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onNext(String s) {
                        Log.i("test_merge", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test_merge", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }

                    @Override
                    public void onComplete() {
                        Log.i("test_merge", "Observer--onCompleted---" + Thread.currentThread().getName());

                    }
                });


        Disposable disposableFilter = Observable.just(3, 4, 5, 6)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer > 4;
                    }
                }).subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        Log.i("test_filter", "Observer--onNext---" + integer + "----" + Thread.currentThread().getName());

                    }
                });



        /*-------------------------------------------map-------------------------------------------------*/

        Disposable disposableMap = Observable.just("a", "b", "c")
                //使用map进行转换，参数1：转换前的类型，参数2：转换后的类型
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        String name = s.length() + "";
                        Log.i("map", "map--1----" + name);
                        return name;
                    }
                }).subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("map", "map--2----" + s);
                    }
                });


        /*--------------------------------------------flatMap-------------------------------------------------*/

        Disposable disposableFlatMap = Observable.just("a", "b", "c")
                .flatMap(new Function<String, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(String s) throws Exception {
                        Log.i("flatMap", "map--1----" + s);
                        return Observable.just(s + "!!!", "d");
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                    }
                });



        Disposable disposableFlat6 = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> e) throws Exception {
                e.onNext(new Student("第一名", 100));
                e.onNext(new Student("第二名", 90));
                e.onNext(new Student("第三名", 80));
            }
        }).subscribeOn(Schedulers.io())
                .flatMap(new Function<Student, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Student student) throws Exception {
                        final String str = "老师正在教导" + student.getName() + ",她考了" + student.getScore() + "分";
                        Log.i("LHD1", str);//在这里检测事件的转换顺序
                        int delay = 100;
                        if (student.getScore() == 100) {
                            delay = 500;
                        }
                        return Observable.just(str).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("LHD2", s + "\n");//在这里检测事件的接受顺序
                    }
                });

        Disposable disposableFlat7 = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> e) throws Exception {
                e.onNext(new Student("第一名", 100));
                e.onNext(new Student("第二名", 90));
                e.onNext(new Student("第三名", 80));
            }
        })
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<Student, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Student student) throws Exception {
                        final String str = "老师正在教导" + student.getName() + ",她考了" + student.getScore() + "分";
                        Log.i("LHD1", str);//在这里检测事件的转换顺序
                        int delay = 100;
                        if (student.getScore() == 100) {
                            delay = 500;
                        }
                        return Observable.just(str).delay(delay, TimeUnit.MILLISECONDS);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i("LHD2", s + "\n");//在这里检测事件的接受顺序
                    }
                });


    }


    @Override
    public void doSomething() {
        Log.e("test", "doSomething");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            // mCompositeDisposable.clear();
        }
    }


}


class Student {
    int score;
    String name;

    public Student(String name, int score) {
        this.score = score;
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}