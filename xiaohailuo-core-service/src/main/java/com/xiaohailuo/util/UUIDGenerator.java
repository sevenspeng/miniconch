package com.xiaohailuo.util;

import java.util.UUID;

public class UUIDGenerator {
	public UUIDGenerator() {
	}

	/**
	 * 获得一个UUID
	 * 
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		// 去掉“-”符号
		return s.substring(0, 8) + s.substring(9, 13) + s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
	}
	
	/**
	 * 获得一个UUID,带有附加属性
	 * Upper Lower case
	 * @return String UUID
	 */
	/**
	 * @param toggle
	 * 			upper、lower、空字符串和null（二者相同）
	 * @param separator
	 *        英文字符、空字符串、null（空字符串和null不同，null表示不做改变）
	 * @return
	 */
	public static String getUUID(String toggle, String separator) {
		String s = UUID.randomUUID().toString();
		if (separator != null/* &&!"".equals(separator) */) {
			s = s.replace("-", separator);
		}
		if (toggle != null && !"".equals(toggle)) {
			if (toggle.equalsIgnoreCase("upper")) {
				s = s.toUpperCase();
			} else if (toggle.equalsIgnoreCase("lower")) {
				s = s.toLowerCase();
			}
		}

		return s;
	}

	/**
	 * 获得指定数目的UUID
	 * 
	 * @param number
	 *            int 需要获得的UUID数量
	 * @return String[] UUID数组
	 */
	public static String[] getUUID(int number) {
		if (number < 1) {
			return null;
		}
		String[] ss = new String[number];
		for (int i = 0; i < number; i++) {
			ss[i] = getUUID();
		}
		return ss;
	}

/*	public static void main(String[] args) {
		String[] ss = getUUID(10);
		for (int i = 0; i < ss.length; i++) {
			System.out.println(ss[i]);
		}
//		System.out.println(getUUID("Upper","_"));
//		System.out.println(getUUID("Lower","_"));
//		System.out.println(getUUID("upper","_"));
//		System.out.println(getUUID("lower","_"));
//		System.out.println(getUUID("","_"));
		System.out.println(getUUID("",""));
		System.out.println(getUUID("",null));
		
		//System.out.println("abc".toUpperCase());
		//System.out.println("64ddaff7-c5b1-49f3-8ecc-0eefe37b4035".toUpperCase());
	}*/
}
