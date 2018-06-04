package com.example.maple.dashboardtest.model.device;

/**
 * @author Chun-Chieh Liang on 2/9/18.
 * The wrapper class for calls selected by call type (incoming, outgoing, missed)
 */

public class CallsSummary {
    private int callType;
    private int count;
    private long totalDuration;

    public CallsSummary(int type, long totalDuration) {
        this.callType = type;
        this.totalDuration = totalDuration;
    }

    public CallsSummary(int type, int count, long totalDuration) {
        this.callType = type;
        this.count = count;
        this.totalDuration = totalDuration;
    }

    public int getCallType() {
        return callType;
    }

    public void setCallType(int type) {
        this.callType = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(long totalDuration) {
        this.totalDuration = totalDuration;
    }
}
