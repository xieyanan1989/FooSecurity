package com.fsapp.sunsi.foosecurity.dubo.bean;

import java.util.ArrayList;


public class JZMatchListBean  implements SafelotteryType{
	/**场次列表**/
	private ArrayList<JZMatchBean> matchList = new ArrayList<JZMatchBean>();
	
	private ArrayList<JZMatchBean> backupList = new ArrayList<JZMatchBean>();
	/**日期星期**/
	private String section;
	/**状态：1为展开显示，2未展示**/
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<JZMatchBean> getMatchList() {
		return matchList;
	}
	public void setMatchList(ArrayList<JZMatchBean> matchList) {
		this.matchList = matchList;
	}
	public ArrayList<JZMatchBean> getBackupList() {
		return backupList;
	}
	public void setBackupList(ArrayList<JZMatchBean> backupList) {
		this.backupList = backupList;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	@Override
	public String toString() {
		return "JZMatchListBean [matchList=" + matchList
				+ ", backupList=" + backupList + ", section=" + section + "]";
	}
}
