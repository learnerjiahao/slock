package com.hungry.slock;


import android.annotation.SuppressLint;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

public class SLockService extends Service {

	private static String TAG = "ZdLockService";
	private Intent zdLockIntent = null ;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressLint("NewApi")
	public void onCreate(){
		super.onCreate();
		zdLockIntent = new Intent(SLockService.this , MainActivity.class);
		zdLockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Log.i("wwww", "----creat---");
		
		
		
		IntentFilter mScreenOnFilter = new IntentFilter("android.intent.action.SCREEN_ON");
		SLockService.this.registerReceiver(mScreenOnReceiver, mScreenOnFilter);
		
		IntentFilter mScreenOffFilter = new IntentFilter("android.intent.action.SCREEN_OFF");
		SLockService.this.registerReceiver(mScreenOffReceiver, mScreenOffFilter);
		
	}

	@SuppressLint("NewApi")
	public int onStartCommand(Intent intent , int flags , int startId){
		Log.i("wwww", "----startcom---");
		//return Service.START_STICKY;
		return super.onStartCommand(intent, flags, Service.START_STICKY);
		
	}
	
	public void onDestroy(){
		super.onDestroy();
		Log.i("wwww", "----desss---");
		SLockService.this.unregisterReceiver(mScreenOnReceiver);
		SLockService.this.unregisterReceiver(mScreenOffReceiver);
		
		startService(new Intent(SLockService.this, SLockService.class));
		Log.i("wwww", "----desss---");
	}
	
	private BroadcastReceiver mScreenOnReceiver = new BroadcastReceiver(){

		@Override
		public void onReceive(Context context , Intent intent) {
			
			if(intent.getAction().equals("android.intent.action.SCREEN_ON")){
				Log.i(TAG, "----------------- android.intent.action.SCREEN_ON------");
			}
		}
		
	};
	private BroadcastReceiver mScreenOffReceiver = new BroadcastReceiver(){
		@SuppressWarnings("deprecation")
		@Override
		public void onReceive(Context context , Intent intent) {
			String action = intent.getAction() ;
			
		    Log.i(TAG, intent.toString());
			if(action.equals("android.intent.action.SCREEN_OFF")){
				startActivity(zdLockIntent);
			}
		}
		
	};
	
}
