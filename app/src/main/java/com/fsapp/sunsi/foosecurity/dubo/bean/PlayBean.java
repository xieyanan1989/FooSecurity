package com.fsapp.sunsi.foosecurity.dubo.bean;

public class PlayBean {

	private String playName; //玩法名
	private String playId; // 玩法
	private String pollId; // 选号方式
	private int icon; // 图标
	
	public int getIcon() {
		return icon;
	}
	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getPlayId() {
		return playId;
	}
	public void setPlayId(String playId) {
		this.playId = playId;
	}
	public String getPlayName() {
		return playName;
	}
	public void setPlayName(String playName) {
		this.playName = playName;
	}
	public String getPollId() {
		return pollId;
	}
	public void setPollId(String pollId) {
		this.pollId = pollId;
	}
}
