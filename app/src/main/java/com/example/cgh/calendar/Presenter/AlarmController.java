package com.example.cgh.calendar.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.cgh.calendar.Model.DataSaveByRealm;

import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by cgh on 2017/10/6.
 */

public class AlarmController implements IAlarmController {
    @Override
    public void setAlarm(String itemText, int position, String dateTime, Context context){
        //將dateTime轉為calendar
        Calendar calendar = Calendar.getInstance();
        //設定Calendar為dateTime時間
        //Format of dateTime: "YYYYMMDDhhmm"
        calendar.set(Calendar.YEAR, Integer.parseInt(dateTime.substring(0,4)));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateTime.substring(4,6))-1);//因系統月份是0~11，因顯示問題+1月份，此處要-1回復原本系統月份
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateTime.substring(6,8)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateTime.substring(8,10)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(dateTime.substring(10,12)));
        calendar.set(Calendar.SECOND, 0);
        //如果設定時間大於目前時間才設置alarmManager
        if(calendar.compareTo(Calendar.getInstance()) == 1) {//設定日期比現在時間早:-1，相同:0，晚:1
            Log.i("setAlarm","setAlarm");
            //設定alarmManager依時間傳送廣播訊息(帶有itemText)
            Intent mIntent = new Intent();
            //透過setData來控制取消
            mIntent.setClass(context, ItemBroadCastReceiver.class);
            mIntent.setData(Uri.parse(String.valueOf(position)));
            mIntent.setAction("com.cgh.ItemBroadCastMessage");
            mIntent.putExtra("itemText", itemText);//notification文字內容

            PendingIntent pi = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
        }
    }
    @Override
    public void cancel(int position, Context context){
        //取消該position的廣播
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent mIntent = new Intent();
        mIntent.setClass(context, ItemBroadCastReceiver.class);
        //透過setData來控制取消
        mIntent.setData(Uri.parse(String.valueOf(position)));
        mIntent.setAction("com.cgh.ItemBroadCastMessage");

        //PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_NO_CREATE);
        if (pi != null) am.cancel(pi);
    }
}