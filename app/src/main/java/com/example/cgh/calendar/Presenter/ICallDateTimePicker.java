package com.example.cgh.calendar.Presenter;

import android.widget.EditText;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public interface ICallDateTimePicker{
    //取得日期
    void callDatePickerDialog(EditText dateText, String dateTime);
    //取得時間
    void callTimePickerDialog(EditText timeText, String dateTime);

    //回傳目前時間
    String getCurrentTime();
}
