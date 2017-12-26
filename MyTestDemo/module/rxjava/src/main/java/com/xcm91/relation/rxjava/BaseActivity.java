package com.xcm91.relation.rxjava;

import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by lhy on 2017/12/11.
 */

public abstract class BaseActivity extends RxAppCompatActivity {
    protected CompositeSubscription subscription = new CompositeSubscription();

    protected void addSub(Subscription sub) {
        if (sub != null && !sub.isUnsubscribed()) {
            subscription.add(sub);
        }
    }

    @Override
    protected void onDestroy() {
        if (subscription.hasSubscriptions()) {
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}

