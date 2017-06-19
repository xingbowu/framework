package com.example;

import io.atomix.copycat.server.Commit;
import io.atomix.copycat.server.StateMachine;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by xingbowu on 17/6/15.
 */
public class KeyValueStore extends StateMachine {
    private Map<Object, Commit> storage = new HashMap<>();
    private Set<Commit> listeners = new HashSet<>();

    public Object put(Commit<Put> commit) {
        Commit<Put> put = storage.put(commit.operation().key, commit);
        Object oldValue = put== null? null : put.operation().value;
        publish("put", commit.operation().key,
                oldValue, commit.operation().value);
        return oldValue;
    }

    public Object get(Commit<Get> commit) {
        try {
            Commit<Put> put = storage.get(commit.operation().key);
            Object oldValue =  put == null ? null : put.operation().value;
            publish("get", commit.operation().key,
                    oldValue, null);
            return oldValue;        }finally {
            commit.release();
        }
    }

    public Object delete(Commit<Delete> commit) {
        Commit<Put> put = null;
        try{
            put = storage.remove(commit.operation().key);
            Object oldValue = put == null ? null : put.operation().value;
            publish("delete", commit.operation().key,
                    oldValue, null);
            return oldValue;
        } finally {
            if(put != null){
                put.release();
                commit.release();
            }
        }
    }

    public Object putWithTtl(Commit<PutWithTtl> commit){
        Object result = storage.put(commit.operation().key, commit);

        executor.schedule(Duration.ofMillis(commit.operation().ttl), ()->{
            Commit<PutWithTtl> put = storage.remove(commit.operation().key);
            Object oldValue = put == null ? null : put.operation().value;
            publish("expire", commit.operation().key,
                    oldValue, null);
            commit.release();
        });
        return result;
    }

    public void listen(Commit<Listen> listenCommit){
        listeners.add(listenCommit);
    }

    private void publish(String event, Object key, Object oldValue, Object newValue) {
        listeners.forEach(commit -> {
            commit.session().publish(event, new EntryEvent(key, oldValue, newValue));
        });
    }


}
