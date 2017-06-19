package com.example;

import io.atomix.copycat.Command;

/**
 * Created by xingbowu on 17/6/15.
 */
public class Put implements Command<Object> {
    public Object key;
    public Object value;

    public Put(Object key, Object value){
        this.key = key;
        this.value = value;
    }
}
