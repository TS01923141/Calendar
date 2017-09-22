package com.example.cgh.calendar.Presenter;

import com.example.cgh.calendar.Model.DataSaveByRealm;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public interface IonClickDialogEvent{
    //取得item內的資料
    void init(String itemText, String dateTime);

    DataSaveByRealm Click();
}
