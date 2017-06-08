package com.example;

import com.grpc.hello.HelloModel;
import com.grpc.hello.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by xingbowu on 17/6/7.
 */
public class HelloClient {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceBlockingStub blockingStub;

    /** Construct client connecting to HelloWorld server at {@code host:port}. */
    public HelloClient(String host, int port) {
        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true) // - for debug only
                .build();
        blockingStub = HelloServiceGrpc.newBlockingStub(channel);
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    /** Say hello to server. */
    public void greet(String name) {
        log.warn("Will try to greet {} ...", name);
        HelloModel.GreetingRequest request = HelloModel.GreetingRequest.newBuilder().setName(name).build();
        HelloModel.GreetingReply response;
        try {
            response = blockingStub.getGreeting(request);
        } catch (StatusRuntimeException e) {
            log.warn("RPC failed: {}", e.getStatus());
            return;
        }
        log.warn("Greeting: {}", response.getGreeting());
    }

    public static void main(String[] args) throws InterruptedException {
        HelloClient client = new HelloClient("localhost", 8081);
        try {
      /* Access a service running on the local machine on port 50051 */
            String user = "world";
            if (args.length > 0) {
                user = args[0]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user);

            String user2 = "GRPC client";
            if (args.length > 1) {
                user2 = args[1]; /* Use the arg as the name to greet if provided */
            }
            client.greet(user2);
        } finally {
            client.shutdown();
        }
    }

}
