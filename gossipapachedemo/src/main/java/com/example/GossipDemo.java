package com.example;

import com.codahale.metrics.MetricRegistry;
import org.apache.gossip.GossipMember;
import org.apache.gossip.GossipService;
import org.apache.gossip.GossipSettings;
import org.apache.gossip.RemoteGossipMember;
import org.apache.gossip.event.GossipListener;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by xingbowu on 17/5/9.
 */
public class GossipDemo {
    public static void main(String[] args) {

        try {
            GossipSettings settings = new GossipSettings();
            List<GossipService> clients = new ArrayList();
            String myIpAddress = InetAddress.getLocalHost().getHostAddress();
            String cluster = "My Gossip Cluster";
            List<GossipMember> startupMembers = new ArrayList();

            for(int i = 0; i < 4; ++i) {
                URI u;
                try {
                    u = new URI("udp://" + myIpAddress + ":" + (2000 + i));
                } catch (URISyntaxException var9) {
                    throw new RuntimeException(var9);
                }
                startupMembers.add(new RemoteGossipMember(cluster, u, "", 0L, new HashMap()));
            }

            Iterator var12 = startupMembers.iterator();

            while(var12.hasNext()) {
                GossipMember member = (GossipMember)var12.next();
                GossipService gossipService = new GossipService(cluster, member.getUri(), "", new HashMap(), startupMembers, settings, (GossipListener)null, new MetricRegistry());
                clients.add(gossipService);
                gossipService.start();
                sleep((long)(settings.getCleanupInterval() + 1000));
            }
            System.out.println(clients.get(0).getGossipManager().getMembers());
            sleep(10000L);
            System.err.println("Going to shutdown all services...");
            ((GossipService)clients.get(0)).shutdown();
        } catch (UnknownHostException var10) {
            var10.printStackTrace();
        } catch (InterruptedException var11) {
            var11.printStackTrace();
        }

    }
}
