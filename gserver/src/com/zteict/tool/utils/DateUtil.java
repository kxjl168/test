package com.zteict.tool.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author zj
 * @date 2015-12-25 上午9:53:23
 */
public class DateUtil {

	public static String defaultFormat = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 获取当前时间串
	 * 
	 * @param format
	 *            为空，则默认yyyy-MM-dd HH:mm:ss
	 * @return
	 * @author zj
	 * @date 2015-12-25 上午9:55:49 *
	 */
	public static String getNowStr(String format) {
		if (format == null || format.equals(""))
			format = defaultFormat;
		DateFormat dateFormat = new SimpleDateFormat(format);

		Date now = new Date();

		String DateStr = dateFormat.format(now);

		return DateStr;
	}

	
	public static String getDateStr(Date date,String format) {
		if (format == null || format.equals(""))
			format = defaultFormat;
		DateFormat dateFormat = new SimpleDateFormat(format);

		Date now = date;

		String DateStr = dateFormat.format(now);

		return DateStr;
	}
	
	/**
	 * 返回时间格式
	 * @param strdate
	 * @param format
	 * @return
	 * @date 2016-3-24
	 * @author zj
	 */
	public static Date getDate(String strdate, String format) {
		if (format == null || format.equals(""))
			format = "yyyyMMddHHmmss";
		Date d=new Date();
		try {
			DateFormat dateFormat = new SimpleDateFormat(format);
			
			d = dateFormat.parse(strdate);
		} catch (ParseException e) {
			Calendar c=Calendar.getInstance();
			c.set(2000, 1, 1);
			d=c.getTime();
		}
		return d;
	}

}
