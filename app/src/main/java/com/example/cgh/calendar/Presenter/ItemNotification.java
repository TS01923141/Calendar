package com.example.cgh.calendar.Presenter;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.cgh.calendar.View.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by cgh on 2017/9/6.
 */
//在此設定推播
public class ItemNotification implements IItemNotification{
    private Intent intent;
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager notificationManager;
    private Notification notification;

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setNotification(String itemText, Context context){
        Log.i("setNotification", itemText);
        Log.i("setNotification.context", String.valueOf(context));
        notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        //Channel:Android 8.0以上必須加入才能運作notification
        ////////////////////////////////////////////////////////////////////////////////////////////////////////
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.i("this API levels >=","Oreo");
            int defaults = 0;
            defaults |= Notification.DEFAULT_VIBRATE;//加入震動效果
            defaults |= Notification.DEFAULT_LIGHTS;//加入閃燈效果

            // The id of the channel.
            final String id = "Calendar_Channel";
            // The user-visible name of the channel.
            CharSequence name = "Calendar_Channel";
            // The user-visible description of the channel.
            String description = "Calendar_Channel";
            int importance = NotificationManager.IMPORTANCE_LOW;
            //Channel:Android 8.0以上必須加入才能運作notification
            NotificationChannel calendarChannel = new NotificationChannel(id, name, importance);
            // Configure the notification channel.
            calendarChannel.setDescription(description);
            calendarChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            calendarChannel.setLightColor(Color.RED);
            calendarChannel.enableVibration(true);
            calendarChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            notificationManager.createNotificationChannel(calendarChannel);

            // Sets an ID for the notification, so it can be updated.
            int notifyID = 1;
            // The id of the channel.
            String CHANNEL_ID = "Calendar_Channel";
            ////////////////////////////////////////////////////////////////////////////////////////////////////////

            notification = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle("NotificationMessage")
                    .setContentText(itemText)
                    .setContentIntent(pendingIntent)
                    .setChannelId(CHANNEL_ID)
                    .setDefaults(defaults)
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }else{
            Log.i("this API levels <","Oreo");
            int defaults = 0;
            defaults |= Notification.DEFAULT_VIBRATE;//加入震動效果
            defaults |= Notification.DEFAULT_LIGHTS;//加入閃燈效果
            notification = new Notification.Builder(context)
                    .setSmallIcon(android.R.drawable.sym_def_app_icon)
                    .setContentTitle("NotificationMessage")
                    .setContentText(itemText)
                    .setContentIntent(pendingIntent)
                    .setDefaults(defaults)
                    .build();
            notification.flags = Notification.FLAG_AUTO_CANCEL;
        }

        //寄送notification
        notificationManager.notify(0, notification);
    }
}
