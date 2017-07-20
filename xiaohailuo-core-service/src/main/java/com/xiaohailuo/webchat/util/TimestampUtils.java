package com.xiaohailuo.webchat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

//import java.security.Timestamp;
//import com.sun.jmx.snmp.Timestamp;
//import java.sql.Timestamp;

public class TimestampUtils {
	/*public static void main(String[] args) {
		//TimestampUtils.getTimestampTest();
		java.sql.Timestamp timestamp = TimestampUtils.getTimestamp();
		System.out.println("timestamp:" + timestamp);
		System.out.println("timestamp:" + timestamp.getNanos());//毫微秒（十万分之一秒）
		System.out.println("timestamp:" + timestamp.getTime());//返回此 Timestamp 对象表示的自 1970 年 1 月 1 日 00:00:00 GMT 以来的毫秒数。
		
		System.out.println("getTimestampInMilliseconds:\t" + TimestampUtils.getTimestampInMilliseconds());
		System.out.println("getTimestampInNanosecond:\t" + TimestampUtils.getTimestampInNanosecond());
	
		System.out.println("**************************");
		String time1 = TimestampUtils.transferLongToDate("yyyy-MM-dd HH:mm:ss", timestamp.getTime());
		String time2 = TimestampUtils.transferLongToDate2("yyyy-MM-dd HH:mm:ss", timestamp.getTime());
		System.out.println("time1: " + time1);
		System.out.println("time2: " + time2);
	}*/
	
	
	public static java.sql.Timestamp getTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}
	public static long getTimestampInMilliseconds() {
		return new java.sql.Timestamp(System.currentTimeMillis()).getTime();
	}
	public static int getTimestampInNanosecond() {
		return new java.sql.Timestamp(System.currentTimeMillis()).getNanos();
	}
	
	
	/*public static void getTimestampTest(){
		com.sun.jmx.snmp.Timestamp d = new com.sun.jmx.snmp.Timestamp(System.currentTimeMillis()); 
		java.sql.Timestamp d2 = new java.sql.Timestamp(System.currentTimeMillis());
		
		System.out.println("d:" + d);
		System.out.println("d2:" + d2);
		
		java.util.Date date = new java.util.Date();       
		com.sun.jmx.snmp.Timestamp nousedate = new com.sun.jmx.snmp.Timestamp(date.getTime());
		java.sql.Timestamp nousedate2 = new java.sql.Timestamp(System.currentTimeMillis());
		System.out.println("nousedate:" + nousedate);
		System.out.println("nousedate2:" + nousedate2);
		
	}*/

	/**
	 * 把毫秒转化成日期
	 * 
	 * @param dateFormat(日期格式，例如：MM/
	 *            dd/yyyy HH:mm:ss)
	 * @param millSec(毫秒数)
	 * @return
	 */
	private static String transferLongToDate(String dateFormat, Long millSec) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		return sdf.format(date);
	}
	
	private static String transferLongToDate2(String dateFormat, Long millSec) {
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		Date date = new Date(millSec);
		
		java.sql.Timestamp timestamp = new java.sql.Timestamp(millSec);
		System.out.println("timestamp.toGMTString(): "+timestamp.toGMTString());
		System.out.println("timestamp.toGMTString(): "+timestamp.toLocaleString());
		
		return timestamp.toString();
	}
	
}
