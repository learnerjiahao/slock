package com.hungry.slock;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;

    public class NotificationMonitor extends NotificationListenerService {  
        private static final String TAG = "SevenNLS";  
        private static final String TAG_PRE = "[" + NotificationMonitor.class.getSimpleName() + "] ";  
        private static final int EVENT_UPDATE_CURRENT_NOS = 0;  
        public static final String ACTION_NLS_CONTROL = "com.hungry.slock.NLSCONTROL";  
        //用于存储当前所有的Notification的StatusBarNotification对象数组  
        public static List<StatusBarNotification[]> mCurrentNotifications = new ArrayList<StatusBarNotification[]>();  
        public static int mCurrentNotificationsCounts = 0;
        public static StatusBarNotification[] abns ;
        //收到新通知后将通知的StatusBarNotification对象赋值给mPostedNotification  
        public static StatusBarNotification mPostedNotification;  
        // String a;  
        private Handler mMonitorHandler = new Handler() {  
            @Override  
            public void handleMessage(Message msg) {  
                switch (msg.what) {  
                    case EVENT_UPDATE_CURRENT_NOS:  
                        updateCurrentNotifications();  
                        break;  
                    default:  
                        break;  
                }  
            }  
        };  
      
        @Override  
        public void onCreate() {  
            super.onCreate();  
        //在onCreate时第一次调用getActiveNotifications()  
            mMonitorHandler.sendMessage(mMonitorHandler.obtainMessage(EVENT_UPDATE_CURRENT_NOS));  
        } 
      
        @Override  
        public void onDestroy() {  
            super.onDestroy();  
        }  
      
        @Override  
        public IBinder onBind(Intent intent) {  
            return super.onBind(intent);  
        }  
      
        @Override  
        public void onNotificationPosted(StatusBarNotification sbn) {  
            //当系统收到新的通知后，更新mCurrentNotifications列表  
            updateCurrentNotifications();  
            mPostedNotification = sbn;
        }  
      
        @Override  
        public void onNotificationRemoved(StatusBarNotification sbn) {  
            //当有通知被删除后，更新mCurrentNotifications列表  
            updateCurrentNotifications(); 
        }  
      
        private void updateCurrentNotifications() {  
            try {  
                StatusBarNotification[] activeNos = getActiveNotifications();
                abns = activeNos;
                if (mCurrentNotifications.size() == 0) {  
                    mCurrentNotifications.add(null);  
                }  
                mCurrentNotifications.set(0, activeNos);  
                mCurrentNotificationsCounts = activeNos.length;  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
    }  