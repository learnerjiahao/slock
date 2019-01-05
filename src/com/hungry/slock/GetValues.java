package com.hungry.slock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.provider.CalendarContract.Reminders;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class GetValues {

	/** 
    * 根据给定的url地址访问网络，得到响应内容(这里为GET方式访问) 
    * @param url 指定的url地址 
    * @return 
    */  
	public static String getWeatherInfo(String url,Context context) {  
        //创建一个http请求对象  
        HttpGet request = new HttpGet(url);  
        //创建HttpParams以用来设置HTTP参数  
        HttpParams params=new BasicHttpParams();  
        //设置连接超时或响应超时  
        HttpConnectionParams.setConnectionTimeout(params, 3000);  
        HttpConnectionParams.setSoTimeout(params, 5000);  
        //创建一个网络访问处理对象  
        HttpClient httpClient = new DefaultHttpClient(params);  
        String resString = null;
        try{ 
            //执行请求参数项  
            HttpResponse response = httpClient.execute(request);
            String content = EntityUtils.toString(response.getEntity()); 
            //判断是否请求成功  
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                //获得响应信息  
                //String content = EntityUtils.toString(response.getEntity()); 
                try {
        			JSONObject obj = new JSONObject(content);
        			Log.i("eeee", obj.toString());
        			JSONArray jsonArray = obj.getJSONArray("results");
        			JSONObject resJSONObject = jsonArray.getJSONObject(0);
        			JSONArray resultArray = resJSONObject.getJSONArray("weather_data");
        			JSONObject resultJSON = resultArray.getJSONObject(0);
        			resString = resultJSON.getString("weather") + "\n" + resultJSON.getString("temperature");
        		} catch (JSONException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        			Log.i("eeee", e.toString());
        		}
            } else {  
                //网连接失败，使用Toast显示提示信息  
                Toast.makeText(context, "网络访问失败，请检查您机器的联网设备!", Toast.LENGTH_LONG).show();  
            }  
              
        }catch(Exception e) {  
            e.printStackTrace();
            Log.i("hhhh", e.toString());
        } finally {  
            //释放网络连接资源  
            httpClient.getConnectionManager().shutdown();  
        }
        if(resString == null) resString = "未连接网络";
        return resString;
    }
	//获得日期
	public static String getDate(){
		String date = null;
		SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
		date = adf.format(new Date());
		return date;
	}
	//获得时间
	public static String getTime(){
		String time = null;
		SimpleDateFormat adf = new SimpleDateFormat("hh:mm");
		time = adf.format(new Date());
		return time;
	}
	//判断上下午
	public static String am_pm(){
		String hour = new SimpleDateFormat("HH").format(new Date());
		int int_hour = Integer.parseInt(hour);
		if(int_hour <= 12) {
			return "上午";
		}else{
			return "下午";
		}
	}
	
	//获得未读短信和彩信的数量
	public static int getSmsCount(Context context){
		int smsCount = 0;
		 Cursor csr = context.getContentResolver().query(Uri.parse("content://sms"), null,  
		            "type = 1 and read = 0", null, null);  
		    if (csr != null) {  
		        smsCount = csr.getCount();  
		        csr.close();  
		    }
		    csr = context.getContentResolver().query(Uri.parse("content://mms/inbox"),  
		            null, "read = 0", null, null);  
		    if (csr != null) {  
		        smsCount += csr.getCount();  
		        csr.close();  
		    }  
		return smsCount;
	}
	//获得未接来电的数量
	public static int getMissCallCount(Context context){
		int MissCallCount = 0;
		 Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, new String[] {  
		            Calls.TYPE  
		        }, " type=? and new=?", new String[] {  
		                Calls.MISSED_TYPE + "", "1"  
		        }, "date desc");  
		  
		    if (cursor != null) {  
		    	MissCallCount = cursor.getCount();  
		        cursor.close();  
		    }  
		return MissCallCount;
	}
	//获得日程提醒事项
	@SuppressLint("NewApi")
	public static ArrayList<HashMap<String, String>> getCalanderInfo(Context context){
		ArrayList<HashMap<String, String>> listCalanderInfo = new ArrayList<HashMap<String, String>>();
		 Cursor cur = null;
		 ContentResolver cr = context.getContentResolver();
		 cur = cr.query(Events.CONTENT_URI, new String[]{Events.DTSTART,Events.DTEND,Events.TITLE},
				 Events.DTEND + ">" + new Date().getTime(), null, Events.DTSTART);
		 String startTime = null;
		 String endTime = null;
		 String title = null;
				 while(cur.moveToNext()){
					 HashMap<String, String> info = new HashMap<String, String>();
					 startTime = parseTime(cur.getString(cur.getColumnIndex(Events.DTSTART)));
					 endTime = parseTime(cur.getString(cur.getColumnIndex(Events.DTEND)));
					 title = cur.getString(cur.getColumnIndex(Events.TITLE));
					 info.put("starttime", startTime);
					 info.put("endtime", endTime);
					 info.put("title", title);
					 listCalanderInfo.add(info);
				 }
		 cur.close();
         return listCalanderInfo;
	}
	//添加日历
	@SuppressLint("NewApi")
	public static void addCalendar(Context context,String starttime,String endtime,String title){
		long startMillis = 0; 
		long endMillis = 0;     
		startMillis = toMillTime(starttime);
		endMillis = toMillTime(endtime);
		
		ContentResolver cr = context.getContentResolver();
		ContentValues values = new ContentValues();
		values.put(Events.CALENDAR_ID, "1");
		values.put(Events.DTSTART, startMillis);
		values.put(Events.DTEND, endMillis);
		values.put(Events.TITLE, title);
		values.put(Events.EVENT_TIMEZONE, TimeZone.getDefault().getID().toString());
		values.put(Events.EVENT_END_TIMEZONE, TimeZone.getDefault().getID().toString());
		Uri uri = cr.insert(Events.CONTENT_URI, values);
		Long eventId= Long.parseLong(uri.getLastPathSegment());
		Log.i("id", eventId+"-------");
    	ContentValues remider = new ContentValues();
    	remider.put(Reminders.EVENT_ID, eventId );
        //提前10分钟有提醒
    	remider.put(Reminders.MINUTES,"10");
    	remider.put(Reminders.METHOD, Reminders.METHOD_ALERT );
        context.getContentResolver().insert(Reminders.CONTENT_URI, remider);
        Toast.makeText(context, "添加事件成功", Toast.LENGTH_SHORT).show();
	}
	
	//获得闹钟时间
	public static String getClock(Context context){
		String clockTime = null;
		clockTime = Settings.System.getString(context.getContentResolver(),  
                            Settings.System.NEXT_ALARM_FORMATTED);
		if(clockTime.equals(""))  
			clockTime = "添加闹铃";
		return clockTime;
}
	
	//时间格式解析
	public static String parseTime(String strTime){
		Date dTime = new Date(Long.parseLong(strTime));
		SimpleDateFormat adp = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		String date = adp.format(dTime);
		return date;
	}
	public static long toMillTime(String dateString){
		SimpleDateFormat adp = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
		long milTime = (new Date()).getTime();
		try {
			Date time = adp.parse(dateString);
			milTime = time.getTime();
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return milTime;
		
	}
	
}
