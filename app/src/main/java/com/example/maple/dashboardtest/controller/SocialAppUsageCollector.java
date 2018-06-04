package com.example.maple.dashboardtest.controller;

import android.app.Activity;
import android.app.AppOpsManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.model.social.SocialAppUsage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.maple.dashboardtest.util.getBeginningOfDayInMillisecond;

/**
 * @author Chun-Chieh Liang on 2/8/18.
 */

public class SocialAppUsageCollector {
    private SocialAppUsageCollector instance = null;
    private Activity mRootActivity;
    private UsageStatsManager mUsageStatsManager;
    private PackageManager mPackageManager;

    private long mToday;

    private List<String> mSocialAppNames = new ArrayList<>();


    public SocialAppUsageCollector(Context context) {
        // Initiate usage stats manager
        mUsageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);

        // Initiate package manager
        mPackageManager = context.getPackageManager();

        // Set the time to 0:00
        mToday = getBeginningOfDayInMillisecond();

        // Set the social app names
        setSocialAppNames();
    }

    public SocialAppUsageCollector(Activity root, Context context) {
        this(context);
        this.mRootActivity = root;
    }

    // use singleton pattern, create only one instance
    public synchronized SocialAppUsageCollector getInstance(Context context) {
        if (instance == null) {
            instance = new SocialAppUsageCollector(context);
            return instance;
        } else return instance;
    }

    /**
     * Setup the social apps list
     * <p>
     * todo: the app names could be retrieved from the server
     */
    private void setSocialAppNames() {
        mSocialAppNames.add("Hangouts");
        mSocialAppNames.add("Facebook");
        mSocialAppNames.add("Duo");
        mSocialAppNames.add("WeChat");
        mSocialAppNames.add("Pinterest");
        mSocialAppNames.add("Instagram");
        mSocialAppNames.add("Messenger");
        mSocialAppNames.add("Twitter");
        mSocialAppNames.add("Messages");
    }

    /**
     * Get all apps usage with time span
     *
     * @return A Map of package name and it's UsageStats.
     */
    public Map<String, UsageStats> getAllUsageStatistics() {
        long startTime = mToday;
        long endTime = System.currentTimeMillis();
        return mUsageStatsManager.queryAndAggregateUsageStats(startTime, endTime);
    }

    /**
     * Get a social app usage by compiling the usageStats
     *
     * @param usageStatsMap A Map of ALL apps usageStats with pair of <packageName, UsageStats>
     * @return A list of SocialAppUsage.
     */
    public List<SocialAppUsage> getSocialAppUsageList(Map<String, UsageStats> usageStatsMap) {
        List<SocialAppUsage> socialAppUsageList = new ArrayList<>();

        for (Map.Entry<String, UsageStats> entry : usageStatsMap.entrySet()) {
            // Get the app package name
            String packageName = entry.getKey();
            String appName = getAppName(entry.getValue().getPackageName());

            // Filter out the social apps
            if (mSocialAppNames.contains(appName) && isAppInstalled(packageName)) {

                SocialAppUsage socialAppUsage = new SocialAppUsage();

                // Set the app name
                socialAppUsage.setAppName(appName);

                // Set the app package name
                socialAppUsage.setAppPackageName(packageName);

                // Set the app last time used
                // socialAppUsage.setAppLastTimeUsed(lastTimeUsed);
                socialAppUsage.setAppLastTimeUsed(System.currentTimeMillis());

                // Set the app usage time
                long usageTime = entry.getValue().getTotalTimeInForeground();
                socialAppUsage.setAppUsageTime(usageTime);

                // Set the app icon
                Drawable appIcon = getAppIcon(packageName);
                socialAppUsage.setAppIcon(appIcon);

                socialAppUsageList.add(socialAppUsage);
            }
        }
        return socialAppUsageList;
    }

    public List<SocialAppUsage> getSortedSocialAppUsageList(List<SocialAppUsage> socialAppUsageList){
        Collections.sort(socialAppUsageList, new UsageTimeComparatorDesc());
        return socialAppUsageList;
    }

    /**
     * Retrieve the app name by the package name
     *
     * @param packageName the only package name
     * @return app name
     */
    private String getAppName(String packageName) {
        ApplicationInfo applicationInfo = null;
        try {
            applicationInfo = mPackageManager.getApplicationInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, String.format("%s is not found", packageName));
        }
        return (String) (applicationInfo != null ? mPackageManager.getApplicationLabel(applicationInfo) : "Unknown");
    }

    /**
     * Retrieve the app icon by the package name
     *
     * @param packageName the package name
     * @return app icon
     */
    private Drawable getAppIcon(String packageName) {
        Drawable appIcon = null;
        try {
            appIcon = mPackageManager.getApplicationIcon(packageName);

        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, String.format("App Icon is not found for %s", packageName));
        }
        return appIcon != null ? appIcon : mRootActivity.getDrawable(R.mipmap.ic_launcher_round);
    }


    public long getAllTotalUsageTime(List<SocialAppUsage> socialAppUsageList) {
        long totalUsageTime = 0;
        for (SocialAppUsage socialAppUsage : socialAppUsageList) {
            totalUsageTime += socialAppUsage.getAppUsageTime();
        }
        return totalUsageTime;
    }


    /**
     * Check if the settings, "app usage", can be accessed
     *
     * @return true if it can be accessed. Otherwise, false.
     */
    public boolean isAccessGranted() {
        try {
            ApplicationInfo applicationInfo = mPackageManager.getApplicationInfo(mRootActivity.getPackageName(), 0);
            AppOpsManager appOpsManager = (AppOpsManager) mRootActivity.getSystemService(Context.APP_OPS_SERVICE);
            int mode = 0;
            if (appOpsManager != null) {
                mode = appOpsManager.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS,
                        applicationInfo.uid, applicationInfo.packageName);
            }
            return (mode == AppOpsManager.MODE_ALLOWED);

        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * Check if the app is installed
     *
     * @param packageName the package name.
     * @return true if it's installed. Otherwise, false.
     */
    private boolean isAppInstalled(String packageName) {
        try {
            mPackageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, String.format("%s is not installed", packageName));
        }
        return false;
    }


    /**
     * The Comparator to sort a collection by the timestamp of the specified argument
     * in the descendant order.
     */
    private static class NameComparatorDesc implements Comparator<SocialAppUsage> {
        @Override
        public int compare(SocialAppUsage left, SocialAppUsage right) {
            return left.getAppName().compareTo(right.getAppName());
        }
    }

    private static class UsageTimeComparatorDesc implements Comparator<SocialAppUsage> {
        @Override
        public int compare(SocialAppUsage left, SocialAppUsage right) {
            return Long.compare(right.getAppUsageTime(), left.getAppUsageTime());
        }
    }

    private static class LastUsedTimeComparatorDesc implements Comparator<SocialAppUsage> {
        @Override
        public int compare(SocialAppUsage left, SocialAppUsage right) {
            return Long.compare(right.getAppLastTimeUsed(), left.getAppLastTimeUsed());
        }
    }
}
