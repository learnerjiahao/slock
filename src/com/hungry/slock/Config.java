package com.hungry.slock;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.Log;


public class Config {

	public static final String APP_ID = "com.hungry.slock";
	public final static String WEATHER_URL ="http://api.map.baidu.com/telematics/v3/weather?location=北京" +
			"&output=json&ak=IkXW5TeBoveZ93NAaVqRNLPM" +
			"&mcode=E0:12:61:32:8E:93:6D:0F:5E:E5:28:95:CE:98:9A:9B:8A:4E:9C:37;com.hungry.slock";
	
	//获得系统应用包名
		public static String[] getPackName(Context context){
			PackageManager pm = context.getPackageManager(); //获得PackageManager对象
	        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
	        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
	        String[] strs = new String[6];
	        // 通过查询，获得所有ResolveInfo对象.
	        List<ResolveInfo> resolveInfos = pm
	                .queryIntentActivities(mainIntent, PackageManager.MATCH_DEFAULT_ONLY);
	            for (ResolveInfo reInfo : resolveInfos) {
	                String activityName = reInfo.activityInfo.name; // 获得该应用程序的启动Activity的name
	                String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
	                Log.i("www", "xxxx--"+activityName+"----"+pkgName);
	                if(activityName.contains(".mms")||pkgName.contains(".mms")){
	                	strs[0] = pkgName;
	                	strs[1] = activityName;
	                }
	                if((pkgName.contains("clock")||pkgName.contains("Clock"))
	                		&&(activityName.contains("Alarm")||activityName.contains("alarm")
	                				||activityName.contains("DeskClock")
	                				||activityName.contains("deskClock"))){
	                	strs[2] = pkgName;
	                	strs[3] = activityName;
	                }
	                if(activityName.contains("PeopleActivity")){
	                	strs[4] = pkgName;
	                	strs[5] = activityName;
	                }
	        }
	            return strs;
		}
	
	
	public static void cacheAccountInfo(Context context){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putString("0", getPackName(context)[0]);
		e.putString("1", getPackName(context)[1]);
		e.putString("2", getPackName(context)[2]);
		e.putString("3", getPackName(context)[3]);
		e.putString("4", getPackName(context)[4]);
		e.putString("5", getPackName(context)[5]);
		e.commit();
	}
	
	public static void cacheTag(Context context,int tag){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).edit();
		e.putInt("tag", tag);
		e.commit();
	}
	
	public static int getCachedTag(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getInt("tag",0);
	}
	
	public static String get0(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("0", null);
	}
	public static String get1(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("1", null);
	}
	public static String get2(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("2", null);
	}
	public static String get3(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("3", null);
	}
	public static String get4(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("4", null);
	}
	public static String get5(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE).getString("5", null);
	}
	
}
