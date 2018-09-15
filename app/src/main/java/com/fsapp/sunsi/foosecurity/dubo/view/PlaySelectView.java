package com.fsapp.sunsi.foosecurity.dubo.view;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;

public class PlaySelectView {

	private Context mContext;
	private JCSelectPlayTypeView mScroll;
	private boolean show;
	
	private int defaultIndex;
	
	private List<TextView> listViews;
	private OnClickPlaySelect onClick;
	
	public PlaySelectView(Context mContext, JCSelectPlayTypeView mScroll, ArrayList<PlayBean> mListPlayBeans, int defaultIndex){
		this.mContext = mContext;
		this.mScroll = mScroll;
		this.defaultIndex = defaultIndex;
		listViews = new ArrayList<TextView>();
		addScrollPlay(mListPlayBeans);
	}

	private void addScrollPlay( ArrayList<PlayBean> mListPlayBeans){
		for(int i = 0, size = mListPlayBeans.size(); i < size; i++){
			PlayBean mBean = mListPlayBeans.get(i);
			View view = LayoutInflater.from(mContext).inflate(R.layout.lottery_number_play_item, null);
			TextView tvName = (TextView) view.findViewById(R.id.lottery_play_item_name);
			tvName.setText(mBean.getPlayName());
			tvName.setId(i);
			view.setId(i);
			if(i == defaultIndex) {
				tvName.setTextColor(mContext.getResources().getColor(R.color.red));
			}
			view.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					for(TextView view: listViews){
						if(view.getId() == v.getId()){
							view.setTextColor(mContext.getResources().getColor(R.color.red));
						}else{
							view.setTextColor(mContext.getResources().getColor(R.color.black));
						}
					}
					if(onClick != null)
						onClick.onClick(v);
				}
			});
			listViews.add(tvName);
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
