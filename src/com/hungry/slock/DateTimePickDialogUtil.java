package com.hungry.slock;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.TimePicker.OnTimeChangedListener;


public class DateTimePickDialogUtil implements OnDateChangedListener,
		OnTimeChangedListener {
	private DatePicker datePicker;
	private TimePicker timePicker;
	private AlertDialog ad;
	private String dateTime;
	private String initDateTime;
	private Activity activity;
	public DateTimePickDialogUtil(Activity activity, String initDateTime) {
		this.activity = activity;
		this.initDateTime = initDateTime;

	}

	public void init(DatePicker datePicker, TimePicker timePicker) {
		Calendar calendar = Calendar.getInstance();
		if (!(null == initDateTime || "".equals(initDateTime))) {
			calendar = this.getCalendarByInintData(initDateTime);
		} else {
			initDateTime = calendar.get(Calendar.YEAR) + "年"
					+ calendar.get(Calendar.MONTH) + "月"
					+ calendar.get(Calendar.DAY_OF_MONTH) + "日"
					+ calendar.get(Calendar.HOUR_OF_DAY) + ":"
					+ calendar.get(Calendar.MINUTE);
		}

		datePicker.init(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DAY_OF_MONTH), this);
		timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
	}

	public AlertDialog dateTimePicKDialog(final EditText inputDate) {
		LinearLayout dateTimeLayout = (LinearLayout) activity
				.getLayoutInflater().inflate(R.layout.common_datetime, null);
		datePicker = (DatePicker) dateTimeLayout.findViewById(R.id.datepicker);
		timePicker = (TimePicker) dateTimeLayout.findViewById(R.id.timepicker);
		init(datePicker, timePicker);
		timePicker.setIs24HourView(true);
		timePicker.setOnTimeChangedListener(this);

		ad = new AlertDialog.Builder(activity)
				.setTitle(initDateTime)
				.setView(dateTimeLayout)
				.setPositiveButton("设置", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						inputDate.setText(dateTime);
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						inputDate.setText("");
					}
				}).show();

		onDateChanged(null, 0, 0, 0);
		return ad;
	}

	public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
		onDateChanged(null, 0, 0, 0);
	}

	public void onDateChanged(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		Calendar calendar = Calendar.getInstance();

		calendar.set(datePicker.getYear(), datePicker.getMonth(),
				datePicker.getDayOfMonth(), timePicker.getCurrentHour(),
				timePicker.getCurrentMinute());
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");

		dateTime = sdf.format(calendar.getTime());
		ad.setTitle(dateTime);
	}

	private Calendar getCalendarByInintData(String initDateTime) {
		Calendar calendar = Calendar.getInstance();

		String date = spliteString(initDateTime, "日", "index", "front"); 
		String time = spliteString(initDateTime, "日", "index", "back"); 

		String yearStr = spliteString(date, "年", "index", "front"); 
		String monthAndDay = spliteString(date, "年", "index", "back"); 

		String monthStr = spliteString(monthAndDay, "月", "index", "front");
		String dayStr = spliteString(monthAndDay, "月", "index", "back"); 

		String hourStr = spliteString(time, ":", "index", "front"); 
		String minuteStr = spliteString(time, ":", "index", "back"); 

		int currentYear = Integer.valueOf(yearStr.trim()).intValue();
		int currentMonth = Integer.valueOf(monthStr.trim()).intValue() - 1;
		int currentDay = Integer.valueOf(dayStr.trim()).intValue();
		int currentHour = Integer.valueOf(hourStr.trim()).intValue();
		int currentMinute = Integer.valueOf(minuteStr.trim()).intValue();

		calendar.set(currentYear, currentMonth, currentDay, currentHour,
				currentMinute);
		return calendar;
	}
	
	public static String spliteString(String srcStr, String pattern,
			String indexOrLast, String frontOrBack) {
		String result = "";
		int loc = -1;
		if (indexOrLast.equalsIgnoreCase("index")) {
			loc = srcStr.indexOf(pattern); // ȡ���ַ�����һ�γ��ֵ�λ��
		} else {
			loc = srcStr.lastIndexOf(pattern); // ���һ��ƥ�䴮��λ��
		}
		if (frontOrBack.equalsIgnoreCase("front")) {
			if (loc != -1)
				result = srcStr.substring(0, loc); // ��ȡ�Ӵ�
		} else {
			if (loc != -1)
				result = srcStr.substring(loc + 1, srcStr.length()); // ��ȡ�Ӵ�
		}
		return result;
	}

}
