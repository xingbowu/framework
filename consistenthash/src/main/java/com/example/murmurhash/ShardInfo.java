package com.example.murmurhash;

/**
 * Created by xingbowu on 17/4/12.
 */
public abstract class ShardInfo<T> {
    private int weight;

    public ShardInfo() {
    }

    public ShardInfo(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    protected abstract T createResource();

    public abstract String getName();

    public abstract String getHost();

}