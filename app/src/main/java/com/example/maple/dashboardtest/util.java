package com.example.maple.dashboardtest;

import android.content.Context;
import android.content.res.Resources;
import android.util.Pair;
import android.util.TypedValue;
import android.view.ViewGroup;

import com.google.android.gms.location.DetectedActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * @author Chun-Chieh Liang on 5/2/18.
 */

public class util {
    // Constants
    public static final String JSON_FILE = "EMASchema.json";

    // Survey question fragment
    public static final String ARG_PAGE = "page";
    public static final String ARG_QUESTION = "question";
    public static final String ARG_OPTIONS = "options";

    //
    public static final String ARG_DONE_PROMPT = "done prompt";

    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * Get today's beginning date time (0:00) in milliseconds
     * <p>
     * Usage: DeviceUsageCollector
     *
     * @return milliseconds of today date time
     */
    public static long getBeginningOfDayInMillisecond() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTimeInMillis();
    }

    /**
     * Format millisecond to string
     *
     * @param millisec millisecond
     * @return the formatted string of the date
     */
    public static String formatMillisecondToString(long millisec) {
        if (millisec == 0) {
            return "Not reported.";
        } else {
            return "Last time reported: " + new SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault()).format(new Date(millisec));
        }
    }


    /**
     * Get current time in string
     *
     * @return formatted string of current time
     */
    public static String getCurrentTimeFormatString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                .format(Calendar.getInstance().getTime());
    }


    /**
     * Get today's date. e.g. May 23, 2018
     *
     * @return formatted string of today's date
     */
    public static String getTodayDateFormatString() {
        return new SimpleDateFormat("MMM d, yyyy", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    /**
     * Get today's weekday. e.g. Thursday
     *
     * @return string of today's weekday
     */
    public static String getTodayWeekdayFormatString() {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(Calendar.getInstance().getTime());
    }

    /////////////////////////////////////////////////////////////////////////////////////

    /**
     * Format the call type to string
     *
     * Usage: DeviceUsage (model)
     *
     * @param type the call type
     * @return the string represents the call type
     */
    public static String formatCallType(int type) {
        switch (type) {
            case 0:
                return "Total";
            case 1:
                return "Incoming";
            case 2:
                return "Outgoing";
            case 3:
                return "Missed";
            default:
                return "Unknown";
        }
    }

    /**
     * Get duration quantity and unit separately and grammatically
     *
     * @param second the duration
     * @return pair of quantity and it's unit
     */
    public static Pair<Long, String> getDurationPair(long second) {
        long hr = second / (60 * 60);
        long min = (second - hr * 3600) / 60;
        long sec = (second - hr * 3600 - min * 60);


        if (hr != 0) {
            String hourUnit = (hr == 1) ? "hour " : "hours ";
            return new Pair<>(hr, hourUnit);
        }

        if (min != 0) {
            String minUnit = (min == 1) ? "minute " : "minutes ";
            return new Pair<>(min, minUnit);
        }

        if (sec != 0) {
            String secUnit = (sec == 1) ? "second " : "seconds ";
            return new Pair<>(sec, secUnit);
        }
        // none
        return new Pair<>(0L, "second");
    }


    /**
     * Format milliseconds to H - M - S grammatically.
     *
     * @param millisec milliseconds
     * @return string of the formatted result
     */
    public static Pair<Long, String> formatMilliSecToHMS(long millisec) {
        StringBuilder formatResult = new StringBuilder();
        long hr = TimeUnit.MILLISECONDS.toHours(millisec);
        long min = TimeUnit.MILLISECONDS.toMinutes(millisec) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisec));
        long sec = TimeUnit.MILLISECONDS.toSeconds(millisec) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisec));

        if (hr != 0) {
            String hourUnit = (hr == 1) ? "hour" : "hours ";
            return new Pair<>(hr, hourUnit);
        }

        if (min != 0) {
            String minUnit = (min == 1) ? "minute" : "minutes";
            return new Pair<>(min, minUnit);
        }

        if (sec != 0) {
            String secUnit = (sec == 1) ? "second " : "seconds";
            return new Pair<>(sec, secUnit);
        }
        if (formatResult.length() != 0) {
            formatResult.deleteCharAt(formatResult.length() - 1);
        }
        // none
        return new Pair<>(0L, "second");
    }

    // Format the count of calls to string

    /**
     * Get count (number) and unit separately and grammatically
     *
     * @param count quantity
     * @return pair of count and it's unit
     */
    public static Pair<Integer, String> getCountPair(int count, String singularUnit) {
        String countUnit = count > 1 ? singularUnit + "s" : singularUnit;
        return new Pair<>(count, countUnit);
    }
    /////////////////////////////////////////////////////////////////////////////////////


    /**
     * Format the activity type from int to string
     * <p>
     * int	IN_VEHICLE	The device is in a vehicle, such as a car.
     * int	ON_BICYCLE	The device is on a bicycle.
     * int	ON_FOOT	The device is on a user who is walking or running.
     * int	RUNNING	The device is on a user who is running.
     * int	STILL	The device is still (not moving).
     * int	WALKING	The device is on a user who is walking.
     *
     * @param activityType the type of the detected activity
     * @return the string represents the detected activity
     */
    public static String formatActivityTypeToString(int activityType) {
        switch (activityType) {
            case DetectedActivity.IN_VEHICLE: {
                return "Vehicle";
            }
            case DetectedActivity.ON_BICYCLE: {
                return "Bicycle";
            }
            case DetectedActivity.ON_FOOT: {
                return "Foot";
            }
            case DetectedActivity.RUNNING: {
                return "Running";
            }
            case DetectedActivity.STILL: {
                return "Still";
            }
            case DetectedActivity.TILTING: {
                return "Tilting";
            }
            case DetectedActivity.WALKING: {
                return "Walking";
            }
            case DetectedActivity.UNKNOWN: {
                return "Unknown";
            }
            default:
                return "Unknown";
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////
    // UI Configuration
    //
    //
    /////////////////////////////////////////////////////////////////////////////////////

    public static float convertDpToPixel(float dp, Context context){
        Resources r = context.getResources();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
    }


    public static int getNavigationBarHeight(Context context){
        int navigationBarHeight = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = context.getResources().getDimensionPixelSize(resourceId);
        }
        return navigationBarHeight;
    }
    /////////////////////////////////////////////////////////////////////////////////////

}
