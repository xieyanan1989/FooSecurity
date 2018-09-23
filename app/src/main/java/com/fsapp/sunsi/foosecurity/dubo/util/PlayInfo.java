package com.fsapp.sunsi.foosecurity.dubo.util;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;
import com.fsapp.sunsi.foosecurity.R;

import java.util.ArrayList;

public class PlayInfo {

	public static ArrayList<PlayBean> getPlay(String lid){
		/** 存放玩法 所当前彩种所支持的所有玩法传进来 **/
		ArrayList<PlayBean> mListPlayBeans = new ArrayList<PlayBean>();
		if(lid.equals(LotteryId.JCZQ)){
			PlayBean mBean1 = new PlayBean();
			mBean1.setPlayName("胜平负");
			mBean1.setPlayId("01");
			mBean1.setPollId("01");
			mBean1.setIcon(R.mipmap.jczq_spf);
			mListPlayBeans.add(mBean1);
			
			PlayBean mBean2 = new PlayBean();
			mBean2.setPlayName("让球胜平负");
			mBean2.setPlayId("02");
			mBean2.setPollId("01");
			mBean2.setIcon(R.mipmap.jczq_rqspf);
			mListPlayBeans.add(mBean2);
			
			PlayBean mBean3 = new PlayBean();
			mBean3.setPlayName("总进球数");
			mBean3.setPlayId("03");
			mBean3.setPollId("01");
			mBean3.setIcon(R.mipmap.jczq_zjqs);
			mListPlayBeans.add(mBean3);
			
			PlayBean mBean4 = new PlayBean();
			mBean4.setPlayName("半全场");
			mBean4.setPlayId("04");
			mBean4.setPollId("01");
			mBean4.setIcon(R.mipmap.jczq_bqc);
			mListPlayBeans.add(mBean4);

			PlayBean mBean5 = new PlayBean();
			mBean5.setPlayName("比分");
			mBean5.setPlayId("05");
			mBean5.setPollId("01");
			mBean5.setIcon(R.mipmap.jczq_bf);
			mListPlayBeans.add(mBean5);
			
			PlayBean mBean6 = new PlayBean();
			mBean6.setPlayName("混合过关");
			mBean6.setPlayId("06");
			mBean6.setPollId("01");
			mBean6.setIcon(R.mipmap.jczq_hhgg);
			mListPlayBeans.add(mBean6);
		}else if(lid.equals(LotteryId.JCLQ)){
			PlayBean mBean1 = new PlayBean();
			mBean1.setPlayName("胜负");
			mBean1.setPlayId("01");
			mBean1.setPollId("01");
			mBean1.setIcon(R.mipmap.play_jclq_01);
			mListPlayBeans.add(mBean1);

			PlayBean mBean2 = new PlayBean();
			mBean2.setPlayName("让分胜负");
			mBean2.setPlayId("02");
			mBean2.setPollId("01");
			mBean2.setIcon(R.mipmap.play_jclq_02);
			mListPlayBeans.add(mBean2);

			PlayBean mBean3 = new PlayBean();
			mBean3.setPlayName("胜分差");
			mBean3.setPlayId("03");
			mBean3.setPollId("01");
			mBean3.setIcon(R.mipmap.play_jclq_03);
			mListPlayBeans.add(mBean3);

			PlayBean mBean4 = new PlayBean();
			mBean4.setPlayName("大小分");
			mBean4.setPlayId("04");
			mBean4.setPollId("01");
			mBean4.setIcon(R.mipmap.play_jclq_04);
			mListPlayBeans.add(mBean4);

			PlayBean mBean5 = new PlayBean();
			mBean5.setPlayName("混合过关");
			mBean5.setPlayId("05");
			mBean5.setPollId("01");
			mBean5.setIcon(R.mipmap.play_jclq_05);
			mListPlayBeans.add(mBean5);
		}
		
		return mListPlayBeans;
	}
	
	public static String getShowstr(String lid, String playId, String pollId, String number){
		number = number.replace(",", " ");
		number = number.replace(";", "|");
		
		if(lid.equals(LotteryId.SSC)){
			if(playId.equals("13")){
				number = number.replace("0", "小");
				number = number.replace("9", "大");
				number = number.replace("1", "单");
				number = number.replace("2", "双");
			}else if(playId.equals("14")){
				String[] num = number.split("\\|");
				num[0] = num[0].replace("0", "小");
				num[0] = num[0].replace("9", "大");
				StringBuilder sb = new StringBuilder();
				for(String n: num){
					sb.append("|");
					sb.append(n);
				}
				number = sb.deleteCharAt(0).toString();
			}else if(playId.equals("15")){
				String[] num = number.split("\\|");
				num[0] = num[0].replace("1", "一区");
				num[0] = num[0].replace("2", "二区");
				num[0] = num[0].replace("3", "三区");
				num[0] = num[0].replace("4", "四区");
				num[0] = num[0].replace("5", "五区");
				StringBuilder sb = new StringBuilder();
				for(String n: num){
					sb.append("|");
					sb.append(n);
				}
				number = sb.deleteCharAt(0).toString();
			}
		}else if(lid.equals(LotteryId.YTDJ)){
			number = number.replace("*", "|");
		}
		return splitNum(number);
	}
	
	private static String splitNum(String number){
		StringBuilder sb = new StringBuilder();
		if(number.contains("#")){
			String[] num = number.split("#");
			sb.append(splitTowber(num[0]));
			sb.append(" + ");
			sb.append(splitTowber(num[1]));
		}else{
			sb.append(splitTowber(number));
		}
		return sb.toString();
	}
	
	private static String splitTowber(String number){
		StringBuilder sb = new StringBuilder();
		if(number.contains("@")){
			String[] ns = number.split("@");
			sb.append("胆：");
			sb.append(ns[0]);
			sb.append(" 拖：");
			sb.append(ns[1]);
			return sb.toString();
		}else if(number.contains("*")){
			String[] ns = number.split("\\*");
			sb.append("双号：");
			sb.append(ns[0]);
			sb.append(" 单号：");
			sb.append(ns[1]);
			return sb.toString();
		}
		return number;
	}
}
