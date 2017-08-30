package com.spi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by xingbowu on 17/8/30.
 */
public class AsynCB1 {
    public static void main(String[] args) {

        CompletableFuture<Double> futurePrice = CompletableFuture.supplyAsync(() -> {
            if (false) {
                throw new RuntimeException("Something wrong");
            }
            return 23.5;
        }, runnable -> new Thread(runnable).start());

        try {
            System.out.println(futurePrice.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
