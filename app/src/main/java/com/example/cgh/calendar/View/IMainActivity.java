package com.example.cgh.calendar.View;

import android.content.Context;

import com.example.cgh.calendar.Presenter.ICallDateTimePicker;
import com.example.cgh.calendar.Presenter.IItemAdapter;
import com.example.cgh.calendar.Presenter.IRealmController;
import com.example.cgh.calendar.Presenter.IonClickDialogEvent;

/**
 * Created by cgh on 2017/10/5.
 */

public interface IMainActivity {
    Context getAppContext();

    IItemAdapter getItemAdapter();

    IonClickDialogEvent getOnClickDialogEvent();

    IRealmController getRealmController();

    ICallDateTimePicker getCallDateTimePicker();
}
