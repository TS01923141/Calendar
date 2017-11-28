# Calendar #
## 使用 ## 
按下右下fab新增事件(item)  
點擊item以修改事件與時間  
左右滑動item刪除事件  
當到達事件設定時間時會寄出推播以通知使用者  

## 架構圖 ##
![image](https://raw.githubusercontent.com/TS01923141/Calendar/master/Overview.jpg)
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

## 程式運作: ##
程式分為兩部分:開機時設定推播與一般程式使用時

##### 手機開機處理 #####

當手機開機時會呼叫BootBroadCast設定AlarmController讀取資料庫(Realm)內容定時寄出廣播通知 當時間到達AlarmController設定時間時會寄出廣播通知，由ItemBroadCastReceive接收，此broadcast會設定ItemNotification以寄出推播

##### 一般使用 #####

當程式開啟後，待MainActivity執行完onCreate，建立每個Presenter的實例，在建立RealmController的實例後，馬上透過查詢檢查Realm內有無資料，若沒有就新增一筆避免之後ItemAdapter報錯。

建立完實例後初始化(init)各個Presenter，透過MainActivity的getPresenter方式讓各個Presenter可以互相調用。

###### 新增 ######
當點擊fab後，MainActivity會呼叫onClickDialogEvent建立Dialog，當Dialog需要作時間調整時會呼叫CallDateTimePicker操作DatePicker、TimePicker設定時間，當設定完成按下確定後，會呼叫ItemAdapter新增Item並透過ItemAdapter呼叫RealmController新增資料至Realm、呼叫AlarmController新增alarm
###### 修改 ######
當點擊Item後，ItemAdapter會帶入該筆資料呼叫onClickDialogEvent建立Dialog，當Dialog需要作時間調整時會呼叫CallDateTimePicker操作DatePicker、TimePicker設定時間，當設定完成按下確定後，會呼叫ItemAdapter修改Item並透過ItemAdapter呼叫RealmController修改資料至Realm、呼叫AlarmController刪除該筆資料目前alarm後新增alarm
###### 刪除 ######
當滑動Item至視窗外後，ItemAdapter會透過swipController的onSwiped呼叫AlarmController刪除alarm，呼叫RealmController刪除Item資料並刷新資料

