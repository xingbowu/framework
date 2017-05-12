package com.example.memindex;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.*;

/**
 * Created by xingbowu on 17/5/2.
 */
public class IndexDemo {
    public static void main(String[] args) {

        Map<String, Set<String>> originalMap = new HashMap<>();
        Set<String> postingList1 = Sets.newConcurrentHashSet();
        postingList1.add("a");
        postingList1.add("b");
        originalMap.put("k1=v1",postingList1);
        Set<String> postingList2 = Sets.newConcurrentHashSet();
        postingList1.add("c");
        postingList1.add("d");
        originalMap.put("k1=v2", postingList2);
        originalMap.put("k2=v3", postingList1);
        originalMap.put("k3=v4", postingList2);
        Collection<String> filteredKeys = Arrays.asList("k3=v4","k1=");
        Map<String, Set<String>> targetMap = Maps.filterKeys(originalMap, filteredKeys::contains);
        System.out.println(targetMap.toString());
    }
}
