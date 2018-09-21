package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.view.ListLineFeedView;

public class JZZJQSCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private ArrayList<String> jzzjqslist;
	private ArrayList<String> jzzjqsPvlist;
	private String fenshuSP[];
	private String[] backName = { "0球", "1球", "2球", "3球", "4球", "5球", "6球", "7+球" };
	private ArrayList<View> list;
	private String lotteryid;
	private boolean status;
	public JZZJQSCLass(Context context, LayoutInflater inflater,String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid = lotteryid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {
		jzzjqslist = bean.getJzzjqsList();
		if (jzzjqslist.size() == 0) {
			jzzjqslist = new ArrayList<String>();
		}
		jzzjqsPvlist = bean.getJzzjqsPvList();
		if (jzzjqsPvlist.size() == 0) {
			jzzjqsPvlist = new ArrayList<String>();
		}
		list = new ArrayList<View>(backName.length);
		View convertView = inflater.inflate(R.layout.jz_zjqs_item, null);
		TextView item_home_tx = (TextView) convertView.findViewById(R.id.item_home_tx);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		LinearLayout selectlayout = (LinearLayout) convertView.findViewById(R.id.main_grid_view_show);
		selectlayout.setGravity(Gravity.CENTER);
		parent_layout.addView(convertView);
		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		fenshuSP = bean.getOdds().split(",");
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());
		item_home_tx.setText( bean.getHometeam()+" VS "+bean.getGuestteam());
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		for (int i = 0; i < backName.length; i++) {
			final View view = inflater.inflate(R.layout.jingcai_gridview_items, null);
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.gridview_items_firsttx);
			checkBox.setText(backName[i]+"\n"+fenshuSP[i]);
			checkBox.setId(i);
			LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
			layoutParams.setMargins(5, 5, 0, 0);
			view.setLayoutParams(layoutParams);
			for(String zjqs : jzzjqslist){
				if(zjqs.equals(""+i)){
					checkBox.setChecked(true);
				}
			}
			checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					try {
						Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
			        	if((System.currentTimeMillis()+1200000) > dt1.getTime()){
			        		Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
			        	}else{
							int index = v.getId();
							if (((CheckBox)v).isChecked()) {
								jzzjqslist.add(""+index);
								jzzjqsPvlist.add(fenshuSP[index]);
							} else {
								jzzjqslist.remove(""+index);
								jzzjqsPvlist.remove(fenshuSP[index]);
								status =false;
							}
							bean.setJzzjqsList(jzzjqslist);
							bean.setJzzjqsPvList(jzzjqsPvlist);
							SetReturnOnclick(lotteryid, LotteryId.PLAY_ID_03,bean);
			        	}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});


			list.add(view);
		}
		ListLineFeedView listView = new ListLineFeedView(context, list, 4);
		selectlayout.addView(listView.getView());
	}
	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if(jzzjqslist.size() >0){
			status = true;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean,status);
		}
	}
}
