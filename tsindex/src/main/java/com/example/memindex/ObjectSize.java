package com.example.memindex;

import java.lang.instrument.Instrumentation;

/**
 * Created by xingbowu on 17/5/3.
 */
public class ObjectSize {
    private static volatile Instrumentation instru;

    public static void premain(String args, Instrumentation inst) {
        instru = inst;
    }

    public static Long getSizeOf(Object object) {
        if (instru == null) {
            throw new IllegalStateException("Instrumentation is null");
        }
        return instru.getObjectSize(object);
    }
}
