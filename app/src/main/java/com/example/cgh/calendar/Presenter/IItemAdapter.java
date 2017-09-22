package com.example.cgh.calendar.Presenter;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/7.
 */

public interface IItemAdapter{
    void ItemAdapter();
    int getItemCount();
    void addItem(String itemText, String dateTime);
    //刪除item
    void removeItem(int index);
}