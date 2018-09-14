package com.example.test.mytestdemo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.test.mytestdemo.R;
import com.example.test.mytestdemo.event.BaseEvent;
import com.example.test.mytestdemo.event.TestEvent;

import de.greenrobot.event.EventBus;
import de.greenrobot.event.Subscribe;
import de.greenrobot.event.ThreadMode;

public class EventBusTestActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_test);


        EventBus.getDefault().register(this);

         //发起事件
        EventBus.getDefault().post(new TestEvent(1));
    }


    /**
     * ThreadMode.PostThread 这是 默认的线程模式。即post发起所在线程，事件的传递是同步的，一旦发布事件，所有该模式的订阅者方法都将被调用。这种线程模式意味着最少的性能开销，因为它避免了线程的切换。因此，对于不要求是主线程并且耗时很短的简单任务推荐使用该模式。使用该模式的订阅者方法应该快速返回，以避免阻塞发布事件的线程，这可能是主线程。
     * ThreadMode.MainThread 订阅者方法将在主线程（UI线程）中被调用。因此，可以在该模式的订阅者方法中直接更新UI界面。如果发布事件的线程是主线程，那么该模式的订阅者方法将被直接调用。使用该模式的订阅者方法必须快速返回，以避免阻塞主线程。
     * ThreadMode.BackgroundThread 订阅者方法将在后台线程中被调用。如果发布事件的线程不是主线程，那么订阅者方法将直接在该线程中被调用。如果发布事件的线程是主线程，那么将使用一个单独的后台线程，该线程将按顺序发送所有的事件。使用该模式的订阅者方法应该快速返回，以避免阻塞后台线程。
     * ThreadMode.ASYNC 订阅者方法将在一个单独的线程中被调用。因此，发布事件的调用将立即返回。如果订阅者方法的执行需要一些时间，例如网络访问，那么就应该使用该模式。避免触发大量的长时间运行的订阅者方法，以限制并发线程的数量。EventBus使用了一个线程池来有效地重用已经完成调用订阅者方法的线程。
     *
     * @param baseEvent
     */
    @Subscribe(threadMode = ThreadMode.BackgroundThread)
    public void Event(BaseEvent baseEvent) {
        if (baseEvent != null) {
            if (baseEvent instanceof TestEvent) {

            }

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
