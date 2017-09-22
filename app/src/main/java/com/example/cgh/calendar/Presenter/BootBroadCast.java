package com.example.cgh.calendar.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import java.util.Calendar;
import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/6.
 */

public class BootBroadCast extends BroadcastReceiver {
    IRealmController realmController = new RealmController();
    //////////////////////////////////////////////////////////沒有Realm.init(context);
    @Override
    public void onReceive(Context context, Intent intent){
        //接收Android開機完成的廣播通知
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            RealmResults<DataSaveByRealm> itemData = realmController.searchAll();
            int i=0;
            for(i=0; i<itemData.size(); i++) {
                DataSaveByRealm sendData = itemData.get(i);///////////////////需修改get(0)為最小日期的數字 or 設成for全部丟進alarmManager
                //取得所有notifcation的時間
                String dateTime = sendData.getDateTime();
                Calendar c = Calendar.getInstance();
                //設定Calendar為dateTime時間
                //Format of dateTime: "YYYYMMDDhhmm"
                c.set(Calendar.YEAR, Integer.parseInt(dateTime.substring(0,4)));
                c.set(Calendar.MONTH, Integer.parseInt(dateTime.substring(4,6)));
                c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateTime.substring(6,8)));
                c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateTime.substring(8,10)));
                c.set(Calendar.MINUTE, Integer.parseInt(dateTime.substring(10,12)));
                c.set(Calendar.SECOND, 0);
                //設定alarmManager依時間傳送廣播訊息(帶有itemText)
                Intent mIntent = new Intent();
                mIntent.setAction("com.cgh.ItemBroadCastMessage");
                mIntent.putExtra("itemText", sendData.getItemText());//notification文字內容

                PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);

                AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pi);
            }
        }
    }
}
