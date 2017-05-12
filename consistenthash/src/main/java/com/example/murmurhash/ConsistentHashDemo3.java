package com.example.murmurhash;

import com.example.md5hashshard.Const;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xingbowu on 17/4/10.
 */
public class ConsistentHashDemo3 {
    public static void main(String[] args) {
        HashMap<String, AtomicInteger> keyCountPerNode = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        List<TsdShardInfo> nodeServerList = new ArrayList<>();
        List<TsdShardInfo> nodeServerListAdded = new ArrayList<>();
        List<TsdShardInfo> nodeServerListDeleted = new ArrayList<>();
        AtomicInteger addedCount = new AtomicInteger();
        AtomicInteger deletedCount = new AtomicInteger();

        LoadNodeServerList(nodeServerList, Const.NODESERVERFILEPATH);
        Sharded<TsdInstance, TsdShardInfo> nodeShardInfo = new Sharded(nodeServerList);
        long nodeCount = nodeServerList.size();

        nodeServerListAdded.addAll(nodeServerList);
        TsdShardInfo tsdShardInfoAdded = new TsdShardInfo(Const.NEWIP);
        nodeServerListAdded.add(tsdShardInfoAdded);
        Sharded<TsdInstance, TsdShardInfo> nodeShardInfoAdded = new Sharded(nodeServerListAdded);

        nodeServerListDeleted.addAll(nodeServerList);
        TsdShardInfo tsdShardInfoDeleted = new TsdShardInfo(Const.DELIP);
        nodeServerListDeleted.remove(tsdShardInfoDeleted);
        Sharded<TsdInstance, TsdShardInfo> nodeShardInfoDeleted = new Sharded(nodeServerListDeleted);

        AtomicInteger keyCount = new AtomicInteger(0);
        float percentTotal = ((float)1/nodeCount);

        LoadKeyList(keyList, Const.KEYSFILEPATH);
        for (String key:keyList) {
            keyCount.getAndIncrement();
            TsdShardInfo  nodeName  = nodeShardInfo.getShardInfo(key);
            TsdShardInfo nodeNameAdded = nodeShardInfoAdded.getShardInfo(key);
            TsdShardInfo nodeNameDeleted = nodeShardInfoDeleted.getShardInfo(key);
            if(!(nodeName.getHost().equals(nodeNameAdded.getHost()))){
                addedCount.getAndIncrement();
            }
            if(!(nodeName.getHost().equals(nodeNameDeleted.getHost()))){
                deletedCount.getAndIncrement();
            }
            if (keyCountPerNode.containsKey(nodeName.getHost())){
                keyCountPerNode.get(nodeName.getHost()).getAndIncrement();
            }else{
                AtomicInteger counter = new AtomicInteger(1);
                keyCountPerNode.put(nodeName.getHost(),counter);
            }
        }
        System.out.println("Nodes counts: " + nodeCount +
                "   Key counts: " + keyCount +
                "   avg percentage " + percentTotal*100 +"%");
        System.out.println("----------------------------------");
        for (Map.Entry<String, AtomicInteger> entry : keyCountPerNode.entrySet()){
            float value = entry.getValue().floatValue();
            System.out.print("nodeName: " + entry.getKey());
            System.out.print("  keyCount: " + value);
            System.out.println("  Percent: " + (value/keyCount.intValue())*100 +"%");
        }

        System.out.println("----------------------------------");
        System.out.println("Normal Node Count: " + nodeServerList.size());
        System.out.println("Added Node Count: " + nodeServerListAdded.size());
        System.out.println("Deleted Node Count: " + nodeServerListDeleted.size());
        System.out.println("----------------------------------");
        System.out.println("Added Node Same Node Percentage: "
                + (keyCount.floatValue()-addedCount.floatValue())*100/keyCount.floatValue() +"%");
        System.out.println("Deleted Node Same Node Percentage: "
                + (keyCount.floatValue()-deletedCount.floatValue())*100/keyCount.floatValue() +"%");
    }

    public static void LoadNodeServerList(List<TsdShardInfo> nodeServerList, String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            while((key = bufferedReader.readLine())!=null){
                TsdShardInfo shardInfo = new TsdShardInfo(key);
                nodeServerList.add(shardInfo);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static void LoadKeyList(List<String> nodeServerList, String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            while((key = bufferedReader.readLine())!=null){
                nodeServerList.add(key);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

}
