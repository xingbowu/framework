package com.example.demo;

import com.javamex.classmexer.MemoryUtil;
import org.mapdb.DB;
import org.mapdb.DBMaker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.ConcurrentMap;

import static com.example.memindex.Test.KEYSFILEPATH;

/**
 * Created by xingbowu on 17/5/4.
 */
public class MultiMap
{
    public static void main(String[] args) {

        // this is wrong, do not do it !!!
        //  Map<String,List<Long>> map
        DB db = DBMaker.memoryDB().make();
        ConcurrentMap map = db.hashMap("test").createOrOpen();
        try {
            FileReader fileReader = new FileReader(KEYSFILEPATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String key;
            long length = 0;
            int count = 0;
            while ((key = bufferedReader.readLine()) != null) {
                String[] keyArray = key.split("&",2);
                String metric = keyArray[0];
                String tags = keyArray[1];
                map.putIfAbsent(key, 1);
            }
        }catch (IOException e){
            e.getMessage();
        }

        long byteArrayMemoryUsage = MemoryUtil.deepMemoryUsageOf(map);
        System.out.println("map size:" + map.size());
        System.out.println("MemoryUsage of keylist index =" + (float)byteArrayMemoryUsage/1024/1024 + "M");

    }
}
