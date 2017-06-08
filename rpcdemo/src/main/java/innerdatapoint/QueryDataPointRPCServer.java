package innerdatapoint;

import com.grpc.querydatapoint.QueryDataPoint;
import com.grpc.querydatapoint.QueryDataPointServiceGrpc;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingbowu on 17/6/7.
 */
public class QueryDataPointRPCServer {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final int port;
    private final Server server;
    private String serverName;

    public QueryDataPointRPCServer(String serverName){
        this.serverName = serverName;
        this.port = 8202;
        this.server = ServerBuilder.forPort(port)
                .addService(new QueryDataPointServiceImpl(this.serverName))
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
                QueryDataPointRPCServer.this.stop();
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

    private static class QueryDataPointServiceImpl extends QueryDataPointServiceGrpc.QueryDataPointServiceImplBase{
        private final Logger log = LoggerFactory.getLogger(getClass());
        String serverName;

        public QueryDataPointServiceImpl(String serverName){
            this.serverName = serverName;
        }

        @Override
        public void queryDP(QueryDataPoint.QueryDPRequest queryDPRequest,
                          StreamObserver<QueryDataPoint.QueryDPReply> queryDPReplyStreamObserver){
           log.warn("Server Name: {} {} {}, {}, {}", serverName, queryDPRequest.getMetric(),
                   queryDPRequest.getTagsMap(),
                   queryDPRequest.getStartTime(),
                   queryDPRequest.getEndTime());

           Map<String, String> map = new HashMap<>();
           map.put("k1","v1");
           QueryDataPoint.QueryDPs queryDPs = QueryDataPoint.QueryDPs.newBuilder()
                    .setMetric("test")
                    .putAllTags(map)
                    .setTimestamp(100)
                    .setValue(1).build();
           List<QueryDataPoint.QueryDPs>  queryDPsList= new ArrayList<>();
           queryDPsList.add(queryDPs);
            queryDPReplyStreamObserver.onNext(QueryDataPoint.QueryDPReply.newBuilder()
           .addAllQuerydps(queryDPsList).build());
            queryDPReplyStreamObserver.onCompleted();
        }
    }

    public static void main(String[] args) {
        String serverName = "WriteDataPoint Server";
        QueryDataPointRPCServer server = new QueryDataPointRPCServer(serverName);
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true);
    }
}