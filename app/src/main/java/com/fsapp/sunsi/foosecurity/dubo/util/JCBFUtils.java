package com.fsapp.sunsi.foosecurity.dubo.util;

import java.util.List;

import android.widget.CheckBox;
import com.fsapp.sunsi.foosecurity.R;

public class JCBFUtils {
	public static String getJZString(int position) {
		if (position == 0) {
			return "1:0";
		} else if (position == 1) {
			return "2:0";
		} else if (position == 2) {
			return "2:1";
		} else if (position == 3) {
			return "3:0";
		} else if (position == 4) {
			return "3:1";
		} else if (position == 5) {
			return "3:2";
		} else if (position == 6) {
			return "4:0";
		} else if (position == 7) {
			return "4:1";
		} else if (position == 8) {
			return "4:2";
		} else if (position == 9) {
			return "5:0";
		} else if (position == 10) {
			return "5:1";
		} else if (position == 11) {
			return "5:2";
		} else if (position == 12) {
			return "胜其他";
		} else if (position == 13) {
			return "0:0";
		} else if (position == 14) {
			return "1:1";
		} else if (position == 15) {
			return "2:2";
		} else if (position == 16) {
			return "3:3";
		} else if (position == 17) {
			return "平其他";
		} else if (position == 18) {
			return "0:1";
		} else if (position == 19) {
			return "0:2";
		} else if (position == 20) {
			return "1:2";
		} else if (position == 21) {
			return "0:3";
		} else if (position == 22) {
			return "1:3";
		} else if (position == 23) {
			return "2:3";
		} else if (position == 24) {
			return "0:4";
		} else if (position == 25) {
			return "1:4";
		} else if (position == 26) {
			return "2:4";
		} else if (position == 27) {
			return "0:5";
		} else if (position == 28) {
			return "1:5";
		} else if (position == 29) {
			return "2:5";
		} else if (position == 30) {
			return "负其他";
		}
		return null;
	}
	public static String getBDBFString(int position) {
		if (position == 0) {
			return "1:0";
		} else if (position == 1) {
			return "2:0";
		} else if (position == 2) {
			return "2:1";
		} else if (position == 3) {
			return "3:0";
		} else if (position == 4) {
			return "3:1";
		} else if (position == 5) {
			return "3:2";
		} else if (position == 6) {
			return "4:0";
		} else if (position == 7) {
			return "4:1";
		} else if (position == 8) {
			return "4:2";
		}  else if (position == 9) {
			return "胜其他";
		} else if (position == 10) {
			return "0:0";
		} else if (position == 11) {
			return "1:1";
		} else if (position == 12) {
			return "2:2";
		} else if (position == 13) {
			return "3:3";
		} else if (position == 14) {
			return "平其他";
		} else if (position == 15) {
			return "0:1";
		} else if (position == 16) {
			return "0:2";
		} else if (position == 17) {
			return "1:2";
		} else if (position == 18) {
			return "0:3";
		} else if (position == 19) {
			return "1:3";
		} else if (position == 20) {
			return "2:3";
		} else if (position == 21) {
			return "0:4";
		} else if (position == 22) {
			return "1:4";
		} else if (position == 23) {
			return "2:4";
		} else if (position == 24) {
			return "负其他";
		}
		return null;
	}
	public static String getJZBqcString(int position) {
		if (position == 0) {
			return "胜胜";
		} else if (position == 1) {
			return "胜平";
		} else if (position == 2) {
			return "胜负";
		} else if (position == 3) {
			return "平胜";
		} else if (position == 4) {
			return "平平";
		} else if (position == 5) {
			return "平负";
		} else if (position == 6) {
			return "负胜";
		} else if (position == 7) {
			return "负平";
		} else if (position == 8) {
			return "负负";
		} 
		return null;
	}
	public static String getJZSpfString(int position) {
		if (position == 3) {
			return "胜";
		} else if (position == 1) {
			return "平";
		} else if (position == 0) {
			return "负";
		}
		return null;
	}
	
	public static String getBDString(int position){
		if (position == 0) {
			return "上+单";
		} else if (position == 1) {
			return "上+双";
		} else if (position == 2) {
			return "下+单";
		} else if (position == 3) {
			return "下+双";
		} 
		return null;
	}
	public static String getBDResultString(int position){
		if (position == 0) {
			return "01";
		} else if (position == 1) {
			return "02";
		} else if (position == 2) {
			return "31";
		} else if (position == 3) {
			return "32";
		} 
		return null;
	}

	public static String getJLString(int position) {
		if (position == 0) {
			return "客胜1-5";
		} else if (position == 1) {
			return "客胜6-10";
		} else if (position == 2) {
			return "客胜11-15";
		} else if (position == 3) {
			return "客胜16-20";
		} else if (position == 4) {
			return "客胜21-25";
		} else if (position == 5) {
			return "客胜26+";
		} else if (position == 6) {
			return "主胜1-5";
		} else if (position == 7) {
			return "主胜6-10";
		} else if (position == 8) {
			return "主胜11-15";
		} else if (position == 9) {
			return "主胜16-20";
		} else if (position == 10) {
			return "主胜21-25";
		} else if (position == 11) {
			return "主胜26+";
		}
		return null;
	}

	public static String getJZZFString(int position) {
		if (position == 0) {
			return "0";
		} else if (position == 1) {
			return "1";
		} else if (position == 2) {
			return "2";
		} else if (position == 3) {
			return "3";
		} else if (position == 4) {
			return "4";
		} else if (position == 5) {
			return "5";
		} else if (position == 6) {
			return "6";
		} else if (position == 7) {
			return "7+";
		}
		return null;
	}

	public static String getJzResult(int position) {
		if (position == 0) {
			return "10";
		} else if (position == 1) {
			return "20";
		} else if (position == 2) {
			return "21";
		} else if (position == 3) {
			return "30";
		} else if (position == 4) {
			return "31";
		} else if (position == 5) {
			return "32";
		} else if (position == 6) {
			return "40";
		} else if (position == 7) {
			return "41";
		} else if (position == 8) {
			return "42";
		} else if (position == 9) {
			return "50";
		} else if (position == 10) {
			return "51";
		} else if (position == 11) {
			return "52";
		} else if (position == 12) {
			return "90";
		} else if (position == 13) {
			return "00";
		} else if (position == 14) {
			return "11";
		} else if (position == 15) {
			return "22";
		} else if (position == 16) {
			return "33";
		} else if (position == 17) {
			return "99";
		} else if (position == 18) {
			return "01";
		} else if (position == 19) {
			return "02";
		} else if (position == 20) {
			return "12";
		} else if (position == 21) {
			return "03";
		} else if (position == 22) {
			return "13";
		} else if (position == 23) {
			return "23";
		} else if (position == 24) {
			return "04";
		} else if (position == 25) {
			return "14";
		} else if (position == 26) {
			return "24";
		} else if (position == 27) {
			return "05";
		} else if (position == 28) {
			return "15";
		} else if (position == 29) {
			return "25";
		} else if (position == 30) {
			return "09";
		}
		return "";
	}
	public static String getBDResult(int position) {
		if (position == 0) {
			return "10";
		} else if (position == 1) {
			return "20";
		} else if (position == 2) {
			return "21";
		} else if (position == 3) {
			return "30";
		} else if (position == 4) {
			return "31";
		} else if (position == 5) {
			return "32";
		} else if (position == 6) {
			return "40";
		} else if (position == 7) {
			return "41";
		} else if (position == 8) {
			return "42";
		} else if (position == 9) {
			return "90";
		} else if (position == 10) {
			return "00";
		} else if (position == 11) {
			return "11";
		} else if (position == 12) {
			return "22";
		} else if (position == 13) {
			return "33";
		} else if (position == 14) {
			return "99";
		} else if (position == 15) {
			return "01";
		} else if (position == 16) {
			return "02";
		} else if (position == 17) {
			return "12";
		} else if (position == 18) {
			return "03";
		} else if (position == 19) {
			return "13";
		} else if (position == 20) {
			return "23";
		} else if (position == 21) {
			return "04";
		} else if (position == 22) {
			return "14";
		} else if (position == 23) {
			return "24";
		}else if (position == 24) {
			return "09";
		}
		return "";
	}
	public static String getJzBqcResult(int position) {
		if (position == 0) {
			return "33";
		} else if (position == 1) {
			return "31";
		} else if (position == 2) {
			return "30";
		} else if (position == 3) {
			return "13";
		} else if (position == 4) {
			return "11";
		} else if (position == 5) {
			return "10";
		} else if (position == 6) {
			return "03";
		} else if (position == 7) {
			return "01";
		} else if (position == 8) {
			return "00";
		} 
		return "";
	}
	
	public static String jzBqcResultToName(String  result) {
		if (result.equals("33")) {
			return "胜胜";
		} else if (result.equals("31")) {
			return "胜平";
		} else if (result.equals("30")) {
			return "胜负";
		} else if (result.equals("13")) {
			return "平胜";
		} else if (result.equals("11")) {
			return "平平";
		} else if (result.equals("10")) {
			return "平负";
		} else if (result.equals("03")) {
			return "负胜";
		} else if (result.equals("01")) {
			return "负平";
		} else if (result.equals("00")) {
			return "负负";
		} 
		return null;
	}
	//返回1到8的随机数
	public static int getRandomNum() {
		return (int) (Math.random() * 8)+1;
	}

	//随机返回颜色
	//随机返回颜色
	public static int getColor(int num) {
		if(num==1){
			return R.color.color1;
		}if(num==2){
			return R.color.color2;
		}if(num==3){
			return R.color.color3;
		}if(num==4){
			return R.color.color4;
		}if(num==5){
			return R.color.color5;
		}if(num==6){
			return R.color.color6;
		}if(num==7){
			return R.color.color7;
		}if(num==8){
			return R.color.color8;
		}
		return R.color.white;
	}
	
//	//随机返回颜色
//	public static int getBackGround(int num) {
//		if(num==1){
//			return R.drawable.color1_background;
//		}if(num==2){
//			return R.drawable.color2_background;
//		}if(num==3){
//			return R.drawable.color3_background;
//		}if(num==4){
//			return R.drawable.color4_background;
//		}if(num==5){
//			return R.drawable.color5_background;
//		}if(num==6){
//			return R.drawable.color6_background;
//		}if(num==7){
//			return R.drawable.color7_background;
//		}if(num==8){
//			return R.drawable.color8_background;
//		}
//		return R.drawable.color1_background;
//	}
//
	/**
	 * 把串关的checkbox变成不可点击
	 * @param num
	 */
	public static void disableCheckBox( List<CheckBox> listChecks,int num ){
		try{
			int count = listChecks.size();
			for(int i = 0; i < count; i++){
				CheckBox checkBox = listChecks.get(i);
				checkBox.setEnabled(true);
			}
			for (int i = 2; i <= num; i++) {
				if(num > 1 && count > (i-2)){
					CheckBox checkBox = listChecks.get(i-2);
					checkBox.setChecked(false);
					checkBox.setEnabled(false);
				}
			}
		}catch (Exception e) {
//			LogUtil.ExceptionLog("ViewUtil-disableCheckBox");
		}
	}
	
	/**
	 * 初始化串关checkbox
	 * @param num//选择场次数
	 */
	public static int initCGCheckBox(int num,String lid,String method){
		if(num == 1)
			return num;
		if(lid.equals(LotteryId.JCZQ)){
			if(method.equals(LotteryId.PLAY_ID_01) || method.equals(LotteryId.PLAY_ID_02)){
				if(num > 8){
					num = 8;
				}
			}else if(method.equals(LotteryId.PLAY_ID_05)|| method.equals(LotteryId.PLAY_ID_04)){
				if(num > 4){
					num = 4;
				}
			}else if(method.equals(LotteryId.PLAY_ID_03)){
				if(num > 6){
					num = 6;
				}
			}else{
				if(num > 8){
					num = 8;
				}
			}
		}else if(lid.equals(LotteryId.JCLQ)){
			if(method.equals(LotteryId.PLAY_ID_03)){
				if(num > 4){
					num = 4;
				}
			}else{
				if(num > 8){
					num = 8;
				}
			}
		}
		else if(lid.equals(LotteryId.BJDC)){
			if(method.equals(LotteryId.PLAY_ID_01)){
				if(num > 15){
					num = 15;
				}
			}else if(method.equals(LotteryId.PLAY_ID_03)|| method.equals(LotteryId.PLAY_ID_02)|| method.equals(LotteryId.PLAY_ID_04)){
				if(num > 6){
					num = 6;
				}
			}else if(method.equals(LotteryId.PLAY_ID_05)){
				if(num > 3){
					num = 3;
				}
			}
		}
		return num;
	}
	
	public static String getZC6CString(String position) {
		if (position.equals("0")) {
			return "3";
		} else if (position.equals("1")) {
			return "1";
		} else if (position.equals("2")) {
			return "0";
		} else if (position.equals("3")) {
			return "3";
		} else if (position.equals("4")) {
			return "1";
		} else if (position.equals("5")) {
			return "0";
		} 
		return null;
	}
	public static String getJLResult(int position) {
		if (position == 0) {
			return "01";
		} else if (position == 1) {
			return "02";
		} else if (position == 2) {
			return "03";
		} else if (position == 3) {
			return "04";
		} else if (position == 4) {
			return "05";
		} else if (position == 5) {
			return "06";
		} else if (position == 6) {
			return "11";
		} else if (position == 7) {
			return "12";
		} else if (position == 8) {
			return "13";
		} else if (position == 9) {
			return "14";
		} else if (position == 10) {
			return "15";
		} else if (position == 11) {
			return "16";
		}
		return "";
	}
}
