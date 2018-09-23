package com.fsapp.sunsi.foosecurity.dubo.ticai;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.dialogs.JCGradeShowDialog;
import com.fsapp.sunsi.foosecurity.dubo.dialogs.JCSPFDialog;
import com.fsapp.sunsi.foosecurity.dubo.util.JCBFUtils;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.view.JCAbstractClass;
import com.fsapp.sunsi.foosecurity.R;

public class JZZHGGCLass extends JCAbstractClass {

	private LayoutInflater inflater;
	private Context context;
	private String fenshuSP[];
	private String fenshuSP1[];
	private String fenshuSP2[];
	private String fenshuSP3[];
	private String fenshuSP4[];
	private String[] spGroup;
	private JZMatchBean zhggbean;
	private CheckBox jz_rqspf_edt;
	private CheckBox jz_spf_edt ;
	private CheckBox jz_bf_edt ;
	private CheckBox jz_bqc_edt;
	private CheckBox jz_zjqs_edt ;
	private String lotteryid;
	private boolean status;
	public JZZHGGCLass(Context context, LayoutInflater inflater,String lotteryid) {
		this.context = context;
		this.inflater = inflater;
		this.lotteryid = lotteryid;
	}

	@Override
	public void showItems(final JZMatchBean bean, LinearLayout parent_layout) {
		zhggbean=bean;
		View convertView = inflater.inflate(R.layout.jz_zhgg_item, null);
		parent_layout.addView(convertView);
		TextView item_home_tx = (TextView) convertView.findViewById(R.id.item_home_tx);
		 jz_rqspf_edt = (CheckBox) convertView.findViewById(R.id.jz_rqspf_edt);
		 jz_spf_edt = (CheckBox) convertView.findViewById(R.id.jz_spf_edt);
		 jz_bf_edt = (CheckBox) convertView.findViewById(R.id.jz_bf_edt);
		 jz_bqc_edt = (CheckBox) convertView.findViewById(R.id.jz_bqc_edt);
		 jz_zjqs_edt = (CheckBox) convertView.findViewById(R.id.jz_zjqs_edt);
		TextView matched_tx = (TextView) convertView.findViewById(R.id.jc_matched_tx);
		matched_tx.setText(bean.getRacename()+"\n"+bean.getWeekName().replaceAll("星期","周")+bean.getMatchnum()+"\n"+bean.getLongtime().substring(5,10)+" "+bean.getPlaytime());
		item_home_tx.setText( bean.getHometeam()+" VS "+bean.getGuestteam());
		isselectBtn();
		spGroup= bean.getOdds().split(";");
		if(spGroup.length == 1){
			fenshuSP = spGroup[0].split(":");
			jz_rqspf_edt.setVisibility(View.GONE);
			jz_zjqs_edt.setVisibility(View.GONE);
			jz_bqc_edt.setVisibility(View.GONE);
			jz_bf_edt.setVisibility(View.GONE);
		}
		if(spGroup.length == 2){
			fenshuSP = spGroup[0].split(":");
			fenshuSP1 = spGroup[1].split(":");
			jz_zjqs_edt.setVisibility(View.GONE);
			jz_bqc_edt.setVisibility(View.GONE);
			jz_bf_edt.setVisibility(View.GONE);
		}
		if(spGroup.length == 3){
			fenshuSP = spGroup[0].split(":");
			fenshuSP1 = spGroup[1].split(":");
			fenshuSP2 = spGroup[2].split(":");
			jz_bqc_edt.setVisibility(View.GONE);
			jz_bf_edt.setVisibility(View.GONE);
		}
		if(spGroup.length == 4){
			fenshuSP = spGroup[0].split(":");
			fenshuSP1 = spGroup[1].split(":");
			fenshuSP2 = spGroup[2].split(":");
			fenshuSP3 = spGroup[3].split(":");
			jz_bf_edt.setVisibility(View.GONE);
		}
		if(spGroup.length == 5){
			fenshuSP = spGroup[0].split(":");
			fenshuSP1 = spGroup[1].split(":");
			fenshuSP2 = spGroup[2].split(":");
			fenshuSP3 = spGroup[3].split(":");
			fenshuSP4 = spGroup[4].split(":");
		}
		final DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		jz_rqspf_edt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
					Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
					if((System.currentTimeMillis()+1200000) > dt1.getTime()){
						Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						jz_rqspf_edt.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gray_background));
					}else{
						if(fenshuSP1.length==1){
							jz_rqspf_edt.setChecked(false);
							Toast.makeText(context, "暂无赛事", Toast.LENGTH_SHORT).show();
						}else{
							JCSPFDialog showDialog = new JCSPFDialog(context, inflater, LotteryId.JCZQ, "02", bean,fenshuSP1[1]);
							showDialog.setOnShowClickListener(new JCSPFDialog.OnShowOnClickListener() {
								@Override
								public void onCancleBtnClick() {
									isselectBtn();
								}
							});
							showDialog.show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		jz_spf_edt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
										Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
					if((System.currentTimeMillis()+1200000) > dt1.getTime()){
						Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						jz_spf_edt.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gray_background));
					}else{
						if(fenshuSP.length==1){
							jz_spf_edt.setChecked(false);
							Toast.makeText(context, "暂无赛事", Toast.LENGTH_SHORT).show();
						}else{
						JCSPFDialog showDialog = new JCSPFDialog(context, inflater, LotteryId.JCZQ, "01", bean,fenshuSP[1]);
						showDialog.setOnShowClickListener(new JCSPFDialog.OnShowOnClickListener() {
							@Override
							public void onCancleBtnClick() {
								isselectBtn();
							}
						});
						showDialog.show();
					}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		jz_bf_edt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
										Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
					if((System.currentTimeMillis()+1200000) > dt1.getTime()){
						Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						jz_bf_edt.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gray_background));
					}else{
						if(fenshuSP4.length==1){
							jz_bf_edt.setChecked(false);
							Toast.makeText(context, "暂无赛事", Toast.LENGTH_SHORT).show();
						}else{
							JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, LotteryId.JCZQ, "05", bean, -1,fenshuSP4[1]);
							showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
								@Override
								public void onCancleBtnClick() {
									isselectBtn();
								}
							});
							showDialog.show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		jz_bqc_edt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
										Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
					if((System.currentTimeMillis()+1200000) > dt1.getTime()){
						Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						jz_bqc_edt.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gray_background));
					}else{
						if(fenshuSP3.length==1){
							jz_bqc_edt.setChecked(false);
							Toast.makeText(context, "暂无赛事", Toast.LENGTH_SHORT).show();
						}else{
							JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, LotteryId.JCZQ, "04", bean, -1,fenshuSP3[1]);
							showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
								@Override
								public void onCancleBtnClick() {
									isselectBtn();
								}
							});
							showDialog.show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
		jz_zjqs_edt.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				try {
										Date dt1 = df.parse(bean.getLongtime()+" "+bean.getPlaytime()+":00");
//					Date dt1 = df.parse("2015-10-18 17:00:00");
					if((System.currentTimeMillis()+1200000) > dt1.getTime()){
						Toast.makeText(context, "本场比赛投注结束", Toast.LENGTH_SHORT).show();
						jz_zjqs_edt.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.gray_background));
					}else{
						if(fenshuSP2.length==1){
							jz_zjqs_edt.setChecked(false);
							Toast.makeText(context, "暂无赛事", Toast.LENGTH_SHORT).show();
						}else{
							JCGradeShowDialog showDialog = new JCGradeShowDialog(context, inflater, LotteryId.JCZQ, "03", bean, -1,fenshuSP2[1]);
							showDialog.setOnShowClickListener(new JCGradeShowDialog.OnShowOnClickListener() {
								@Override
								public void onCancleBtnClick() {
									isselectBtn();
								}
							});
							showDialog.show();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
	}
	
	public void isselectBtn() {
		//显示选择的选项
		StringBuffer spf = new StringBuffer();
		StringBuffer rqspf = new StringBuffer();
		StringBuffer zjqs = new StringBuffer();
		StringBuffer bqc = new StringBuffer();
		StringBuffer bf = new StringBuffer();
		if(zhggbean.getJzspfList().size()>0){
			jz_spf_edt.setChecked(true);
			for(String pv : zhggbean.getJzspfList()){
				spf.append(JCBFUtils.getJZSpfString(Integer.parseInt(pv))+"|");
			}
			jz_spf_edt.setText("胜平负\n("+spf.substring(0,spf.length()-1)+")");
		}else{
			jz_spf_edt.setChecked(false);
			jz_spf_edt.setText("胜平负");
		}
		if(zhggbean.getJzrqspfLiset().size()>0){
			jz_rqspf_edt.setChecked(true);
			for(String pv : zhggbean.getJzrqspfLiset()){
				rqspf.append(JCBFUtils.getJZSpfString(Integer.parseInt(pv))+"|");
			}
			jz_rqspf_edt.setText("让球胜平负\n("+rqspf.substring(0,rqspf.length()-1)+")");
		}else{
			jz_rqspf_edt.setChecked(false);
			jz_rqspf_edt.setText("让球胜平负");
		}
		if(zhggbean.getJzzjqsList().size()>0){
			jz_zjqs_edt.setChecked(true);
			for(String pv : zhggbean.getJzzjqsList()){
				zjqs.append(JCBFUtils.getJZZFString(Integer.parseInt(pv.replaceAll("\\+","")))+"|");
			}
			jz_zjqs_edt.setText("总进球\n("+zjqs.substring(0,zjqs.length()-1)+")");
		}else{
			jz_zjqs_edt.setChecked(false);
			jz_zjqs_edt.setText("总进球数");
		}
		if(zhggbean.getJzbqcList().size()>0){
			jz_bqc_edt.setChecked(true);
			for(String pv : zhggbean.getJzbqcList()){
				bqc.append(JCBFUtils.getJZBqcString(Integer.parseInt(pv))+"|");
			}
			jz_bqc_edt.setText("半全场\n("+bqc.substring(0,bqc.length()-1)+")");
		}else{
			jz_bqc_edt.setChecked(false);
			jz_bqc_edt.setText("半全场");
		}
		if(zhggbean.getJzbfList().size()>0){
			jz_bf_edt.setChecked(true);
			for(String pv : zhggbean.getJzbfList()){
				bf.append(JCBFUtils.getJZString(Integer.parseInt(pv))+"|");
			}
			jz_bf_edt.setText("比分\n("+bf.substring(0,bf.length()-1)+")");
		}else{
			jz_bf_edt.setChecked(false);
			jz_bf_edt.setText("比分");
		}
		SetReturnOnclick(LotteryId.JCZQ,LotteryId.PLAY_ID_06,zhggbean);
	}
	private void SetReturnOnclick(String lotteryId, String playMethod, JZMatchBean bean) {
		if(zhggbean.getJzspfList().size() >0 || zhggbean.getJzrqspfLiset().size() >0 ||zhggbean.getJzbqcList().size() >0 ||zhggbean.getJzzjqsList().size() >0 ||zhggbean.getJzbfList().size() >0 ){
			status=true;
		}else {
			status=false;
		}
		if(onBetOnClickListener != null){
			onBetOnClickListener.onOkBtnClick(lotteryId,playMethod,bean, status);
		}
	}
}
