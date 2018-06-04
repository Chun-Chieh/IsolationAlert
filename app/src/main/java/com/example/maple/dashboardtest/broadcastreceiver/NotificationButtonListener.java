package com.example.maple.dashboardtest.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.example.maple.dashboardtest.database.DaoHelper;
import com.example.maple.dashboardtest.model.survey.SurveySelectedAnswer;

/**
 * @author Chun-Chieh Liang on 4/3/18.
 */

public class NotificationButtonListener extends BroadcastReceiver {
    public static final String YES_ACTION = "yes", NO_ACTION = "No";

    @Override
    public void onReceive(final Context context, Intent intent) {
        final String action = intent.getAction();
        final Long timeStamp = System.currentTimeMillis();
        if (action != null) {
            int notifyId = 0; // specified id
//            int notifyId = Integer.parseInt(intent.getStringExtra("notification id"));
            NotificationManagerCompat.from(context).cancel(notifyId);

            switch (action) {
                case YES_ACTION:
                    Log.d("Listener", String.format("Id:  %d, %s, Time: %s", notifyId, "YES", timeStamp));
                    break;
                case NO_ACTION:
                    Log.d("Listener", String.format("Id:  %d, %s, Time: %s", notifyId, "No", timeStamp));
                    break;
                default:
                    Log.d("Listener", "Error   " + timeStamp);
                    break;
            }

            // works (pre-java8)
            Thread t = new Thread() {
                public void run() {
                    new DaoHelper(context).getSurveyAnswerDao().save(new SurveySelectedAnswer(timeStamp, "micro", action));
                }
            };
            t.start();
        }
    }
}
