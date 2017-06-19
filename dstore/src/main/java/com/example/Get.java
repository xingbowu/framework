package com.example;

import io.atomix.copycat.Query;

/**
 * Created by xingbowu on 17/6/15.
 */
public class Get implements Query<Object> {
    public Object key;

    public Get(Object key){
        this.key = key;
    }
}
