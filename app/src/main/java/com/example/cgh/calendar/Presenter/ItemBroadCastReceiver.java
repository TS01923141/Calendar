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
            //Intent mIntent = new Intent(context,BootBroadCast.class);
            //mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
    }
}
/*
public class ItemBroadCastReceiver implements IItemBroadCastReceiver{
    //廣播事件的action名稱
    public static final String BROADCAST_ACTION ="com.cgh.ItemBroadCastMessage";
    IRealmController realmController = new RealmController();
    IItemNotification itemNotification;

    public void register(){
        registerReceiver(ItemBroadCastReceiver, new IntentFilter(BROADCAST_ACTION));
    }

    public BroadcastReceiver init = new BroadcastReceiver(){
        @Override
        public void onReceiver(Context mContext, Intent mIntent){
            if(BROADCAST_ACTION.equals(mIntent.getAction())){
                Intent intent = getIntent();
                String itemText = intent.getStringExtra("itemText");

                itemNotification = new ItemNotification(itemText);
            }
        }
    }
}*/