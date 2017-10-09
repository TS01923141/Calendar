package com.example.cgh.calendar.Presenter;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by cgh on 2017/9/6.
 */
//此處接收alarmManger的設定廣播，並設定寄出推播
public class ItemBroadCastReceiver extends BroadcastReceiver{
    public static final String BROADCAST_ACTION = "com.cgh.ItemBroadCastMessage";
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    //接收itemText並推播
    public void onReceive(Context context, Intent intent) {
        if(BROADCAST_ACTION.equals(intent.getAction())){
            String itemText = intent.getStringExtra("itemText");
            IItemNotification itemNotification = new ItemNotification();
            itemNotification.setNotification(itemText, context);
        }
    }
}