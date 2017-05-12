package com.example.memindex;

import org.roaringbitmap.RoaringBitmap;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xingbowu on 17/5/2.
 */
public class TermDictionary {
    private Map<String, RoaringBitmap> invertedTags;

    public TermDictionary(){
        invertedTags = new ConcurrentHashMap<>();
    }

    public void createInvertedIndex(int docId, String tags){
        RoaringBitmap postListingKeys = invertedTags.get(tags);
        if (postListingKeys==null){
            postListingKeys = new RoaringBitmap();
            invertedTags.put(tags, postListingKeys);
        }
        postListingKeys.add(docId);

    }

    public Set<Integer> getInvertedIndex(String tags) {
        RoaringBitmap roaringBitmap = invertedTags.get(tags);
        Set<Integer> index = new HashSet<>();
        for (int i: roaringBitmap){
            index.add(i);
        }
        return index;
    }

}