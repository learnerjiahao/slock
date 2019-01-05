package com.hungry.slock;



import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

//    private static final int EVENT_GET_CURRENT_NOS = 1;  
//    private static final String ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners";  
//    private static final String ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS";
//    private boolean isEnabledNLS = false;  
    private Context mContext = null ;
    private AnimationDrawable anim;
    private ImageView animationIV; 
    public static int MSG_LOCK_SUCESS = 1;
    private Handler initHandler = null;
    private Handler freshHandler = null;
    private TextView tv_weather;
    private TextView tv_date;
    private TextView tv_time;
    private TextView tv_am_pm;
    private TextView tv_call;
    private TextView tv_text;
    private TextView tv_clock;
//    private ImageView tv_qq;
//    private ImageView tv_weichat;
    private ListViewBaseAdapter baseAdapter;
    private ListView lv;
    private LinearLayout ll_showCal;
    private LinearLayout ll_addCal;
    private LinearLayout ll_tough_unlock;
    private ImageView iv_addCal;
    private EditText et_startTime;
    private EditText et_endTime;
    private TextView tv_con;
    private TextView tv_confirm;
    private EditText et_title;
	private TextView tv_Cal;
	private LinearLayout ll_5;
    
	
    
    
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = MainActivity.this;
		//屏蔽系统锁屏
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		
		/*设置全屏，无标题*/
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.main);
		
		animationIV = (ImageView)findViewById(R.id.anim_arrow);  
       
        animationIV.setBackgroundResource(R.anim.slider_tip_anim);  
        anim = (AnimationDrawable)animationIV.getBackground(); 
        
        tv_weather = ((TextView)findViewById(R.id.tv_weather));
        tv_date = ((TextView)findViewById(R.id.date));
		tv_time = ((TextView)findViewById(R.id.time));
		tv_am_pm = ((TextView)findViewById(R.id.am_pm));
		tv_call = ((TextView)findViewById(R.id.tvCalled));
		tv_text = ((TextView)findViewById(R.id.tvTexted));
		tv_clock = ((TextView)findViewById(R.id.tvClock));
//		tv_qq = ((ImageView)findViewById(R.id.tvQQ));
//		tv_weichat = ((ImageView)findViewById(R.id.tvWeiChat));
        lv = (ListView) findViewById(R.id.lv_calander);
        ll_addCal = (LinearLayout) findViewById(R.id.ll_addCal);
        ll_showCal = (LinearLayout) findViewById(R.id.ll_showCal);
        et_endTime = (EditText) findViewById(R.id.et_endtime);
        et_startTime = (EditText) findViewById(R.id.et_starttime);
        et_title = (EditText) findViewById(R.id.et_title);
        iv_addCal = (ImageView) findViewById(R.id.iv_addCal);
		tv_con = (TextView) findViewById(R.id.tv_con);
		tv_confirm = (TextView) findViewById(R.id.tv_confirm);
		ll_tough_unlock = (LinearLayout) findViewById(R.id.ll_tough_unlock);
		tv_Cal = (TextView) findViewById(R.id.tv_Cal);
		ll_5 = (LinearLayout) findViewById(R.id.ll_5);
		
		System.out.println(Config.getCachedTag(mContext));
		
		if(Config.getCachedTag(mContext)==1){
			Config.cacheTag(mContext, 0);
			findViewById(R.id.ll_1).setVisibility(View.GONE);
			findViewById(R.id.ll_2).setVisibility(View.GONE);
			findViewById(R.id.ll_3).setVisibility(View.GONE);
			findViewById(R.id.ll_4).setVisibility(View.GONE);
			RelativeLayout.LayoutParams lp =  (android.widget.RelativeLayout.LayoutParams) ll_5.getLayoutParams();
			lp.setMargins(10, 200, 10, 10);
			ll_5.setLayoutParams(lp);
		}else{
			Config.cacheTag(mContext, 1);
			findViewById(R.id.ll_1).setVisibility(View.VISIBLE);
			findViewById(R.id.ll_2).setVisibility(View.VISIBLE);
			findViewById(R.id.ll_3).setVisibility(View.VISIBLE);
			findViewById(R.id.ll_4).setVisibility(View.VISIBLE);
			RelativeLayout.LayoutParams lp =  (android.widget.RelativeLayout.LayoutParams) ll_5.getLayoutParams();
			lp.setMargins(10, 60, 10, 10);
			ll_5.setLayoutParams(lp);
		}
		
		findViewById(R.id.change).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Config.getCachedTag(mContext)==0){
					Config.cacheTag(mContext, 1);
					findViewById(R.id.ll_1).setVisibility(View.GONE);
					findViewById(R.id.ll_2).setVisibility(View.GONE);
					findViewById(R.id.ll_3).setVisibility(View.GONE);
					findViewById(R.id.ll_4).setVisibility(View.GONE);
					RelativeLayout.LayoutParams lp =  (android.widget.RelativeLayout.LayoutParams) ll_5.getLayoutParams();
					lp.setMargins(10, 200, 10, 10);
					ll_5.setLayoutParams(lp);
				}else{
					Config.cacheTag(mContext, 0);
					findViewById(R.id.ll_1).setVisibility(View.VISIBLE);
					findViewById(R.id.ll_2).setVisibility(View.VISIBLE);
					findViewById(R.id.ll_3).setVisibility(View.VISIBLE);
					findViewById(R.id.ll_4).setVisibility(View.VISIBLE);
					RelativeLayout.LayoutParams lp =  (android.widget.RelativeLayout.LayoutParams) ll_5.getLayoutParams();
					lp.setMargins(10, 60, 10, 10);
					ll_5.setLayoutParams(lp);
				}
				
			}
		});
		
		
		Config.cacheAccountInfo(mContext);
		baseAdapter = new ListViewBaseAdapter(mContext);
		baseAdapter.freshCal(GetValues.getCalanderInfo(mContext));
		lv.setAdapter(baseAdapter);
		
		initValues();
		setClickEvents();
		
		th_init.start();
		th_fresh.start();
		runInit.setFlag(true);
		runFresh.setFlag(true);
		
		initHandler = new Handler(){
			public void handleMessage(Message msg) {
				if(msg.arg1 == 1){
					//更新信息
					initValues();
				}
			}
		};
		
		freshHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				Bundle date = msg.getData();
				tv_weather.setText(date.getCharSequence("weatherInfo"));
				tv_date.setText(GetValues.getDate());
				tv_am_pm.setText(GetValues.am_pm());
				tv_clock.setText(GetValues.getClock(mContext));
				baseAdapter.freshCal(GetValues.getCalanderInfo(mContext));
			}
			
		};
		
		startService(new Intent(MainActivity.this, SLockService.class));
//		startService(new Intent(MainActivity.this, NotificationMonitor.class));
		
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		//设置动画
		anim.start();
		//开启run方法
		runInit.setFlag(true);
		runFresh.setFlag(true);
//		//判断权限
//		isEnabledNLS = isEnabled();  
//        if (!isEnabledNLS) {  
//        //如果没有开启则显示确认对话框  
//            showConfirmDialog();  
//        }  
	}
	@Override
	protected void onPause() {
		super.onPause();
		anim.stop();
		runInit.setFlag(false);
		runFresh.setFlag(false);
	}

	protected void onDestory(){
		super.onDestroy();
	}
	
	    //更新状态
		private void initValues(){	
			tv_time.setText(GetValues.getTime());
			tv_call.setText(GetValues.getMissCallCount(mContext)+"");
			tv_text.setText(GetValues.getSmsCount(mContext)+"");
//			if(getCurrentNotificationString().contains("com.tencent.mobileqq")){
//				tv_qq.setImageResource(R.drawable.qq2);
//			}else{
//				tv_qq.setImageResource(R.drawable.qq1);
//			}
//			if(getCurrentNotificationString().contains("com.tencent.mm")){
//				tv_weichat.setImageResource(R.drawable.wei2);
//			}else{
//				tv_weichat.setImageResource(R.drawable.wei1);
//			}
		}
		
	// 设置监听时间
	public void setClickEvents(){
		//unlock
		ll_tough_unlock.setOnTouchListener(new OnTouchListener() {
			private float xDown = 0, xMove = 0, xUp = 0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
				    xDown = event.getX();
					break;
				case MotionEvent.ACTION_MOVE:
					xMove = event.getX();
					break;
				case MotionEvent.ACTION_UP:
					xUp = event.getX();
					if(xUp - xDown >= 100){
						MainActivity.this.finish();
						virbate();
					}
					break;
				}
				return true;
			}
		});
		
		
		
		//设置查看未接来电的点击事件
		tv_call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			    Intent intent = new Intent("android.intent.action.DIAL");
				ComponentName comp = new ComponentName("com.android.contacts","com.android.contacts.DialtactsActivity");
				if(comp != null){
					   intent.setComponent(comp);
					   startActivity(intent);
				   }
			    

			}
		});
		//设置查看未读短信的点击事件
		tv_text.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				   Intent intent= new Intent( );
				    // 获得短信的包名
				   ComponentName comp = new ComponentName(Config.get0(mContext), Config.get1(mContext));
				   if(comp != null){
					   intent.setComponent(comp);
					   startActivity(intent);
				   }
				  
			}
		});
		 //设置日程的点击事件
		tv_Cal.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
                Intent intent = new Intent();
				ComponentName comp = new ComponentName("com.android.calendar", "com.android.calendar.LaunchActivity");
				if(comp != null){
					   intent.setComponent(comp);
					   startActivity(intent);
				   }
			}
		});
		//闹钟的点击事件
		tv_clock.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				ComponentName comp = new ComponentName(Config.get2(mContext), Config.get3(mContext));
				if(comp != null){
					   intent.setComponent(comp);
					   startActivity(intent);
				   }
			}
		});
		
//		tv_qq.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				PackageManager manager = getPackageManager();
//				Intent openQQ = manager.getLaunchIntentForPackage("com.tencent.mobileqq");
//				if(openQQ == null){
//					Toast.makeText(mContext, "请先安装QQ", Toast.LENGTH_SHORT).show();
//				}else{
//					startActivity(openQQ);
//				}
//				 
//			}
//		});
		
//		tv_weichat.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				PackageManager manager = getPackageManager();
//				Intent openWeiChat = manager.getLaunchIntentForPackage("com.tencent.mm");
//				if(openWeiChat == null){
//					Toast.makeText(mContext, "请先安装微信", Toast.LENGTH_SHORT).show();
//				}else{
//					startActivity(openWeiChat);
//				}
//			}
//		});
		
		//添加日程
		iv_addCal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ll_showCal.setVisibility(View.GONE);
				iv_addCal.setVisibility(View.GONE);
				ll_addCal.setVisibility(View.VISIBLE);
				tv_Cal.setText("新建活动");
				long time = (new Date()).getTime();
				String startTime = GetValues.parseTime(time+"");
				String endTime = GetValues.parseTime(time+1000*3600+"");
				et_title.setText("");
				et_startTime.setText(startTime);
				et_endTime.setText(endTime);
			}
		});
		
		tv_con.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv_addCal.setVisibility(View.VISIBLE);
				ll_showCal.setVisibility(View.VISIBLE);
				ll_addCal.setVisibility(View.GONE);
				tv_Cal.setText("日程");
			}
		});
		
		tv_confirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GetValues.addCalendar(mContext, et_startTime.getText().toString(), 
						et_endTime.getText().toString(), et_title.getText().toString());
				baseAdapter.freshCal(GetValues.getCalanderInfo(mContext));
				iv_addCal.setVisibility(View.VISIBLE);
				ll_showCal.setVisibility(View.VISIBLE);
				ll_addCal.setVisibility(View.GONE);
				tv_Cal.setText("日程");
			}
		});
		
		
		et_startTime.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
						MainActivity.this, et_endTime.getText().toString());
				dateTimePicKDialog.dateTimePicKDialog(et_startTime);

			}
		});

		et_endTime.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
						MainActivity.this, et_startTime.getText().toString());
				dateTimePicKDialog.dateTimePicKDialog(et_endTime);
			}
		});
		
		
	}
//	//屏蔽掉Home
//	@SuppressLint("NewApi")
//	public void onAttachedToWindow() {
//		this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//	    super.onAttachedToWindow();
//    }
	
	//屏蔽掉Back
	public boolean onKeyDown(int keyCode ,KeyEvent event){
		
		if(event.getKeyCode() == KeyEvent.KEYCODE_BACK)
			return true ;
		else
			return super.onKeyDown(keyCode, event);
		
	}
//	//判断是否开启权限
//	 private boolean isEnabled() {
//	        String pkgName = getPackageName();
//	        final String flat = Settings.Secure.getString(getContentResolver(),
//	                ENABLED_NOTIFICATION_LISTENERS);
//	        if (!TextUtils.isEmpty(flat)) {
//	            final String[] names = flat.split(":");
//	            for (int i = 0; i < names.length; i++) {
//	                final ComponentName cn = ComponentName.unflattenFromString(names[i]);
//	                if (cn != null) {
//	                    if (TextUtils.equals(pkgName, cn.getPackageName())) {
//	                        return true;
//	                    }
//	                }
//	            }
//	        }
//	        return false;
//	    }
	 
//     //获得通知栏消息
//	 private static String getCurrentNotificationString() {
//	        String listNos = "";
//	        StatusBarNotification[] currentNos = NotificationMonitor.abns;
//	        if (currentNos != null) {
//	            for (int i = 0; i < currentNos.length; i++) {
//	                listNos = currentNos[i].getPackageName() + "\n" + listNos;
//	                
//	            }
//	        }
//	        Log.i("hh", "currentNos"+currentNos+"/n"+listNos);
//	        return listNos;
//	    }
//	 //没开启权限则跳转到权限开启界面
//	 private void openNotificationAccess() {
//	        startActivity(new Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS));
//	    }
	 //提示框
//	 private void showConfirmDialog() {
//	        new AlertDialog.Builder(this)
//	                .setMessage("请求访问通知栏")
//	                .setTitle("权限请求")
//	                .setIconAttribute(android.R.attr.alertDialogIcon)
//	                .setCancelable(true)
//	                .setPositiveButton(android.R.string.ok,
//	                        new DialogInterface.OnClickListener() {
//	                            public void onClick(DialogInterface dialog, int id) {
//	                                openNotificationAccess();
//	                            }
//	                        })
//	                .setNegativeButton(android.R.string.cancel,
//	                        new DialogInterface.OnClickListener() {
//	                            public void onClick(DialogInterface dialog, int id) {
//	                                // do nothing
//	                            }
//	                        })
//	                .create().show();
//	    }
	 
	 //振动
	 private void virbate(){
			Vibrator vibrator = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
			vibrator.vibrate(200);
		}
	 
	 
	 private RunInit runInit = new RunInit();
     private Thread th_init = new Thread(runInit);
     
     private class RunInit implements Runnable{
    	private boolean flag = false;
    	public void setFlag(boolean flag){
    		this.flag = flag;
    	}
		@Override
		public void run() {
			while(true){
				if(flag){
					try {
						Message initMessage = new Message();
						initMessage.arg1 = 1;
						initHandler.sendMessage(initMessage);
						Thread.sleep(1000*2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		}
		
		
	}
     
     private RunFresh runFresh = new RunFresh();
     private Thread th_fresh = new Thread(runFresh);
     
     private class RunFresh implements Runnable{
    	private boolean flag = false;
    	public void setFlag(boolean flag){
    		this.flag = flag;
    	}
		@Override
		public void run() {
			while(true){
				if(flag){
					try {
						Message freshMessage = new Message();
						Bundle date = new Bundle();
						date.putCharSequence("weatherInfo", GetValues.getWeatherInfo(Config.WEATHER_URL, mContext));
						freshMessage.setData(date);
						freshHandler.sendMessage(freshMessage);
						Thread.sleep(1000*60*20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
			
		}
		
		
	}
}