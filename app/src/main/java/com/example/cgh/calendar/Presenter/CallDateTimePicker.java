package com.example.cgh.calendar.Presenter;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.cgh.calendar.View.MainActivity;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by cgh on 2017/9/6.
 */

public class CallDateTimePicker implements ICallDateTimePicker{
    /*
        callDate/TimePickerDialog
        傳入要修改的editText與日期/時間dateTime
        PickDialog日期/時間預設值為讀入dateTime的日期/時間
        PickDialog直接修改editText的值
     */
    @Override
    public void callDatePickerDialog(final EditText dateText, String dateTime){
        int year = Integer.parseInt(dateTime.substring(0,4));
        int month = Integer.parseInt(dateTime.substring(4,6));
        int day = Integer.parseInt(dateTime.substring(6,8));

        //Create a new instance of DatePickerDialog and return it
        new DatePickerDialog(MainActivity.activity, new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day){
                String date = String.valueOf(year) + "/" + timeFormat(month+1) + "/" + timeFormat(day);
                dateText.setText(date);
            }
        },year,month,day).show();
    }
    @Override
    public void callTimePickerDialog(final EditText timeText, String dateTime){
        int hour = Integer.parseInt(dateTime.substring(8,10));
        int minute = Integer.parseInt(dateTime.substring(10));

        //Create a new instance of TimePickerDialog and return it
        new TimePickerDialog(MainActivity.activity, new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                String time = timeFormat(hourOfDay) + ":" + timeFormat(minute);
                timeText.setText(time);
            }
        },hour,minute,true).show();
		/*
			第一個參數是 Context , 也就是說必須把 MainActivity 本身或者 Context 物件傳入。
			第二個參數是 OnTimeSetListener , 這邊是實作 OnTimeSetListener 這個介面的事件, 它提供使用者操控完時間介面後, 所傳回的時間。
			第三個是現在是幾點, 我們可以透過 Canlendar 的幫忙得到這個資訊。
			第四個是現在是幾分, 我們可以透過 Canlendar 的幫忙得到這個資訊。
			最後一個參數是boolean, true代表呈現24小時, false代表只顯示12小時。
		*/
    }

    //取得現在時間
    @Override
    public String getCurrentTime(){
        String dateTime;
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(System.currentTimeMillis());
        dateTime =
                String.valueOf(c.get(Calendar.YEAR)) +
                        timeFormat(c.get(Calendar.MONTH)+1) +
                        timeFormat(c.get(Calendar.DAY_OF_MONTH)) +
                        timeFormat(c.get(Calendar.HOUR_OF_DAY)) +
                        timeFormat(c.get(Calendar.MINUTE));
        return dateTime;
    }

    public String timeFormat(int time){
        return String.format("%02d", time);
    }
}
