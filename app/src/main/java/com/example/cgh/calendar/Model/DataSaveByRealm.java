package com.example.cgh.calendar.Model;

import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by cgh on 2017/9/5.
 */

public class DataSaveByRealm extends RealmObject {
    //Realm資料庫
    private String itemText;
    private String dateTime;
    @PrimaryKey
    private int ID;

    public String getItemText(){
        return itemText;
    }
    public void setItemText(String itemText){
        this.itemText = itemText;
    }

    public String getDateTime(){
        return dateTime;
    }
    public void setDateTime(String dateTime){
        this.dateTime = dateTime;
    }

    public int getID(){
        return ID;
    }
    public void setID(int ID){
        this.ID = ID;
    }



}
