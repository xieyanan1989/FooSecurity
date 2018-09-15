package com.fsapp.sunsi.foosecurity.dubo.util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * @author Jiang
 *设置、配置
 */
public class Tools {

	public static String SHARED_PREFERENCES_KEY = "001";
	
	public static SharedPreferences sharedPrefs;
	
	/**
	 * String 转换成 Int 型
	 * @param str 放入的String 
	 * @return
	 */
	public static int getStringOfInteger(String str){
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
	public static Double getStringOfDouble(String str){
		try{
			return Double.parseDouble(str);
		}catch (Exception e) {
			Log.w("Form", "getStringOfInteger exception");
			return 0.0;
		}
	}

	/**
	 * String 转换成 Long 型
	 * @param str 放入的String 
	 * @return
	 */
	public static long getStringOfLong(String str){
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
	 * 保存配置信息
	 * @param mContext 当前的mContext
	 * @param key Key
	 * @param value 要存入的值
	 */
	public static void saveSharedPreferences(Context mContext, String key, int value) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putInt(key, value);
		editor.commit();
	}
	/**
	 * 保存配置信息
	 * @param mContext 当前的mContext
	 * @param key Key
	 * @param value 要存入的值
	 */
	public static void saveSharedPreferences(Context mContext, String key, boolean value) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}
	/**
	 * 保存配置信息
	 * @param mContext 当前的mContext
	 * @param key Key
	 * @param value 要存入的值
	 */
	public static void saveSharedPreferences(Context mContext, String key, String value) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		Editor editor = sharedPrefs.edit();
		editor.putString(key, value);
		editor.commit();
	}
	
	/**
	 * 取配置信息
	 * @param mContext 当前的mContext
	 * @param key 要取的数据的Key
	 */
	public static int getSharedPreferences(Context mContext, String key) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		return sharedPrefs.getInt(key, 0);
	}
	/**
	 * 取配置信息
	 * @param mContext 当前的mContext
	 * @param key 要取的数据的Key
	 */
	public static int getSharedPreferencesInt(Context mContext, String key) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		return sharedPrefs.getInt(key, 0);
	}
	/**
	 * 取配置信息
	 * @param mContext 当前的mContext
	 * @param key 要取的数据的Key
	 */
	public static boolean getSharedPreferencesBoolean(Context mContext, String key) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		return sharedPrefs.getBoolean(key, false);
	}
	/**
	 * 取配置信息
	 * @param mContext 当前的mContext
	 * @param key 要取的数据的Key
	 */
	public static String getSharedPreferencesString(Context mContext, String key) {
		sharedPrefs = mContext.getSharedPreferences(SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
		return sharedPrefs.getString(key, "");
	}
	
	/**
	 *float 保留两位小数
	 */
	public static float getFloatnum(float num) {
		  BigDecimal   b   =   new   BigDecimal(num);  
		   float   f1   =   b.setScale(2,   BigDecimal.ROUND_HALF_UP).floatValue();  
		   return f1;
	}
	/**
	 * 得到一个Xml 转换成View 返回
	 * @param layout Xml的Id
	 * @return View 用于操作
	 */
	public static View getXml(Context mContext, int layout){
		return LayoutInflater.from(mContext).inflate(layout, null);
	}

	/**
	 * 取个TextView
	 * @param mContext 当前操作的Activity
	 * @param whide 宽
	 * @return
	 */
	public static TextView getTextView(Context mContext, int whide){
		TextView tv = new TextView(mContext);
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, whide);
		tv.setLayoutParams(params);
		return tv;
	}
	
	/**
	 * 返回时间格式：201203281122
	 */
	public static String getTimeAsNumber(long time) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
		return sdf.format(date);
	}
	
	/**
	 * 返回时间格式：2011-12-28
	 */
	public static String getTimeDate(long time) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
/***
	 * 日期格式：yyyy-MM-dd HH:mm:ss 转成时间戳
	 */
	public static long getDateToTime(String time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date;
		long t = 0;
		try {
			date = format.parse(time);
			t = date.getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			// System.out.println("确认转换数据没错");
			e.printStackTrace();
		}
		return t;
	}
	
	/**
	 * 返回时间格式：00:00
	 */
	public static String getTimeDatehhmm(long time) {
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		return sdf.format(date);
	}
	/**
	 * 机选不重复
	 * @param sum 总个数
	 * @param count 机选的个数
	 * @param multi 去重的数组
	 * @return 机选结果
	 */
	public static int[] getRandom(int sum, int count, int[] multi){
		Random r = new Random();
		List<Integer> list = new ArrayList<Integer>(sum);
		int[] rand = new int[count];
		int temp;
		for(int i = 0; i < sum; i++){
			list.add(i);
		}
		for(int i = 0; i < multi.length; i++){
			temp = multi[i];
//			list.remove(temp);
			list.remove(new Integer(temp));
		}
		for(int i = 0; i < count; i++){
			temp = r.nextInt(list.size());
			rand[i] = list.get(temp);
			list.remove(temp);
		}
		return rand;
	}

	/**
	 * 双色球复式注数
	 * @param redNumber 红球个数
	 * @param FixNum 固定红球个数6
	 * @param bluedanNumber 篮球个数
	 * @return 注数
	 */
	public static int doubleArithmetic(int redNumber,int FixNum,int bluedanNumber){
		return CountNum(redNumber,FixNum)*bluedanNumber;
	}

	/**
	 * 双色球胆拖注数
	 * @param tuoNumber 拖码个数
	 * @param danNumber 胆码个数
	 * @param FixNum 球本身个数
	 * @param bluedanNumber 篮球个数
	 * @return 注数
	 */
	public static int danTuoArithmetic(int tuoNumber,int danNumber,int FixNum,int bluedanNumber){
		return CountNum(tuoNumber, FixNum - danNumber)*CountNum(bluedanNumber,1);
	}
	private static int CountNum(int MaxNum, int FixNum)
	{
		return CountP(MaxNum, FixNum) / CountF(FixNum);
	}
	private static int CountF(int FixNum)
	{
		int total = 1;
		if(FixNum < 0)
			return total;
		for(int i = FixNum; i>0; i--)
			total *= i;
		return total;
	}

	private static int CountP(int MaxNum, int FixNum)
	{
		int total = 1;
		if(MaxNum < 0 || FixNum < 0)
			return total;
		for(int i = MaxNum; i>MaxNum-FixNum; i--)
			total *= i;
		return total;
	}
}
