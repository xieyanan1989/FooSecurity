package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.MainActivity;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xyn-pc on 2017/5/30.
 */

public class RegiontDialog extends Dialog {
    private JSONArray jsonArry;
    private String errormsg;
    private Message msg;
    private String parendId ;
    private Context context;
    private ListView regist_region_linerlayout;
    private Button region_dia_sure;
    private OnButtonOnClickListener buttonOnClickListener;
    private String cityId;
    private String cityName;
    private MyListViewAdapter lv_adapter;
    private Map map = new HashMap();
    public RegiontDialog(@NonNull Context context, String parendId) {
        super(context);
        this.parendId = parendId;
        this.context = context;
        initView();
        //获取region数据
        getRegion();
    }

    private void getRegion() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = "{\"parentid\":\""+parendId+"\"}";
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("pro/cities",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        jsonArry = ob.getJSONArray("list");
                        errormsg = UTIL.errorCode(ob.getString("msg"));
                        msg = new Message();
                        msg.what = 2;
                        allhand.removeMessages(2);
                        allhand.sendMessage(msg);
                    }else{
                        errormsg = UTIL.errorCode(ob.getString("msg"));
                        msg = new Message();
                        msg.what = 1;
                        allhand.removeMessages(1);
                        allhand.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        setCanceledOnTouchOutside(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_region, null);
        regist_region_linerlayout = (ListView) view.findViewById(R.id.regist_region_linerlayout);
        region_dia_sure = (Button) view.findViewById(R.id.region_dia_sure);
        diasureEvent(region_dia_sure);
        setContentView(view);
    }

    private void diasureEvent(Button diasure) {
        region_dia_sure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
                if(buttonOnClickListener != null){
                    buttonOnClickListener.onOkBtnClick(cityId,cityName);
                }
            }
        });
    }
    public void show(){

//        if(!TextUtils.isEmpty(text)){
//            content.setText(text);
//        }
        super.show();
//		this.getWindow().setLayout(ScreenUtil.dip2px(context, 650), LayoutParams.WRAP_CONTENT);
//        this.getWindow().setLayout(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    public interface OnButtonOnClickListener {
        public void onOkBtnClick(String reginCode,String regionName);
    }
    public void setButtonOnClickListener(OnButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
    }

    Handler allhand = new Handler(){
        public void handleMessage(Message msg) {
            this.obtainMessage();
            // 更新UI
            switch (msg.what) {
                case 1:
                    contextDialog(errormsg);
                    break;
                case 2:
                    initScroll();
                    break;
            }
        }
    };


    private  void initScroll(){
        try {
            lv_adapter = new MyListViewAdapter(context);
            regist_region_linerlayout.setAdapter(lv_adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }

    class MyListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public MyListViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            int length = 0 ;
            try {
                length = jsonArry.length();
                Log.e("length:",String.valueOf(length));
            }catch (Exception e){
                e.printStackTrace();
            }
            return length;
        }

        public Object getItem(int position) {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonArry.get(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return jsonObject;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                final ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.region_list_item, null);
                    holder.region_checkBox = (RadioButton) convertView.findViewById(R.id.region_checkBox);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
//                    holder.region_checkBox.setTag(position);
                }
                final JSONObject jsonObject = (JSONObject)jsonArry.get(position);
                holder.region_checkBox.setText(jsonObject.getString("cityname"));
                final View finalConvertView = convertView;
                holder.region_checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(index == position){
                                index = position;
                                map.put("position",holder);
                            }else{
                                index = position;
                                ViewHolder ho = (ViewHolder) map.get("position");
                                if (ho == null){
                                    map.put("position",holder);
                                }else{
                                    ho.region_checkBox.setChecked(false);
                                    map.put("position",holder);
                                }
                            }
                            cityId = jsonObject.getString("cityid");
                            cityName = jsonObject.getString("cityname");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                return convertView;
            }catch (Exception e){
                e.printStackTrace();
            }
            return convertView;
        }
    }
        static class ViewHolder {
            RadioButton region_checkBox;
        }

        private int index = -1;
}
