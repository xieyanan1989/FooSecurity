package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.fsapp.sunsi.foosecurity.R;
/**自定义退出dialog
 * @author Messi
 *
 */
public class BaseDialog extends Dialog {
	
	private Button okBtn,cancleBtn;
	private TextView titleTx;
	private LinearLayout layout;
	private String title;
	private String okStr, cancleStr;
	private int type;//默认两个按钮，1为一个按钮

	public Context context;
	
	private OnButtonOnClickListener buttonOnClickListener;

	public BaseDialog(Context context) {
		super(context, R.style.dialog);
		this.context = context;
		initView();
	}

	public BaseDialog(Context context, String title) {
		super(context, R.style.dialog);
		this.context = context;
		this.title = title;
		initView();
	}

	public BaseDialog(Context context, int type) {
		super(context, R.style.dialog);
		this.context = context;
		this.type = type;
		initView();
	}
	
	public BaseDialog(Context context, String ok_btn_text,String cancle_btn_text) {
		super(context, R.style.dialog);
		this.context = context;
		initView();
	}
	
	private void initView(){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.dialog_base_view, null);
		
		okBtn = (Button) view.findViewById(R.id.dialog_sure);
		cancleBtn = (Button) view.findViewById(R.id.dialog_cancel);
		layout = (LinearLayout) view.findViewById(R.id.dialog_base_layout);
		titleTx = (TextView) view.findViewById(R.id.dialog_title);
		
		okBtn.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				dismiss();
				if(buttonOnClickListener != null){
					buttonOnClickListener.onOkBtnClick();
				}
			}
		});
		
		cancleBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(buttonOnClickListener != null){
					buttonOnClickListener.onCancleBtnClick();
				}
			}
		});
		setContentView(view);
	}
	
	public void show(){
		
		if(type == 1){
			cancleBtn.setVisibility(View.GONE);
		}
		
		if(!TextUtils.isEmpty(title)){
			titleTx.setText(title);
		}
		
		if(okStr != null){
			if(!okStr.equals("")){
				okBtn.setText(okStr);
			}
		}
		
		if(cancleStr != null){
			if(!cancleStr.equals("")){
				cancleBtn.setText(cancleStr);
			}
		}
		
		super.show();
//		this.getWindow().setLayout(ScreenUtil.dip2px(context, 700), 350);
		this.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	}
	
	public interface OnButtonOnClickListener {
		public void onOkBtnClick();
		public void onCancleBtnClick();
	}
	
	public void setButtonOnClickListener(OnButtonOnClickListener buttonOnClickListener) {
		this.buttonOnClickListener = buttonOnClickListener;
	}
	
	public void setLayout(View view){
		layout.addView(view);
	}
	
	public void setOkStr(String okStr) {
		this.okStr = okStr;
	}

	public void setCancleStr(String cancleStr) {
		this.cancleStr = cancleStr;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public void setCancelable(boolean isCancle){
		setCancelable(isCancle);
	}

}
