package com.fsapp.sunsi.foosecurity.dubo.dialogs;

import java.util.ArrayList;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.R;

/**
 * 自定义退出dialog
 * 
 * @author Messi
 * 
 */
public class JCSPFDialog extends Dialog {
	private Dialog dialog;
	private Context context;
	private String lid;
	private String wanfa;
	private JZMatchBean bean;
	private LayoutInflater inflater;
	private OnShowOnClickListener buttonOnClickListener;
	private int heiht = 0;
	private CheckBox item_win_team;
	private CheckBox item_flat_team;
	private CheckBox item_lose_team;
	private Button btn_cancel;

	private ArrayList<String> jzspfList;
	private ArrayList<String> jzrqsbfList;// 竞彩足球半全场list

	private ArrayList<String> jzspfPvList;// 竞彩篮球list-赔率集合
	private ArrayList<String> jzrqspfPvList;// 竞彩篮球list-赔率集合
	private String sPstr;
	private String[] sPstr1;

	public JCSPFDialog(Context context) {
		super(context);
		this.context = context;
	}

	public JCSPFDialog(Context context, LayoutInflater inflater, String lid, String wanfa, JZMatchBean bean, String sPstr) {
		super(context);
		this.context = context;
		this.lid = lid;
		this.wanfa = wanfa;
		this.bean = bean;
		this.inflater = inflater;
		this.sPstr = sPstr;
	}

	public void show() {
		sPstr1 = sPstr.split(",");

		if (lid.equals(LotteryId.JCLQ)) {

		} else if (lid.equals(LotteryId.JCZQ)) {
			if (wanfa.equals(LotteryId.PLAY_ID_01)) {
				jzspfList = bean.getJzspfList();
				jzspfPvList = bean.getJzspfPvList();
				if (jzspfList.size() == 0) {
					jzspfList = new ArrayList<String>();
				}
				if (jzspfPvList.size() == 0) {
					jzspfPvList = new ArrayList<String>();
				}
			}
			if (wanfa.equals(LotteryId.PLAY_ID_02)) {
				jzrqsbfList = bean.getJzrqspfLiset();
				jzrqspfPvList = bean.getJzrqspfPvLiset();
				if (jzrqsbfList.size() == 0) {
					jzrqsbfList = new ArrayList<String>();
				}
				if (jzrqspfPvList.size() == 0) {
					jzrqspfPvList = new ArrayList<String>();
				}
			}
		}
		View view = inflater.inflate(R.layout.jingcai_spf_dialog, null);
		LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(800, LayoutParams.WRAP_CONTENT);
		dialog = new Dialog(context, R.style.dialog);
		item_win_team = (CheckBox) view.findViewById(R.id.item_win_team);
		item_flat_team = (CheckBox) view.findViewById(R.id.item_flat_team);
		item_lose_team = (CheckBox) view.findViewById(R.id.item_lose_team);

//		item_win_team.setText("胜(" + sPstr1[0] + ")");
//		item_flat_team.setText("平(" + sPstr1[1] + ")");
//		item_lose_team.setText("负(" + sPstr1[2] + ")");
//		spGroup = bean.getOdds().split(",");
		if(sPstr1[0].equals("0") ){
			item_win_team.setText("未开售");
			item_win_team.setTextColor(context.getResources().getColor(R.color.black));
		}else{
			item_win_team.setText("(胜)"+ sPstr1[0]);
		}
		if(sPstr1[1].equals("0")){
			item_flat_team.setText("未开售");
			item_flat_team.setTextColor(context.getResources().getColor(R.color.black));
		}else{
			item_flat_team.setText(sPstr1[1]+"(平)");
		}
		if(sPstr1[2].equals("0")){
			item_lose_team.setText("未开售");
			item_lose_team.setTextColor(context.getResources().getColor(R.color.black));
		}else{
			item_lose_team.setText(sPstr1[2] +"(负)");
		}
		btn_cancel = (Button) view.findViewById(R.id.btn_cancel);
		if (lid.equals(LotteryId.JCLQ)) {

		} else if (lid.equals(LotteryId.JCZQ)) {
			if (wanfa.equals(LotteryId.PLAY_ID_01)) {
				if (jzspfList.size() > 0) {
					for (int i = 0; i < jzspfList.size(); i++) {
						if (jzspfList.get(i).equals("3")) {
							item_win_team.setChecked(true);
						} else if (jzspfList.get(i).equals("1")) {
							item_flat_team.setChecked(true);
						} else if (jzspfList.get(i).equals("0")) {
							item_lose_team.setChecked(true);
						}
					}
				}
			} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
				if (jzrqsbfList.size() > 0) {
					for (int i = 0; i < jzrqsbfList.size(); i++) {
						if (jzrqsbfList.get(i).equals("3")) {
							item_win_team.setChecked(true);
						} else if (jzrqsbfList.get(i).equals("1")) {
							item_flat_team.setChecked(true);
						} else if (jzrqsbfList.get(i).equals("0")) {
							item_lose_team.setChecked(true);
						}
					}
				}
			}
		}
		item_win_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(sPstr1[0].equals("0")){
					item_win_team.setChecked(false);
				}else{
					if (item_win_team.isChecked() == true) {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.add("3");
							jzspfPvList.add(sPstr1[0]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.add("3");
							jzrqspfPvList.add(sPstr1[0]);
						}
					} else {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.remove("3");
							jzspfPvList.remove(sPstr1[0]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.remove("3");
							jzrqspfPvList.remove(sPstr1[0]);

						}
					}
				}
				
			}
		});
		item_flat_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(sPstr1[1].equals("0")){
					item_flat_team.setChecked(false);
				}else{
					if (item_flat_team.isChecked() == true) {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.add("1");
							jzspfPvList.add(sPstr1[1]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.add("1");
							jzrqspfPvList.add(sPstr1[1]);
						}
					} else {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.remove("1");
							jzspfPvList.remove(sPstr1[1]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.remove("1");
							jzrqspfPvList.remove(sPstr1[1]);
						}
					}

				}
				
			}
		});
		item_lose_team.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(sPstr1[2].equals("0")){
					item_lose_team.setChecked(false);
				}else{
					if (item_lose_team.isChecked() == true) {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.add("0");
							jzspfPvList.add(sPstr1[2]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.add("0");
							jzrqspfPvList.add(sPstr1[2]);
						}
					} else {
						if (wanfa.equals(LotteryId.PLAY_ID_01)) {
							jzspfList.remove("0");
							jzspfPvList.add(sPstr1[2]);
						} else if (wanfa.equals(LotteryId.PLAY_ID_02)) {
							jzrqsbfList.remove("0");
							jzrqspfPvList.remove(sPstr1[2]);
						}
					}
				}
				
			}
		});
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();
				if (lid.equals(LotteryId.JCLQ)) {

				} else if (lid.equals(LotteryId.JCZQ)) {
					if (wanfa.equals(LotteryId.PLAY_ID_01)) {
						if (jzspfList.size() > 0) {
							bean.setJzspfList(jzspfList);
							bean.setJzspfPvList(jzspfPvList);
						}
					}
					if (wanfa.equals(LotteryId.PLAY_ID_02)) {
						if (jzrqsbfList.size() > 0) {
							bean.setJzrqspfLiset(jzrqsbfList);
							bean.setJzrqspfPvLiset(jzrqspfPvList);
						}
					}
				}
				if (buttonOnClickListener != null) {
					buttonOnClickListener.onCancleBtnClick();
				}
			}
		});
		dialog.setCancelable(false);
		view.setLayoutParams(mParams);
		dialog.setContentView(view);
		dialog.show();
	}

	public interface OnShowOnClickListener {
		public void onCancleBtnClick();
	}

	public void setOnShowClickListener(OnShowOnClickListener buttonOnClickListener) {
		this.buttonOnClickListener = buttonOnClickListener;
	}

	public int getHeiht() {
		return heiht;
	}

	public void setHeiht(int heiht) {
		this.heiht = heiht;
	}
}
