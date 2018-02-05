package com.example.test.mytestdemo.model;

/**
 * Created by lhy on 2018/1/15.
 */

public class AlertDialog {

    private String key;
    private String title;
    private String messsage;

    private AlertDialog(Builder builder) {
        title = builder.titleBuilder;
        messsage = builder.messsageBuilder;
        key = builder.key;
    }


    public void loginfo() {
        System.out.println("显示窗口 : " + this);
    }


    public static class Builder {

        private String titleBuilder = "";
        private String messsageBuilder = "";
        private String key = "";

        public Builder(String key) {
            this.key = key;
        }

        public Builder setTitle(String title) {
            titleBuilder = title;
            return this;
        }

        public Builder setMessage(String messsage) {
            titleBuilder = messsage;
            return this;
        }


        public AlertDialog Builder() {
            return new AlertDialog(this);
        }


    }


}
