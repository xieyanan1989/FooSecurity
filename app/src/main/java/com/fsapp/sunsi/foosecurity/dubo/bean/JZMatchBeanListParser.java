package com.fsapp.sunsi.foosecurity.dubo.bean;

import com.fsapp.sunsi.foosecurity.dubo.util.Tools;

import org.json.JSONException;
import org.json.JSONObject;

public class JZMatchBeanListParser extends AbstractParser<JZMatchBean>{
	@Override
	public JZMatchBean parse(JSONObject json) throws JSONException {
		JZMatchBean jzMatchBean = new JZMatchBean();
		if (json.has("matchno")) {
			jzMatchBean.setMatchno(json.getString("matchno"));
			String match=json.getString("matchno");
			if(match.length()>3){
				String matchstr=match.substring(match.length()-3, match.length());
				jzMatchBean.setMatchnum(matchstr);
			}
        } 
		if (json.has("matched")) {
			jzMatchBean.setMatched(json.getString("matched"));
        } 
		if (json.has("racename")) {
			jzMatchBean.setRacename(json.getString("racename"));
        } 
		if (json.has("playtime")) {
			String longtime=json.getString("playtime");
			String time1= Tools.getTimeDate(Tools.getDateToTime(longtime));
			jzMatchBean.setBdtime(time1);
        } 
		if (json.has("endtime")) {
			String longtime=json.getString("endtime");
			String time1=Tools.getTimeDatehhmm(Tools.getDateToTime(longtime));
			jzMatchBean.setPlaytime(time1);
			jzMatchBean.setEndtime("截止投注时间："+longtime);
        } 
		if (json.has("hometeam")) {
			jzMatchBean.setHometeam(json.getString("hometeam"));
        } 
		if (json.has("guestteam")) {
			jzMatchBean.setGuestteam(json.getString("guestteam"));
        } 
		if (json.has("letcount")) {
			jzMatchBean.setLetcount(json.getString("letcount"));
        } 
		if (json.has("odds")) {
			jzMatchBean.setOdds(json.getString("odds"));
        } 
		if (json.has("week")) {
			jzMatchBean.setWeek(json.getString("week"));
        } 
		if (json.has("color")) {
			jzMatchBean.setColor("#"+json.getString("color"));
        } 
		if (json.has("single")) {
			jzMatchBean.setSingle(json.getString("single"));
        } 
		if(json.has("begintime")){
			String longtime=json.getString("begintime");
			String time=Tools.getTimeDate(Tools.getDateToTime(longtime));
			jzMatchBean.setLongtime(time);
		}
		return jzMatchBean;
	}
}
