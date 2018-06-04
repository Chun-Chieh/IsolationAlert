package com.example.maple.dashboardtest.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.maple.dashboardtest.notification.NotificationUtility;

import java.util.Calendar;

/**
 * @author Chun-Chieh Liang on 4/1/18.
 * <p>
 * Start foreground service and trigger alarm if it's needed
 * The alarm (to cancel a micro ema) is set to 10 minutes
 */

public class NotificationForegroundService extends Service {
    public final static String ACTION_NOTIFICATION = "ACTION_NOTIFICATION";
    public static final String ACTION_STOP_FOREGROUND_SERVICE = "ACTION_STOP_FOREGROUND_SERVICE";

    IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public NotificationForegroundService getService() {
            return NotificationForegroundService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Setup the summary notification
        Notification notification = new NotificationUtility(getApplicationContext()).createSummaryNotification();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();

//            switch (action) {
//                case ACTION_NOTIFICATION:
////                    new NotificationUtility(getApplicationContext()).sendNotification();
//
//                    // cancel the specified notification after specified time
//                    new NotificationUtility(getApplicationContext()).cancelNotification(0);
//                    break;
//                case ACTION_STOP_FOREGROUND_SERVICE:
//                    stopForegroundService();
//                    break;
//                default:
//
//            }

            if (ACTION_NOTIFICATION.equals(action)) {
//                 new NotificationUtility(getApplicationContext()).sendNotification();

                // cancel the specified notification after specified time
                new NotificationUtility(getApplicationContext()).cancelNotification(0);
            }

            if (ACTION_STOP_FOREGROUND_SERVICE.equals(action)){
                stopForegroundService();
                Toast.makeText(getBaseContext(), "Service is stopped", Toast.LENGTH_SHORT).show();
            }

        }
        return START_NOT_STICKY;
    }



    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public void initAlarm(String action) {
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent serviceIntent = new Intent(this, NotificationForegroundService.class);
        serviceIntent.setAction(action);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, serviceIntent, 0);
        mAlarmManager.cancel(pendingIntent);

        Calendar checkInCal = Calendar.getInstance();

        // roll up 10 minute to dismiss the notification
        checkInCal.roll(Calendar.MINUTE, 10);


        // setRepeating is "more" accurate.
//        mAlarmManager.setRepeating(AlarmManager.RTC, checkInCal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pi);

        // use setInexactRepeating to reduce resource usage at the risk of inaccurate time
        //mAlarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, checkInCal.getTimeInMillis(), 60*1000, pi);

        // set 1-time alarm
        mAlarmManager.set(AlarmManager.RTC_WAKEUP, checkInCal.getTimeInMillis(), pendingIntent);
    }

    private void stopForegroundService() {
        Log.d("Foreground Service", "Stop foreground service.");

        // Stop foreground service and remove the notification.
        stopForeground(true);

        // Stop the foreground service.
        stopSelf();
    }
}
