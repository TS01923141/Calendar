package com.example.cgh.calendar.Presenter;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
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
    ICallDateTimePicker callDateTimePicker;
    IRealmController realmController;

    public onClickDialogEvent(IRealmController realmController, ICallDateTimePicker callDateTimePicker){
        this.realmController = realmController;
        this.callDateTimePicker = callDateTimePicker;
    }

    //取得item內的資料
    @Override
    public void init(String itemText, String dateTime){
        this.itemText = itemText;
        this.dateTime = dateTime;
    }
    @Override
    public void Click(final int position){////////////////////////////回傳值修改
        //初始化自訂Dialog介面與設定
        final View item = LayoutInflater.from(MainActivity.activity).inflate(R.layout.dialog_view,null);
        final EditText edit_itemText = item.findViewById(R.id.edit_itemText);
        final EditText edit_dateTime = item.findViewById(R.id.edit_dataTime);
        //final Button confirm_edit = item.findViewById(R.id.confirm_edit);
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
                edit_dateTime.setText(dateTime);
            }
        });
/*
        confirm_edit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //取得在AlertDialog修改後的值並新增/修改資料庫的值
                itemText = edit_itemText.getText().toString();
                dateTime = edit_dateTime.getText().toString();
                //if(position == -1)代表新增，其餘則為修改
                if (position == -1) {
                    int newPrimaryKey  = realmController.searchAll().last().getID()+1;
                    realmController.insertData(itemText, dateTime, newPrimaryKey);
                }else realmController.updateData(itemText, dateTime, position);
                Log.i("confirm_edit","confirm_edit");
            }
        });
*/
        //Dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.activity);
        alertDialogBuilder
                .setTitle("修改資料")
                .setView(item)
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //取得在AlertDialog修改後的值並新增/修改資料庫的值
                        itemText = edit_itemText.getText().toString();
                        dateTime = edit_dateTime.getText().toString();
                        //if(position == -1)代表新增，其餘則為修改
                        if (position == -1) {
                            int newPrimaryKey  = realmController.searchAll().last().getID()+1;
                            realmController.insertData(itemText, dateTime, newPrimaryKey);
                        }else realmController.updateData(itemText, dateTime, position);
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}



