package top.banach.emergency.utils;

import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import top.banach.emergency.main.MainActivity;

public class DateUtil {
	public static String getCurrentDate() {
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH)+1;
		int date = c.get(Calendar.DATE);
		String m = month > 9 ? "" + month : "0" + month;
		String d = date > 9 ? "" + date : "0" + date;
		String s = year + "-" + m + "-" + d;
		return s;
	}
	
	public static String getCurrentTime() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time=format.format(date);
		return time;
	}
	
	public static String getCurrentTodayTime() {
		Date date=new Date();
		DateFormat format=new SimpleDateFormat("今天 HH:mm");
		String time=format.format(date);
		return time;
	}

	private static long getTimeMillis(String strTime) {
		long returnMillis = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date d = null;
		try {
			d = sdf.parse(strTime);
			returnMillis = d.getTime();
		} catch (ParseException e) {

		}
		return returnMillis;
	}

	public static String getTimeExpend(String startTime, String endTime){
		//传入字串类型 2016/06/28 08:30
		long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
		long longEnd = getTimeMillis(endTime);  //获取结束时间毫秒数
		long longExpend = longEnd - longStart;  //获取时间差

		long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数
		long longMinutes = (longExpend - longHours * (60 * 60 * 1000)) / (60 * 1000);   //根据时间差来计算分钟数

		return longHours + ":" + longMinutes;
	}

	/**
	 * 判断现在的时间是已比startTime多出hours小时
	 * @param startTime
	 * @param hours
	 * @return
	 */
	public static boolean isOverForHours(String startTime, long hours){
		//传入字串类型 2016/06/28 08:30
		long longStart = getTimeMillis(startTime); //获取开始时间毫秒数
		long longEnd = getTimeMillis(getCurrentTime());  //获取结束时间毫秒数
		long longExpend = longEnd - longStart;  //获取时间差

		long longHours = longExpend / (60 * 60 * 1000); //根据时间差来计算小时数

		if (longHours >=hours) {
			return true;
		} else {
			return false;
		}
	}
}
