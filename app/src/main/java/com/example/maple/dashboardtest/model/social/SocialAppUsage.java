package com.example.maple.dashboardtest.model.social;

import android.graphics.drawable.Drawable;

/**
 * @author Chun-Chieh Liang on 5/9/18.
 */
public class SocialAppUsage {
    private String appName;
    private String appPackageName;
    private Drawable appIcon;
    private long appUsageTime;
    private long appLastTimeUsed;

    public SocialAppUsage() {
    }

    public SocialAppUsage(String appName, String appPackageName, long appUsageTime, long appLastTimeUsed) {
        this.appName = appName;
        this.appPackageName = appPackageName;
        this.appUsageTime = appUsageTime;
        this.appLastTimeUsed = appLastTimeUsed;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }


    public void setAppIcon(Drawable appIcon) {
        this.appIcon = appIcon;
    }

    public long getAppLastTimeUsed() {
        return appLastTimeUsed;
    }

    public void setAppLastTimeUsed(long appLastTimeUsed) {
        this.appLastTimeUsed = appLastTimeUsed;
    }

    public long getAppUsageTime() {
        return appUsageTime;
    }

    public void setAppUsageTime(long appUsageTime) {
        this.appUsageTime = appUsageTime;
    }
}
