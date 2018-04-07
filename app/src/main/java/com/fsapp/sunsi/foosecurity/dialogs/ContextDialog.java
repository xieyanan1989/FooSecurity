package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;

/**
 * Created by xyn-pc on 2017/5/30.
 */

public class ContextDialog extends Dialog {
    private String text ;
    private Context context;
    private TextView content;
    private Button diasure;
    public ContextDialog(@NonNull Context context,String text) {
        super(context);
        this.text = text;
        this.context = context;
        initView();
    }

    private void initView() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_context, null);
        content = (TextView) view.findViewById(R.id.dia_context);
        diasure = (Button) view.findViewById(R.id.dia_sure);
        diasureEvent(diasure);
        setContentView(view);
    }

    private void diasureEvent(Button diasure) {
        diasure.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void show(){

        if(!TextUtils.isEmpty(text)){
            content.setText(text);
        }

        super.show();
//		this.getWindow().setLayout(ScreenUtil.dip2px(context, 650), LayoutParams.WRAP_CONTENT);
//        this.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
}
