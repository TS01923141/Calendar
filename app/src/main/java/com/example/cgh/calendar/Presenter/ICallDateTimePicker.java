package com.example.cgh.calendar.Presenter;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public interface ICallDateTimePicker{
    //呼叫時間、日期選擇器，將時間日期轉為String(yyyymmddhhmm)後回傳
    //****************************************************************************須設定Calendar預設值
    String callDateTimePickerDialog();
    //回傳目前時間
    String getCurrentTime();
}
