package com.example;

import com.grpc.hello.HelloModel;
import com.grpc.hello.HelloServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * Created by xingbowu on 17/6/7.
 */
public class HelloServer {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int port;
    private final Server server;

    public HelloServer(ServerBuilder<?> serverBuilder, int port) {
        this.port = port;
        this.server = serverBuilder
                .addService(new HelloServiceImpl())
                .build();
    }

    public HelloServer(int port) {
        this(ServerBuilder.forPort(port), port);
    }


    /** Start serving requests. */
    public void start() throws IOException {
        server.start();
        log.info("Server started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                System.err.println("Shutting down gRPC server since JVM is shutting down");
                HelloServer.this.stop();
                System.err.println("Server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Entry point.
     */
    public static void main(String[] args) throws Exception {
        HelloServer server = new HelloServer(8081);
        server.start();
        server.blockUntilShutdown();
    }

    //
    // HelloService
    //

    private static final class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {
        @Override
        public void getGreeting(HelloModel.GreetingRequest request,
                                StreamObserver<HelloModel.GreetingReply> responseObserver) {
            final String name = request.getName();
            final String greeting = "Hello, " + name + " from GRPC server! Time=" + new Date();

            responseObserver.onNext(HelloModel.GreetingReply.newBuilder().setGreeting(greeting).build());
            responseObserver.onCompleted();
        }
    }

}
