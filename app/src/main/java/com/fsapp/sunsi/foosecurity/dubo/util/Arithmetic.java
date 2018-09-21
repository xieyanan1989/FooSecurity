package com.fsapp.sunsi.foosecurity.dubo.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Arithmetic {

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

	/**
	 * 3D直选注数
	 * @param balls
	 * @return
	 */
	public static Integer threeSumArithmetic(String[] balls) {
		Integer sum = 0;
		for(int x = 0;x <balls.length;x++){
			int ballNumber = Integer.parseInt(balls[x]);
			sum = sum+threeSumSize(ballNumber);
		}
		return sum;
	}

	/**
	 * 组3 复式注数
	 * @param length 号码长度
	 * @return
	 */

	public static Integer groupThreeArithmetic(int length) {
		// TODO Auto-generated method stub
		return length*(length-1);
	}

	/**
	 * 3d组3 和值注数
	 * @return
	 */
	public static Integer getSummationPermutation(String[] G){
		int h = 0;
		for (int g = 0; g < G.length; g++) {
			int f = Integer.parseInt(G[g]);
			for (int e = 0; e < 9; e++) {
				for (int d = e; d < 10; d++) {
					for (int c = d; c < 10; c++) {
						if (e + d + c == f) {
							if (!(e == d && e == c) && (e == d || e == c || d == c)) {
								h++;
							}
						}
					}
				}
			}
		}
		return h;
	}

	/**
	 * 3d组6  和值注数
	 * @return
	 */
	public static Integer getSummationPermutationSix(String[] G){
		int h = 0;
		for (int g = 0; g < G.length; g++) {
			int f = Integer.parseInt(G[g]);
			for (int e = 0; e < 8; e++) {
				for (int d = e + 1; d < 9; d++) {
					for (int c = d + 1; c < 10; c++) {
						if (e + d + c == f) {
							h++;
						}
					}
				}
			}
		}
		return h;
	}

	/**
	 * 时时彩二星和值
	 * @param balls
	 * @return
	 */
	public static Integer addCountArithmetic(String[] balls) {
		Integer sum = 0;
		for(int x = 0;x <balls.length;x++){
			int ballNumber = Integer.parseInt(balls[x]);
			sum = sum+twoStarSize(ballNumber);
		}
		return sum;
	}

	/**
	 * 时时彩二星组选复式注数
	 * @param MaxNum
	 * @param FixNum
	 * @return
	 */
	public static Integer doubleCount(int MaxNum, int FixNum){
		return CountP(MaxNum, FixNum) / CountF(FixNum);
	}

	/**
	 * 组三 胆拖
	 */
	public static Integer threeGroundArithmetic(int danLength,int tuoLength,int geshu){

		return CountNum(geshu,danLength)*CountNum(tuoLength,geshu-danLength);
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
	private static int CountNum(int MaxNum, int FixNum)
	{
		return CountP(MaxNum, FixNum) / CountF(FixNum);
	}

	/**
	 * 时时彩二星和值
	 * @param boolNumber 球号码
	 * @return
	 */
	private static int twoStarSize(int boolNumber) {
		Integer number = 0;
		switch (boolNumber) {
			case 0:
			case 18:
				return number=1;
			case 1:
			case 17:
				return number=2;
			case 2:
			case 16:
				return number=3;
			case 3:
			case 15:
				return number=4;
			case 4:
			case 14:
				return number=5;
			case 5:
			case 13:
				return number=6;
			case 6:
			case 12:
				return number=7;
			case 7:
			case 11:
				return number=8;
			case 8:
			case 10:
				return number=9;
			case 9:
				return number=10;
		}
		return number;
	}


	private static int threeSumSize(int boolNumber){
		Integer number = 0;
		switch(boolNumber){
			case 0:
			case 27:
				return number=1;
			case 1:
			case 26:
				return number=3;
			case 2:
			case 25:
				return number=6;
			case 3:
			case 24:
				return number=10;
			case 4:
			case 23:
				return number=15;
			case 5:
			case 22:
				return number=21;
			case 6:
			case 21:
				return number=28;
			case 7:
			case 20:
				return number=36;
			case 8:
			case 19:
				return number=45;
			case 9:
			case 18:
				return number=55;
			case 10:
			case 17:
				return number=63;
			case 11:
			case 16:
				return number=69;
			case 12:
			case 15:
				return number=73;
			case 13:
			case 14:
				return number=75;
		}
		return number;
	}

	/**
	 *  复式注数
	 * @param length
	 * @return
	 */
	public static Integer sumArithmetic(Integer length,Integer fixNum) {
		return combine(length,fixNum);
	}

	/**
	 *
	 * @param balls
	 * @param fixNum
	 * @return
	 */
	public static Integer sumArithmetic2(String[] balls,Integer fixNum) {
		int f = 1;
		for (int e = 0, c = balls.length; e < c; e++) {
			int a = balls[e].length();
			f *= combine(a, fixNum);
		}
		return f;
	}

	/**
	 *
	 * @param length
	 * @param fixNum
	 * @return
	 */
	private static Integer combine(Integer length,Integer fixNum){
		Integer b =permute(length, fixNum);
		b /= permute(fixNum, fixNum);
		return b;
	}

	private static Integer permute(Integer number, Integer fixNum) {
		int b = 1;
		for (int c = 0; c < fixNum; c++) {
			b *= (number - c);
		}
		return b;
	}

	/**
	 * 任一 注数
	 * @param balls
	 * @return
	 */
	public static Integer yourselfOne(String[] balls) {
		int d = 0;
		for (int c = 0, b = balls.length; c < b; c++) {
			if (balls[c].contains("_")) {
				continue;
			}
			d += Integer.parseInt(balls[c]);
		}
		return d;
	}

	/**
	 * 任一 复式注数
	 * @param balls
	 * @return
	 */
	public static Integer yourselfTwo(String[] balls) {
		int d = 0;
		for (int c = 0, b = balls.length; c < b; c++) {
			String[] i = balls[c].split("\\,");
			for(int k =0;k<i.length;k++){
				if (i[k].contains("_")) {
					continue;
				}
				d += i[k].length();
			}
		}
		return d;
	}

	/**
	 *
	 * @param length
	 * @param index 胆码下标开始位置
	 * @return
	 */
	public static List convertFromPlanContentElementArray(int length,int index){
		List strArr = new ArrayList();
		for (int i = 0; i < length; i++) {
			index++;
			strArr.add(index);
		}
		return strArr;
	}
	public static List fullCombinationDantuo(int required,List danArr,List tuoArr){
		int danN = 0;
		if (danArr != null) {
			danN = danArr.size();
		}
		int tuoN = tuoArr.size();

		// 胆码数量 + 拖码数量 >= 一注需要的总数量
		if (danN + tuoN < required) {
			return null;
		}

		// 需要从拖选择的号码个数
		int choose = required - danN;

		// 获取拖码的组合结果列表
		List combinedList = fullCombination(tuoArr, choose);

		// 与胆码组合
		if(danArr != null){
			for (int i = 0, max = combinedList.size(); i < max; i++) {
				List list = (List)combinedList.get(i);
				for(int j = 0;j<danArr.size();j++){
					list.add(danArr.get(j));
				}
			}
		}

		return combinedList;
	}

	public static List fullCombination(List elementArr,int choose){
		List combinedResult = new ArrayList();

		int total = elementArr.size();

		// 保存查找到的元素的索引
		List index = new ArrayList();
		for (int i = 0; i <= choose; i++) {
			index.add(i - 1);
		}

		int key = choose;
		boolean flag = true;
		while ( (Integer) index.get(0)== -1) {
			if (flag) {
				// 查找到符合要求的组合结果
				List foundItem = new ArrayList();
				for (int i = 1; i <= choose; i++) {
					// 通过元素索引读取实际元素
					foundItem.add(elementArr.get((Integer) index.get(i)));
				}
				combinedResult.add(foundItem);
				flag = false;
			}
			int a = (Integer) index.get(key);
			a++;
			index.set(key, a);
			if ((Integer) index.get(key) == total) {
				// 当前位置已无数字可选，回溯
//                index.get(key) = 0;
				index.set(key, 0);
				key--;
				continue;
			}
			if (key < choose) {
				// 更新当前位置的下一位置的数字
				key++;
				index.set(key, index.get(key-1));
				continue;
			}
			if (key == choose) {
				flag = true;
			}
		}
		return combinedResult;
	}
	/**
	 *
	 * @param bunch 串数组
	 * @param tuoArr  拖数组 保存 map的key值
	 * @param danArr  胆数组 保存 map的key值
	 * @param map key保存期号，value保存每期个数
	 * @return
	 */
	public static Integer multipMethod(String[] bunch ,List danArr,List tuoArr,Map map){
		int bettype = 0;
		for (int i = 0; i < bunch.length; i++) {
			String[] eachBunch = bunch[i].split("\\*");
			int required =Integer.parseInt(eachBunch[0]);//得到是几串1
//			List fullCombinedArr = fullCombinationDantuo(required, danArr, tuoArr);
//
//			bettype += countCombinedArr(fullCombinedArr,map);

			int befRequired =Integer.parseInt(eachBunch[1]);
			//胆拖的各个排列
			List fullCombinedArr = fullCombinationDantuo(required, danArr, tuoArr);
			if(befRequired ==1){
				//每串下面的注数
				bettype += countCombinedArr(fullCombinedArr,map);
			}else{
				//多串下面的注数
				bettype += countCombinedArr2(required,befRequired,fullCombinedArr,map);
			}
		}
		return bettype;
	}

	/**
	 *
	 * @param arr 分组后的场次
	 * @param map 期次与投注个数
	 * @return
	 */
	public static int countCombinedArr(List arr,Map<String, List> map) {
		int count = 0;
		for (int i = 0, imax = arr.size(); i < imax; i++) {
			count += countN_1((List)arr.get(i),map);
		}
		return count;
	}

	private static int countN_1(List arr, Map<String, List> map) {
		int count = 1;
		for (int i = 0, imax = arr.size(); i < imax; i++) {
			int single_selected_count = 0;
			List jmax = map.get(arr.get(i));
			for (int j = 0; j < jmax.size(); j++) {
				single_selected_count++;
			}
			count *= single_selected_count;
		}
		return count;
	}
	/**
	 *
	 * @param arr 分组后的场次
	 * @param map 期次与投注个数
	 * @return
	 */
	public static BigDecimal countPrizeCombinedArr(List arr,Map<String, List> map) {
		BigDecimal count = new BigDecimal(0);
		for (int i = 0; i < arr.size(); i++) {
			count = count.add(countPrize((List)arr.get(i),map));
		}
		return count;
	}

	private static BigDecimal countPrize(List arr, Map<String, List> map) {
		BigDecimal count = new BigDecimal("1");
		for (int i = 0; i < arr.size(); i++) {
			List jmax = map.get(arr.get(i));
			BigDecimal max = new BigDecimal((String)jmax.get(jmax.size()-1));
			count = count.multiply(max);
		}
		return count;
	}

	/**
	 * 计算多串
	 * @param befRequired
	 * @param required
	 * @param map
	 * @return
	 */
	public static int countCombinedArr2(int required, int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		for (int i = 0, imax = arr.size(); i < imax; i++) {
			count += countN_2((List)arr.get(i),map,required,befRequired);
		}
		return count;
	}
	/**
	 * 将每一次计算的结果相加
	 * @param map
	 * @param befRequired
	 * @param required
	 * @return
	 */
	private static int countN_2(List arr, Map<String, List> map, int required, int befRequired) {
		int count = 1;
		switch (required) {
			case 3:
				count = moreThree(befRequired,arr,map);
				break;
			case 4:
				count = moreFour(befRequired,arr,map);
				break;
			case 5:
				count = moreFive(befRequired,arr,map);
				break;
			case 6:
				count = moreSix(befRequired,arr,map);
				break;
			case 7:
				count = moreSeven(befRequired,arr,map);
				break;
			case 8:
				count = moreEight(befRequired,arr,map);
				break;
		}
		return count;
	}
	/**
	 * 八串多
	 * @param befRequired
	 * @param arr
	 * @param map
	 * @return
	 */
	private static int moreEight(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		int d =map.get(arr.get(3)).size();
		int e =map.get(arr.get(4)).size();
		int f =map.get(arr.get(5)).size();
		int g =map.get(arr.get(6)).size();
		int h =map.get(arr.get(7)).size();
		switch (befRequired) {
			case 8:
				count =  a * b * c * d * e * f * g + a * b * c * d * e * f * h + a * b * c * d * e * g * h + a * b * c * d * f * g * h + a * b * c * e * f * g * h + a * b * d * e * f * g * h + a * c * d * e * f * g * h + b * c * d * e * f * g * h;
				break;
			case 9:
				count =   a * b * c * d * e * f * g * h + a * b * c * d * e * f * g + a * b * c * d * e * f * h + a * b * c * d * e * g * h + a * b * c * d * f * g * h + a * b * c * e * f * g * h + a * b * d * e * f * g * h + a * c * d * e * f * g * h + b * c * d * e * f * g * h;
				break;
			case 28:
				count = a * b * c * d * e * f + a * b * c * d * e * g + a * b * c * d * e * h + a * b * c * d * f * g + a * b * c * d * f * h + a * b * c * d * g * h + a * b * c * e * f * g + a * b * c * e * f * h + a * b * c * e * g * h + a * b * c * f * g * h + a * b * d * e * f * g + a * b * d * e * f * h + a * b * d * e * g * h + a * b * d * f * g * h + a * b * e * f * g * h + a * c * d * e * f * g + a * c * d * e * f * h + a * c * d * e * g * h + a * c * d * f * g * h + a * c * e * f * g * h + a * d * e * f * g * h + b * c * d * e * f * g + b * c * d * e * f * h + b * c * d * e * g * h + b * c * d * f * g * h + b * c * e * f * g * h + b * d * e * f * g * h + c * d * e * f * g * h;
				break;
			case 56:
				count = a * b * c * d * e + a * b * c * d * f + a * b * c * d * g + a * b * c * d * h + a * b * c * e * f + a * b * c * e * g + a * b * c * e * h + a * b * c * f * g + a * b * c * f * h + a * b * c * g * h + a * b * d * e * f + a * b * d * e * g + a * b * d * e * h + a * b * d * f * g + a * b * d * f * h + a * b * d * g * h + a * b * e * f * g + a * b * e * f * h + a * b * e * g * h + a * b * f * g * h + a * c * d * e * f + a * c * d * e * g + a * c * d * e * h + a * c * d * f * g + a * c * d * f * h + a * c * d * g * h + a * c * e * f * g + a * c * e * f * h + a * c * e * g * h + a * c * f * g * h + a * d * e * f * g + a * d * e * f * h + a * d * e * g * h + a * d * f * g * h + a * e * f * g * h + b * c * d * e * f + b * c * d * e * g + b * c * d * e * h + b * c * d * f * g + b * c * d * f * h + b * c * d * g * h + b * c * e * f * g + b * c * e * f * h + b * c * e * g * h + b * c * f * g * h + b * d * e * f * g + b * d * e * f * h + b * d * e * g * h + b * d * f * g * h + b * e * f * g * h + c * d * e * f * g + c * d * e * f * h + c * d * e * g * h + c * d * f * g * h + c * e * f * g * h + d * e * f * g * h;
				break;
			case 70:
				count = a * b * c * d + a * b * c * e + a * b * c * f + a * b * c * g + a * b * c * h + a * b * d * e + a * b * d * f + a * b * d * g + a * b * d * h + a * b * e * f + a * b * e * g + a * b * e * h + a * b * f * g + a * b * f * h + a * b * g * h + a * c * d * e + a * c * d * f + a * c * d * g + a * c * d * h + a * c * e * f + a * c * e * g + a * c * e * h + a * c * f * g + a * c * f * h + a * c * g * h + a * d * e * f + a * d * e * g + a * d * e * h + a * d * f * g + a * d * f * h + a * d * g * h + a * e * f * g + a * e * f * h + a * e * g * h + a * f * g * h + b * c * d * e + b * c * d * f + b * c * d * g + b * c * d * h + b * c * e * f + b * c * e * g + b * c * e * h + b * c * f * g + b * c * f * h + b * c * g * h + b * d * e * f + b * d * e * g + b * d * e * h + b * d * f * g + b * d * f * h + b * d * g * h + b * e * f * g + b * e * f * h + b * e * g * h + b * f * g * h + c * d * e * f + c * d * e * g + c * d * e * h + c * d * f * g + c * d * f * h + c * d * g * h + c * e * f * g + c * e * f * h + c * e * g * h + c * f * g * h + d * e * f * g + d * e * f * h + d * e * g * h + d * f * g * h + e * f * g * h;
				break;
			case 247:
				count = (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) * (g + 1) * (h + 1) - (a + b + c + d + e + f + g + h + 1);
				break;
		}
		return count;
	}

	/**
	 * 七串多
	 * @param befRequired
	 * @param arr
	 * @param map
	 * @return
	 */
	private static int moreSeven(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		int d =map.get(arr.get(3)).size();
		int e =map.get(arr.get(4)).size();
		int f =map.get(arr.get(5)).size();
		int g =map.get(arr.get(6)).size();
		switch (befRequired) {
			case 7:
				count = a * b * c * d * e * f + a * b * c * d * e * g + a * b * c * d * f * g + a * b * c * e * f * g + a * b * d * e * f * g + a * c * d * e * f * g + b * c * d * e * f * g;
				break;
			case 8:
				count =  a * b * c * d * e * f * g + a * b * c * d * e * f + a * b * c * d * e * g + a * b * c * d * f * g + a * b * c * e * f * g + a * b * d * e * f * g + a * c * d * e * f * g + b * c * d * e * f * g;
				break;
			case 21:
				count = a * b * c * d * e + a * b * c * d * f + a * b * c * d * g + a * b * c * e * f + a * b * c * e * g + a * b * c * f * g + a * b * d * e * f + a * b * d * e * g + a * b * d * f * g + a * b * e * f * g + a * c * d * e * f + a * c * d * e * g + a * c * d * f * g + a * c * e * f * g + a * d * e * f * g + b * c * d * e * f + b * c * d * e * g + b * c * d * f * g + b * c * e * f * g + b * d * e * f * g + c * d * e * f * g;
				break;
			case 35:
				count = a * b * c * d + a * b * c * e + a * b * c * f + a * b * c * g + a * b * d * e + a * b * d * f + a * b * d * g + a * b * e * f + a * b * e * g + a * b * f * g + a * c * d * e + a * c * d * f + a * c * d * g + a * c * e * f + a * c * e * g + a * c * f * g + a * d * e * f + a * d * e * g + a * d * f * g + a * e * f * g + b * c * d * e + b * c * d * f + b * c * d * g + b * c * e * f + b * c * e * g + b * c * f * g + b * d * e * f + b * d * e * g + b * d * f * g + b * e * f * g + c * d * e * f + c * d * e * g + c * d * f * g + c * e * f * g + d * e * f * g;
				break;
			case 120:
				count = (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) * (g + 1) - (a + b + c + d + e + f + g + 1);
				break;
		}
		return count;
	}

	/**
	 * 六串多
	 * @param befRequired
	 * @param arr
	 * @param map
	 * @return
	 */
	private static int moreSix(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		int d =map.get(arr.get(3)).size();
		int e =map.get(arr.get(4)).size();
		int f =map.get(arr.get(5)).size();
		switch (befRequired) {
			case 6:
				count = a * b * c * d * e + a * b * c * d * f + a * b * c * e * f + a * b * d * e * f + a * c * d * e * f + b * c * d * e * f;
				break;
			case 7:
				count =  a * b * c * d * e * f + a * b * c * d * e + a * b * c * d * f + a * b * c * e * f + a * b * d * e * f + a * c * d * e * f + b * c * d * e * f;
				break;
			case 15:
				count = a * b + a * c + a * d + a * e + a * f + b * c + b * d + b * e + b * f + c * d + c * e + c * f + d * e + d * f + e * f;
				break;
			case 20:
				count = a * b * c + a * b * d + a * b * e + a * b * f + a * c * d + a * c * e + a * c * f + a * d * e + a * d * f + a * e * f + b * c * d + b * c * e + b * c * f + b * d * e + b * d * f + b * e * f + c * d * e + c * d * f + c * e * f + d * e * f;
				break;
			case 22:
				count = a * b * c * d * e * f + a * b * c * d * e + a * b * c * d * f + a * b * c * e * f + a * b * d * e * f + a * c * d * e * f + b * c * d * e * f + a * b * c * d + a * b * c * e + a * b * c * f + a * b * d * e + a * b * d * f + a * b * e * f + a * c * d * e + a * c * d * f + a * c * e * f + a * d * e * f + b * c * d * e + b * c * d * f + b * c * e * f + b * d * e * f + c * d * e * f;
				break;
			case 35:
				count =  a * b * c + a * b * d + a * b * e + a * b * f + a * c * d + a * c * e + a * c * f + a * d * e + a * d * f + a * e * f + b * c * d + b * c * e + b * c * f + b * d * e + b * d * f + b * e * f + c * d * e + c * d * f + c * e * f + d * e * f + a * b + a * c + a * d + a * e + a * f + b * c + b * d + b * e + b * f + c * d + c * e + c * f + d * e + d * f + e * f;
				break;
			case 42:
				count = (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) - (a * (b + c + d + e + f + 1) + b * (c + d + e + f + 1) + c * (d + e + f + 1) + d * (e + f + 1) + (e + 1) * (f + 1));
				break;
			case 50:
				count =  (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) - (a + b + c + d + e + f + 1) - (a * b * c * d * e + a * b * c * d * f + a * b * c * e * f + a * b * d * e * f + a * c * d * e * f + b * c * d * e * f + a * b * c * d * e * f);
				break;
			case 57:
				count = (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) - (a + b + c + d + e + f + 1);
				break;
			case 63:
				count =  (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) * (f + 1) - 1;
				break;
		}
		return count;
	}

	/**
	 * 五串多
	 * @param befRequired
	 * @param arr
	 * @param map
	 * @return
	 */
	private static int moreFive(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		int d =map.get(arr.get(3)).size();
		int e =map.get(arr.get(4)).size();
		switch (befRequired) {
			case 5:
				count = a * b * c * d + a * b * c * e + a * b * d * e + a * c * d * e + b * c * d * e;
				break;
			case 6:
				count =  a * b * c * d * e + a * b * c * d + a * b * c * e + a * b * d * e + a * c * d * e + b * c * d * e;
				break;
			case 10:
				count = a * b + a * c + a * d + a * e + b * c + b * d + b * e + c * d + c * e + d * e;
				break;
			case 16:
				count =   (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) - (a * (b + c + d + e + 1) + b * (c + d + e + 1) + c * (d + e + 1) + (d + 1) * (e + 1));
				break;
			case 20:
				count =  a * b * c + a * b * d + a * b * e + a * c * d + a * c * e + a * d * e + b * c * d + b * c * e + b * d * e + c * d * e + a * b + a * c + a * d + a * e + b * c + b * d + b * e + c * d + c * e + d * e;
				break;
			case 26:
				count =  (a + 1) * (b + 1) * (c + 1) * (d + 1) * (e + 1) - (a + b + c + d + e + 1);
				break;
		}
		return count;
	}

	/**
	 * 四串多
	 * @param befRequired
	 * @param arr
	 * @param map
	 * @return
	 */
	private static int moreFour(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		int d =map.get(arr.get(3)).size();
		switch (befRequired) {
			case 4:
				count = a * b * c + a * b * d + a * c * d + b * c * d;
				break;
			case 5:
				count =  (a + 1) * (b + 1) * (c + 1) * (d + 1) - (a * (b + c + d + 1) + b * (c + d + 1) + (c + 1) * (d + 1));
				break;
			case 6:
				count =  a * b + a * c + a * d + b * c + b * d + c * d;
				break;
			case 11:
				count =  (a + 1) * (b + 1) * (c + 1) * (d + 1) - (a + b + c + d + 1);
				break;
		}
		return count;
	}

	/**
	 * 3串多
	 * @param befRequired
	 * @param map
	 * @param arr
	 * @return
	 */
	private static int moreThree(int befRequired, List arr, Map<String, List> map) {
		int count = 0;
		int a =map.get(arr.get(0)).size();
		int b =map.get(arr.get(1)).size();
		int c =map.get(arr.get(2)).size();
		switch (befRequired) {
			case 3:
				count = a * b + a * c + b * c;
				break;
			case 4:
				count =  a * b * c + a * b + a * c + b * c;
				break;
		}
		return count;
	}

}
