package com.example.cgh.calendar.Presenter;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.cgh.calendar.View.MainActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by cgh on 2017/9/6.
 */

public class ItemNotification implements IItemNotification{
    private Intent intent;
    private static final int NOTIFICATION_ID = 0;
    private NotificationManager notificationManager;
    private Notification notification;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setNotification(String itemText, Context context){
        notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);

        intent = new Intent();
        intent.setClass(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT);

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


        //寄送notification
        notificationManager.notify(0, notification);


		/*
		//建立Notification.Builder物件
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		//設定Notification物件內容
		builder.setSmallIcon(r.drawable.notify_small_icon)
				.setWhen(System.currentTimeMillis())
				.setContentTitle("Title")
				.setContentText("Text");
		int defaults = 0;
		defaults |= Notification.DEFAULT_VIBRATE;//加入震動效果
		defaults |= Notification.DEFAULT_LIGHTS;//加入閃燈效果
		builder.setDefaults(defaults);

		Manifest需開啟震動功能
		<?xml version="1.0" encoding="utf-8"?>
		<manifest ... >
			<uses-permission android:name="android.permission.VIBRATE"/>
			<application ... >
				...
			</application>
		</manifest>

		Notification notification = builder.build();
		manager.notify(CUSTOM_EFFECT_ID, notification);
		*/
    }
}
