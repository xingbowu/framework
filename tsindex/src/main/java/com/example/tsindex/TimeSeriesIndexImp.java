package com.example.tsindex;


import java.util.List;
import java.util.Map;

/**
 * Created by xingbowu on 17/4/7.
 */
public class TimeSeriesIndexImp implements TimeSeriesIndex {

    public List<String> getOrCreateTimeSeriesIndex(String metric, Map<String, String> tags) {
        return null;
    }

    public boolean WriteTimeSeriesIndex(String metric, Map<String, String> tags) {

        return false;
    }

    public List<String> QueryTimeSeriesIndex(String metric, Map<String, String> tags) {
        return null;
    }

    public void ReloadTimeSeriesIndex(short tsInstanceId) {

    }
}
