package com.example.cgh.calendar.View;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.Presenter.ICallDateTimePicker;
import com.example.cgh.calendar.Presenter.IItemAdapter;
import com.example.cgh.calendar.Presenter.IonClickDialogEvent;
import com.example.cgh.calendar.Presenter.ItemAdapter;
import com.example.cgh.calendar.Presenter.TestOnClickDialogEvent;
import com.example.cgh.calendar.Presenter.onClickDialogEvent;
import com.example.cgh.calendar.R;

import java.util.Calendar;

import io.realm.Realm;

public class MainActivity extends AppCompatActivity{
    private static Context context;

    private TextView dateTextext;
    private Button dateButton;
    private RecyclerView itemRecyclerView;
    private IItemAdapter itemAdapter;
    ICallDateTimePicker callDateTimePicker;
    IonClickDialogEvent onClickDialogEvent;
    public static Context getAppContext(){
        return MainActivity.context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        //MainActivity.context = this;
        Realm.init(this);

        //初始化RecyclerView
        itemAdapter = new ItemAdapter();
        itemRecyclerView = (RecyclerView) findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setAdapter((RecyclerView.Adapter) itemAdapter);
        //onCreate之後才初始化alertDialog，避免null問題
        onClickDialogEvent = new onClickDialogEvent();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //按下fab跳出AlertDialog，設定完成後新增資料
                DataSaveByRealm returnString = onClickDialogEvent.Click();
                String itemText = returnString.getItemText();
                String dateTime = returnString.getDateTime();
                itemAdapter.addItem(itemText, dateTime);

            }
        });
    }
}
