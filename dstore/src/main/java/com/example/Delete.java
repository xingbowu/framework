package com.example;

import io.atomix.copycat.Command;

/**
 * Created by xingbowu on 17/6/15.
 */
public class Delete implements Command<Object> {
    public Object key;

    public Delete(Object key){
        this.key =key;
    }
}
