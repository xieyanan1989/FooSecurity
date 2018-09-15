package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;

public class JZSPFCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private String[] spGroup;
	private ArrayList<String> jzspflist;
	private ArrayList<String> jzspfJbvlist;
	private String color;
	private String lotteryid;
	public JZSPFCLass(Context context, LayoutInflater inflater,String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid = lotteryid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {

		jzspflist = bean.getJzspfList();
		if (jzspflist.size() == 0) {
			jzspflist = new ArrayList<String>();
		}
		jzspfJbvlist=bean.getJzspfPvList();
		if (jzspfJbvlist.size() == 0) {
			jzspfJbvlist = new ArrayList<String>();
		}
		View convertView = inflater.inflate(R.layout.jz_spf_item, null);
		parent_layout.addView(convertView);

		final CheckBox item_win_team = (CheckBox) convertView.findViewById(R.id.item_win_team);
		final CheckBox item_lose_team = (CheckBox) convertView.findViewById(R.id.item_lose_team);
		final CheckBox item_flat_team = (CheckBox) convertView.findViewById(R.id.item_flat_team);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		for(int i=0;i<jzspflist.size();i++){
			if(jzspflist.get(i)=="3"){
				item_win_team.setChecked(true);
			}else if(jzspflist.get(i)=="1"){
				item_flat_team.setChecked(true);
			}else if(jzspflist.get(i)=="0"){
				item_lose_team.setChecked(true);
			}
		}
		String Racename = bean.getRacename();
		color=bean.getColor();
		if(TextUtils.isEmpty(color)){
			color="#23add2";
		}
		 matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());

		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		String homeTeam = bean.getHometeam();
		String gustTeam = bean.getGuestteam();
		spGroup = bean.getOdds().split(",");
		if(spGroup[0].equals("0") ){
			item_win_team.setText(homeTeam+"\n未开售");
		}else{
			item_win_team.setText(homeTeam+"\n(胜)"+ spGroup[0]);
		}
		if(spGroup[1].equals("0")){
			item_flat_team.setText("VS\n未开售");
		}else{
			item_flat_team.setText("VS\n"+spGroup[1]+"(平)");
		}
		if(spGroup[2].equals("0")){
			item_lose_team.setText(gustTeam+"\n未开售");
		}else{
			item_lose_team.setText(gustTeam+"\n"+spGroup[2] +"(负)");
		}
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		item_win_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
						if(spGroup[0].equals("0")){
							item_win_team.setChecked(false);
						}else{
							if (item_win_team.isChecked()) {
								jzspflist.add("3");
								jzspfJbvlist.add(spGroup[0]);
							} else {
								jzspflist.remove("3");
								jzspfJbvlist.remove(spGroup[0]);
							}
							bean.setJzspfList(jzspflist);
							bean.setJzspfPvList(jzspfJbvlist);
						}

		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		item_lose_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		if(spGroup[1].equals("0")){
							item_lose_team.setChecked(false);
						}else{
							if (item_lose_team.isChecked()) {
								jzspflist.add("0");
								jzspfJbvlist.add(spGroup[2]);
							} else {
								jzspflist.remove("0");
								jzspfJbvlist.remove(spGroup[2]);
							}
							bean.setJzspfList(jzspflist);
							bean.setJzspfPvList(jzspfJbvlist);
						}
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		item_flat_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		if(spGroup[2].equals("0")){
							item_flat_team.setChecked(false);
						}else{
							if (item_flat_team.isChecked()) {
								jzspflist.add("1");
								jzspfJbvlist.add(spGroup[1]);
							} else {
								jzspflist.remove("1");
								jzspfJbvlist.remove(spGroup[1]);
							}
							bean.setJzspfList(jzspflist);
							bean.setJzspfPvList(jzspfJbvlist);
						}
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}

