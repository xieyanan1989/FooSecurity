package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.dialogs.JCGradeShowDialog;
import com.fsapp.sunsi.foosecurity.dubo.util.JCBFUtils;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;
import com.fsapp.sunsi.foosecurity.R;
public class JZBFCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> jzbflist;
	private CheckBox win_team;
	private CheckBox lose_team;
	private CheckBox flat_team;
	private JZMatchBean bFbean;
	private String lotterylid;
	private boolean status;
	public JZBFCLass(Context context, LayoutInflater inflater,String lotterylid) {
		this.context = context;
		this.inflater = inflater;
		this.lotterylid=lotterylid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {
		this.bFbean = bean;
		View convertView = inflater.inflate(R.layout.jz_bf_item, null);
		parent_layout.addView(convertView);
		TextView item_home_tx = (TextView) convertView.findViewById(R.id.item_home_tx);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		win_team = (CheckBox) convertView.findViewById(R.id.win_team);
		lose_team = (CheckBox) convertView.findViewById(R.id.lose_team);
		flat_team = (CheckBox) convertView.findViewById(R.id.flat_team);
		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		isselectBtn(bean);
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());
		item_home_tx.setText( bean.getHometeam()+" VS "+bean.getGuestteam());
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		win_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, lotterylid, "05", bean, 3, bean.getOdds());
						showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
							@Override
							public void onCancleBtnClick() {
								isselectBtn(bean);
							}
						});
						showDialog.show();
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		lose_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
						JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, lotterylid, "05", bean, 0, bean.getOdds());
						showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
							@Override
							public void onCancleBtnClick() {
								isselectBtn(bean);
							}
						});
						showDialog.show();
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		flat_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
		        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
		        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
		        	}else{
		        		JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, LotteryId.JCZQ, "05", bean, 1, bean.getOdds());
						showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
							@Override
							public void onCancleBtnClick() {
								isselectBtn(bean);
							}
						});
						showDialog.show();
		        	}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void isselectBtn(JZMatchBean bean) {
		jzbflist = bFbean.getJzbfList();
		win_team.setChecked(false);
		flat_team.setChecked(false);
		lose_team.setChecked(false);
		//显示选择的选项
		StringBuffer win = new StringBuffer();
		StringBuffer flat = new StringBuffer();
		StringBuffer lose = new StringBuffer();
		if (jzbflist.size() > 0) {
			for (int i = 0; i < jzbflist.size(); i++) {
				int bfnum = Tools.getStringOfInteger(jzbflist.get(i));
				if (bfnum < 13) {
					win_team.setChecked(true);
					win.append(JCBFUtils.getJZString(Integer.parseInt(jzbflist.get(i)))+"|");
				} else if (bfnum >= 13 && bfnum < 18) {
					flat_team.setChecked(true);
					flat.append(JCBFUtils.getJZString(Integer.parseInt(jzbflist.get(i)))+"|");
				} else if (bfnum >= 18) {
					lose_team.setChecked(true);
					lose.append(JCBFUtils.getJZString(Integer.parseInt(jzbflist.get(i)))+"|");
				}
			}
		}
		if(win.length() > 0){
			win_team.setText("胜\n"+win.substring(0,win.length()-1));
		}
		if(flat.length() > 0){
			flat_team.setText("平\n"+flat.substring(0,flat.length()-1));
		}
		if(lose.length() > 0){
			lose_team.setText("负\n"+lose.substring(0,lose.length()-1));
		}
		SetReturnOnclick(LotteryId.JCZQ,LotteryId.PLAY_ID_05,bean);
	}

	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if(jzbflist.size() >0){
			status=true;
		}else {
			status=false;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean, status);
		}
	}
}
