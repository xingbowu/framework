package com.example.memindex;

import com.example.compact.CompactHashMap;
import com.example.compact.CompactHashSet;
import com.example.compact.CompactMapTranslator;
import com.example.compact.CompactSetTranslator;
import com.example.utils.MurmurHashImp;
import kotlin.text.Charsets;
import net.opentsdb.core.TSDB;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by xingbowu on 17/5/4.
 */
public class TimeseriesIndexByte {
    // ConcurrentMap<Integer, String> tsIndexer;
    Map<Integer, String> tsIndexer;
    private Map<String, Set<String>> invertedSeries;
   // private Map<String, RoaringBitmap> invertedSeries;
    AtomicInteger atomicInteger = new AtomicInteger();

    public TimeseriesIndexByte() {
        /*DB db = DBMaker.memoryDirectDB().make();
         tsIndexer = db.hashMap("map")
                 .valueSerializer(Serializer.INTEGER)
        .valueSerializer(new SerializerCompressionWrapper(Serializer.STRING))
        .createOrOpen();
         */
        tsIndexer = new CompactHashMap<Integer, String>(TRANSLATOR);
        /*tsIndexer = ChronicleMapBuilder
                .of(Integer.class, String.class)
                .name("test")
                .constantKeySizeBySample(50*000)
                .entries(1100*1000)
                .create();*/

       // tsIndexer = new ConcurrentHashMap<>();
        invertedSeries = new ConcurrentHashMap<>();
    }

    public Map<Integer, String> getTsIndexer() {
        return tsIndexer;
    }

    public Map<String, Set<String>> getInvertedSeries() {
        return invertedSeries;
    }

    public byte[] constructTimeserieKey(byte[] metric, Map<byte[], byte[]> tags){
        int size = tags.size()*(TSDB.tagk_width()+TSDB.tagv_width());
        byte[] timeseriesBuilder = new byte[TSDB.metrics_width()+size];
        int i =0;
        System.arraycopy(metric,0, timeseriesBuilder, 0, TSDB.metrics_width());
        for(Map.Entry<byte[], byte[]> entry: tags.entrySet()) {
            int position = i * (TSDB.tagk_width()+TSDB.tagv_width())+TSDB.metrics_width();
            System.arraycopy(entry.getKey(),0, timeseriesBuilder, position, TSDB.tagk_width());
            System.arraycopy(entry.getValue(),0, timeseriesBuilder, position+TSDB.tagk_width(), TSDB.tagk_width());
            i++;
        }

        return timeseriesBuilder;
    }

    public void createTimeseriesIndex(byte[] metric, Map<byte[], byte[]> tags){
        byte[] timeseriesKey = constructTimeserieKey(metric, tags);
        String key = new String(timeseriesKey, Charsets.ISO_8859_1);
        int tsId = MurmurHashImp.hash32(key);
        String result = tsIndexer.putIfAbsent(tsId, key);
        if (result != null && !result.equals(key)){
            atomicInteger.getAndIncrement();
            System.out.println("Hash Collision");
        }

        for(Map.Entry<byte[], byte[]> entry: tags.entrySet()) {
            int size = (TSDB.tagk_width()+TSDB.tagv_width());
            byte[] tagBuilder = new byte[size];
            System.arraycopy(entry.getKey(),0, tagBuilder, 0, TSDB.tagk_width());
            System.arraycopy(entry.getValue(),0, tagBuilder, TSDB.tagk_width(), TSDB.tagk_width());
            String tmp = new String(tagBuilder, Charsets.ISO_8859_1);
            //RoaringBitmap postListingKeys = invertedSeries.get(tmp);
            Set<String> postListingKeys = invertedSeries.get(tmp);
            if (postListingKeys==null){
             //   postListingKeys = Sets.newConcurrentHashSet();
             postListingKeys = new CompactHashSet<>(SETTRANSLATOR);
               // postListingKeys = new RoaringBitmap();
                invertedSeries.put(tmp, postListingKeys);
            }
            postListingKeys.add(String.valueOf(tsId));
            //postListingKeys.add(tsId);
        }
    }

    // Serialization format: (String s, int n) -> [s as bytes in UTF-8] + [n as 4 bytes in big endian].
    private static final CompactMapTranslator<Integer,String> TRANSLATOR = new CompactMapTranslator<Integer,String>() {

        public boolean isKeyInstance(Object obj) {
            return obj instanceof Integer;
        }


        public int getHash(Integer key) {
         /*   int state = 0;
            for (int i = 0; i < key.length(); i++) {
                state += key.charAt(i);
                for (int j = 0; j < 4; j++) {
                    state *= 0x7C824F73;
                    state ^= 0x5C12FE83;
                    state = Integer.rotateLeft(state, 5);
                }
            }*/
            return key;
        }


        public byte[] serialize(Integer key, String value) {
            try {
                byte[] packed = value.getBytes("UTF-8");
                int off = packed.length;
                packed = Arrays.copyOf(packed, off + 4);
                int key1 = key;
                packed[off + 0] = (byte)(key1 >>> 24);
                packed[off + 1] = (byte)(key1 >>> 16);
                packed[off + 2] = (byte)(key1 >>>  8);
                packed[off + 3] = (byte)(key1 >>>  0);
                return packed;
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public String deserializeValue(byte[] packed) {
            try {
                return new String(packed, 0, packed.length - 4, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public Integer deserializeKey(byte[] packed) {
            int n = packed.length;
            return (packed[n - 1] & 0xFF) | (packed[n - 2] & 0xFF) << 8 | (packed[n - 3] & 0xFF) << 16 | packed[n - 4] << 24;
        }
    };

    // Serialization format: String s -> [s as bytes in UTF-8].
    private static final CompactSetTranslator<String> SETTRANSLATOR = new CompactSetTranslator<String>() {

        public byte[] serialize(String s) {
            try {
                return s.getBytes("UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }


        public boolean isInstance(Object obj) {
            return obj instanceof String;
        }


        public int getHash(String s) {
            int state = 0;
            for (int i = 0; i < s.length(); i++) {
                state += s.charAt(i);
                for (int j = 0; j < 4; j++) {
                    state *= 0x7C824F73;
                    state ^= 0x5C12FE83;
                    state = Integer.rotateLeft(state, 5);
                }
            }
            return state;
        }


        public String deserialize(byte[] packed) {
            try {
                return new String(packed, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new AssertionError(e);
            }
        }

    };


}
