package com.example.cgh.calendar.Presenter;

import android.content.Context;

import java.util.Calendar;

/**
 * Created by cgh on 2017/10/6.
 */

public interface IAlarmController {
    void setAlarm(String itemText, int position, String dateTime, Context context);

    void cancel(int position, Context context);
}
