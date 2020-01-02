package com.learn.cursomc.utils.format;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class FormatUtils {
	public static final String separator_dash_DDMMYYYY = "dd-MM-yyyy";
	public static final String separator_dash_DDMMYYYYHH = "dd-MM-yyyy HH";
	public static final String separator_dash_DDMMYYYYHHMM = "dd-MM-yyyy HH:mm";
	public static final String separator_dash_DDMMYYYYHHMMSS = "dd-MM-yyyy HH:mm:ss";
	public static final String separator_dash_DDMMYYYYHHMMSSML = "dd-MM-yyyy HH:mm:ss.mmm";
	
	public static final String separator_dot_DDMMYYYY = "dd.MM.yyyy";
	public static final String separator_dot_DDMMYYYYHH = "dd.MM.yyyy HH";
	public static final String separator_dot_DDMMYYYYHHMM = "dd.MM.yyyy HH:mm";
	public static final String separator_dot_DDMMYYYYHHMMSS = "dd.MM.yyyy HH:mm:ss";
	public static final String separator_dot_DDMMYYYYHHMMSSML = "dd.MM.yyyy HH:mm:ss.mmm";
	
	public static final String separator_slash_DDMMYYYY = "dd/MM/yyyy";
	public static final String separator_slash_DDMMYYYYHH = "dd/MM/yyyy HH";
	public static final String separator_slash_DDMMYYYYHHMM = "dd/MM/yyyy HH:mm";
	public static final String separator_slash_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
	public static final String separator_slash_DDMMYYYYHHMMSSML = "dd/MM/yyyy HH:mm:ss.mmm";
	
	public static final String separator_dash_YYYYMMDD = "yyyy-MM-dd";
	public static final String separator_dash_YYYYMMDDHH = "yyyy-MM-dd HH";
	public static final String separator_dash_YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String separator_dash_YYYYMMDDHHMMSSML = "yyyy-MM-dd HH:mm:ss.mmm";
	
	public static final String separator_dot_YYYYMMDD = "yyyy.MM.dd";
	public static final String separator_dot_YYYYMMDDHH = "yyyy.MM.dd HH";
	public static final String separator_dot_YYYYMMDDHHMM = "yyyy.MM.dd HH:mm";
	public static final String separator_dot_YYYYMMDDHHMMSS = "yyyy.MM.dd HH:mm:ss";
	public static final String separator_dot_YYYYMMDDHHMMSSML = "yyyy.MM.dd HH:mm:ss.mmm";
	
	public static final String separator_slash_YYYYMMDD = "yyyy/MM/dd";
	public static final String separator_slash_YYYYMMDDHH = "yyyy/MM/dd HH";
	public static final String separator_slash_YYYYMMDDHHMM = "yyyy/MM/dd HH:mm";
	public static final String separator_slash_YYYYMMDDHHMMSS = "yyyy/MM/dd HH:mm:ss";
	public static final String separator_slash_YYYYMMDDHHMMSSML = "yyyy/MM/dd HH:mm:ss.mmm";
	
	public static final String separator_dash_DDMMYY = "dd-MM-yy";
	public static final String separator_dash_DDMMYYHH = "dd-MM-yy HH";
	public static final String separator_dash_DDMMYYHHMM = "dd-MM-yy HH:mm";
	public static final String separator_dash_DDMMYYHHMMSS = "dd-MM-yy HH:mm:ss";
	public static final String separator_dash_DDMMYYHHMMSSML = "dd-MM-yy HH:mm:ss.mmm";
	
	public static final String separator_dot_DDMMYY = "dd.MM.yy";
	public static final String separator_dot_DDMMYYHH = "dd.MM.yy HH";
	public static final String separator_dot_DDMMYYHHMM = "dd.MM.yy HH:mm";
	public static final String separator_dot_DDMMYYHHMMSS = "dd.MM.yy HH:mm:ss";
	public static final String separator_dot_DDMMYYHHMMSSML = "dd.MM.yy HH:mm:ss.mmm";
	
	public static final String separator_slash_DDMMYY = "dd/MM/yy";
	public static final String separator_slash_DDMMYYHH = "dd/MM/yy HH";
	public static final String separator_slash_DDMMYYHHMM = "dd/MM/yy HH:mm";
	public static final String separator_slash_DDMMYYHHMMSS = "dd/MM/yy HH:mm:ss";
	public static final String separator_slash_DDMMYYHHMMSSML = "dd/MM/yy HH:mm:ss.mmm";
	
	public static final String separator_dash_YYMMDD = "yy-MM-dd";
	public static final String separator_dash_YYMMDDHH = "yy-MM-dd HH";
	public static final String separator_dash_YYMMDDHHMM = "yy-MM-dd HH:mm";
	public static final String separator_dash_YYMMDDHHMMSS = "yy-MM-dd HH:mm:ss";
	public static final String separator_dash_YYMMDDHHMMSSML = "yy-MM-dd HH:mm:ss.mmm";
	
	public static final String separator_dot_YYMMDD = "yy.MM.dd";
	public static final String separator_dot_YYMMDDHH = "yy.MM.dd HH";
	public static final String separator_dot_YYMMDDHHMM = "yy.MM.dd HH:mm";
	public static final String separator_dot_YYMMDDHHMMSS = "yy.MM.dd HH:mm:ss";
	public static final String separator_dot_YYMMDDHHMMSSML = "yy.MM.dd HH:mm:ss.mmm";
	
	public static final String separator_slash_YYMMDD = "yy/MM/dd";
	public static final String separator_slash_YYMMDDHH = "yy/MM/dd HH";
	public static final String separator_slash_YYMMDDHHMM = "yy/MM/dd HH:mm";
	public static final String separator_slash_YYMMDDHHMMSS = "yy/MM/dd HH:mm:ss";
	public static final String separator_slash_YYMMDDHHMMSSML = "yy/MM/dd HH:mm:ss.mmm";
	
	public static String formatDate(String sDate, String format) {
		return null;
	};
	
	public static String formatDate(Date oDate, String formato) {
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		Calendar cal = new GregorianCalendar();
		cal.setTime(oDate);
		return sdf.format(cal.getTime());
	};
	
	public static String formatBRMonetary(double value) {
		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "br"));
		return nf.format(value);
	}
}