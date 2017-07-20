package com.xiaohailuo.webchat.util;

public /*final*/ class Hourglass {
	private Hourglass(){}
	
	private static long expiresIn_AccessToken = 7200l;
	private static long timeOffset = 60l;
	private static java.sql.Timestamp startTime_AccessToken = new java.sql.Timestamp(System.currentTimeMillis());
	private static java.sql.Timestamp endTime_AccessToken = new java.sql.Timestamp(System.currentTimeMillis() + expiresIn_AccessToken);
	
	public static java.sql.Timestamp getStartTime_AccessToken() {
		return startTime_AccessToken;
	}
	public static java.sql.Timestamp getEndTime_AccessToken() {
		return endTime_AccessToken;
	}
	public static long getExpiresIn_AccessToken() {
		return expiresIn_AccessToken;
	}
	
	public static void updateHourglass(Long starttime, long expiresIn, String type){
		startTime_AccessToken = new java.sql.Timestamp(startTime_AccessToken.getTime());
		expiresIn_AccessToken = expiresIn;
		endTime_AccessToken = new java.sql.Timestamp(startTime_AccessToken.getTime() + expiresIn_AccessToken);
	}
	
	/**
	 * @param type
	 * @return true:未过期;false:已过期，需重新申请
	 * 
	 */
	public static boolean checkHourglass(String type){
		
		return new java.sql.Timestamp(System.currentTimeMillis()).getTime() + timeOffset < endTime_AccessToken.getTime();
	}
	
	
	
}
