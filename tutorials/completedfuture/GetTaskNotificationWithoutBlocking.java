package com.spi.demo;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Created by xingbowu on 17/8/30.
 */
public class GetTaskNotificationWithoutBlocking {

    public static void main(String[] args) {
        ExampleService svc = new ExampleService();
        GetTaskNotificationWithoutBlocking listener = new GetTaskNotificationWithoutBlocking();
        CompletableFuture<String> future = CompletableFuture.supplyAsync(svc::work);
        future.thenAccept(listener::notify);
        System.out.println("Exiting main()");

    }

    void notify(String msg) {
        System.out.println("Received message: " + msg);
    }

}

class ExampleService {

    String work() {
        sleep(7000, TimeUnit.MILLISECONDS); /* Pretend to be busy... */
        char[] str = new char[5];
        ThreadLocalRandom current = ThreadLocalRandom.current();
        for (int idx = 0; idx < str.length; ++idx)
            str[idx] = (char) ('A' + current.nextInt(26));
        String msg = new String(str);
        System.out.println("Generated message: " + msg);
        return msg;
    }

    public static void sleep(long average, TimeUnit unit) {
        String name = Thread.currentThread().getName();
        long timeout = Math.min(exponential(average), Math.multiplyExact(10, average));
        System.out.printf("%s sleeping %d %s...%n", name, timeout, unit);
        try {
            unit.sleep(timeout);
            System.out.println(name + " awoke.");
        } catch (InterruptedException abort) {
            Thread.currentThread().interrupt();
            System.out.println(name + " interrupted.");
        }
    }

    public static long exponential(long avg) {
        return (long) (avg * -Math.log(1 - ThreadLocalRandom.current().nextDouble()));
    }
}