package innerdatapoint;

import com.grpc.writedatapoint.WriteDataPoint;
import com.grpc.writedatapoint.WriteDataPointServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by xingbowu on 17/6/7.
 */
public class WriteDataPointRPCServer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int port;
    private final Server server;
    private String serverName;

    public WriteDataPointRPCServer(String serverName){
        this.serverName = serverName;
        this.port = 8201;
        this.server = ServerBuilder.forPort(port)
                .addService(new WriteDataPointServiceImpl(this.serverName))
                .build();
    }

    /** Start serving requests. */
    public void start() throws IOException {
        server.start();
        log.warn("Server started, listening on {}", port);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                log.error("Shutting down gRPC server since JVM is shutting down");
                WriteDataPointRPCServer.this.stop();
                log.error("Server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    private static class WriteDataPointServiceImpl extends WriteDataPointServiceGrpc.WriteDataPointServiceImplBase{
        private final Logger log = LoggerFactory.getLogger(getClass());
        String serverName;
        public WriteDataPointServiceImpl(String serverName){
            this.serverName = serverName;
        }

        @Override
        public void putDP(WriteDataPoint.PutDPRequest putDPRequest,
                          StreamObserver<WriteDataPoint.PutDPReply> putDPReplyStreamObserver){
           log.warn("Server Name: {} {} {}, {}, {}", serverName, putDPRequest.getMetric(),
                   putDPRequest.getTagsMap(),
                   putDPRequest.getTimestamp(),
                   putDPRequest.getValue());
           putDPReplyStreamObserver.onNext(WriteDataPoint.PutDPReply.newBuilder()
           .setCode(true).build());
            putDPReplyStreamObserver.onCompleted();
        }
    }

    public static void main(String[] args) {
        String serverName = "WriteDataPoint Server";
        WriteDataPointRPCServer server = new WriteDataPointRPCServer(serverName);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true);
    }
}