package com.hungry.slock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastToStart extends BroadcastReceiver {
    
	//开机自启动
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		    String action_boot="android.intent.action.BOOT_COMPLETED"; 
			if (intent.getAction().equals(action_boot)){
				Intent StartIntent=new Intent(context,SLockService.class); //接收到广播后，跳转到MainActivity
				StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(StartIntent);    
	      
	    }  
	}

	
}
