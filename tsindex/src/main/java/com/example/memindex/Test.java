package com.example.memindex;

import com.example.utils.Benchmark;
import com.google.common.base.Charsets;
import com.javamex.classmexer.MemoryUtil;
import net.opentsdb.uid.UniqueId;
import org.hbase.async.HBaseClient;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xingbowu on 17/5/2.
 */
public class Test {
    public final static String KEYSFILEPATH = "/Users/xingbowu/javademo/keys.data";
    public static HBaseClient client;
    public static UniqueId metrics;
    public static UniqueId tagk;
    public static UniqueId tagv;

    public static void main(String[] args) {
        List<String> keyList = new ArrayList<>();
        setTSDB();
        Benchmark.getInstance().start("test.termindex");

        TermIndex termIndex = new TermIndex();
        TimeseriesIndexByte timeseriesIndex = new TimeseriesIndexByte();
        LoadNodeServerList(keyList, timeseriesIndex);

        for (String key : keyList)  {

        }
       /* System.out.println("Total memory size: " + ObjectSize.getSizeOf(termIndex));
        System.out.println("Termdict size: " +termIndex.getTermIndexMap().size()
                + " memory "+ ObjectSize.getSizeOf(termIndex.getTermIndexMap()));
*/
        Benchmark.getInstance().end("test.termindex");

        long t = Benchmark.getInstance().getTime("test.termindex");
        System.out.printf("\ntotal time for indexing: %d milliseconds\n", t);
        long mem = Benchmark.getInstance().getMemory("test.termindex");
        System.out.printf("memory used: %f MB\n", (float) mem / 1024 / 1024);

        long byteTimeSeriesMemoryUsage = MemoryUtil.deepMemoryUsageOf(timeseriesIndex);
        long byteTimeSeriesIndexMemoryUsage = MemoryUtil.deepMemoryUsageOf(timeseriesIndex.getTsIndexer());
        long byteTimeSeriesInvertedIndexMemoryUsage = MemoryUtil.deepMemoryUsageOf(timeseriesIndex.getInvertedSeries());
        long metricsMemoryUsage = MemoryUtil.deepMemoryUsageOf(metrics);
        long tagkMemoryUsage = MemoryUtil.deepMemoryUsageOf(tagk);
        long tagvMemoryUsage = MemoryUtil.deepMemoryUsageOf(tagv);
        long byteArrayMemoryUsage = MemoryUtil.deepMemoryUsageOf(termIndex);
        long byteKeyListMemoryUsage = MemoryUtil.deepMemoryUsageOf(keyList);
        System.out.println("tags count  = " +  timeseriesIndex.getInvertedSeries().size()+
                            "\nmetric count " + metrics.cacheSize()  +
                            "\ntagkey count " + tagk.cacheSize()  +
                            "\ntagvalue count " + tagv.cacheSize()  +
                            "\ntimeline count " + timeseriesIndex.getTsIndexer().size()  +
                            "\ncollison hash " + timeseriesIndex.atomicInteger.intValue());
        System.out.println("MemoryUsage of time series total =" + (float)byteTimeSeriesMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of time series index =" + (float)byteTimeSeriesIndexMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of time series inverted index =" + (float)byteTimeSeriesInvertedIndexMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of metrics =" + (float)metricsMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of tagk =" + (float)tagkMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of tagv =" + (float)tagvMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of inverted index =" + (float)byteArrayMemoryUsage/1024/1024 + "M");
        System.out.println("MemoryUsage of KeyList =" + (float)byteKeyListMemoryUsage/1024/1024 + "M");
        // while (true) ;
    }

    public static void LoadNodeServerList(List<String> keyList, TermIndex termIndex) {
        try {
            FileReader fileReader = new FileReader(KEYSFILEPATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            long length = 0;
            int count = 0;
            while((key = bufferedReader.readLine())!=null){
                count ++;
                length = length + key.length();
                String[] keyArray = key.split("&");
                String metric = keyArray[0];
                Map<String, String> tagMap = new HashMap<>();
                for(int i=1; i<keyArray.length; i++){
                    String tag = keyArray[i];
                    String[] tagArray = tag.split("@");
                    String tagKey = tagArray[1];
                    String tagValue = tagArray[0];
               //     byte[] byteTagKey = tagk.getOrCreateId(tagKey);
                 //   byte[] byteTagValue = tagv.getOrCreateId(tagValue);

                    //tagMap.putIfAbsent(new String(byteTagKey, Charsets.ISO_8859_1),
                      //      new String(byteTagValue, Charsets.ISO_8859_1));
                    tagMap.putIfAbsent(tagKey, tagValue);
                }
                //byte[] byteMetric = metrics.getOrCreateId(metric);
              //  termIndex.createTermIndex(new String(byteMetric, Charsets.ISO_8859_1), tagMap);
                //termIndex.createTermIndex(metric, tagMap);
                if(count>=10000){
                    break;
                }
            }
           System.out.println("Total timeline " + count
                   + " Get Str Size " + (float)length/1024/1024 +"M");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static void LoadNodeServerList(List<String> keyList, TimeseriesIndex timeseriesIndex) {
        try {
            FileReader fileReader = new FileReader(KEYSFILEPATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            long length = 0;
            int count = 0;
            while((key = bufferedReader.readLine())!=null){
                count ++;
                length = length + key.length();
                String[] keyArray = key.split("&");
                String metric = keyArray[0];
                String metricBytes = String.valueOf(metrics.getId(metric));
                Map<String, String> tagMap = new HashMap<>();
                for(int i=1; i<keyArray.length; i++){
                    String tag = keyArray[i];
                    String[] tagArray = tag.split("@");
                    String tagKey = tagArray[1];
                    String tagValue = tagArray[0];
                    tagMap.putIfAbsent(tagKey, tagValue);
                }
                if(count>=10000){
                    break;
                }
                timeseriesIndex.createTimeseriesIndex(metricBytes,tagMap);

            }
            System.out.println("Total timeline " + count
                    + " Get Str Size " + (float)length/1024/1024 +"M");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static void LoadNodeServerList(List<String> keyList, TimeseriesIndexByte timeseriesIndex) {
        try {
            FileReader fileReader = new FileReader(KEYSFILEPATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            long length = 0;
            int count = 0;
            while((key = bufferedReader.readLine())!=null){
                count ++;
                length = length + key.length();
                String[] keyArray = key.split("&");
                String metric = keyArray[0];
                byte[] metricBytes = (metrics.getOrCreateId(metric));
                Map<byte[], byte[]> tagMap = new HashMap<>();
                for(int i=1; i<keyArray.length; i++){
                    String tag = keyArray[i];
                    String[] tagArray = tag.split("@");
                    String tagKey = tagArray[1];
                    String tagValue = tagArray[0];
                    byte[] tagKeyByte = tagk.getOrCreateId(tagKey);
                    byte[] tagValueByte = tagv.getOrCreateId(tagValue);
                    tagMap.putIfAbsent(tagKeyByte, tagValueByte);
                }
                if(count>200*1000){
                    break;
                }
                timeseriesIndex.createTimeseriesIndex(metricBytes,tagMap);

            }
            System.out.println("Total timeline " + count
                    + " Get Str Size " + (float)length/1024/1024 +"M");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    public static void setTSDB(){
        final org.hbase.async.Config async_config;
        async_config = new org.hbase.async.Config();
        async_config.overrideConfig("hbase.zookeeper.znode.parent",
                "/hbase");
        async_config.overrideConfig("hbase.zookeeper.quorum", "localhost:2181");
        client = new HBaseClient(async_config);

        metrics = new UniqueId(client, "hitsdb-uid".getBytes(Charsets.ISO_8859_1), "metrics", 3,
                false);
        tagk = new UniqueId(client, "hitsdb-uid".getBytes(Charsets.ISO_8859_1), "tagk", 3,
                false);
        tagv = new UniqueId(client, "hitsdb-uid".getBytes(Charsets.ISO_8859_1), "tagv", 3,
                false);

    }
}
