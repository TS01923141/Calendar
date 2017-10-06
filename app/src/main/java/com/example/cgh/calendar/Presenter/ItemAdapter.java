package com.example.cgh.calendar.Presenter;

import android.content.ClipData;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.R;
import com.example.cgh.calendar.View.IMainActivity;
import com.example.cgh.calendar.View.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/7.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements IItemAdapter{
    private RealmResults<DataSaveByRealm> itemData;
    IMainActivity iMainActivity;
    IRealmController realmController;
    IonClickDialogEvent onClickDialogEvent;
    IAlarmController alarmController;

    @Override
    public void initItemAdapter(IMainActivity iMainActivity) {
        this.iMainActivity = iMainActivity;
        this.onClickDialogEvent = iMainActivity.getOnClickDialogEvent();
        this.realmController = iMainActivity.getRealmController();
        this.alarmController = iMainActivity.getAlarmController();
        itemData = realmController.searchAll();
        Log.i("itemData.size()", String.valueOf(itemData.size()));///////////////////////印出看size是什麼 IF(0 or null)給資料庫預設資料(歡迎使用行事曆)再回傳size else直接回傳size

    }

    //初始化及載入元件
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView itemText;
        public TextView dateTime;
        public ViewHolder(View v){
            super(v);
            itemText = (TextView) v.findViewById(R.id.recyclerView_itemText);
            dateTime = (TextView) v.findViewById(R.id.recyclerView_dataTime);
        }
    }
    //載入資料內容
    @Override
    public void refreshItemAdapter(){
        itemData = realmController.searchAll();
        notifyDataSetChanged();
        Log.i("itemData.size()", String.valueOf(itemData.size()));///////////////////////印出看size是什麼 IF(0 or null)給資料庫預設資料(歡迎使用行事曆)再回傳size else直接回傳size
    }
    //載入ListLayout
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_listview, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    //連結ListLayout的元件並作處理
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        //在此設定notification
        alarmController.setAlarm(itemData.get(position).getItemText(), position, itemData.get(position).getDateTime(), iMainActivity.getAppContext());
        //將YYYYMMDDhhmm格式轉為YYYY/MM/DD hh:mm
        String viewDateTime = itemData.get(position).getDateTime().substring(0,4) + "/" +
                itemData.get(position).getDateTime().substring(4,6) + "/" +
                itemData.get(position).getDateTime().substring(6,8) + " " +
                itemData.get(position).getDateTime().substring(8,10) + ":" +
                itemData.get(position).getDateTime().substring(10);

        holder.itemText.setText(itemData.get(position).getItemText());
        holder.dateTime.setText(viewDateTime);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//點擊修改事件
                //Dialog
                String edit_itemText = itemData.get(position).getItemText();
                String edit_dateTime = itemData.get(position).getDateTime();
                //呼叫Dialog更改值
                onClickDialogEvent.init(edit_itemText,edit_dateTime);
                //儲存更改後的值並改變資料庫的內容
                onClickDialogEvent.Click(position);
                refreshItemAdapter();
                notifyDataSetChanged();
                //修改後取消原本notification，再重新掛載修改後的資料
                alarmController.cancel(position, iMainActivity.getAppContext());
                alarmController.setAlarm(itemData.get(position).getItemText(), position, itemData.get(position).getDateTime(), iMainActivity.getAppContext());
            }
        });
    }
    //計算內容有幾個item
    @Override
    public int getItemCount(){
        return itemData.size();
    }
    //新增item-新增資料進資料庫後刷新
    @Override
    public void addItem(String itemText, String dateTime){
        int newPrimaryKey;
        Log.i("searchAll()", String.valueOf(realmController.searchAll()));
        if(realmController.searchAll().isEmpty()) newPrimaryKey = 0;//////////////////////////////宣告時newPremaryKey直接設0就好，因為只有資料庫無資料才會呼叫
        else newPrimaryKey  = realmController.searchAll().last().getID()+1;
        realmController.insertData(itemText, dateTime, newPrimaryKey);
    }
    @Override
    public void updateItem(String itemText, String dateTime, int position){
        realmController.updateData(itemText, dateTime, position);
    }
    @Override
    //刪除item
    public void removeItem(int index){
        realmController.deleteDate(index);
    }

    @Override
    //RecyclerView滑動刪除與item上下位移
    public ItemTouchHelper.Callback swipController(){
         ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END){
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int formPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();
                notifyItemMoved(formPosition, toPosition);
                //Collections.swap(itemData,formPosition,toPosition);
                return true;
            }

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                removeItem(position);
                notifyItemRemoved(position);
            }
        };
        return mCallback;
    }
}
/*
    //RecyclerView滑動刪除與item上下位移
    ItemTouchHelper.Callback mCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.START|ItemTouchHelper.END){
        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecycleView.ViewHolder target){
            int formPosition = viewHolder.getAdapterPosition();
            int toPosition = target.getAdapterPosition();
            ItemAdapter.notifyItemMoved(fromPosition, toPosition);
            return true;
        }
        @Override
        public int getMoveMentFlages(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder){
            return makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }
        @Override
        public 	void onSwiped(RecycleView.ViewHolder viewHolder, int direction){
            int position = viewHolder.getAdapterPosition();
            itemData.remove(position);
            ItemAdapter.notifyItemRemoved(position);
        }
        ItemTouchHelper mItemTouchHelper = new ItemTouchHelper(mCallback);
mItemTouchHelper.attachToRecyclerView(MainActivity.itemRecycleView);
    }
*/
