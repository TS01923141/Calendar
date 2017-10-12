package com.example.cgh.calendar.Presenter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.example.cgh.calendar.Model.DataSaveByRealm;

import org.reactivestreams.Publisher;

import java.util.Calendar;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/6.
 */

public class BootBroadCast extends BroadcastReceiver {
    IRealmController realmController = new RealmController();
    IAlarmController alarmController = new AlarmController();
    @Override
    public void onReceive(Context context, Intent intent){
        //接收Android開機完成的廣播通知，並呼叫alarmController設定廣播時間
        if(Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())){
            RealmResults<DataSaveByRealm> itemData = realmController.searchAll();
            int i=0;
            //取得所有Realm中資料，設定時間並寄出itemText
            for(i=0; i<itemData.size(); i++) {
                DataSaveByRealm sendData = itemData.get(i);///////////////////需修改get(0)為最小日期的數字 or 設成for全部丟進alarmManager
                //取得所有notifcation的時間
                alarmController.setAlarm(sendData.getItemText(), i, sendData.getDateTime(), context);
            }
            /*
            Flowable.just(itemData)
                    .flatMap(new Function<RealmResults<DataSaveByRealm>, Publisher<DataSaveByRealm>>() {
                        @Override
                        public Publisher<DataSaveByRealm> apply(@NonNull RealmResults<DataSaveByRealm> dataSaveByRealms) throws Exception {
                            return Flowable.fromIterable(dataSaveByRealms);
                        }
                    })
                    .subscribe(new Consumer<DataSaveByRealm>() {
                        @Override
                        public void accept(@NonNull DataSaveByRealm dataSaveByRealm) throws Exception {
                            Log.i("RxJavaX", String.valueOf(dataSaveByRealm.getID()));
                        }
                    });
            */
        }
        realmController.closeRealm();
    }
}
