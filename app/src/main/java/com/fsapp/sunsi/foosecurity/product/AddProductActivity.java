package com.fsapp.sunsi.foosecurity.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.regist.RegistUserImgActivity;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import java.util.Map;

public class AddProductActivity extends AppCompatActivity {

    //产品名称
    private EditText pro_title;
    //产品详情
    private EditText pro_content;
    //下一步
    private Button pro_add_next;
    Map map = UTIL.getProductSingle();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        context = AddProductActivity.this;
        pro_title = (EditText) findViewById(R.id.pro_title);
        pro_content = (EditText) findViewById(R.id.pro_content);
        pro_add_next = (Button) findViewById(R.id.pro_add_next);
        //常量：产品中的数据清空
        UTIL.clearRegist();
        //下一步点击事件
        addProEvent(pro_add_next);
    }

    private void addProEvent(Button pro_add_next) {
        pro_add_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pro_title.getText().toString().equals("")){
                    contextDialog("请输入商品名称");
                }else if(pro_content.getText().toString().equals("")){
                    contextDialog("请输入商品详情");
                }else{
                    map.put("saletitle",pro_title.getText().toString());
                    map.put("saledetail",pro_content.getText().toString());
                    Intent intent = new Intent(context,SellProductActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }

}
