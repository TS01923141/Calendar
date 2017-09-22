package com.example.cgh.calendar.Presenter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cgh.calendar.Model.DataSaveByRealm;
import com.example.cgh.calendar.R;
import com.example.cgh.calendar.View.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;

import io.realm.RealmResults;

/**
 * Created by cgh on 2017/9/7.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> implements IItemAdapter{
    private boolean onBind;
    private RealmResults<DataSaveByRealm> itemData;
    IRealmController realmController = new RealmController();
    IonClickDialogEvent onClickDialogEvent = new onClickDialogEvent();
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
    public void ItemAdapter(){
        itemData = realmController.searchAll();
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
        onBind = true;
        holder.itemText.setText(itemData.get(position).getItemText());
        holder.dateTime.setText(itemData.get(position).getDateTime());
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){//點擊修改事件
                //Dialog
                String edit_itemText = itemData.get(position).getItemText();
                String edit_dateTime = itemData.get(position).getDateTime();
                //呼叫Dialog更改值
                //onClickDialogEvent = new onClickDialogEvent();
                onClickDialogEvent.init(edit_itemText,edit_dateTime);
                //儲存更改後的值並改變資料庫的內容
                DataSaveByRealm returnString = onClickDialogEvent.Click();
                itemData.get(position).setItemText(returnString.getItemText());
                itemData.get(position).setDateTime(returnString.getDateTime());
                realmController.updateData(itemData.get(position).getItemText(), itemData.get(position).getDateTime(), position);
                //ItemAdapter();
                //notifyDataSetChanged();
            }
        });
        holder.itemText.setText(itemData.get(position).getItemText());
        holder.dateTime.setText(itemData.get(position).getDateTime());
        onBind = false;
    }
    //計算內容有幾個item
    @Override
    public int getItemCount(){
/*
        if(itemData==null)  Log.i("itemData.size", "null");
        else    Log.i("itemData.size", String.valueOf(itemData.size()));
*/
        //若資料庫內是空值，新增一筆資料避免錯誤
        if(itemData==null)  addItem("Welcome to use Calendar","000000000000");
        return itemData.size();
    }
    //新增item-新增資料進資料庫後刷新
    @Override
    public void addItem(String itemText, String dateTime){
        int newPrimaryKey;
        Log.i("searchAll()", String.valueOf(realmController.searchAll()));
        if(realmController.searchAll().isEmpty()) newPrimaryKey = 0;
        else newPrimaryKey  = realmController.searchAll().last().getID()+1;
        realmController.insertData(itemText, dateTime, newPrimaryKey);
        ItemAdapter();
        //notifyItemChanged(newPrimaryKey);
        //notifyDataSetChanged();
    }
    @Override
    //刪除item
    public void removeItem(int index){
        itemData.remove(index);
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