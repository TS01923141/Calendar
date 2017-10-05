package com.example.cgh.calendar.Presenter;

import com.example.cgh.calendar.Model.DataSaveByRealm;

import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/5.
 */

public interface IRealmController{
    //新增資料
    void insertData(String itemText, String dateTime, int newPosition);

    //修改資料
    void updateData(String itemText, String dateTime, int position);

    //刪除資料
    void deleteDate(int position);

    //查詢全部資料
    RealmResults<DataSaveByRealm> searchAll();

    //查詢單筆資料
    RealmResults<DataSaveByRealm> searchData(String searchType, String searchEqual);
}
