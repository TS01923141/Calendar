package com.example.cgh.calendar.View;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.example.cgh.calendar.Presenter.IAlarmController;
import com.example.cgh.calendar.Presenter.ICallDateTimePicker;
import com.example.cgh.calendar.Presenter.IItemAdapter;
import com.example.cgh.calendar.Presenter.IRealmController;
import com.example.cgh.calendar.Presenter.IonClickDialogEvent;

/**
 * Created by cgh on 2017/10/5.
 */

public interface IMainActivity {
    //getPresenter，當其他Presenter可能需要呼叫其他Presenter時透過此方法
    Context getAppContext();

    IItemAdapter getItemAdapter();

    IonClickDialogEvent getOnClickDialogEvent();

    IRealmController getRealmController();

    ICallDateTimePicker getCallDateTimePicker();

    IAlarmController getAlarmController();
}
