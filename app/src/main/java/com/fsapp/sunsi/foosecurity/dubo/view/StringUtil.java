package com.fsapp.sunsi.foosecurity.dubo.view;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.util.Log;

import com.fsapp.sunsi.foosecurity.dubo.bean.CpType;
import com.fsapp.sunsi.foosecurity.dubo.bean.Parser;

import org.json.JSONArray;
import org.json.JSONObject;

public class StringUtil {
	
	/**
	 * String 转换成 Int 型
	 * @param str 放入的String 
	 * @return
	 */
	public static int getOfInteger(String str){
		try{
			return Integer.parseInt(str);
		}catch (Exception e) {
			Log.w("Form", "getStringOfInteger exception");
			return 0;
		}
	}

	/**
	 * String 转换成 Double 型
	 * @param str 放入的String 
	 * @return
	 */
	public static double getOfDouble(String str){
		try{
			return Double.parseDouble(str);
		}catch (Exception e) {
			Log.w("Form", "getStringOfInteger exception");
			return 0;
		}
	}

	/**
	 * String 转换成 Long 型
	 * @param str 放入的String 
	 * @return
	 */
	public static long getOfLong(String str){
		try{
			return Long.parseLong(str);
		}catch (Exception e) {
			Log.w("Form", "getStringOfInteger exception");
			return 0;
		}
	}
	
	/**
	 * 返回时间格式
	 * @param time 当前的时间戳
	 * @param format 要转换的格式
	 * @return 要的时间格式
	 */
	public static String getTimeForm(long time, String format) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 返回时间戳
	 * @param time 当前的时间
	 * @param format 时间格式
	 * @return 要的时间戳
	 */
	public static long getTimeForm(String time, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		long t = 0;
		try {
			Date date = sdf.parse(time);
			t = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// System.out.println("确认转换数据没错");
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 自定义时间格式
	 * @param dateStr  时间字符串；
	 * @param oldStyle 传进来的时间字符串的格式；
	 * @param newStyle 希望得到的时间格式
	 * @return
	 */
	public static String getTimeForm(String dateStr, String oldStyle, String newStyle) {
		try {
			SimpleDateFormat simpleDateormat = new SimpleDateFormat(oldStyle);
			Date date = simpleDateormat.parse(dateStr);
			
			SimpleDateFormat sdf = new SimpleDateFormat(newStyle);
			return sdf.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * 转换成金额的千位分隔符 1,234,567
	 * @param value 值
	 * @return
	 */
	public static String getNumberFormat(long value){
		NumberFormat mFormat = NumberFormat.getInstance();
		return mFormat.format(value);
	}

	/**
	 * 转换成金额的千分隔符 1,234,567.89
	 * @param value 值
	 * @return
	 */
	public static String getNumberFormat(double value){
		NumberFormat mFormat = NumberFormat.getInstance();
		mFormat.setMaximumFractionDigits(2);
		return mFormat.format(value);
	}
	
	/**
	 * 拼写字符串
	 * @param value 拼的值 为String数组
	 * @param divide 拼接的分隔符
	 * @return 拼接好的结果
	 */
	public static String spell(String[] value, String divide){
		StringBuilder sb = new StringBuilder();
		if(value != null){
			for(String s: value){
				sb.append(s);
				sb.append(divide);
			}
			if(!isEmpty(sb))
				sb.deleteCharAt(sb.lastIndexOf(divide));
		}
		return sb.toString();
	}
	
	/**
	 * 拼写字符串
	 * @param value 拼的值 为String数组
	 * @param divide 拼接的分隔符
	 * @return 拼接好的结果
	 */
	public static String spell(int[] value, String divide){
		StringBuilder sb = new StringBuilder();
		if(value != null){
			for(int s: value){
				sb.append(s);
				sb.append(divide);
			}
			if(!isEmpty(sb))
				sb.deleteCharAt(sb.lastIndexOf(divide));
		}
		return sb.toString();
	}
	
	/**
	 * 拆分字符串
	 * @param value 拆分的String值
	 * @param divide 拆分的符号
	 * @return 拆分后的结果 String数组
	 */
	public static String[] split(String value, String divide){
		return !isEmpty(value)? value.split(divide) : new String[]{};
	}
	
	/**
	 * 判断字符是否为空
	 * @param value 传入的字符
	 * @return 空为true 否则为false
	 */
	public static boolean isEmpty(CharSequence value){
		 if (value == null || value.length() == 0)
	            return true;
	        else
	            return false;
	}
	
	/**
	 * 判断字符是否为空
	 * @param value 传入的字符
	 * @return 空为true 否则为false
	 */
	public static boolean isEmpty(int[] value){
		if (value == null || value.length == 0)
			return true;
		else
			return false;
	}

	/**
	 * 取得数据的长度
	 * @param group 传入的数组
	 * @return int 长度
	 */
	public static int size(int[] group){
		if (group != null)
			return group.length;
		else
			return 0;
	}

	/**
	 * 取得数据的长度
	 * @param group 传入的数组
	 * @return int 长度
	 */
	public static int size(String[] group){
		if (group != null)
			return group.length;
		else
			return 0;
	}


	/**将json数据转换成为对应的bean list
	 * @param parser
	 * @return
	 */
	public static List<? extends CpType> parserJsonArray(JSONArray jsonArray, Parser<? extends CpType> parser){
		List<CpType> list = null;
		try {
			list = new ArrayList<CpType>();
				JSONObject jsonObject;
				for (int i = 0,j = jsonArray.length(); i < j; i++) {
					jsonObject = jsonArray.getJSONObject(i);
					CpType mBean = parser.parse(jsonObject);
					list.add( mBean );
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**将将week数字(1,2,3,4,5,6)转为星期一、二，三
	 * @param week
	 * @return
	 */
	public static String getWeekName(String week){
		String result ="";
		try {
			if ("1".equals(week)) {
				result ="星期一";
			} else if ("2".equals(week)) {
				result ="星期二";
			} else if ("3".equals(week)) {
				result ="星期三";
			} else if ("4".equals(week)) {
				result ="星期四";
			} else if ("5".equals(week)) {
				result ="星期五";
			} else if ("6".equals(week)) {
				result ="星期六";
			} else if ("0".equals(week) || "7".equals(week)) {
				result ="星期日";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

    /**
     * 获取当前日期是星期几<br>
     *
     * @param date
     * @return 当前日期是星期几
     */
    public static String getWeekOfDate(String date) {
        int w = 0;
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        try {
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            java.util.Date dt=sdf.parse(date);
            Calendar cal=Calendar.getInstance();
            cal.setTime(dt);
            w = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (w < 0)
                w = 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekDays[w];
    }
}
