package com.example.ehab.medapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import org.joda.time.LocalTime;

public class Utils {
    public static boolean isWithinInterval(LocalTime start,
                                           LocalTime end,
                                           LocalTime time) {
        if (start.isAfter(end)) {
            // Return true if the time is after (or at) start,
            // *or* it's before end
            return time.compareTo(start) >= 0 ||
                    time.compareTo(end) < 0;
        } else {
            return start.compareTo(time) <= 0 &&
                    time.compareTo(end) < 0;
        }
    }

    public static String getDayPart(LocalTime time)
    {
        LocalTime morning = new LocalTime(6, 0, 0);
        LocalTime evening = new LocalTime(18, 0, 0);
        LocalTime noon = new LocalTime(12, 0, 0);
        LocalTime midnight = new LocalTime(0, 0, 0);
        if(isWithinInterval(morning, noon, time))
            return "Morning";
        else if(isWithinInterval(noon, evening, time))
            return "AfterNoon";
        else if(isWithinInterval(evening,midnight, time))
            return "Evening";
        else if(isWithinInterval(midnight,morning, time))
            return "Night";

        return "";
    }


    public static void  sendNotification(String title, String message, Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = "1";
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri)
                        .setVibrate(new long[] { 1000, 1000})
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    "readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(1 /* ID of notification */, notificationBuilder.build());
    }

}
