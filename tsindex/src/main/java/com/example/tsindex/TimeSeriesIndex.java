package com.example.tsindex;

import java.util.List;
import java.util.Map;

/**
 * Created by xingbowu on 17/4/7.
 */

public interface TimeSeriesIndex {
    List<String> getOrCreateTimeSeriesIndex(String metric, Map<String, String> tags);

    boolean WriteTimeSeriesIndex(String metric, Map<String, String> tags);

    List<String> QueryTimeSeriesIndex(String metric, Map<String, String> tags);

    void ReloadTimeSeriesIndex(short tsInstanceId);
}