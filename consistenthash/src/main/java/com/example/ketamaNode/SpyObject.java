package com.example.ketamaNode;

/**
 * Created by xingbowu on 17/4/12.
 */

import java.util.logging.Logger;

/**
 * Superclass for all Spy Objects.
 */
public class SpyObject extends Object {

    private transient Logger logger = null;

    /**
     * Get an instance of SpyObject.
     */
    public SpyObject() {
        super();
    }

    /**
     * Get a Logger instance for this class.
     *
     * @return an appropriate logger instance.
     */
    protected Logger getLogger() {
        if (logger == null) {

        }
        return (logger);
    }
}
