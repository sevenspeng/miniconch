package com.xiaohailuo.webchat.util;

import java.util.Random;

public class StringUtils {
	private static final String BASE_CHARACTER = "-+=*_|0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//private static final String BASE_CHARACTER = "_0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	//private static final String BASE_CHARACTER = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	
	/*public static void main(String[] args) {
		System.out.println(StringUtils.getNonceStr());
		System.out.println();
		System.out.println(TimestampUtils.getTimestamp());
		System.out.println(StringUtils.getRandomStringBuffer(256));
		System.out.println();
		System.out.println(TimestampUtils.getTimestamp());
		System.out.println(StringUtils.getRandomString(256));
		System.out.println(TimestampUtils.getTimestamp());
	}*/

	public static String getNonceStr() {
		return getRandomString(16);
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		//String base = "_abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			//int number = random.nextInt(base.length());
			//sb.append(base.charAt(number));
			int number = random.nextInt(BASE_CHARACTER.length());
			//System.out.println("number:" + number);
			sb.append(BASE_CHARACTER.charAt(number));
		}
		return sb.toString();
	}
	
	public static String getRandomStringBuffer(int length) {
		StringBuffer buffer = new StringBuffer(BASE_CHARACTER);
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++) {
			sb.append(buffer.charAt(random.nextInt(range)));
		}
		return sb.toString();
	}
}
