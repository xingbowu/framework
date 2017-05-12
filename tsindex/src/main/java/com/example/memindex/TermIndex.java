package com.example.memindex;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by xingbowu on 17/5/2.
 */
public class TermIndex {
    Map<String, TermDictionary> termIndexMap;
    public TermIndex() {
        termIndexMap = new ConcurrentHashMap<>();
    }

    public int constructDocId(Map<String, String> tags){
        StringBuilder docId = new StringBuilder();
        int count = tags.size();
        for(Map.Entry<String, String> entry: tags.entrySet()) {
            docId.append(entry.getKey());
            docId.append(Const.TAGCONNECTOR);
            docId.append(entry.getValue());
            count--;
            if (count!=0){
                docId.append(Const.TAGSCONNECTOR);
            }
        }
        Random rand = new Random();
        int id = rand.nextInt(1000*1000*100)+1;
       /* try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            GZIPOutputStream gzipOut = new GZIPOutputStream(baos);
            ObjectOutputStream objectOut = new ObjectOutputStream(gzipOut);
            objectOut.writeObject(docId);
            objectOut.close();
            byte[] bytes = baos.toByteArray();
            return Arrays.toString(bytes);

        }catch (Exception e){
            System.out.println(e.getMessage());
            return docId.toString();
        }*/
       return id;
    }

    public void createTermIndex(String metric, Map<String, String> tags){
        TermDictionary termDictionary = termIndexMap.get(metric);
        if(termDictionary==null){
            termDictionary = new TermDictionary();
            termIndexMap.put(metric, termDictionary);
        }

        int docId = constructDocId(tags);
        for(Map.Entry<String, String> entry: tags.entrySet()) {
            String tagBuilder = entry.getKey() + Const.TAGCONNECTOR + entry.getValue();
            termDictionary.createInvertedIndex(docId, tagBuilder);
        }
    }

    public Set<Integer> getTermIndex(String metric, Map<String, String> tags) throws Exception {
        TermDictionary termDictionary = termIndexMap.get(metric);
        if (termDictionary==null){
            throw new Exception("NO such metric index");
        }
        Set<Integer> docIdSet = new HashSet<>();
        for(Map.Entry<String, String> entry: tags.entrySet()) {
            String tagBuilder = entry.getKey() + Const.TAGCONNECTOR + entry.getValue();
            Set<Integer> docId = termDictionary.getInvertedIndex(tagBuilder);
            docIdSet.addAll(docId);
        }
        return docIdSet;
    }
}
