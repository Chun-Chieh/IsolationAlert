package com.example.maple.dashboardtest.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.RemoteViews;

import com.example.maple.dashboardtest.R;
import com.example.maple.dashboardtest.broadcastreceiver.NotificationButtonListener;
import com.example.maple.dashboardtest.ui.activity.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.maple.dashboardtest.broadcastreceiver.NotificationButtonListener.NO_ACTION;
import static com.example.maple.dashboardtest.broadcastreceiver.NotificationButtonListener.YES_ACTION;
import static com.example.maple.dashboardtest.ui.activity.MainActivity.PACKAGE_NAME;
import static com.example.maple.dashboardtest.util.getCurrentTimeFormatString;

/**
 * @author Chun-Chieh Liang on 4/1/18.
 */

public class NotificationUtility {
    private static final String KEY_NOTIFICATION_GROUP = "Notification group";
    private static final String ACTION_QUICK_ANS = "answer";
    private final String channelId = "101";


    private Context context;
    private NotificationManager notificationManager;

    public NotificationUtility(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
    }

    public void sendNotification() {
        Log.d("Notification", "Notification created: " + getCurrentTimeFormatString());

        // todo: create multiple notifications for testing
//        notificationId = new Random().nextInt();
//        notificationManager.notify(notificationId, createNotification());

        // create a unique notification with specified id (0).
        notificationManager.notify(0, createNotification());

        // create summary (group) notification
//        notificationManager.notify(2, createSummaryNotification());

        // works (pre-java8)
//        Thread t = new Thread() {
//            public void run() {
//                new DaoHelper(context).getQuickSurveyDao().save(new QuickSurvey("Question", getFormattedCurrentTime()));
//            }
//        };
//        t.start();
    }


    public Notification createNotification() {
        final String title = "Time for a quick survey";
        final String content = "Swipe down to answer the question";

        RemoteViews expandedView = new RemoteViews(PACKAGE_NAME, R.layout.notification_expanded_micro);

        // add action to yes button
        Intent leftIntent = new Intent(context, NotificationButtonListener.class);
        leftIntent.setAction(YES_ACTION);
//        leftIntent.putExtra("notification id", String.valueOf(notificationId));
        PendingIntent leftPendingIntent = PendingIntent.getBroadcast(context, 101, leftIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        expandedView.setOnClickPendingIntent(R.id.button_yes, leftPendingIntent);

        // add action to no button
        Intent rightIntent = new Intent(context, NotificationButtonListener.class);
        rightIntent.setAction(NO_ACTION);
//        rightIntent.putExtra("notification id", String.valueOf(notificationId));
        PendingIntent rightPendingIntent = PendingIntent.getBroadcast(context, 102, rightIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        expandedView.setOnClickPendingIntent(R.id.button_no, rightPendingIntent);

        return new NotificationCompat.Builder(context, "101")
                .setSmallIcon(R.drawable.ic_menu_drawer_survey)
                .setColor(ContextCompat.getColor(context, R.color.colorAccent))
                .setContentTitle(title)
                .setContentText(content)
                .setCustomBigContentView(expandedView)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(Notification.PRIORITY_HIGH)
                .setGroup(KEY_NOTIFICATION_GROUP)
                .build();
    }

    /**
     * Group notification
     *
     * @return group builder
     */
    public Notification createSummaryNotification() {
        int importance = NotificationManager.IMPORTANCE_HIGH;
//        String contentTitle = context.getString(R.string.app_name);
        String contentText = context.getString(R.string.text_content_text);

        // add pending intent to the content
        PendingIntent pendingIntent = PendingIntent.getActivity(context
                , 0
                , new Intent(context, MainActivity.class)
                , 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);


            // The user-visible name of the channel.
            CharSequence name = "edu.bu";

            // The user-visible description of the channel.
            String description = "channel description";
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);

            // Configure the notification channel.
            channel.setDescription(description);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.enableLights(true);
            channel.setShowBadge(true);

            mNotificationManager.createNotificationChannel(channel);

            return new Notification.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentText(contentText)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentIntent(pendingIntent)
                    .setGroupSummary(true)
                    .setGroup(KEY_NOTIFICATION_GROUP)
                    .build();
        } else {
            return new NotificationCompat.Builder(context, channelId)
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .setContentIntent(pendingIntent)
                    .setContentText(contentText)
                    .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(importance)
                    .setGroupSummary(true)
                    .setGroup(KEY_NOTIFICATION_GROUP)
                    .build();
        }
    }


    public void cancelNotification(int notificationId) {
        Log.d("Notification", "Notification canceled: " + getCurrentTimeFormatString());
        NotificationManagerCompat.from(context).cancel(notificationId);
    }
}
