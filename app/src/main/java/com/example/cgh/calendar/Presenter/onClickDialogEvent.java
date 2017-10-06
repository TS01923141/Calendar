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
import com.example.cgh.calendar.View.IMainActivity;
import com.example.cgh.calendar.View.MainActivity;

import java.util.Calendar;

/**
 * Created by cgh on 2017/9/6.
 */

public class onClickDialogEvent implements IonClickDialogEvent{
    String itemText = ""; String dateTime = "";
    IMainActivity iMainActivity;
    ICallDateTimePicker callDateTimePicker;
    IRealmController realmController;

    @Override
    public void initOnClickDialogEvent(IMainActivity iMainActivity){
        this.iMainActivity = iMainActivity;
        this.realmController = iMainActivity.getRealmController();
        this.callDateTimePicker = iMainActivity.getCallDateTimePicker();
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
        final EditText edit_date = item.findViewById(R.id.edit_data);
        final EditText edit_time = item.findViewById(R.id.edit_time);
        //editText帶入目前資料
        edit_itemText.setText(itemText);
        //將YYYYMMDDhhmm轉為 YYYY/MM/DD hh:mm
        //edit_date.setText(dateTime.substring(0,8));
        edit_date.setText(
                dateTime.substring(0,4) + "/" +
                dateTime.substring(4,6) + "/" +
                dateTime.substring(6,8)
        );
        //edit_time.setText(dateTime.substring(8));
        edit_time.setText(
                dateTime.substring(8,10) + ":" +
                dateTime.substring(10)
        );

        //點擊edit_date修改日期
        edit_date.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    callDateTimePicker.callDatePickerDialog(edit_date, dateTime);
                }
            }
        });
        //點擊edit_time修改時間
        edit_time.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus){
                if(hasFocus){
                    callDateTimePicker.callTimePickerDialog(edit_time, dateTime);
                }
            }
        });
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
                        dateTime = dateFormat2String(edit_date.getText().toString(), edit_time.getText().toString());
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
    //將edit的YYYY/MM/DD hh:mm 轉為 YYYYMMDDhhmm
    private String dateFormat2String(String date, String time){
        String dateTime = date.substring(0,4) +
                date.substring(5,7) +
                date.substring(8) +
                time.substring(0,2) +
                time.substring(3);
        return dateTime;
    }
}



