package com.example;

import java.io.Serializable;

/**
 * Created by xingbowu on 17/6/15.
 */
public class EntryEvent<K, V> implements Serializable{
    public Object key;
    public Object oldValue;
    public Object newValue;

    public EntryEvent(Object key, Object oldValue, Object newValue) {
        this.key = key;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }

    public String toString(){
        return String.format("EntryEvent [key=%s, oldValue=%s, newValue=%s",
                key, oldValue, newValue);
    }
}
