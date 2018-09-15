package com.fsapp.sunsi.foosecurity.dubo.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;

import java.util.ArrayList;
import java.util.List;

public class ChuanSelectView {

	private Context mContext;
	private JCSelectPlayTypeView mScroll;
	private boolean show;

	private List<CheckBox> listViews;
	private OnClickPlaySelect onClick;

	public ChuanSelectView(Context mContext, JCSelectPlayTypeView mScroll,String[] chuans){
		this.mContext = mContext;
		this.mScroll = mScroll;
		listViews = new ArrayList<CheckBox>();
		addScrollPlay(chuans);
	}

	private void addScrollPlay(String[] mListPlayBeans){
		for(int i = 0, size = mListPlayBeans.length; i < size; i++){
			String chuanName = mListPlayBeans[i];
			CheckBox view = (CheckBox) Tools.getXml(mContext,R.layout.dubo_chuanguan_item);
			view.setText(chuanName);
			view.setId(i+0);
			listViews.add(view);
			mScroll.addView(view);
		}
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		if(show){
			mScroll.setVisibility(View.VISIBLE);
		}else{
			mScroll.setVisibility(View.GONE);
		}
		this.show = show;
	}
	
	/**
	 * SelectView 的点击事件
	 * @param onClick
	 */
	public void setOnClickPlaySelect(OnClickPlaySelect onClick){
		this.onClick = onClick;
	}
	
	public interface OnClickPlaySelect{
		public void onClick(View v);
	}
}
