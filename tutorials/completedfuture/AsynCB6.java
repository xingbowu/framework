package com.spi.demo;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xingbowu on 17/8/30.
 */
public class AsynCB6 {

    public static void main(String[] args) {
        Map<Long, Double> resultMap = new TreeMap<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        List<CompletableFuture<Map<Long, Double>>> tasks = new ArrayList<>();
        long beginTime = System.currentTimeMillis();
        for(int i=0; i<10; i++) {
            long start = i;
            long end = i+1;
            tasks.add(CompletableFuture.supplyAsync(()->getDP(start, end), executor));
        }
        tasks.stream().
                map(CompletableFuture::join).forEach(resultMap::putAll);
        long endTime = System.currentTimeMillis();
        System.out.println(resultMap);
        System.out.println("time cost " + (endTime-beginTime) +"ms");
        executor.shutdown();
        //Iterator<> iterator = tasks.stream().map(CompletableFuture::join);
    }


    private static Map<Long, Double> getDP(final long start, final long end) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Map<Long, Double> dp = new HashMap<>();
        for(int i=0;i<2;i++) {
            dp.put(start * 1000 + i, Double.valueOf(1));
        }
        return dp;
    }

}
