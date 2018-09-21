package com.fsapp.sunsi.foosecurity.dubo.view;


import android.widget.LinearLayout;

import com.fsapp.sunsi.foosecurity.dialogs.ProDialog;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;


public abstract class JCAbstractClass {
	
	public abstract void showItems(JZMatchBean bean, LinearLayout parent_layout );
	public OnBetOnClickListener onBetOnClickListener;
	public interface OnBetOnClickListener {
		public void onOkBtnClick(String lotteryId, String playMethod, JZMatchBean jzMatchBean, boolean status);
	}
	public void setBetOnClickListener(OnBetOnClickListener onBetOnClickListener) {
		this.onBetOnClickListener = onBetOnClickListener;
	}
}
