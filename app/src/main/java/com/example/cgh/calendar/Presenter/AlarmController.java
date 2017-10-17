package com.example.cgh.calendar.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.example.cgh.calendar.Model.DataSaveByRealm;

import java.util.Calendar;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.realm.RealmResults;

/**
 * Created by cgh on 2017/10/6.
 */
//此處設定定時廣播或取消廣播
public class AlarmController implements IAlarmController {
    @Override
    //依dateTime設定廣播時間，內容為itemText，position用來確定在資料庫位置，以便取消廣播
    public void setAlarm(final String itemText, final int position, String dateTime, final Context context){
        /*
        //將dateTime轉為calendar
        Calendar calendar = Calendar.getInstance();
        //設定Calendar為dateTime時間
        //Format of dateTime: "YYYYMMDDhhmm"
        calendar.set(Calendar.YEAR, Integer.parseInt(dateTime.substring(0,4)));
        Log.i("Calendar.Month",dateTime.substring(4,6));
        Log.i("Calendar.Month-1", String.valueOf(Integer.parseInt(dateTime.substring(4,6))-1));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateTime.substring(4,6))-1);//因系統月份是0~11，因顯示問題+1月份，此處要-1回復原本系統月份
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateTime.substring(6,8)));
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(dateTime.substring(8,10)));
        calendar.set(Calendar.MINUTE, Integer.parseInt(dateTime.substring(10,12)));
        calendar.set(Calendar.SECOND, 0);
        //如果設定時間大於目前時間才設置alarmManager
        if(calendar.compareTo(Calendar.getInstance()) == 1) {//設定日期比現在時間早:-1，相同:0，晚:1
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
        */
        Flowable.just(dateTime)
                .map(new Function<String, Calendar>() {
                    @Override
                    public Calendar apply(@NonNull String s) throws Exception {
                        //將dateTime轉為calendar
                        Calendar calendar = Calendar.getInstance();
                        //設定Calendar為dateTime時間
                        //Format of dateTime: "YYYYMMDDhhmm"
                        calendar.set(Calendar.YEAR, Integer.parseInt(s.substring(0,4)));
                        Log.i("Calendar.Month",s.substring(4,6));
                        Log.i("Calendar.Month-1", String.valueOf(Integer.parseInt(s.substring(4,6))-1));
                        calendar.set(Calendar.MONTH, Integer.parseInt(s.substring(4,6))-1);//因系統月份是0~11，因顯示問題+1月份，此處要-1回復原本系統月份
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(s.substring(6,8)));
                        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(s.substring(8,10)));
                        calendar.set(Calendar.MINUTE, Integer.parseInt(s.substring(10,12)));
                        calendar.set(Calendar.SECOND, 0);
                        return calendar;
                    }
                })
                .subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Calendar>() {
                    @Override
                    public void accept(@NonNull Calendar calendar) throws Exception {
                        //如果設定時間大於目前時間才設置alarmManager
                        if (calendar.compareTo(Calendar.getInstance()) == 1) {//設定日期比現在時間早:-1，相同:0，晚:1
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
                });
    }
    @Override
    //依照position取消廣播
    public void cancel(final int position, final Context context){
        Flowable.just(position)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        //取消該position的廣播
                        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent mIntent = new Intent();
                        mIntent.setClass(context, ItemBroadCastReceiver.class);
                        //透過setData來控制取消
                        mIntent.setData(Uri.parse(String.valueOf(position)));
                        mIntent.setAction("com.cgh.ItemBroadCastMessage");
                        PendingIntent pi = PendingIntent.getBroadcast(context, 0, mIntent, PendingIntent.FLAG_NO_CREATE);
                        //如果目前已有alarm就取消
                        if (pi != null) am.cancel(pi);
                    }
                });
    }
}
