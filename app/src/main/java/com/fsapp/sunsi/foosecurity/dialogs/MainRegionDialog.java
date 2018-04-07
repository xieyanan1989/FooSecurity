package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.regist.RegistUserImgActivity;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2017/5/30.
 */

public class MainRegionDialog extends Dialog {
    private Context context;
    private Button region_dia_sure;
    private Map regionMap = new HashMap();
    //省选择按钮
    private Button regist_check_province;
    //市选择按钮
    private Button regist_check_city;
    //县选择按钮
    private Button regist_check_county;
    //镇选择按钮
    private Button regist_check_town;
    //村选择按钮
    private Button regist_check_village;

    //省显示
    private TextView regist_text_province;
    //市显示
    private TextView regist_text_city;
    //县区显示
    private TextView regist_text_county;
    //镇显示
    private TextView regist_text_town;
    //村显示
    private TextView regist_text_village;
    //下一步
    private Button regist_address_submit;
    private OnButtonOnClickListener buttonOnClickListener;
    public MainRegionDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    private void initView() {
        try {
            setCanceledOnTouchOutside(false);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.dialog_region_main, null);
            regist_text_province = (TextView) view.findViewById(R.id.regist_text_province);
            regist_text_city = (TextView) view.findViewById(R.id.regist_text_city);
            regist_text_county = (TextView) view.findViewById(R.id.regist_text_county);
            regist_text_town = (TextView) view.findViewById(R.id.regist_text_town);
            regist_text_village = (TextView) view.findViewById(R.id.regist_text_village);

            regist_check_province = (Button) view.findViewById(R.id.regist_check_province);
            regist_check_city = (Button) view.findViewById(R.id.regist_check_city);
            regist_check_county = (Button) view.findViewById(R.id.regist_check_county);
            regist_check_town = (Button) view.findViewById(R.id.regist_check_town);
            regist_check_village = (Button) view.findViewById(R.id.regist_check_village);
            region_dia_sure = (Button) view.findViewById(R.id.region_dia_sure);
            setContentView(view);
            diasureEvent();
            //获取省事件
            regionProvinceEvent();
            //获取市事件
            regionCityeEvent();
            //获取县事件
            regionCountyeEvent();
            //获取县事件
            regionTownEvent();
            //获取县事件
            regionVallageEvent();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void regionVallageEvent() {
        regist_check_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) regionMap.get("townId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        regionMap.put("countryId",reginCode);
                        regionMap.put("countryName",regionName);
                        regist_text_village.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionTownEvent() {
        regist_check_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) regionMap.get("districtId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        regionMap.put("townId",reginCode);
                        regionMap.put("townName",regionName);
                        regist_text_town.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionCountyeEvent() {
        regist_check_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) regionMap.get("cityId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        regionMap.put("districtId",reginCode);
                        regionMap.put("districtName",regionName);
                        regist_text_county.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionProvinceEvent() {
        try {
            regist_check_province.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        RegiontDialog regionDialog = new RegiontDialog(context,"000000000000");
                        regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                            @Override
                            public void onOkBtnClick(String reginCode, String regionName) {
                                regionMap.put("provinceId",reginCode);
                                regionMap.put("provinceName",regionName);
                                regist_text_province.setText(regionName);
                            }
                        });
                        regionDialog.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void regionCityeEvent() {
        regist_check_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) regionMap.get("provinceId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        regionMap.put("cityId",reginCode);
                        regionMap.put("cityName",regionName);
                        regist_text_city.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }
    private void diasureEvent() {
            region_dia_sure.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if((regionMap.get("provinceId") == null || regionMap.get("provinceId").equals("")) &&
                            (regionMap.get("cityId") == null || regionMap.get("cityId").equals("")) &&
                            (regionMap.get("districtId") == null || regionMap.get("districtId").equals("")) &&
                            (regionMap.get("townId") == null || regionMap.get("townId").equals("")) &&
                            (regionMap.get("countryId") == null || regionMap.get("countryId").equals(""))){
                        contextDialog("请选择省/市/县/镇/村，其中一项");
                    }else {
                        dismiss();
                        if (buttonOnClickListener != null) {
                            buttonOnClickListener.onOkBtnClick(regionMap);
                        }
                    }
                }
            });
    }
    public void show(){
        super.show();
    }
    public interface OnButtonOnClickListener {
        public void onOkBtnClick(Map mainRegionMap);
    }
    public void setButtonOnClickListener(OnButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
    }


    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
//    Handler allhand = new Handler(){
//        public void handleMessage(Message msg) {
//            this.obtainMessage();
//            // 更新UI
//            switch (msg.what) {
//                case 1:
//                    contextDialog(errormsg);
//                    break;
//            }
//        }
//    };
}
