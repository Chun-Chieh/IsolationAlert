package com.example.maple.dashboardtest.model.activity;

import java.util.Map;

import static com.example.maple.dashboardtest.util.formatActivityTypeToString;

/**
 * @author Chun-Chieh Liang on 5/10/18.
 */
public class ActivityStatistics {
    private Map<Integer, ActivityData> activityDataMap;


    public ActivityStatistics(Map<Integer, ActivityData> activityDataMap) {
        this.activityDataMap = activityDataMap;
    }

    public Map<Integer, ActivityData> getActivityDataMap() {
        return activityDataMap;
    }

    public ActivityData getActivityDataByKey(int key) {
        return this.activityDataMap.get(key);
    }

    public String getActivityTypeInString(int key) {
        return formatActivityTypeToString(this.activityDataMap.get(key).getActivityType());
    }
}
