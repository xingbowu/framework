package com.spi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Created by xingbowu on 17/8/30.
 */
public class AsynCallback {
    public static void main(String[] args) {
        CompletableFuture<Double> futurePrice = getPriceAsync();

        //do anything you want, 当前线程不被阻塞
        System.out.println(111);

        //线程任务完成的话，执行回调函数，不阻塞后续操作
        futurePrice.whenComplete((aDouble, throwable) -> {
            System.out.println(aDouble);
            //do something else
        });

        try {
            futurePrice.get(2000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        System.out.println(222);
    }

    static CompletableFuture<Double> getPriceAsync() {
        CompletableFuture<Double> futurePrice = new CompletableFuture<>();
        new Thread(() -> {
            try {
                if(true) {
                    throw new InterruptedException("");
                }
                Thread.sleep(1000);
                futurePrice.complete(23.55);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        return futurePrice;
    }

}
