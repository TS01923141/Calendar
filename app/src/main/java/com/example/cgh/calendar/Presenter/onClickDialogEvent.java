package com.example.cgh.calendar.Presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.R;
import com.example.cgh.calendar.View.MainActivity;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public class onClickDialogEvent implements IonClickDialogEvent{
    String itemText = ""; String dateTime = "";
    ICallDateTimePicker callDateTimePicker = new CallDateTimePicker();
    Context context = MainActivity.getAppContext();
    //取得item內的資料
    @Override
    public void init(String itemText, String dateTime){
        this.itemText = itemText;
        this.dateTime = dateTime;
    }
    @Override
    public DataSaveByRealm Click(){////////////////////////////回傳值修改
        final DataSaveByRealm returnString = new DataSaveByRealm();//回傳itemText跟dateTime用
        //初始化自訂Dialog介面與設定
        View item = LayoutInflater.from(context).inflate(R.layout.dialog_view,null);
        EditText edit_itemText = item.findViewById(R.id.edit_itemText);
        EditText edit_dateTime = item.findViewById(R.id.edit_dataTime);
        //editText帶入目前資料
        edit_itemText.setText(itemText);
        edit_dateTime.setText(dateTime);
        //edit_dateTime.setText(dateTime.get(Calendar.YEAR)+dateTime.get(Calendar.MONTH)+
        //        dateTime.get(Calendar.DAY_OF_MONTH)+dateTime.get(Calendar.HOUR_OF_DAY)+dateTime.get(Calendar.MINUTE));
        //點擊edit_dateTime跳出日期時間選單
        /*
        edit_dateTime.setOnClickListener(new View.OnClickListener(){
            dateTime = callDateTimePicker.callDateTimePickerDialog();
        });*/
        edit_dateTime.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    dateTime = callDateTimePicker.callDateTimePickerDialog();
                }
            }
        });
        /*
        //Dialog
        new AlertDialog.Builder(context)
                .setTitle("修改資料")
                .setView(item)
                .setPositiveButton("確定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        returnString.setID(-1);
                        returnString.setItemText(itemText);
                        returnString.setDateTime(dateTime);
                    }
                })
                .show();
        */

        //Dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
        alertDialogBuilder
                .setTitle("修改資料")
                .setView(item)
                .setPositiveButton("確定", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        returnString.setID(-1);
                        returnString.setItemText(itemText);
                        returnString.setDateTime(dateTime);
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        /*避免token null錯誤
        * Window token null exception, when trying to show alert dialog from
        * service class.use alertDialog.getWindow() for getting window and
        * add permission in manifest
        */
        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        alertDialog.show();
        return returnString;

    }
}



