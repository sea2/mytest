package com.example.test.mytestdemo.model;

import java.io.Serializable;

/**
 * Created by lhy on 2017/9/27.
 */

public class SerializableInfo implements Serializable {

    String name;
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
