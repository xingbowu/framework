package com.example;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.client.CopycatClient;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Created by xingbowu on 17/6/15.
 */
public class KeyValueClient {
    public static void main(String[] args) {
        try {
            deleteLogs();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        //create client
        CopycatClient client = CopycatClient.builder()
                .withTransport(NettyTransport.builder()
                        .withThreads(2)
                        .build()).build();
        Address clusterAddress = new Address("localhost", 8001);
        client.connect(clusterAddress).join();

        //register listen
        client.submit(new Listen()).thenRun(() -> System.out.println("Now listening for events")).join();
        client.onEvent("put", (EntryEvent event) -> System.out.println("Put: " + event));
        client.onEvent("delete", (EntryEvent event) -> System.out.println("Delete: " + event));
        client.onEvent("expire", (EntryEvent event) -> System.out.println("Expire: " + event));
        client.onEvent("get", (EntryEvent event) -> System.out.println("Get: " + event));

        //submit operation
        CompletableFuture<Object> future = client.submit(new Put("foo", "bar"));
        try {
            Object result = future.get();
            client.submit(new Get("foo")).thenAccept(getResult->System.out.println("foo is: " + getResult));
            client.submit(new Delete("foo")).thenAccept(getResult->System.out.println("foo is: " + getResult));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return;
    }

    private static void deleteLogs() throws Throwable {
        Files.walkFileTree(new File("logs").toPath(), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException e) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
