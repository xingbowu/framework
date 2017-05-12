package com.example;

import com.codahale.metrics.MetricRegistry;
import org.apache.gossip.*;
import org.apache.gossip.event.GossipListener;
import org.apache.gossip.event.GossipState;

import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by xingbowu on 17/5/9.
 */
public class GossipDemo3 {
    public static void main(String[] args) {


        try {
            GossipSettings settings = new GossipSettings();
            settings.setGossipInterval(1);
            settings.setCleanupInterval(1000);
            List<GossipService> clients = new ArrayList();
            String myIpAddress = InetAddress.getLocalHost().getHostAddress();
            String cluster = "My Gossip Cluster";
            List<GossipMember> startupMembers = new ArrayList();

            URI seedUrl;
            try {
                seedUrl = new URI("udp://" + myIpAddress + ":" + 50001);
            } catch (URISyntaxException var9) {
                throw new RuntimeException(var9);
            }
            startupMembers.add(new RemoteGossipMember(cluster, seedUrl, "seed", 0L, new HashMap()));

            URI url;
            try {
                url = new URI("udp://" + myIpAddress + ":" + 50003);
            } catch (URISyntaxException var9) {
                throw new RuntimeException(var9);
            }
            GossipService gossipService = new GossipService(cluster,
                    url, "seed3", new HashMap(), startupMembers, settings,
                    new GossipListener() {
                        @Override
                        public void gossipEvent(GossipMember member, GossipState state) {
                            System.out.println(System.currentTimeMillis() + " "
                                    + member + " " + state);
                        }
                    },
                    //(GossipListener)null,
                    new MetricRegistry());
            clients.add(gossipService);
            gossipService.start();
            //sleep((long)(settings.getCleanupInterval() + 1000));

            while(true) {
                sleep(3000L);
                System.err.println("Going to shutdown all services...");
                List<LocalGossipMember> localGossipMemberList = clients.get(0).getGossipManager().getLiveMembers();
                for (LocalGossipMember localGossipMember: localGossipMemberList) {
                    System.out.print(" " + localGossipMember.getUri());
                }
                System.out.println();
            }

           // ((GossipService)clients.get(0)).shutdown();
        } catch (UnknownHostException var10) {
            var10.printStackTrace();
        } catch (InterruptedException var11) {
            var11.printStackTrace();
        }

    }
}
