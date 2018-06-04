package com.example.maple.dashboardtest.controller;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.provider.Telephony;

import com.example.maple.dashboardtest.model.device.CallsSummary;

import static com.example.maple.dashboardtest.util.getBeginningOfDayInMillisecond;


/**
 * @author Chun-Chieh Liang on 2/9/18.
 */

public class DeviceUsageCollector {
    public static final int CALL_TYPE_INCOMING = CallLog.Calls.INCOMING_TYPE; // 1
    public static final int CALL_TYPE_OUTGOING = CallLog.Calls.OUTGOING_TYPE; // 2
    public static final int CALL_TYPE_MISSED = CallLog.Calls.MISSED_TYPE; // 3

    private Context mContext;
    private Uri mUri;
    private long mTodayDate;

    public DeviceUsageCollector(Context context, Uri uri) {
        this.mContext = context;
        this.mUri = uri;
    }


    /**
     * Get today's "count of calls" and "total duration" based on the call type
     *
     * @param callType incoming, outgoing or missed
     * @return a list of call counts and total duration
     */
    public CallsSummary getTodayCallsDetail(int callType) {
        mTodayDate = getBeginningOfDayInMillisecond();
        int count = 0;
        long totalDuration = 0;
        String[] projection = new String[]{CallLog.Calls.DURATION};
        String selectionClause = CallLog.Calls.DATE + ">=? and " + CallLog.Calls.TYPE + "=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate), String.valueOf(callType)};

        Cursor cursor = mContext.getContentResolver().query(mUri, projection, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getCount();
            do {
                long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
                totalDuration += duration;
            } while (cursor.moveToNext());
            cursor.close();
        }

        return new CallsSummary(callType, count, totalDuration);
    }

    /**
     * Get today's calls total duration including incoming and outgoing
     *
     * @return today's total duration
     */
    public long getTodayCallsTotalDuration() {
        mTodayDate = getBeginningOfDayInMillisecond();
        long totalDuration = 0;
        String[] projection = new String[]{CallLog.Calls.DURATION};
        String selectionClause = CallLog.Calls.DATE + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate)};

        Cursor cursor = mContext.getContentResolver().query(mUri, null, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                long date = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
                long duration = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DURATION));
//                Log.d("Collector", new SimpleDateFormat().format(date) + "\n" + duration+"\n");
                totalDuration += duration;
            } while (cursor.moveToNext());
            cursor.close();
        }
        return totalDuration;

    }

    public int getTodayConversationCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        int totalCount = 0;
        Uri SMS_INBOX = Uri.parse("content://sms/conversations/");

        String selectionClause = Telephony.Sms.Conversations.DATE + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate)};

        Cursor cursor = mContext.getContentResolver().query(SMS_INBOX, null, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            totalCount = cursor.getCount();
            cursor.close();
        }

        return totalCount;
    }

    public int getTodayAllMessagesCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        int totalCount = 0;
        Uri SMS_INBOX = Uri.parse("content://sms/");

        String selectionClause = Telephony.Sms.Conversations.DATE + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate)};

        Cursor cursor = mContext.getContentResolver().query(SMS_INBOX, null, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            totalCount = cursor.getCount();
            cursor.close();
        }

        return totalCount;
    }

    public int getTodayInboxMessagesCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        int totalCount = 0;
        Uri SMS_INBOX = Uri.parse("content://sms/inbox/");

        String selectionClause = Telephony.Sms.Conversations.DATE + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate)};

        Cursor cursor = mContext.getContentResolver().query(SMS_INBOX, null, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            totalCount = cursor.getCount();
            cursor.close();
        }

        return totalCount;
    }

    public int getTodaySentMessagesCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        int totalCount = 0;
        Uri SMS_INBOX = Uri.parse("content://sms/sent/");

        String selectionClause = Telephony.Sms.Conversations.DATE + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate)};

        Cursor cursor = mContext.getContentResolver().query(SMS_INBOX, null, selectionClause, selectionArg, null);
        if (cursor != null && cursor.moveToFirst()) {
            totalCount = cursor.getCount();
            cursor.close();
        }

        return totalCount;
    }


    public int getTotalPhotoCount() {
        int count = 0;
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
        if (cursor != null) {
            count = cursor.getCount();
            cursor.close();
        }
        return count;
    }

    public int getTodayPhotoCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        String selectionClause = MediaStore.MediaColumns.DATE_ADDED + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate / 1000)};

        int count = 0;
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, selectionClause, selectionArg, null);
        if (cursor != null) {
            count += cursor.getCount();
            cursor.close();
        }

        cursor = mContext.getContentResolver().query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, null, selectionClause, selectionArg, null);
        if (cursor != null) {
            count += cursor.getCount();
            cursor.close();
        }

        return count;
    }

    public int getTodayVideoCount() {
        mTodayDate = getBeginningOfDayInMillisecond();
        String selectionClause = MediaStore.MediaColumns.DATE_ADDED + ">=?";
        String[] selectionArg = new String[]{String.valueOf(mTodayDate / 1000)};

        int count = 0;
        Cursor cursor = mContext.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, selectionClause, selectionArg, null);
        if (cursor != null) {
            count += cursor.getCount();
            cursor.close();
        }

        cursor = mContext.getContentResolver().query(MediaStore.Video.Media.INTERNAL_CONTENT_URI, null, selectionClause, selectionArg, null);
        if (cursor != null) {
            count += cursor.getCount();
            cursor.close();
        }
        return count;
    }
}
