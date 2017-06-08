package innerdatapoint;

import com.grpc.writedatapoint.WriteDataPoint;
import com.grpc.writedatapoint.WriteDataPointServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xingbowu on 17/6/7.
 */
public class WriteDataPointRPCClient {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ManagedChannel channel;
    private final WriteDataPointServiceGrpc.WriteDataPointServiceBlockingStub blockingStub;

    public WriteDataPointRPCClient(String host, int port){

        channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext(true) // - for debug only
                .build();
        blockingStub = WriteDataPointServiceGrpc.newBlockingStub(channel);
    }

    public void request(String metric, Map<String, String> tags, long timestamp, long value){
        WriteDataPoint.PutDPRequest request = WriteDataPoint.PutDPRequest.newBuilder()
                .setMetric(metric)
                .putAllTags(tags)
                .setTimestamp(timestamp)
                .setValue(value)
                .build();
        WriteDataPoint.PutDPReply response;
        response = blockingStub.putDP(request);
        log.warn("Get the Response: {}", response.getCode());
    }

    public void shutdown() throws InterruptedException {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS);
    }

    public static void main(String[] args) {
        WriteDataPointRPCClient client = new WriteDataPointRPCClient("localhost", 8201);
        Map<String, String> map = new HashMap<>();
        map.put("k1","v1");
        client.request("test", map, 1000, 1 );
        try {
            client.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
