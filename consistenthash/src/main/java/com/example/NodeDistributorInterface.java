package com.example;

/**
 * Created by xingbowu on 17/4/10.
 */
public interface NodeDistributorInterface {
    String getNode(String key);
    void setNode(String host);
    String getKey();
}
