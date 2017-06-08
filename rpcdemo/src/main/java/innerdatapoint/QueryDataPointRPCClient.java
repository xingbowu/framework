package innerdatapoint;

import com.grpc.querydatapoint.QueryDataPoint;
import com.grpc.querydatapoint.QueryDataPointServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xingbowu on 17/6/7.
 */
public class QueryDataPointRPCClient {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ManagedChannel channel;
    private final QueryDataPointServiceGrpc.QueryDataPointServiceBlockingStub blockingStub;

    public QueryDataPointRPCClient(String host, int port){

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true) // - for debug only
                .build();
        blockingStub = QueryDataPointServiceGrpc.newBlockingStub(channel);
    }

    public void request(String metric, Map<String, String> tags, long startTime, long endTime){
        QueryDataPoint.QueryDPRequest request = QueryDataPoint.QueryDPRequest.newBuilder()
                .setMetric(metric)
                .putAllTags(tags)
                .setStartTime(startTime)
                .setEndTime(endTime)
                .build();
        QueryDataPoint.QueryDPReply response;
        response = blockingStub.queryDP(request);
        List<QueryDataPoint.QueryDPs> queryDPsList = response.getQuerydpsList();
        for(QueryDataPoint.QueryDPs queryDPs: queryDPsList) {
            log.warn("Get the Response: {} {} {} {}",
                    queryDPs.getMetric(),
                    queryDPs.getTagsMap(),
                    queryDPs.getTimestamp(),
                    queryDPs.getValue());
        }
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        QueryDataPointRPCClient client = new QueryDataPointRPCClient("localhost", 8202);
        Map<String, String> map = new HashMap<>();
        map.put("k1","v1");
        client.request("test", map, 1, 2 );
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
