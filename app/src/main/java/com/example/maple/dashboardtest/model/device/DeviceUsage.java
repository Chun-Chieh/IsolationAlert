package com.example.maple.dashboardtest.model.device;

import com.example.maple.dashboardtest.R;

import static com.example.maple.dashboardtest.util.formatCallType;
import static com.example.maple.dashboardtest.util.getCountPair;
import static com.example.maple.dashboardtest.util.getDurationPair;

/**
 * @author Chun-Chieh Liang on 5/9/18.
 */
public class DeviceUsage {
    private String usageTitle;
    private long usageQuantity;
    private String usageUnit;
    private int usageImageSrc;

    // Media
    public DeviceUsage(String title, int quantity) {
        this.usageTitle = title;
        this.usageQuantity = quantity;
        this.usageUnit = ""; // no units for image and video
        if (title.equals("Image")) {
            this.usageImageSrc = R.drawable.ic_device_usage_image;
        }
        if (title.equals("Video")) {
            this.usageImageSrc = R.drawable.ic_device_usage_video;
        }

    }

    // SMS (or Media)
    public DeviceUsage(String title, int quantity, String unit) {
        this.usageTitle = title;
        this.usageQuantity = getCountPair(quantity, unit).first;
        this.usageUnit = getCountPair(quantity, unit).second;

        if (title.equals("Conversation")){
            this.usageQuantity = quantity;
            this.usageUnit = "people";
            this.usageImageSrc = R.drawable.ic_device_usage_sms_conversation;
        }
        if (title.equals("Total")){
            this.usageImageSrc = R.drawable.ic_device_usage_sms;
        }

        if (title.equals("Received")){
            this.usageImageSrc = R.drawable.ic_device_usage_sms_received;
        }
        if (title.equals("Sent")){
            this.usageImageSrc = R.drawable.ic_device_usage_sme_sent;
        }
        if (title.equals("Image")) {
            this.usageImageSrc = R.drawable.ic_device_usage_image;
        }
        if (title.equals("Video")) {
            this.usageImageSrc = R.drawable.ic_device_usage_video;
        }
    }

    // Call
    public DeviceUsage(CallsSummary callsSummary) {
        this.usageTitle = formatCallType(callsSummary.getCallType());
        this.usageQuantity = getDurationPair(callsSummary.getTotalDuration()).first;
        this.usageUnit = getDurationPair(callsSummary.getTotalDuration()).second;

        switch (callsSummary.getCallType()) {
            case 0:
                // Total
                this.usageImageSrc = R.drawable.ic_device_usage_call;
                break;
            case 1:
                // Incoming Call
                this.usageImageSrc = R.drawable.ic_device_usage_call_incoming;
                break;
            case 2:
                // Outgoing Call
                this.usageImageSrc = R.drawable.ic_device_usage_call_outgoing;
                break;
            case 3:
                // Missed Call
                this.usageQuantity = getCountPair(callsSummary.getCount(), "time").first;
                this.usageUnit = getCountPair(callsSummary.getCount(), "time").second;
                this.usageImageSrc = R.drawable.ic_device_usage_call_missedl;
                break;
        }

    }

    public String getUsageTitle() {
        return usageTitle;
    }

    public void setUsageTitle(String usageTitle) {
        this.usageTitle = usageTitle;
    }

    public long getUsageQuantity() {
        return usageQuantity;
    }

    public void setUsageQuantity(long usageQuantity) {
        this.usageQuantity = usageQuantity;
    }

    public String getUsageUnit() {
        return usageUnit;
    }

    public void setUsageUnit(String usageUnit) {
        this.usageUnit = usageUnit;
    }

    public int getUsageImageSrc() {
        return usageImageSrc;
    }

    public void setUsageImageSrc(int usageImageSrc) {
        this.usageImageSrc = usageImageSrc;
    }
}