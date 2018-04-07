package com.fsapp.sunsi.foosecurity.product;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.CatesDialog;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.dialogs.RegiontDialog;
import com.fsapp.sunsi.foosecurity.regist.RegistUserImgActivity;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SellProductActivity extends AppCompatActivity {
    //产品类型
    private TextView pro_cates;
    //选择类型
    private Button pro_check_cates;
    Map map = UTIL.getProductSingle();
    private Context context = this;
    //数量
    private EditText pro_sale_count;
    //单价
    private EditText pro_price;
    private double comm;
    private int catetype;
    private TextView pro_com;
    //单卖
    private CheckBox pro_sale_single;
    //自取包裹
    private CheckBox pro_send_ornot;
    //预售
    private CheckBox pro_sale_type;
    //预售时间
    private Button pro_type_time;
    //时间显示
    private TextView pro_time_show;
    //下一步
    private Button pro_next;
    //计量单位
    private Spinner pro_sale_mea;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_product);
        pro_sale_count = (EditText) findViewById(R.id.pro_sale_count);
        pro_cates = (TextView) findViewById(R.id.pro_cates);
        pro_check_cates = (Button) findViewById(R.id.pro_check_cates);
        pro_sale_count = (EditText) findViewById(R.id.pro_sale_count);
        pro_price = (EditText) findViewById(R.id.pro_price);
        pro_com = (TextView) findViewById(R.id.pro_com);
        pro_sale_single = (CheckBox) findViewById(R.id.pro_sale_single);
        pro_send_ornot = (CheckBox) findViewById(R.id.pro_send_ornot);
        pro_sale_type = (CheckBox) findViewById(R.id.pro_sale_type);
        pro_type_time = (Button) findViewById(R.id.pro_type_time);
        pro_time_show = (TextView) findViewById(R.id.pro_time_show);
        pro_next = (Button) findViewById(R.id.pro_next);
        pro_sale_mea = (Spinner) findViewById(R.id.pro_sale_mea);
        //类别选择事件
        catesEvent(pro_check_cates);
        //价格鼠标离开事件
        priceKeyEvent(pro_price);
        //时间选择事件
        timerEvent(pro_type_time);
        //下一步点击事件
        proNextEvent(pro_next);
        initDate();
    }

    private void proNextEvent(Button pro_next) {
        pro_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map.get("cateid") == null || map.get("cateid").equals("")){
                    contextDialog("请选择类型");
                }else if(pro_sale_count.getText().toString().equals("")){
                    contextDialog("请输入数量");
                }else if(pro_price.getText().toString().equals("")){
                    contextDialog("请输入单价");
                }else{
                    map.put("salecount",pro_sale_count.getText().toString());
                    map.put("pro_price",pro_price.getText().toString());
                    if(pro_sale_single.isChecked()){
                        //单卖是1非单卖是2
                        map.put("salesingle",1);
                    }else{
                        map.put("salesingle",2);
                    }
                    if(pro_send_ornot.isChecked()){
                        //自取包裹是1店家发货是2
                        map.put("send_ornot",1);
                    }else{
                        map.put("send_ornot",2);
                    }
                    if(pro_sale_type.isChecked()){
                        //预售是1非预售是2
                        map.put("saletype",1);
                    }else{
                        map.put("saletype",2);
                    }
                    map.put("book_time",pro_time_show.getText().toString());
                    String selectText = pro_sale_mea.getSelectedItem().toString();
                    map.put("salemea",selectText);
                    Intent intent = new Intent(context,ImageProductActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private void timerEvent(Button pro_type_time) {
        pro_type_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(context, new Datelistener(), mYear, mMonth, mDay).show();//调用
            }
        });
    }

    private void initDate() {
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String strmonth = String.format("%02d", mMonth);
        String strday = String.format("%02d", mDay);
        pro_time_show.setText(mYear+"-"+strmonth+"-"+strday);
    }

    private void priceKeyEvent(EditText pro_price) {
        pro_price.addTextChangedListener(textWatcher);
    }
    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            calPrice();
        }
    };
    private void calPrice() {
        Double price = Double.parseDouble(pro_price.getText().toString());
        Double count = Double.parseDouble(pro_sale_count.getText().toString());
        pro_com.setText(""+(price*count*comm));
    }


    private void catesEvent(Button pro_check_cates) {
        pro_check_cates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Map jsonmap = new HashMap();
                jsonmap.put("parentid","0000000000");
                CatesDialog catesDialog = new CatesDialog(context, jsonmap);
                catesDialog.setButtonOnClickListener(new CatesDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(Map returnamp) {
                        String cate_name = (String) returnamp.get("cateName");
                        if (cate_name == null || cate_name.equals("")){
                            contextDialog("您未选择类别");
                            return;
                        }
                        map.put("cateid",(String) returnamp.get("cateId"));
                        map.put("catetype",returnamp.get("catetype"));
                        pro_cates.setText(cate_name);
                        comm = (double) returnamp.get("comm");
//                        catetype = returnamp.get("catetype");
                    }
//                    @Override
//                    public void onOkBtnClick(String cate_id, String cate_name,double com,int ct) {
//                        if (cate_name == null || cate_name.equals("")){
//                            contextDialog("您未选择类别");
//                            return;
//                        }
//                        map.put("cateid",cate_id);
//                        map.put("catetype",ct);
//                        pro_cates.setText(cate_name);
//                        comm = com;
//                        catetype = ct;
//                    }
                });
                catesDialog.show();
            }
        });
    }
    private class Datelistener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            //调用完日历控件点完成后干的事
            String strmonth = String.format("%02d", month+1);
            String strday = String.format("%02d", day);
            pro_time_show.setText(year+"-"+strmonth+"-"+strday);
        }
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
}
