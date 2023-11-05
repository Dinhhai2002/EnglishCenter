package com.english_center.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UtilsResponse {
	
	
	public static String formatDate(String inputDate) throws ParseException
	{

		SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		Date date = mysqlDateFormat.parse(inputDate);

		SimpleDateFormat javaDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return javaDateFormat.format(date);
	}
	
	public static String format(String inputDate) throws ParseException
	{

		SimpleDateFormat mysqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Date date = mysqlDateFormat.parse(inputDate);

		SimpleDateFormat javaDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        return javaDateFormat.format(date);
	}
	
	
	
}
