package com.fsapp.sunsi.foosecurity.dubo.dialogs;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.fsapp.sunsi.foosecurity.R;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.util.JCBFUtils;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;

/**
 * 自定义退出dialog
 * 
 * @author Messi
 * 
 */
public class JCGradeShowDialog extends Dialog {

	private View contentView;

	private Dialog dialog;
	private LinearLayout contentLayout;
	private Context context;
	private String cancle_btn_text;
	private int type;// 默认两个按钮，1为一个按钮
	private BFAdapter bfAdapter;
	private ArrayList<String> jlsfcList;// 竞彩篮球list-投注数据结果集合
	private ArrayList<String> jzbfList;// 竞彩足球比分list
	private ArrayList<String> jzzjqsList;// 竞彩足球总进球list
	private ArrayList<String> jzbqcList;// 竞彩足球半全场list

	private ArrayList<String> jlsfcPvList;// 竞彩篮球list-赔率集合
	private ArrayList<String> jzbfPvList;// 竞彩篮球list-赔率集合
	private ArrayList<String> jzzjqsPvList;// 竞彩篮球list-赔率集合
	private ArrayList<String> jzbqcPvList;// 竞彩篮球list-赔率集合
	private ArrayList<Itembean> bList;

	private String lid;
	private String wanfa;
	private JZMatchBean bean;
	private LayoutInflater inflater;
	private OnShowOnClickListener buttonOnClickListener;
	private int heiht = 0;
	private GridView grid;
	private String sPstr;
	private int jzbfshownum=-1;//显示哪些数据  3=胜/1=平/0负/-1全部

	public JCGradeShowDialog(Context context) {
		super(context);
		this.context = context;
	}

	public JCGradeShowDialog(Context context, LayoutInflater inflater, String lid, String wanfa, JZMatchBean bean,int jzbfshownum,String sPstr) {
		super(context);
		this.context = context;
		this.lid = lid;
		this.wanfa = wanfa;
		this.bean = bean;
		this.inflater = inflater;
		this.jzbfshownum=jzbfshownum;
		this.sPstr=sPstr;
	}

	public void show() {
		String sPstr1[]=sPstr.split(",");
		bList = new ArrayList<Itembean>();
		View view = inflater.inflate(R.layout.jingcai_gridview_dialog, null);
		dialog = new Dialog(context, R.style.dialog);
		Button cancle_btn = (Button) view.findViewById(R.id.money_not_allowed_btn_cancel);
		grid = (GridView) view.findViewById(R.id.grid_view_show);
		if (lid.equals(LotteryId.JCLQ)) {
			for (int i = 0; i < sPstr1.length; i++) {
				Itembean itembean = new Itembean();
				itembean.setSp(sPstr1[i]);
				itembean.setIsselect(0);
				bList.add(itembean);
			}
			
			grid.setNumColumns(4);
			jlsfcList = bean.getJlsfcList();
			jlsfcPvList = bean.getJlsfcPvList();
			if (jlsfcList.size() == 0) {
				jlsfcList = new ArrayList<String>();
			}
			if (jlsfcPvList.size() == 0) {
				jlsfcPvList = new ArrayList<String>();
			}
		} else if (lid.equals(LotteryId.JCZQ)) {
			if (wanfa.equals(LotteryId.PLAY_ID_05)) {
				if(jzbfshownum==-1){
					for (int i = 0; i < sPstr1.length; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==3){
					for (int i = 0; i < 13; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==1){
					for (int i = 13; i <18; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==0){
					for (int i = 18; i <sPstr1.length; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}
				
				grid.setNumColumns(5);
				jzbfList = bean.getJzbfList();
				jzbfPvList = bean.getJzbfPvList();
				if (jzbfList.size() == 0) {
					jzbfList = new ArrayList<String>();
				}
				if (jzbfPvList.size() == 0) {
					jzbfPvList = new ArrayList<String>();
				}
			} else if (wanfa.equals(LotteryId.PLAY_ID_03)) {
				for (int i = 0; i < sPstr1.length; i++) {
					Itembean itembean = new Itembean();
					itembean.setSp(sPstr1[i]);
					itembean.setIsselect(0);
					bList.add(itembean);
				}
				
				grid.setNumColumns(4);
				jzzjqsList = bean.getJzzjqsList();
				jzzjqsPvList = bean.getJzzjqsPvList();
				if (jzzjqsList.size() == 0) {
					jzzjqsList = new ArrayList<String>();
				}
				if (jzzjqsPvList.size() == 0) {
					jzzjqsPvList = new ArrayList<String>();
				}
			} else if (wanfa.equals(LotteryId.PLAY_ID_04)) {
				for (int i = 0; i < sPstr1.length; i++) {
					Itembean itembean = new Itembean();
					itembean.setSp(sPstr1[i]);
					itembean.setIsselect(0);
					bList.add(itembean);
				}
				
				grid.setNumColumns(5);
				jzbqcList = bean.getJzbqcList();
				jzbqcPvList = bean.getJzbqcPvList();
				if (jzbqcList.size() == 0) {
					jzbqcList = new ArrayList<String>();
				}
				if (jzbqcPvList.size() == 0) {
					jzbqcPvList = new ArrayList<String>();
				}
			}
		}else if (lid.equals(LotteryId.BJDC)) {
			if (wanfa.equals(LotteryId.PLAY_ID_05)) {
				if(jzbfshownum==-1){
					for (int i = 0; i < sPstr1.length; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==3){
					for (int i = 0; i < 10; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==1){
					for (int i = 10; i <15; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}else if(jzbfshownum==0){
					for (int i = 15; i <sPstr1.length; i++) {
						Itembean itembean = new Itembean();
						itembean.setSp(sPstr1[i]);
						itembean.setIsselect(0);
						bList.add(itembean);
					}
				}
				
				grid.setNumColumns(5);
				jzbfList = bean.getJzbfList();
				jzbfPvList = bean.getJzbfPvList();
				if (jzbfList.size() == 0) {
					jzbfList = new ArrayList<String>();
				}
				if (jzbfPvList.size() == 0) {
					jzbfPvList = new ArrayList<String>();
				}
			} 
		}
		// grid.setColumnWidth(100);
		if (heiht > 0) {
			LayoutParams lParams = grid.getLayoutParams();
			lParams.height = heiht;
		}

		bfAdapter = new BFAdapter(bList);
		grid.setAdapter(bfAdapter);

		if (type == 1) {
			cancle_btn.setVisibility(View.GONE);
		}
		if (contentView != null) {
			contentLayout.addView(contentView);
		}

		if (!TextUtils.isEmpty(cancle_btn_text)) {
			cancle_btn.setText(cancle_btn_text);
		}

		cancle_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				for (int i = 0; i < bList.size(); i++) {
					if (bList.get(i).getIsselect() == 1) {
						if(lid.equals(LotteryId.JCLQ)){
							if (wanfa.equals(LotteryId.PLAY_ID_03)) {
								for(int j=0;j<jlsfcList.size();j++){
									if(jlsfcList.get(j).equals(i+"")){
										jlsfcList.remove(i+"");
										jlsfcPvList.remove(bList.get(i).getSp());
									}
								}
								jlsfcList.add(i+"");
								jlsfcPvList.add(bList.get(i).getSp());
							}
						}else if(lid.equals(LotteryId.BJDC)){
						    if (wanfa.equals(LotteryId.PLAY_ID_05)) {
						    	if(jzbfshownum==-1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+"");
						    	}else if(jzbfshownum==3){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+"");
						    	}else if(jzbfshownum==1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+13+"")){
							    			jzbfList.remove(i+13+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+13+"");
						    	}else if(jzbfshownum==0){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+18+"")){
							    			jzbfList.remove(i+18+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+18+"");
						    	}
								jzbfPvList.add(bList.get(i).getSp());
							}
						}else if(lid.equals(LotteryId.JCZQ)){
						    if (wanfa.equals(LotteryId.PLAY_ID_05)) {
						    	if(jzbfshownum==-1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+"");
						    	}else if(jzbfshownum==3){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+"");
						    	}else if(jzbfshownum==1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+13+"")){
							    			jzbfList.remove(i+13+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+13+"");
						    	}else if(jzbfshownum==0){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+18+"")){
							    			jzbfList.remove(i+18+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    		jzbfList.add(i+18+"");
						    	}
								jzbfPvList.add(bList.get(i).getSp());
							} else if (wanfa.equals(LotteryId.PLAY_ID_03)) {
								for(int j=0;j<jzzjqsList.size();j++){
									if (jzzjqsList.get(j).equals(i+"+")) {
										jzzjqsList.remove(i+"+");
										jzzjqsPvList.remove(bList.get(i).getSp());
									} else if(jzzjqsList.get(j).equals(i+"")){
					    				jzzjqsList.remove(i+"");
					    				jzzjqsPvList.remove(bList.get(i).getSp());
						    		}
					    		}
								if (i == 7) {
									jzzjqsList.add(i + "+");
								} else {
									jzzjqsList.add(i + "");
								}
								jzzjqsPvList.add(bList.get(i).getSp());
							} else if (wanfa.equals(LotteryId.PLAY_ID_04)) {
								for(int j=0;j<jzbqcList.size();j++){
									if(jzbqcList.get(j).equals(i+"")){
										jzbqcList.remove(i+"");
										jzbqcPvList.remove(bList.get(i).getSp());
									}
								}
								jzbqcList.add(i+"");
								jzbqcPvList.add(bList.get(i).getSp());
							}
						}
					}else{
						if(lid.equals(LotteryId.JCLQ)){
							for(int j=0;j<jlsfcList.size();j++){
								if(jlsfcList.get(j).equals(i+"")){
									jlsfcList.remove(i+"");
									jlsfcPvList.remove(bList.get(i).getSp());
								}
							}
						}else if(lid.equals(LotteryId.BJDC)){
							if (wanfa.equals(LotteryId.PLAY_ID_05)) {
						    	if(jzbfshownum==-1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==3){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+10+"")){
							    			jzbfList.remove(i+10+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==0){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+15+"")){
							    			jzbfList.remove(i+15+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}
							}
							
						}else if(lid.equals(LotteryId.JCZQ)){
						    if (wanfa.equals(LotteryId.PLAY_ID_05)) {
						    	if(jzbfshownum==-1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==3){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(""+i)){
							    			jzbfList.remove(i+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==1){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+13+"")){
							    			jzbfList.remove(i+13+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}else if(jzbfshownum==0){
						    		for(int j=0;j<jzbfList.size();j++){
						    			if(jzbfList.get(j).equals(i+18+"")){
							    			jzbfList.remove(i+18+"");
							    			jzbfPvList.remove(bList.get(i).getSp());
							    		}
						    		}
						    	}
							} else if (wanfa.equals(LotteryId.PLAY_ID_03)) {
								for(int j=0;j<jzzjqsList.size();j++){
									if (jzzjqsList.get(j).equals(i+"+")) {
										jzzjqsList.remove(i+"+");
										jzzjqsPvList.remove(bList.get(i).getSp());
									} else if(jzzjqsList.get(j).equals(i+"")){
					    				jzzjqsList.remove(i+"");
					    				jzzjqsPvList.remove(bList.get(i).getSp());
						    		}
					    		}
							} else if (wanfa.equals(LotteryId.PLAY_ID_04)) {
								for(int j=0;j<jzbqcList.size();j++){
									if(jzbqcList.get(j).equals(i+"")){
										jzbqcList.remove(i+"");
										jzbqcPvList.remove(bList.get(i).getSp());
									}
								}
							}
						}
					}
				}
				// bean.setNumstr(numstr);
				if (jlsfcList != null) {
					bean.setJlsfcList(jlsfcList);
				}
				if (jlsfcPvList != null) {
					bean.setJlsfcPvList(jlsfcPvList);
				}
				if (jzbfList != null) {
					bean.setJzbfList(jzbfList);
				}
				if (jzbfPvList != null) {
					bean.setJzbfPvList(jzbfPvList);
				}
				if (jzzjqsList != null) {
					bean.setJzzjqsList(jzzjqsList);
				}
				if (jzzjqsPvList != null) {
					bean.setJzzjqsPvList(jzzjqsPvList);
				}
				if (jzbqcList != null) {
					bean.setJzbqcList(jzbqcList);
				}
				if (jzbqcPvList != null) {
					bean.setJzbqcPvList(jzbqcPvList);
				}
				if (buttonOnClickListener != null) {
					buttonOnClickListener.onCancleBtnClick();
				}
			}
		});

		dialog.setCancelable(false);
		dialog.setContentView(view);
		dialog.show();
	}

	public interface OnShowOnClickListener {
		public void onCancleBtnClick();
	}

	public void setOnShowClickListener(OnShowOnClickListener buttonOnClickListener) {
		this.buttonOnClickListener = buttonOnClickListener;
	}

	public void setCancle_btn_text(String cancle_btn_text) {
		this.cancle_btn_text = cancle_btn_text;
	}

	public int getHeiht() {
		return heiht;
	}

	public void setHeiht(int heiht) {
		this.heiht = heiht;
	}

	public void addView(View content) {
		this.contentView = content;
	}

	public void setType(int type) {
		this.type = type;
	}

	class BFAdapter extends BaseAdapter {

		private ArrayList<Itembean> bList;

		private BFAdapter(ArrayList<Itembean> bList) {
			this.bList = bList;
		}

		public int getCount() {
			return bList.size();
		}

		public Object getItem(int position) {
			return bList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView, ViewGroup parent) {
			final Holder holder;
			if (convertView == null) {

				holder = new Holder();
				convertView = inflater.inflate(R.layout.jingcai_gridview_dialog_item, null);
				holder.bfTx = (TextView) convertView.findViewById(R.id.bf_text);
				holder.bfsp = (TextView) convertView.findViewById(R.id.sp_text);

				if (lid.equals(LotteryId.JCZQ)) {
					if (wanfa.equals("05")) {
						if(jzbfshownum==-1){
							holder.bfTx.setText(JCBFUtils.getJZString(position));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==3){
							holder.bfTx.setText(JCBFUtils.getJZString(position));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==1){
							holder.bfTx.setText(JCBFUtils.getJZString(position+13));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+13+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==0){
							holder.bfTx.setText(JCBFUtils.getJZString(position+18));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+18+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}
					
					} else if (wanfa.equals("04")) {
						holder.bfTx.setText(JCBFUtils.getJZBqcString(position));
						if (bean.getJzbqcList().size() > 0) {
							for (int i = 0; i < bean.getJzbqcList().size(); i++) {
								if (bean.getJzbqcList().get(i).equals(position+"")) {
									bList.get(position).setIsselect(1);
								}
							}
						}
					} else if (wanfa.equals("03")) {
						holder.bfTx.setText(JCBFUtils.getJZZFString(position));
						if (bean.getJzzjqsList().size() > 0) {
							for (int i = 0; i < bean.getJzzjqsList().size(); i++) {
								if (bean.getJzzjqsList().get(i).equals(JCBFUtils.getJZZFString(position))) {
									bList.get(position).setIsselect(1);
								}
							}
						}
					}
				} else if (lid.equals(LotteryId.JCLQ)) {
					holder.bfTx.setText(JCBFUtils.getJLString(position));
					holder.bfTx.setTextSize(13);
					if (bean.getJlsfcList().size() > 0) {
						for (int i = 0; i < bean.getJlsfcList().size(); i++) {
							if (bean.getJlsfcList().get(i).equals(position+"")) {
								bList.get(position).setIsselect(1);
							}
						}
					}
				}else if (lid.equals(LotteryId.BJDC)) {
					if (wanfa.equals("05")) {
						if(jzbfshownum==-1){
							holder.bfTx.setText(JCBFUtils.getBDBFString(position));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==3){
							holder.bfTx.setText(JCBFUtils.getBDBFString(position));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==1){
							holder.bfTx.setText(JCBFUtils.getBDBFString(position+10));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+10+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}else if(jzbfshownum==0){
							holder.bfTx.setText(JCBFUtils.getBDBFString(position+15));
							if (bean.getJzbfList().size() > 0) {
								for (int i = 0; i < bean.getJzbfList().size(); i++) {
									if (bean.getJzbfList().get(i).equals(position+15+"")) {
										bList.get(position).setIsselect(1);
									}
								}
							}
						}
					
					}
				}

				convertView.setTag(holder);

			} else {
				holder = (Holder) convertView.getTag();
			}

			holder.bfsp.setText(bList.get(position).getSp());

			if (bList.get(position).getIsselect() == 1) {
				addcolorture(convertView, holder);
			} else {
				addcolorfalse(convertView, holder);
			}

			convertView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (bList.get(position).getIsselect() == 1) {
						addcolorture(v, holder);
						bList.get(position).setIsselect(0);
					} else {
						addcolorfalse(v, holder);
						bList.get(position).setIsselect(1);
					}
					bfAdapter.notifyDataSetChanged();
				}
			});
			return convertView;
		}
	}

	public void addcolorture(View convertView, final Holder holder) {
		convertView.setBackgroundColor(getContext().getResources().getColor(R.color.red));
		holder.bfsp.setTextColor(getContext().getResources().getColor(R.color.white));
		holder.bfTx.setTextColor(getContext().getResources().getColor(R.color.white));
	}

	public void addcolorfalse(View convertView, final Holder holder) {
		convertView.setBackgroundColor(getContext().getResources().getColor(R.color.FCFCFC));
		holder.bfsp.setTextColor(getContext().getResources().getColor(R.color.black));
		holder.bfTx.setTextColor(getContext().getResources().getColor(R.color.black));
	}

	static class Holder {
		LinearLayout layout;
		TextView bfTx;
		TextView bfsp;
	}

	private class Itembean {
		public String sp;
		public int isselect = 0;

		public int getIsselect() {
			return isselect;
		}

		public void setIsselect(int isselect) {
			this.isselect = isselect;
		}

		public String getSp() {
			return sp;
		}

		public void setSp(String sp) {
			this.sp = sp;
		}

	}

	public static String getJLResult(int position) {
		if (position == 0) {
			return "01";
		} else if (position == 1) {
			return "02";
		} else if (position == 2) {
			return "03";
		} else if (position == 3) {
			return "04";
		} else if (position == 4) {
			return "05";
		} else if (position == 5) {
			return "06";
		} else if (position == 6) {
			return "11";
		} else if (position == 7) {
			return "12";
		} else if (position == 8) {
			return "13";
		} else if (position == 9) {
			return "14";
		} else if (position == 10) {
			return "15";
		} else if (position == 11) {
			return "16";
		}
		return "";
	}
}
