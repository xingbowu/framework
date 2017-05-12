package com.example.memindex;

import com.example.utils.MurmurHashImp;
import net.openhft.chronicle.map.ChronicleMapBuilder;
import org.roaringbitmap.RoaringBitmap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by xingbowu on 17/5/4.
 */
public class TimeseriesIndex {
    ConcurrentMap<String, Integer> tsIndexer;
    private Map<String, RoaringBitmap> invertedSeries;

    public TimeseriesIndex() {
        /*DB db = DBMaker.memoryDirectDB()
                .closeOnJvmShutdown()
                .make();
         tsIndexer = db.hashMap("map")
        .keySerializer(new SerializerCompressionWrapper(Serializer.STRING))
        .valueSerializer(Serializer.INTEGER).createOrOpen();*/
        tsIndexer = ChronicleMapBuilder.of(String.class, Integer.class).create();
//        tsIndexer = new ConcurrentHashMap<>();
        invertedSeries = new ConcurrentHashMap<>();

    }

    public String constructTimeserieKey(String metric, Map<String, String> tags){
        StringBuilder  timeseriesKey = new StringBuilder();
        Random random = new Random();
        int metricvalue = random.nextInt(10000);
        timeseriesKey.append(metricvalue);
        timeseriesKey.append(Const.TAGSCONNECTOR);
        int count = tags.size();
        for(Map.Entry<String, String> entry: tags.entrySet()) {
            timeseriesKey.append(entry.getKey());
            timeseriesKey.append(Const.TAGCONNECTOR);
            timeseriesKey.append(entry.getValue());
            count--;
            if (count!=0){
                timeseriesKey.append(Const.TAGSCONNECTOR);
            }
        }
        return timeseriesKey.toString();
    }

    public void createTimeseriesIndex(String metric, Map<String, String> tags){
        String timeseriesKey = constructTimeserieKey(metric, tags);
        int tsId = MurmurHashImp.hash32(timeseriesKey);
        tsIndexer.putIfAbsent(timeseriesKey, tsId);
        for(Map.Entry<String, String> entry: tags.entrySet()) {
            String tagBuilder = entry.getKey()+ Const.TAGCONNECTOR + entry.getValue();
            RoaringBitmap postListingKeys = invertedSeries.get(tagBuilder);
            if (postListingKeys==null){
                postListingKeys = new RoaringBitmap();
                invertedSeries.put(tagBuilder, postListingKeys);
            }
            postListingKeys.add(tsId);
        }
    }
}
