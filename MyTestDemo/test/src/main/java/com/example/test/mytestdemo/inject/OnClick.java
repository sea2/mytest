package com.example.test.mytestdemo.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lhy on 2017/5/26.
 */

public interface OnClick {


    @Target(ElementType.ANNOTATION_TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface EventBase {
        Class listenerType();
        String listenerSetter();
        String methodName();
    }
}
