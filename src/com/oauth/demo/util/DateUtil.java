package com.oauth.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static boolean isValidKey(String createKeyDate, long expire_time) {
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		// 当前时间
		Date now = new Date();
		try {
			Date createTime = dateformat.parse(createKeyDate);
			Date currentTime = dateformat.parse(dateformat.format(now));
			long time = (currentTime.getTime() - createTime.getTime())/1000; 
			return time > expire_time ? true : false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
