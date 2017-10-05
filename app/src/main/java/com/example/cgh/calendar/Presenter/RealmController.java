package com.example.cgh.calendar.Presenter;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.View.IMainActivity;
import com.example.cgh.calendar.View.MainActivity;

import java.util.Calendar;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/5.
 */

public class RealmController implements IRealmController{
    Realm mRealm = Realm.getDefaultInstance();
    IMainActivity iMainActivity;
    IItemAdapter itemAdapter;

    @Override
    public void initRealmController(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
        this.itemAdapter = iMainActivity.getItemAdapter();
    }



    //新增資料
    @Override
    public void insertData(final String itemText, final String dateTime, final int newPrimaryKey){
        int newPrimaryKeyValue;
        /*
        Log.i("searchAll()", String.valueOf(searchAll()));
        if(searchAll().isEmpty()) newPrimaryKeyValue = 0;
        else newPrimaryKeyValue = searchAll().last().getID()+1;
        final int newPrimaryKey = newPrimaryKeyValue;
        */
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                DataSaveByRealm dataSaveByRealm = realm.createObject(DataSaveByRealm.class,newPrimaryKey);
                dataSaveByRealm.setItemText(itemText);
                dataSaveByRealm.setDateTime(dateTime);
            }
        });
        itemAdapter.refreshItemAdapter();
        Log.i("RealmController.insertData_newPrimaryKey", String.valueOf(newPrimaryKey));
        Log.i("itemText",itemText);
        Log.i("dateTime",dateTime);
    }
    //修改資料
    @Override
    public void updateData(final String itemText, final String dateTime, final int position){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                /*
                DataSaveByRealm updateRealmObj = new DataSaveByRealm();
                updateRealmObj.setID(position);
                updateRealmObj.setItemText(itemText);
                updateRealmObj.setDateTime(dataTime);
                realm.copyFromRealm(updateRealmObj);
                */
                DataSaveByRealm updateRealmObj = mRealm.where(DataSaveByRealm.class).equalTo("ID",position).findFirst();
                updateRealmObj.setItemText(itemText);
                updateRealmObj.setDateTime(dateTime);
            }
        });
        itemAdapter.refreshItemAdapter();
        Log.i("RealmController.insertData_newPrimaryKey", String.valueOf(position));
        Log.i("itemText",itemText);
        Log.i("dateTime",dateTime);
    }
    //刪除資料
    @Override
    public void deleteDate(final int position){
        mRealm.executeTransaction(new Realm.Transaction(){
            @Override
            public void execute(Realm realm){
                RealmResults<DataSaveByRealm> dataSaveByRealm = mRealm.where(DataSaveByRealm.class).findAll();
                dataSaveByRealm.deleteFromRealm(position);
            }
        });
        itemAdapter.refreshItemAdapter();
    }
    //查詢資料
    @Override
    public RealmResults<DataSaveByRealm> searchAll(){
        return mRealm.where(DataSaveByRealm.class).findAll();
    }
    @Override
    public RealmResults<DataSaveByRealm> searchData(String searchType, String searchEqual){
        return mRealm.where(DataSaveByRealm.class).equalTo(searchType, searchEqual).findAll();
    }
}
