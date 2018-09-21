package com.fsapp.sunsi.foosecurity.dubo.view;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;

import com.fsapp.sunsi.foosecurity.R;
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
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					if(onClick != null){
						onClick.onClick(view);
					}
				}
			});
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

	public List<CheckBox> getListViews() {
		return listViews;
	}

	public void setListViews(List<CheckBox> listViews) {
		this.listViews = listViews;
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
	public List<Integer> getSelectChuan(){
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0 ; i < listViews.size(); i++){
			if(listViews.get(i).isChecked()){
				list.add(i+1);
			}
		}
		return list;
	}

	public void setUnCeck(int size){
		for (int i = size ; i < listViews.size(); i++){
			listViews.get(i).setChecked(false);
			}
		}
}
