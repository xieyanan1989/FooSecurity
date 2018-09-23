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

public class JLSFCLass extends JCAbstractClass {
	
	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> jlsflist;
	private ArrayList<String> jlsfbvlist;
	private String[] spGroup;
	private String lotteryid;
	public JLSFCLass(Context context, LayoutInflater inflater,String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid=lotteryid;
	}
	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {
		jlsflist = bean.getJlsfList();
		if (jlsflist.size() == 0) {
			jlsflist = new ArrayList<String>();
		}
		jlsfbvlist=bean.getJlsfPvList();
		if (jlsfbvlist.size() == 0) {
			jlsfbvlist = new ArrayList<String>();
		}
		
		View convertView = inflater.inflate(R.layout.jl_sf_item, null);
		parent_layout.addView(convertView);
		final CheckBox homeTeam = (CheckBox) convertView.findViewById(R.id.item_home_team);
		final CheckBox guestTeam = (CheckBox) convertView.findViewById(R.id.item_guest_team);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		TextView item_home_tx=(TextView) convertView.findViewById(R.id.item_home_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		for(int i=0;i<jlsflist.size();i++){
			if(jlsflist.get(i)=="2"){
				guestTeam.setChecked(true);
			}else if(jlsflist.get(i)=="1"){
				homeTeam.setChecked(true);
			}
		}
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());
		item_home_tx.setText( bean.getHometeam()+" VS "+bean.getGuestteam());
		spGroup = bean.getOdds().split(",");
		if(spGroup[0].equals("0") ){
			homeTeam.setText("(主胜)\n未开售");
			homeTeam.setTextColor(context.getResources().getColor(R.color.red));
		}else{
			homeTeam.setText("(主胜)\n"+spGroup[0]);
		}
		if(spGroup[1].equals("0") ){
			guestTeam.setText("(主负)\n未开售");
			guestTeam.setTextColor(context.getResources().getColor(R.color.red));
		}else{
			guestTeam.setText("(主负)\n"+spGroup[1]);
		}
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		homeTeam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
						if(spGroup[0].equals("0")){
							homeTeam.setChecked(false);
						}else{
							if (homeTeam.isChecked()) {
								jlsflist.add("1");
								jlsfbvlist.add(spGroup[1]);
							} else {
								jlsflist.remove("1");
								jlsfbvlist.remove(spGroup[1]);
							}
							bean.setJlsfList(jlsflist);
							bean.setJlsfPvList(jlsfbvlist);
						}
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		guestTeam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
						if(spGroup[1].equals("0")){
							guestTeam.setChecked(false);
						}else{
							if (guestTeam.isChecked()) {
								jlsflist.add("2");
								jlsfbvlist.add(spGroup[0]);
							} else {
								jlsflist.remove("2");
								jlsfbvlist.remove(spGroup[0]);
							}
							bean.setJlsfList(jlsflist);
							bean.setJlsfPvList(jlsfbvlist);
						}
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
}
