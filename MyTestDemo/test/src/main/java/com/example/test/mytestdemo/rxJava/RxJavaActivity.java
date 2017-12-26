package com.example.test.mytestdemo.rxJava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.test.mytestdemo.R;
import com.trello.rxlifecycle.components.support.RxFragmentActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;

public class RxJavaActivity extends RxFragmentActivity {
    Subscription subscription;
    private android.widget.Button btnone;
    private android.widget.Button btnone2;
    private android.widget.Button btnone3;
    private android.widget.Button btnone4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);

        this.btnone4 = (Button) findViewById(R.id.btn_one4);
        this.btnone3 = (Button) findViewById(R.id.btn_one3);
        this.btnone2 = (Button) findViewById(R.id.btn_one2);
        this.btnone = (Button) findViewById(R.id.btn_one);
        Log.e("------ 开始 ", "开始");
        btnone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        btnone2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });


        /**
         *   interval
         */
        // 第一个是延时开始，第二个参数是间隔
        subscription = Observable.interval(2, 5, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
                Log.e("------ interval ", "onCompleted");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                if (subscription != null && aLong >= 100) subscription.unsubscribe();
                Log.e("------ interval ", "aLong : " + aLong);
            }
        });

        /**
         *   create
         */
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {

                subscriber.onNext("create1"); //发射一个"create1"的String
                subscriber.onNext("create2"); //发射一个"create2"的String
                subscriber.onCompleted();//发射完成,这种方法需要手动调用onCompleted，才会回调Observer的onCompleted方法
            }
        }).subscribe(mSubscriber);


        /**
         *   just
         */
        Observable.just("just1", "just2").subscribe(mSubscriber);


        /**
         *   from
         */
        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable.from(list).subscribe(mSubscriber);


    }

    Subscriber mSubscriber = new Subscriber<String>() {
        @Override
        public void onCompleted() {
            Log.e("------rxjava ", "onCompleted");
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onNext(String aLong) {
            Log.e("------rxjava ", "aLong : " + aLong);
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
