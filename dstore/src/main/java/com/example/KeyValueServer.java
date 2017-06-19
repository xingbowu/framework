package com.example;

import io.atomix.catalyst.transport.Address;
import io.atomix.catalyst.transport.netty.NettyTransport;
import io.atomix.copycat.server.CopycatServer;
import io.atomix.copycat.server.storage.Storage;
import io.atomix.copycat.server.storage.StorageLevel;

import java.io.File;

/**
 * Created by xingbowu on 17/6/15.
 */
public class KeyValueServer {
    public static void main(String[] args) {
        Address address = new Address("localhost", 8001);
        CopycatServer.Builder builder = CopycatServer.builder(address);
        builder.withStateMachine((KeyValueStore::new));
        builder.withTransport(NettyTransport.builder()
        .withThreads(4).build());
        builder.withStorage(Storage.builder()
        .withDirectory(new File("logs"))
        .withStorageLevel(StorageLevel.DISK)
        .build());
        CopycatServer server = builder.build();
        server.bootstrap().thenAccept(srvr -> System.out.println(srvr + " has bootstrapped a cluster"));

        Address clusterAddress = new Address("localhost", 8002);
        server.join(clusterAddress).thenAccept(srvr -> System.out.println(srvr + " has joined the cluster"));
    }
}