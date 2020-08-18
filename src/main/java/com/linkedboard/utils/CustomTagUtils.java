package com.linkedboard.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

public class CustomTagUtils {
	
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	public static String getFormattedDate(String date) {
		return getFormattedDate(date, "yyyy.MM.dd");
	}

	
	/**
	 * 날짜 포맷 변경
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getFormattedDate(String date, String format) {
		String newDateString = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			
			newDateString = sdf.format(dateFormat.parse(date));
		}
		catch( Exception e) {
			newDateString = date;
		}
		return newDateString;
	}
	
	/**
	 * 숫자 콤마 추가
	 * @param value
	 * @return
	 */
	public static String toCommas(String value) {
		try {
			long number = Long.parseLong(value);
			DecimalFormat df = new DecimalFormat("#,###");
			return df.format(number);
		} catch (Exception e) {
			return value;
		}
	}
	
	/**
	 * 용량 표기 변경
	 * @param value
	 * @return
	 */
	public static String toUnit(String value) { 
		try {
			int volume = Integer.parseInt(value);
			String unitArr[] = {"B", "KB", "MB", "GB", "TB"};
			
			int i = 0;
			while( volume > 1024) {
				volume = volume / 1024;
				i ++;
			}
			
			return volume + unitArr[i];
		} catch (Exception e) {
			return String.valueOf(value);
		}
	}
}