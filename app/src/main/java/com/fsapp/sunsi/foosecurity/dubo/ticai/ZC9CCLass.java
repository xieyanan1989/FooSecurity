package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.fsapp.sunsi.foosecurity.R;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;

public class ZC9CCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> zclist;
	private ArrayList<String> zcPvlist;
	private boolean status;
	public ZC9CCLass(Context context, LayoutInflater inflater, String lotteryId) {
		this.context = context;
		this.inflater = inflater;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {
		zclist = bean.getZcList();
		if (zclist.size() == 0) {
			zclist = new ArrayList<String>();
		}
		zcPvlist = bean.getZcPvList();
		if (zcPvlist.size() == 0) {
			zcPvlist = new ArrayList<String>();
		}
		View convertView = inflater.inflate(R.layout.zc_9c_item, null);
		parent_layout.addView(convertView);
		//比赛场次
		TextView league_cc = (TextView) convertView.findViewById(R.id.league_cc);
		TextView leagueImg = (TextView) convertView.findViewById(R.id.league_img);
		final CheckBox item_win_team = (CheckBox) convertView.findViewById(R.id.item_win_team);
		final CheckBox item_lose_team = (CheckBox) convertView.findViewById(R.id.item_lose_team);
		final CheckBox item_flat_team = (CheckBox) convertView.findViewById(R.id.item_flat_team);

		for (int i = 0; i < zclist.size(); i++) {
			if (zclist.get(i) == "3") {
				item_win_team.setChecked(true);
			} else if (zclist.get(i) == "1") {
				item_flat_team.setChecked(true);
			} else if (zclist.get(i) == "0") {
				item_lose_team.setChecked(true);
			}
		}
		leagueImg.setText(bean.getRacename());
		league_cc.setText(bean.getMatchno());
		item_win_team.setText(bean.getHometeam()+"\n(胜)");
		item_flat_team.setText("(平)");
		item_lose_team.setText( bean.getGuestteam()+"\n(负)");
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		item_win_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getEndtime().split("\\：")[1]);
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		if (item_win_team.isChecked()) {
							zclist.add("3");
							zcPvlist.add("3");
						} else {
							zclist.remove("3");
							zcPvlist.remove("3");
						}
						bean.setZcList(zclist);
						bean.setZcPvList(zcPvlist);
						SetReturnOnclick(LotteryId.CTZC9,LotteryId.PLAY_ID_01,bean);
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
					Date dt1 = df.parse(bean.getEndtime().split("\\：")[1]);
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		if (item_lose_team.isChecked()) {
							zclist.add("0");
							zcPvlist.add("2");
						} else {
							zclist.remove("0");
							zcPvlist.remove("2");
						}
						bean.setZcList(zclist);
						bean.setZcPvList(zcPvlist);
						SetReturnOnclick(LotteryId.CTZC9,LotteryId.PLAY_ID_01,bean);
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
					Date dt1 = df.parse(bean.getEndtime().split("\\：")[1]);
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		if (item_flat_team.isChecked()) {
							zclist.add("1");
							zcPvlist.add("1");
						} else {
							zclist.remove("1");
							zcPvlist.remove("1");
						}
						bean.setZcList(zclist);
						bean.setZcPvList(zcPvlist);
						SetReturnOnclick(LotteryId.CTZC9,LotteryId.PLAY_ID_01,bean);
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if(zclist.size() >0){
			status=true;
		}else {
			status=false;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean, status);
		}
	}
}
