package com.example.ketamaNode;

/**
 * Created by xingbowu on 17/4/12.
 */
/**
 * Intents to provide hash for locating a server for a key.
 */
public interface HashAlgorithm {

    /**
     * Compute the hash for the given key.
     *
     * @return a positive integer hash
     */
    long hash(final String k);
}