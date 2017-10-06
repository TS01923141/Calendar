package com.example.cgh.calendar.View;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.Presenter.CallDateTimePicker;
import com.example.cgh.calendar.Presenter.ICallDateTimePicker;
import com.example.cgh.calendar.Presenter.IItemAdapter;
import com.example.cgh.calendar.Presenter.IRealmController;
import com.example.cgh.calendar.Presenter.IonClickDialogEvent;
import com.example.cgh.calendar.Presenter.ItemAdapter;
import com.example.cgh.calendar.Presenter.RealmController;
import com.example.cgh.calendar.Presenter.onClickDialogEvent;
import com.example.cgh.calendar.R;

import java.util.Calendar;

import io.realm.Realm;
/*///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
要先解決Presenter互new與nullObject問題(new的時候Activity還沒建)

 */
public class MainActivity extends AppCompatActivity implements IMainActivity{
    private static Context context;
    public static Activity activity;

    private RecyclerView itemRecyclerView;
    IItemAdapter itemAdapter;
    IonClickDialogEvent onClickDialogEvent;
    IRealmController realmController;
    ICallDateTimePicker callDateTimePicker;
    //getPresenter，當其他Presenter可能需要呼叫其他Presenter時透過此方法
    @Override
    public Context getAppContext(){
        return MainActivity.context;
    }
    @Override
    public IItemAdapter getItemAdapter(){
        return this.itemAdapter;
    }
    @Override
    public IonClickDialogEvent getOnClickDialogEvent(){
        return this.onClickDialogEvent;
    }
    @Override
    public IRealmController getRealmController(){
        return this.realmController;
    }
    @Override
    public ICallDateTimePicker getCallDateTimePicker(){
        return this.callDateTimePicker;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.context = getApplicationContext();
        activity = MainActivity.this;
        Realm.init(this);

        //等super.onCreate結束之後才開始new Presenter，避免，避免null問題
        callDateTimePicker = new CallDateTimePicker();
        realmController = new RealmController();
        onClickDialogEvent = new onClickDialogEvent();
        itemAdapter = new ItemAdapter();
        //先new，當要用到時才用init(IMainActivity.getPresenter)的方式取得其他Presenter，避免無窮迴圈與nullObject情況

        //初始化RealmController
        realmController.initRealmController(this);
        //若資料庫大小為0，新增一筆資料避免錯誤
        if(realmController.searchAll().size()==0)  realmController.insertData("Welcome to use Calendar", callDateTimePicker.getCurrentTime(), 0);
        //初始化alertDialog
        onClickDialogEvent.initOnClickDialogEvent(this);
        //初始化RecyclerView
        itemAdapter.initItemAdapter(this);
        itemRecyclerView = (RecyclerView) findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        itemRecyclerView.setLayoutManager(layoutManager);
        itemRecyclerView.setAdapter((RecyclerView.Adapter) itemAdapter);

        //RecycleView滑動控制
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(itemAdapter.swipController());
        mItemTouchHelper.attachToRecyclerView(itemRecyclerView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                //按下fab跳出AlertDialog，設定完成後新增資料
                onClickDialogEvent.init("",callDateTimePicker.getCurrentTime());//清空itemText跟dateTime
                onClickDialogEvent.Click(-1);//-1代表新增

            }
        });
    }
}
