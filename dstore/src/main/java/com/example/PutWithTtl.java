package com.example;

import io.atomix.copycat.Command;

/**
 * Created by xingbowu on 17/6/15.
 */
public class PutWithTtl implements Command<Object> {
    public Object key;
    public Object value;
    public long ttl;

    public PutWithTtl(Object key, Object value, long ttl){
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    @Override
    public CompactionMode compaction(){
        return  CompactionMode.EXPIRING;
    }

}
