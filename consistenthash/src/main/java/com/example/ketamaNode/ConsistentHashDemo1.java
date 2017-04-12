package com.example.ketamaNode;

import com.example.Const;

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
public class ConsistentHashDemo1 {
    public static void main(String[] args) {
        HashMap<String, AtomicInteger> keyCountPerNode = new HashMap<>();
        List<String> keyList = new ArrayList<>();
        List<String> nodeServerList = new ArrayList<>();
        List<String> nodeServerListAdded = new ArrayList<>();
        List<String> nodeServerListDeleted = new ArrayList<>();
        AtomicInteger addedCount = new AtomicInteger();
        AtomicInteger deletedCount = new AtomicInteger();

        LoadNodeServerList(nodeServerList, Const.NODESERVERFILEPATH);
        KetamaNodeLocator nodeShardInfo = new KetamaNodeLocator(nodeServerList, DefaultHashAlgorithm.KETAMA_HASH);
        long nodeCount = nodeServerList.size();

        nodeServerListAdded.addAll(nodeServerList);
        nodeServerListAdded.add(Const.NEWIP);
        KetamaNodeLocator nodeShardInfoAdded = new KetamaNodeLocator(nodeServerListAdded, DefaultHashAlgorithm.KETAMA_HASH);

        nodeServerListDeleted.addAll(nodeServerList);
        nodeServerListDeleted.remove(Const.DELIP);
        KetamaNodeLocator nodeShardInfoDeleted = new KetamaNodeLocator(nodeServerListAdded, DefaultHashAlgorithm.KETAMA_HASH);

        AtomicInteger keyCount = new AtomicInteger(0);
        float percentTotal = ((float)1/nodeCount);

        LoadNodeServerList(keyList, Const.KEYSFILEPATH);
        for (String key:keyList) {
            keyCount.getAndIncrement();
            String  nodeName  = nodeShardInfo.getPrimary(key);
            String nodeNameAdded = nodeShardInfoAdded.getPrimary(key);
            String nodeNameDeleted = nodeShardInfoAdded.getPrimary(key);
            if(!nodeName.equals(nodeNameAdded)){
                addedCount.getAndIncrement();
            }
            if(!nodeName.equals(nodeNameDeleted)){
                deletedCount.getAndIncrement();
            }
            if (keyCountPerNode.containsKey(nodeName)){
                keyCountPerNode.get(nodeName).getAndIncrement();
            }else{
                AtomicInteger counter = new AtomicInteger(1);
                keyCountPerNode.put(nodeName,counter);
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
        System.out.println("Added Node Same Node Percentage: "
                + (keyCount.floatValue()-deletedCount.floatValue())*100/keyCount.floatValue() +"%");
    }

    public static void LoadNodeServerList(List<String> nodeServerList, String filePath) {
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
