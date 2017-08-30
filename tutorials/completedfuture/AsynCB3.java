package com.spi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xingbowu on 17/8/30.
 */
public class AsynCB3 {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        CompletionStage<Double> futurePrice =
                CompletableFuture.supplyAsync(()-> {
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return 22.35;
        },executor);
        System.out.println(111);
        futurePrice.thenAccept(System.out::println);
        System.out.println(222);
        executor.shutdown();
    }
}
