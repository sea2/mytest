package com.example.test.mytestdemo.event;

/**
 * Created by Administrator on 2017/1/5.
 */

public class TestEvent extends BaseEvent {
    int type;

    public TestEvent(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
