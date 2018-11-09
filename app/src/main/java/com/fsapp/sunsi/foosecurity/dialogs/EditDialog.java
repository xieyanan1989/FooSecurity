package com.fsapp.sunsi.foosecurity.dialogs;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EditDialog extends BaseDialog {

	private TextView text;
	private Context context;
	
	private String[] content = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "删除" };
	
	private Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btn0, btnDel; 
	
	private StringBuilder sb;
	
	public EditDialog(Context context) {
		super(context, "输入倍数");
		this.context = context;
		
		initView();
	}

	private void initView(){
		sb  = new StringBuilder();
		View view = Tools.getXml(context, R.layout.dialog_edit);
		text = (TextView) view.findViewById(R.id.dialog_edit_tv);
		
		btn1 = (Button) view.findViewById(R.id.dialog_deit_1);
		btn2 = (Button) view.findViewById(R.id.dialog_deit_2);
		btn3 = (Button) view.findViewById(R.id.dialog_deit_3);
		btn4 = (Button) view.findViewById(R.id.dialog_deit_4);
		btn5 = (Button) view.findViewById(R.id.dialog_deit_5);
		btn6 = (Button) view.findViewById(R.id.dialog_deit_6);
		btn7 = (Button) view.findViewById(R.id.dialog_deit_7);
		btn8 = (Button) view.findViewById(R.id.dialog_deit_8);
		btn9 = (Button) view.findViewById(R.id.dialog_deit_9);
		btn0 = (Button) view.findViewById(R.id.dialog_deit_0);
		btnDel = (Button) view.findViewById(R.id.dialog_deit_delete);
		
		btn1.setOnClickListener(onClick);
		btn2.setOnClickListener(onClick);
		btn3.setOnClickListener(onClick);
		btn4.setOnClickListener(onClick);
		btn5.setOnClickListener(onClick);
		btn6.setOnClickListener(onClick);
		btn7.setOnClickListener(onClick);
		btn8.setOnClickListener(onClick);
		btn9.setOnClickListener(onClick);
		btn0.setOnClickListener(onClick);
		btnDel.setOnClickListener(onClick);
		setLayout(view);
	}
	
	private View.OnClickListener onClick = new View.OnClickListener(){

		@Override
		public void onClick(View v) {
			
			if(v == btn0){
				sb.append(0);
			}else if(v == btn1){
				sb.append(1);
			}else if(v == btn2){
				sb.append(2);
			}else if(v == btn3){
				sb.append(3);
			}else if(v == btn4){
				sb.append(4);
			}else if(v == btn5){
				sb.append(5);
			}else if(v == btn6){
				sb.append(6);
			}else if(v == btn7){
				sb.append(7);
			}else if(v == btn8){
				sb.append(8);
			}else if(v == btn9){
				sb.append(9);
			}else if(v == btn0){
				sb.append(0);
			}else if(v == btnDel){
				if(sb.length() > 0)
					sb.deleteCharAt(sb.length() - 1);
			}
			if(sb.length() > 2)
				sb.deleteCharAt(0);
			
			setText();
		}
	};
	
	private void setText(){
		text.setText(sb.toString());
	}
	
	public String getString(){
		return sb.toString();
	}
}
