package com.xcm91.relation.rxjava;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class RxjavaActivity extends Activity implements PullListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxjava);

        final List<String> list = Arrays.asList(new String[]{"one", "two", "three"});




        Observable observable = Observable.create(new Observable.OnSubscribe<List<String>>() {
            @Override
            public void call(Subscriber<? super List<String>> subscriber) {
                subscriber.onNext(list);
                subscriber.onCompleted();
            }
        });


        PullInfo mPullInfo = new PullInfo();
        mPullInfo.setOnClickListener(this);



    }




    @Override
    public void doSomething() {
        Log.e("test", "doSomething");
    }




}
