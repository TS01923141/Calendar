package com.example.cgh.calendar.Presenter;

import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.cgh.calendar.View.IMainActivity;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/7.
 */
//此處控制RecyclerView
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
    //RecyclerView滑動刪除與item上下位移
    ItemTouchHelper.Callback swipController();
}