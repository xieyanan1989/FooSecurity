package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;
import com.fsapp.sunsi.foosecurity.R;
public class JZRQSPFCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> jzrqspflist;
	private ArrayList<String> jzrqspfJbvlist;
	private String[] spGroup;
	private String lotteryid;
	private boolean status;
	public JZRQSPFCLass(Context context, LayoutInflater inflater,
			String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid = lotteryid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {

		jzrqspflist = bean.getJzrqspfLiset();
		if (jzrqspflist.size() == 0) {
			jzrqspflist = new ArrayList<String>();
		}
		jzrqspfJbvlist = bean.getJzrqspfPvLiset();
		if (jzrqspfJbvlist.size() == 0) {
			jzrqspfJbvlist = new ArrayList<String>();
		}
		View convertView = inflater.inflate(R.layout.jz_rqspf_item, null);
		parent_layout.addView(convertView);
		final CheckBox item_win_team = (CheckBox) convertView.findViewById(R.id.item_win_team);
		final CheckBox item_lose_team = (CheckBox) convertView.findViewById(R.id.item_lose_team);
		final CheckBox item_flat_team = (CheckBox) convertView.findViewById(R.id.item_flat_team);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		for (int i = 0; i < jzrqspflist.size(); i++) {
			if (jzrqspflist.get(i) == "3") {
				item_win_team.setChecked(true);
			} else if (jzrqspflist.get(i) == "1") {
				item_flat_team.setChecked(true);
			} else if (jzrqspflist.get(i) == "0") {
				item_lose_team.setChecked(true);
			}
		}
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());

		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		String homeTeam = bean.getHometeam();
		String gustTeam = bean.getGuestteam();
		if(homeTeam.length()>4){
			homeTeam =homeTeam.substring(0,4);
		}
		if(gustTeam.length()>5){
			gustTeam = gustTeam.substring(0,5);
		}
		homeTeam= "("+bean.getLetcount()+")"+homeTeam;
		spGroup = bean.getOdds().split(",");
		if(spGroup[0].equals("0") ){
			item_win_team.setText(homeTeam+"\n未开售");
			item_win_team.setTextColor(context.getResources().getColor(R.color.red));
		}else{
			item_win_team.setText(homeTeam+"\n(胜)"+ spGroup[0]);
		}
		if(spGroup[1].equals("0")){
			item_flat_team.setText("VS\n未开售");
			item_flat_team.setTextColor(context.getResources().getColor(R.color.red));
		}else{
			item_flat_team.setText("VS\n"+spGroup[1]+"(平)");
		}
		if(spGroup[2].equals("0")){
			item_lose_team.setText(gustTeam+"\n未开售");
			item_lose_team.setTextColor(context.getResources().getColor(R.color.red));
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
		        		if (item_win_team.isChecked()) {
							jzrqspflist.add("3");
							jzrqspfJbvlist.add(spGroup[0]);
						} else {
							jzrqspflist.remove("3");
							jzrqspfJbvlist.remove(spGroup[0]);
							status =false;
						}
						bean.setJzrqspfLiset(jzrqspflist);
						bean.setJzrqspfPvLiset(jzrqspfJbvlist);
						SetReturnOnclick(lotteryid, LotteryId.PLAY_ID_02,bean);
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
		        		if (item_lose_team.isChecked()) {
							jzrqspflist.add("0");
							jzrqspfJbvlist.add(spGroup[2]);
						} else {
							jzrqspflist.remove("0");
							jzrqspfJbvlist.remove(spGroup[2]);
							status =false;
						}
						bean.setJzrqspfLiset(jzrqspflist);
						bean.setJzrqspfPvLiset(jzrqspfJbvlist);
						SetReturnOnclick(lotteryid, LotteryId.PLAY_ID_02,bean);
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
		        		if (item_flat_team.isChecked()) {
							jzrqspflist.add("1");
							jzrqspfJbvlist.add(spGroup[1]);
						} else {
							jzrqspflist.remove("1");
							jzrqspfJbvlist.remove(spGroup[1]);
							status =false;
						}
						bean.setJzrqspfLiset(jzrqspflist);
						bean.setJzrqspfPvLiset(jzrqspfJbvlist);
						SetReturnOnclick(lotteryid, LotteryId.PLAY_ID_02,bean);
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if (jzrqspflist.size() >0){
			status = true;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean, status);
		}
	}
}
