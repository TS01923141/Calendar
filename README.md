# Calendar #
使用:
按下右下fab新增事件(item)
點擊item以修改
左右滑動刪除item
當到達事件設定時間時會寄出推播以通知

## 程式類別: ##
### Model ###
  DataSaveByRealm:Realm資料庫   
### Presenter ###
  AlarmController:控制AlarmManager，設定Alarm寄送廣播  
  BootBroadCast:接收Android開機廣播，寄資料給AlarmController  
  CallDateTimePicker:控制設定時間時的DatePicker與TimePicker  
  ItemAdapter:控制RecyclerView，新增修改刪除RecyclerView的Item並通知RealmController做資料庫處理  
  ItemBroadCastReceive:接收AlarmManager的廣播，並設定ItemNotification推播  
  ItemNotification:推播設定與處理  
  onClickDialogEvent:控制新增修改RecyclerView的Item的Dialog，通知RealmControoler做資料處理  
  RealmController:新增查詢刪除查詢Realm資料庫  
### View ###
  MainActivity:主要Activity  

## 程式大綱: ##
當手機開機時會呼叫broadcast設定alarmManager依資料庫(Realm)內容定時寄出廣播通知
當時間到達alarmManager設定時間時會寄出廣播通知，由另一個broadcast接收，此broadcast會設定並寄出notification

新增、修改、刪除事件皆透過Adapter控制Realm達成，當RecyclerView新增事件後，會馬上將事件加入alarmManager中
修改後，會先刪除該alarmManager再重新
