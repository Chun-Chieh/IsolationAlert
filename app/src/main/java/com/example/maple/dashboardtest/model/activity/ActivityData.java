package com.example.maple.dashboardtest.model.activity;

import com.example.maple.dashboardtest.R;
import com.google.android.gms.location.DetectedActivity;

/**
 * @author Chun-Chieh Liang on 5/10/18.
 */

public class ActivityData {
    private int activityType;
    private long activityDuration; // millisecond
    private int activityFrequency; // times that are unchanged
    private int activityTypeImageSrc; // the image source of the type of activity

    public ActivityData(int activityType, long activityDuration, int activityFrequency) {
        this.activityType = activityType;
        this.activityDuration = activityDuration;
        this.activityFrequency = activityFrequency;
        switch (activityType) {
            case DetectedActivity.IN_VEHICLE: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_vehicle;
                break;
            }
            case DetectedActivity.ON_BICYCLE: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_bike;
                break;
            }
            case DetectedActivity.ON_FOOT: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_running;
                break;
            }
            case DetectedActivity.RUNNING: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_running;
                break;
            }
            case DetectedActivity.STILL: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_still;
                break;
            }
            case DetectedActivity.WALKING: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_walking;
                break;
            }
            default: {
                this.activityTypeImageSrc = R.drawable.ic_activity_type_default;
                break;
            }

        }
    }

    public int getActivityType() {
        return activityType;
    }

    public long getActivityDuration() {
        return activityDuration;
    }

    public int getActivityFrequency() {
        return activityFrequency;
    }

    public int getActivityTypeImageSrc() {
        return activityTypeImageSrc;
    }
}
