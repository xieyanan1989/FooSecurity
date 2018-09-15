package com.fsapp.sunsi.foosecurity.dubo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.fsapp.sunsi.foosecurity.dubo.util.Tools;

import com.fsapp.sunsi.foosecurity.R;

public class BallView {

	/** 大 **/
	public static final int[] SELECT_BALL_BIG      = new int[]{5, 6, 7, 8, 9}; 
	/** 小 **/
	public static final int[] SELECT_BALL_SMALL  = new int[]{0, 1, 2, 3, 4}; 
	/** 单 **/
	public static final int[] SELECT_BALL_ODD     = new int[]{1, 3, 5, 7, 9}; 
	/** 双 **/
	public static final int[] SELECT_BALL_DUAL    = new int[]{0, 2, 4, 6, 8}; 
	/** 全 **/
	public static final int[] SELECT_BALL_ENTIRE  = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9}; 
	
	/**
	 * 取AutoWrapView 往里面添加View
	 * @param mContext 当前的Context
	 * @param list 要添加的内容
	 * @return 返回添加好的AutoWrapView
	 */
	public static AutoWrapView getBall(Context mContext, AutoWrapView mView, List<View> list){
		for(View v: list){
			mView.addView(v);
		}
		return mView;
	}

	/**
	 * 取得一个 List<View> View为 CheckBox 显示值为传进来的Str
	 * @param mContext 当前的Context
	 * @param str 要显示的数组
	 * @param background 背景
	 * @return Lise<View>
	 */
	public static List<View> getListCheck(Context mContext, String[] str, int background){
		return getListCheck(mContext, str, R.layout.ball_red, background);
	}
	
	/**
	 * 取得一个 List<View> View为 CheckBox 显示值为传进来的Str
	 * @param mContext 当前的Context
	 * @param str 要显示的数组
	 * @param layout 当前页面 Xml
	 * @param background 背景
	 * @return Lise<View>
	 */
	public static List<View> getListCheck(Context mContext, String[] str, int layout, int background){
		List<View> list = new ArrayList<View>();
		for(int i = 0, len = str.length; i < len; i++){
			if(!StringUtil.isEmpty(str[i])){
				CheckBox view = (CheckBox) Tools.getXml(mContext, layout);
				view.setText(str[i]);
				view.setId(i);
				list.add(view);
			}
		}
		return list;
	}
	
	/**
	 * 取得一个 List<View> View为 CheckBox 显示的值为数据
	 * @param mContext 当前的Context
	 * @param start 起始值
	 * @param end 总个数
	 * @param background 背景
	 * @return Lise<View>
	 */
	public static List<View> getListCheck(Context mContext, int start, int end, int background){
		return getListCheck(mContext, start, end, R.layout.ball_red, background);
	}
	
	public static List<View> getListGLCheck(Context mContext, int start, int end, int background){
		return getListGLCheck(mContext, start, end, R.layout.ball_red, background);
	}
	/**
	 * 取得一个 List<View> View为 CheckBox 显示的值为数据
	 * @param mContext 当前的Context
	 * @param start 起始值
	 * @param sum 总个数
	 * @param layout 当前页面 Xml
	 * @param background 背景
	 * @return Lise<View>
	 */
	public static List<View> getListCheck(Context mContext, int start, int sum, int layout, int background){
		List<View> list = new ArrayList<View>();
		for(int i = 0; i < sum; i++){
			CheckBox view = (CheckBox) Tools.getXml(mContext, layout);
			view.setText(getNumberStr(sum, i + start));
			view.setId(i);
			list.add(view);
		}
		return list;
	}
	
	private static String getNumberStr(int sum, int value){
		return (sum > 10)? (value < 10)? "0"+value: String.valueOf(value): String.valueOf(value);
	}
	
	
	public static List<View> getListGLCheck(Context mContext, int start, int sum, int layout, int background){
		List<View> list = new ArrayList<View>();
		for(int i = 0; i < sum; i++){
			CheckBox view = (CheckBox) Tools.getXml(mContext, layout);
			view.setText(getGLNumberStr(sum, i + start));
			view.setId(i);
			list.add(view);
		}
		return list;
	}
	
	private static String getGLNumberStr(int sum, int value){
		return (sum > 10)? (value < 10)? ""+value: String.valueOf(value): String.valueOf(value);
	}
	/**
	 * 不可重复选球
	 * @param autoViewLists AutoWrapView 的list
	 * @param in 操作的AutoWrapView
	 * @param index 选球的球ID
	 */
	public static void setUnrepetition(List<AutoWrapView> autoViewLists, int in, int index){
		int size = autoViewLists.size();
		for(int i = 0; i < size; i++){
			if(i != in){
				AutoWrapView auto = autoViewLists.get(i);
				CheckBox checkBox = (CheckBox) auto.getChildAt(index);
				if(checkBox.isChecked()) checkBox.setChecked(false);
			}
		}
	}
	
	/**
	 * 取当前选中的结果
	 * @return 返回 ArrayList<Integer> 的结果集合
	 */
	public static int[] getResultSelect(AutoWrapView autoView){
		ArrayList<CheckBox> mCheckLists = autoView.mCheckLists;
		ArrayList<Integer> resultSelect = new ArrayList<Integer>();
		for(CheckBox checkBox: mCheckLists){
			if(checkBox.isChecked() && checkBox.isClickable()){
				resultSelect.add(checkBox.getId());
//				if(DEBUG) System.out.print(checkBox.getId() + " : ");
			}
		}
//		if(DEBUG) System.out.println();
		int size = resultSelect.size();
		int[] result = new int[size];
		for(int i = 0; i < size; i++){
			result[i] = resultSelect.get(i);
		}
		return result;
	}
	
	/**
	 * 取得选中的号码
	 * @param autoView 操作的AutoWrapView
	 * @param group 选中的数据
	 * @param split 拼的分隔符
	 * @return 拼完的结果
	 */
	public static String getNumber(AutoWrapView autoView, int[] group, String split){
		ArrayList<CheckBox> mCheckLists = autoView.mCheckLists;
		group = sort(group);
		if(mCheckLists != null && mCheckLists.size() > 0 && group != null && group.length > 0){
			StringBuilder sb = new StringBuilder(mCheckLists.get(group[0]).getText().toString());
			for(int i = 1, si = group.length; i < si; i++){
				sb.append(split);
				sb.append(mCheckLists.get(group[i]).getText().toString());
			}
			return sb.toString();
		}
		return "_";
	}
	/**
	 * 机选
	 * @param sum 总个数
	 * @param count 机选的个数
	 * @return 机选结果
	 */
	public static int[] getRandom(int sum, int count){
		Random r = new Random();
		List<Integer> list = new ArrayList<Integer>(sum);
		int[] rand = new int[count];
		int temp;
		for(int i = 0; i < sum; i++){
			list.add(i);
		}
		for(int i = 0; i < count; i++){
			temp = r.nextInt(list.size());
			rand[i] = list.get(temp);
			list.remove(temp);
		}
		return rand;
	}
	
	/**
	 * 机选不可重
	 * @param sum 总个数
	 * @param count 机选的个数
	 * @param enabled 不可选的球
	 * @return 机选结果
	 */
	public static int[] getRandom(int sum, int count, int[] enabled){
//		System.out.println("机选不可重");
		Random r = new Random();
		List<Integer> list = new ArrayList<Integer>(sum);
		int temp;
		int eIndex = 0;
		int elength = 0;
		if(enabled != null){
			elength = enabled.length;
		}
		if(count + elength > sum){
			count =  sum - elength;
		}
		int[] rand = new int[count];
		int i = 0;
		while(i < sum){
			if(elength == 0){
				list.add(i);
			}else if(i != enabled[eIndex]){
				list.add(i);
			}else{
				if(eIndex < elength - 1){
					++eIndex;
				}
			}
			i++;
		}
		for(int j = 0; j < count; j++){
			temp = r.nextInt(list.size());
			rand[j] = list.get(temp);
			list.remove(temp);
		}
		return rand;
	}
	
	/**
	 * 机选可重复
	 * @param sum 总个数
	 * @param count 机选的个数
	 * @param enabled 不可选的球
	 * @return 机选结果
	 */
	public static int[] getRandomDuplicate(int sum, int count, int[] enabled){
		Random r = new Random();
		int[] rand = new int[count];
		int temp;
		int index = 0;
		int eIndex = 0;
		int elength = 0;
		if(enabled != null){
			elength = enabled.length;
		}

		boolean isEnd;
		while (index < count) {
			temp = r.nextInt(sum);
			if( elength == 0){
				rand[index] = temp;
				++index;
			}else {
				isEnd = false;
				for(int e: enabled){
					if(temp == e){
						isEnd = true;
						break;
					}
				}
				if(isEnd){
					rand[index] = temp;
					++index;
				}
			}
		}
		return rand;
	}
	
	/**
	 * 计算注数 阶乘算法
	 * @param n 总球数
	 * @param m 选择的球数
	 * @return 注数
	 */
	public static long C_better(int n, int m) {
		if (n / 2 < m) m = n - m; // 当n/2小于m
		long n1 = 1, n2 = 1;
		for (long i = n, j = 1; j <= m; n1 *= i--, n2 *= j++);// 不满足条件 直接跳过
		return n1 / n2;
	}
	
	/**
	 * 冒泡排序 从小到大
	 * @param value 排序的值
	 * @return 排序结果
	 */
	public static int[] sort(int[] value){
		int temp;
		int len = value.length;
		for(int i = 0; i < len; i++){
			for(int j = len - 1; j > i; j--){
				if(value[i] > value[j]){
					temp = value[j];
					value[j] = value[i];
					value[i] = temp;
				}
			}
		}
		return value;
	}
}
