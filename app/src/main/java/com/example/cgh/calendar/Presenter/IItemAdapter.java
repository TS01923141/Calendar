package com.example.cgh.calendar.Presenter;

import com.example.cgh.calendar.View.IMainActivity;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/7.
 */

public interface IItemAdapter{
    void initItemAdapter(IMainActivity iMainActivity);

    //刷新資料內容
    void refreshItemAdapter();

    int getItemCount();
    //新增item
    void addItem(String itemText, String dateTime);
    //修改item
    void updateItem(String itemText, String dateTime, int position);

    //刪除item
    void removeItem(int index);
}