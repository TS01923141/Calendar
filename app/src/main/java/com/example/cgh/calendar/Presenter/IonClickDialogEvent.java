package com.example.cgh.calendar.Presenter;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.View.IMainActivity;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public interface IonClickDialogEvent{
    void initOnClickDialogEvent(IMainActivity iMainActivity);

    //取得item內的資料
    void init(String itemText, String dateTime);

    void Click(int position);
}
