package com.example.cgh.calendar.Presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.example.cgh.calendar.View.MainActivity;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by cgh on 2017/9/6.
 */

public class CallDateTimePicker implements ICallDateTimePicker{
    //呼叫時間、日期選擇器，將時間日期轉為String(yyyymmddhhmm)後回傳
    //****************************************************************************須設定Calendar預設值
    Context context = MainActivity.getAppContext();
    @Override
    public String callDateTimePickerDialog(){
        //Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();

        c.setTimeInMillis(System.currentTimeMillis());
        c.setTimeZone(TimeZone.getTimeZone("GMT+8"));//////////////////////////////////////////////////////////////

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        final Calendar dateTimeCalendar = Calendar.getInstance();
        //Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                dateTimeCalendar.set(Calendar.YEAR, year);
                dateTimeCalendar.set(Calendar.MONTH, month);
                dateTimeCalendar.set(Calendar.DAY_OF_MONTH, day);
/*
                String date = String.valueOf(year)+ String.valueOf(month)+ String.valueOf(day);
                dateTime = date;
*/
            }
        },year,month,day).show();
        //Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                dateTimeCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                dateTimeCalendar.set(Calendar.MINUTE, minute);
/*
                String time = String.valueOf(hourOfDay) + String.valueOf(minute);
                dateTime = dateTime + time;
*/
            }
        },hour,minute,true).show();
		/*
			第一個參數是 Context , 也就是說必須把 MainActivity 本身或者 Context 物件傳入。
			第二個參數是 OnTimeSetListener , 這邊是實作 OnTimeSetListener 這個介面的事件, 它提供使用者操控完時間介面後, 所傳回的時間。
			第三個是現在是幾點, 我們可以透過 Canlendar 的幫忙得到這個資訊。
			第四個是現在是幾分, 我們可以透過 Canlendar 的幫忙得到這個資訊。
			最後一個參數是boolean, true代表呈現24小時, false代表只顯示12小時。
		*/
        //日期時間轉換為String YYYYMMDDhhmm
        String dateTime = String.valueOf(dateTimeCalendar.get(Calendar.YEAR) + dateTimeCalendar.get(Calendar.MONTH) + dateTimeCalendar.get(Calendar.DAY_OF_MONTH) +
                dateTimeCalendar.get(Calendar.HOUR_OF_DAY) + dateTimeCalendar.get(Calendar.MINUTE));
        return dateTime;
    }
}
