package com.example.md5hashshard;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by xingbowu on 17/4/11.
 */
public class NodeShardInfo {
    private volatile TreeMap<Long, String> nodeShard;
    private volatile List<String> nodeInfo;
    private HashAlgorithmImp hashAlgorithm;
    private short numReplica = Const.NUMBEROFREPLICAS;

    public NodeShardInfo(List<String> nodeInfo, HashAlgorithmImp hashAlgorithm){
        this.hashAlgorithm = hashAlgorithm;
        setNodeShardInfo(nodeInfo);
    }
    public NodeShardInfo(List<String> nodeInfo){
        setNodeShardInfo(nodeInfo);
    }

    public void setNodeShardInfo(List<String> nodeInfo){
        nodeShard = new TreeMap<Long, String>();
        for (String node:nodeInfo) {
            for (int i = 0 ; i<numReplica/4; i++){
                byte[] digest = HashAlgorithmImp.computMD5(node);
                for (int h = 0; h < 4; h++)
                {
                    long m = HashAlgorithmImp.hash(digest,h);
                    nodeShard.put(m,node);
                }
            }
        }
        return ;
    }

    public String getNode(String key){
        byte[] digest = HashAlgorithmImp.computMD5(key);
        long hashKey = HashAlgorithmImp.hash(digest, 0);
        if (!nodeShard.containsKey(hashKey)){
            SortedMap<Long, String> tailMap = nodeShard.tailMap(hashKey);
            if(tailMap.isEmpty()){
                hashKey=nodeShard.firstKey();
            }else {
                hashKey = tailMap.firstKey();
            }
        }
        return nodeShard.get(hashKey);
    }
}
