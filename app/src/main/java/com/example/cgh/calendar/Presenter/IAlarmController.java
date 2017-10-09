package com.example.cgh.calendar.Presenter;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by cgh on 2017/10/6.
 */
//此處設定定時廣播或取消廣播
public interface IAlarmController {
    //依dateTime設定廣播時間，內容為itemText，position用來確定在資料庫位置，以便取消廣播
    void setAlarm(String itemText, int position, String dateTime, Context context);
    //依照position取消廣播
    void cancel(int position, Context context);
}
