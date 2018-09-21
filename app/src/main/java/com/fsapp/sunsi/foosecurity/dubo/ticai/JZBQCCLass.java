package com.fsapp.sunsi.foosecurity.dubo.ticai;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.TypedValue;
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

public class JZBQCCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private String[] backName = { "胜胜", "胜平", "胜负", "平胜", "平平", "平负", "负胜", "负平", "负负" };
	private ArrayList<String> jzbqclist;
	private ArrayList<String> jzbqcPvlist;
	private ArrayList<View> list;
	private String fenshuSP[];
	private String lotteryid;
	private boolean status;
	public JZBQCCLass(Context context, LayoutInflater inflater,String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid=lotteryid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {

		jzbqclist = bean.getJzbqcList();
		if (jzbqclist.size() == 0) {
			jzbqclist = new ArrayList<String>();
		}
		jzbqcPvlist = bean.getJzbqcPvList();
		if (jzbqcPvlist.size() == 0) {
			jzbqcPvlist = new ArrayList<String>();
		}

		list = new ArrayList<View>(backName.length);
		View convertView = inflater.inflate(R.layout.jz_bqc_item, null);
		TextView item_home_tx = (TextView) convertView.findViewById(R.id.item_home_tx);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		ImageView item_single=(ImageView) convertView.findViewById(R.id.item_single);
		LinearLayout selectlayout = (LinearLayout) convertView.findViewById(R.id.main_grid_view_show);
		selectlayout.setGravity(Gravity.CENTER);
		parent_layout.addView(convertView);
		if(bean.getSingle().equals("1")){
			item_single.setVisibility(View.VISIBLE);
		}
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());
		item_home_tx.setText( bean.getHometeam()+" VS "+bean.getGuestteam());
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		fenshuSP = bean.getOdds().split(",");
		for (int i = 0; i < backName.length; i++) {
			final View view = inflater.inflate(R.layout.jingcai_gridview_items, null);
			CheckBox checkBox = (CheckBox) view.findViewById(R.id.gridview_items_firsttx);
			checkBox.setText(backName[i]+"\n"+fenshuSP[i]);
			checkBox.setId(i);
			LayoutParams layoutParams = new LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
			layoutParams.setMargins(2, 2, 2,2);
			view.setLayoutParams(layoutParams);
			for(String bqc : jzbqclist){
				if(bqc.equals(""+i)){
					checkBox.setChecked(true);
				}
			}
			checkBox.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//						Date dt1 = df.parse("2015-10-18 17:00:00");
						if((System.currentTimeMillis()+1200000) > dt1.getTime()){
							Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						}else{int index = v.getId();
							if (((CheckBox)v).isChecked()) {
								jzbqclist.add(""+index);
								jzbqcPvlist.add(fenshuSP[index]);
							} else {
								jzbqclist.remove(""+index);
								jzbqcPvlist.remove(fenshuSP[index]);
								status =false;
							}
							bean.setJzbqcList(jzbqclist);
							bean.setJzbqcPvList(jzbqcPvlist);
							SetReturnOnclick(lotteryid, LotteryId.PLAY_ID_04,bean);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			list.add(view);
		}
		ListLineFeedView listView = new ListLineFeedView(context, list, 5);
		selectlayout.addView(listView.getView());
	}
	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if(jzbqclist.size() >0){
			status = true;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean,status);
		}
	}
}
