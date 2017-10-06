package com.example.cgh.calendar.Presenter;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;

/**
 * Created by cgh on 2017/9/6.
 */

public interface IItemNotification{

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    void setNotification(String itemText, Context context);

}