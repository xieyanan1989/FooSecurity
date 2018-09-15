package com.fsapp.sunsi.foosecurity.dubo.bean;

import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JZMatchBean  implements CpType{
	/** 场次号 **/
	private String matched;
	/** 场次号 **/
	private String matchno;
	/** 场次号 **/
	private String matchnum;
	/** 赛事名称 **/
	private String racename;
	/** 比赛时间 **/
	private String playtime;
	/** 时间格式 年-月-日 **/
	private String longtime;
	/** 北单比赛时间  年-月-日  时-分-秒**/
	private String bdtime;
	/** 停售时间 **/
	private String endtime;
	/** 主队名称 **/
	private String hometeam;
	/** 客队名称 **/
	private String guestteam;
	/** 让球 **/
	private String letcount;
	/** sp列表 **/
	private String odds;
	/** 期次 **/
	private String issue;
	/** 星期 **/
	private String week;
	/** 星期 **/
	private String weekName;
	/** 赛事颜色 **/
	private String color;
	/** 单关 **/
	private String single;
	

	private boolean hasDan;// 是否设胆
	public int count;// 计数器
	private String userSelect;// 用于存放用户选择的最终结果
	private String userSelectSp;// 用于存放用户选择的最终结果sp值，0:0/1:0/1/2:0，场次之间用：号，选项之间用/；
	public String[] pvList;// 初始赔率集合
	private String userSelectBuy;// 用于存放用户投注购买参数
	public ArrayList<String> jlsfList = new ArrayList<String>();// 竞彩篮球list-投注数据结果集合
	public ArrayList<String> jlrfsfList = new ArrayList<String>();// 竞彩篮球list-投注数据结果集合
	public ArrayList<String> jldxfList = new ArrayList<String>();// 竞彩篮球list-投注数据结果集合
	public ArrayList<String> jlsfcList = new ArrayList<String>();// 竞彩篮球list-投注数据结果集合
	public Map<String, ArrayList<String>> jlheggmap = new HashMap<String, ArrayList<String>>();// 竞彩篮球混合过关map
	public ArrayList<String> jlsfPvList = new ArrayList<String>();// 竞彩篮球list-赔率集合
	public ArrayList<String> jlrfsfPvList = new ArrayList<String>();// 竞彩篮球list-赔率集合
	public ArrayList<String> jldxfPvList = new ArrayList<String>();// 竞彩篮球list-赔率集合
	public ArrayList<String> jlsfcPvList = new ArrayList<String>();// 竞彩篮球list-赔率集合
	public Map<String, ArrayList<String>> jlheggPvmap = new HashMap<String, ArrayList<String>>();// 竞彩篮球赔率混合过关map

	public ArrayList<String> jzbfList = new ArrayList<String>();// 竞彩足球比分list
	public ArrayList<String> jzzjqsList = new ArrayList<String>();// 竞彩足球总进球list
	public ArrayList<String> jzbqcList = new ArrayList<String>();// 竞彩足球半全场list
	public ArrayList<String> jzspfList = new ArrayList<String>();// 竞彩足球胜平负list
	public ArrayList<String> jzrqspfLiset = new ArrayList<String>();// 竞彩足球让球胜平负list
	public Map<String, ArrayList<String>> jzheggmap = new HashMap<String, ArrayList<String>>();// 竞彩足球混合过关map

	public ArrayList<String> jzbfPvList = new ArrayList<String>();// 竞彩足球比分list-赔率集合
	public ArrayList<String> jzzjqsPvList = new ArrayList<String>();// 竞彩足球总进球数list-赔率集合
	public ArrayList<String> jzbqcPvList = new ArrayList<String>();// 竞彩足球半全场list-赔率集合
	public ArrayList<String> jzspfPvList = new ArrayList<String>();// 竞彩足球胜平负list
	public ArrayList<String> jzrqspfPvLiset = new ArrayList<String>();// 竞彩足球让球胜平负list
	public Map<String, ArrayList<String>> jzheggPvmap = new HashMap<String, ArrayList<String>>();// 竞彩足球混合过关赔率map

	public ArrayList<String> bdsxdsList = new ArrayList<String>();// 北单上下单双list
	public ArrayList<String> bdsxdsPvList = new ArrayList<String>();// 北单上下单双赔率list

	public ArrayList<String> zcList = new ArrayList<String>();// 足彩list
	public ArrayList<String> zcPvList = new ArrayList<String>();// 足彩赔率list

	public ArrayList<String> zcbcList = new ArrayList<String>();// 足彩6/4场
	public ArrayList<String> zcbcPvList = new ArrayList<String>();
	public ArrayList<String> zcqcList = new ArrayList<String>();// 足彩6/4场
	public ArrayList<String> zcqcPvList = new ArrayList<String>();


	public String getUserSelectBuy() {
		return userSelectBuy;
	}

	public void setUserSelectBuy(String userSelectBuy) {
		this.userSelectBuy = userSelectBuy;
	}

	public String[] getPvList() {
		return pvList;
	}

	public void setPvList(String[] pvList) {
		this.pvList = pvList;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getWeekName() {
		return weekName;
	}

	public void setWeekName(String weekName) {
		this.weekName = weekName;
	}

	public boolean isHasDan() {
		return hasDan;
	}

	public void setHasDan(boolean hasDan) {
		this.hasDan = hasDan;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public String getUserSelect() {
		return userSelect;
	}

	public void setUserSelect(String userSelect) {
		this.userSelect = userSelect;
	}

	public String getUserSelectSp() {
		return userSelectSp;
	}

	public void setUserSelectSp(String userSelectSp) {
		this.userSelectSp = userSelectSp;
	}

	public ArrayList<String> getJlsfcList() {
		return jlsfcList;
	}

	public void setJlsfcList(ArrayList<String> jlsfcList) {
		this.jlsfcList = jlsfcList;
	}

	public ArrayList<String> getJzbfList() {
		return jzbfList;
	}

	public void setJzbfList(ArrayList<String> jzbfList) {
		this.jzbfList = jzbfList;
	}

	public ArrayList<String> getJzzjqsList() {
		return jzzjqsList;
	}

	public void setJzzjqsList(ArrayList<String> jzzjqsList) {
		this.jzzjqsList = jzzjqsList;
	}

	public ArrayList<String> getJzbqcList() {
		return jzbqcList;
	}

	public void setJzbqcList(ArrayList<String> jzbqcList) {
		this.jzbqcList = jzbqcList;
	}

	public ArrayList<String> getJlsfcPvList() {
		return jlsfcPvList;
	}

	public void setJlsfcPvList(ArrayList<String> jlsfcPvList) {
		this.jlsfcPvList = jlsfcPvList;
	}

	public ArrayList<String> getJzbfPvList() {
		return jzbfPvList;
	}

	public void setJzbfPvList(ArrayList<String> jzbfPvList) {
		this.jzbfPvList = jzbfPvList;
	}

	public ArrayList<String> getJzzjqsPvList() {
		return jzzjqsPvList;
	}

	public void setJzzjqsPvList(ArrayList<String> jzzjqsPvList) {
		this.jzzjqsPvList = jzzjqsPvList;
	}

	public ArrayList<String> getJzbqcPvList() {
		return jzbqcPvList;
	}

	public void setJzbqcPvList(ArrayList<String> jzbqcPvList) {
		this.jzbqcPvList = jzbqcPvList;
	}

	public ArrayList<String> getJzspfList() {
		return jzspfList;
	}

	public void setJzspfList(ArrayList<String> jzspfList) {
		this.jzspfList = jzspfList;
	}

	public ArrayList<String> getJzrqspfLiset() {
		return jzrqspfLiset;
	}

	public void setJzrqspfLiset(ArrayList<String> jzrqspfLiset) {
		this.jzrqspfLiset = jzrqspfLiset;
	}

	public ArrayList<String> getJzspfPvList() {
		return jzspfPvList;
	}

	public void setJzspfPvList(ArrayList<String> jzspfPvList) {
		this.jzspfPvList = jzspfPvList;
	}

	public ArrayList<String> getJzrqspfPvLiset() {
		return jzrqspfPvLiset;
	}

	public void setJzrqspfPvLiset(ArrayList<String> jzrqspfPvLiset) {
		this.jzrqspfPvLiset = jzrqspfPvLiset;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}


	public String getRacename() {
		return racename;
	}

	public void setRacename(String racename) {
		this.racename = racename;
	}

	public String getPlaytime() {
		return playtime;
	}

	public void setPlaytime(String playtime) {
		this.playtime = playtime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getHometeam() {
		return hometeam;
	}

	public void setHometeam(String hometeam) {
		this.hometeam = hometeam;
	}

	public String getGuestteam() {
		return guestteam;
	}

	public void setGuestteam(String guestteam) {
		this.guestteam = guestteam;
	}

	public String getLetcount() {
		return letcount;
	}

	public void setLetcount(String letcount) {
		this.letcount = letcount;
	}

	public String getOdds() {
		return odds;
	}

	public void setOdds(String odds) {
		this.odds = odds;
	}

	public Map<String, ArrayList<String>> getJzheggmap() {
		return jzheggmap;
	}

	public void setJzheggmap(Map<String, ArrayList<String>> jzheggmap) {
		this.jzheggmap = jzheggmap;
	}

	public Map<String, ArrayList<String>> getJzheggPvmap() {
		return jzheggPvmap;
	}

	public void setJzheggPvmap(Map<String, ArrayList<String>> jzheggPvmap) {
		this.jzheggPvmap = jzheggPvmap;
	}

	public ArrayList<String> getJlsfList() {
		return jlsfList;
	}

	public void setJlsfList(ArrayList<String> jlsfList) {
		this.jlsfList = jlsfList;
	}

	public ArrayList<String> getJlrfsfList() {
		return jlrfsfList;
	}

	public void setJlrfsfList(ArrayList<String> jlrfsfList) {
		this.jlrfsfList = jlrfsfList;
	}

	public ArrayList<String> getJldxfList() {
		return jldxfList;
	}

	public void setJldxfList(ArrayList<String> jldxfList) {
		this.jldxfList = jldxfList;
	}

	public Map<String, ArrayList<String>> getJlheggmap() {
		return jlheggmap;
	}

	public void setJlheggmap(Map<String, ArrayList<String>> jlheggmap) {
		this.jlheggmap = jlheggmap;
	}

	public ArrayList<String> getJlsfPvList() {
		return jlsfPvList;
	}

	public void setJlsfPvList(ArrayList<String> jlsfPvList) {
		this.jlsfPvList = jlsfPvList;
	}

	public ArrayList<String> getJlrfsfPvList() {
		return jlrfsfPvList;
	}

	public void setJlrfsfPvList(ArrayList<String> jlrfsfPvList) {
		this.jlrfsfPvList = jlrfsfPvList;
	}

	public ArrayList<String> getJldxfPvList() {
		return jldxfPvList;
	}

	public void setJldxfPvList(ArrayList<String> jldxfPvList) {
		this.jldxfPvList = jldxfPvList;
	}

	public Map<String, ArrayList<String>> getJlheggPvmap() {
		return jlheggPvmap;
	}

	public void setJlheggPvmap(Map<String, ArrayList<String>> jlheggPvmap) {
		this.jlheggPvmap = jlheggPvmap;
	}

	public ArrayList<String> getBdsxdsList() {
		return bdsxdsList;
	}

	public void setBdsxdsList(ArrayList<String> bdsxdsList) {
		this.bdsxdsList = bdsxdsList;
	}

	public ArrayList<String> getBdsxdsPvList() {
		return bdsxdsPvList;
	}

	public void setBdsxdsPvList(ArrayList<String> bdsxdsPvList) {
		this.bdsxdsPvList = bdsxdsPvList;
	}

	public ArrayList<String> getZcList() {
		return zcList;
	}

	public void setZcList(ArrayList<String> zcList) {
		this.zcList = zcList;
	}

	public ArrayList<String> getZcPvList() {
		return zcPvList;
	}

	public void setZcPvList(ArrayList<String> zcPvList) {
		this.zcPvList = zcPvList;
	}

	public ArrayList<String> getZcbcList() {
		return zcbcList;
	}

	public void setZcbcList(ArrayList<String> zcbcList) {
		this.zcbcList = zcbcList;
	}

	public ArrayList<String> getZcqcList() {
		return zcqcList;
	}

	public void setZcqcList(ArrayList<String> zcqcList) {
		this.zcqcList = zcqcList;
	}

	public ArrayList<String> getZcbcPvList() {
		return zcbcPvList;
	}

	public void setZcbcPvList(ArrayList<String> zcbcPvList) {
		this.zcbcPvList = zcbcPvList;
	}

	public ArrayList<String> getZcqcPvList() {
		return zcqcPvList;
	}

	public void setZcqcPvList(ArrayList<String> zcqcPvList) {
		this.zcqcPvList = zcqcPvList;
	}

	public String getLongtime() {
		return longtime;
	}

	public void setLongtime(String longtime) {
		this.longtime = longtime;
	}

	public String getBdtime() {
		return bdtime;
	}

	public void setBdtime(String bdtime) {
		this.bdtime = bdtime;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getMatchnum() {
		return matchnum;
	}

	public void setMatchnum(String matchnum) {
		this.matchnum = matchnum;
	}

	
	public String getMatchno() {
		return matchno;
	}

	public void setMatchno(String matchno) {
		this.matchno = matchno;
	}

	public String getMatched() {
		return matched;
	}

	public void setMatched(String matched) {
		this.matched = matched;
	}

	public String getSingle() {
		return single;
	}

	public void setSingle(String single) {
		this.single = single;
	}

	@Override
	public String toString() {
		return "JZMatchBean [ racename=" + racename
				+ ", playtime=" + playtime + ", longtime=" + longtime
				+ ", bdtime=" + bdtime + ", endtime=" + endtime + ", hometeam="
				+ hometeam + ", guestteam=" + guestteam + ", letcount="
				+ letcount + ", odds=" + odds + ", issue=" + issue + ", week="
				+ week + ", hasDan=" + hasDan + ", count=" + count
				+ ", userSelect=" + userSelect + ", userSelectSp="
				+ userSelectSp + ", pvList=" + Arrays.toString(pvList)
				+ ", color=" + color + "]";
	}

}
