package com.spi.demo;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by xingbowu on 17/8/30.
 */
public class AsynCB5 {

    private static int PROCESSORS = Runtime.getRuntime().availableProcessors();
    Map<Long, Double> resultMap = new TreeMap<>();

    public static void main(String[] args) {
        AsynCB5 asynCB5 = new AsynCB5();
        System.out.println("Processors: " + PROCESSORS);
        Executor executor = new ForkJoinPool(20);
        //List<Supplier<Map<Long, Double>>> futureResultList = new ArrayList<>();
        for(int i =0; i<10 ; i++) {
            CompletableFuture<Map<Long, Double>> future =
                    CompletableFuture.supplyAsync(AsynCB5::getDP, executor);
            future.thenAccept(asynCB5::dump);
        }
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(asynCB5.resultMap);
    }

    private void dump(Map<Long, Double> valMap) {
        resultMap.putAll(valMap);
        System.out.println(valMap);
    }


    private static Map<Long, Double> getDP() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<Long, Double> dp = new HashMap<>();
        dp.put(System.currentTimeMillis(), Double.valueOf(1));
        return dp;
    }
}