package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
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

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2017/5/30.
 */

public class CatesDialog extends Dialog {
    private JSONArray jsonArry;
    private JSONArray catejsonArry;
    private String errormsg;
    private Message msg;
    private Context context;
    private ListView pro_cates_par;
    private ListView pro_cates;
    private Button region_dia_sure;
    private OnButtonOnClickListener buttonOnClickListener;
//    private String cateId;
//    private String cateName;
//    private Double comm;
//    private int catetype;
    private MyListViewAdapter lv_adapter;
    private CatesListViewAdapter cates_adapter;
    private Map map = new HashMap();
    private Map jsonmap = new HashMap();
    private Map returnmap = new HashMap();
    private int cate_level = 1;
    public CatesDialog(@NonNull Context context, Map jsonmap) {
        super(context);
        this.jsonmap = jsonmap;
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
                    String json = UTIL.AjaxJson(jsonmap);
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("pro/cates",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        if (cate_level ==1){
                            jsonArry = ob.getJSONArray("list");
                        }else{
                            catejsonArry = ob.getJSONArray("list");
                        }
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
        View view = inflater.inflate(R.layout.dialog_cates, null);
        pro_cates_par = (ListView) view.findViewById(R.id.pro_cates_par);
        pro_cates = (ListView) view.findViewById(R.id.pro_cates);
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
//                    buttonOnClickListener.onOkBtnClick(cateId,cateName,comm,catetype);
                    buttonOnClickListener.onOkBtnClick(returnmap);
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
//        public void onOkBtnClick(String cate_id, String cate_name,double comm,int ct);
            public void onOkBtnClick(Map returnamp);
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
                    if (cate_level == 1){
                        initScroll();
                    }else {
                        initCates();
                    }
                    break;
            }
        }
    };

    private void initCates() {
        try {
            cates_adapter = new CatesListViewAdapter(context);
            cates_adapter.notifyDataSetChanged();
            pro_cates.setAdapter(cates_adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private  void initScroll(){
        try {
            lv_adapter = new MyListViewAdapter(context);
            pro_cates_par.setAdapter(lv_adapter);
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
                holder.region_checkBox.setText(jsonObject.getString("catename"));
                final View finalConvertView = convertView;
                holder.region_checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            cate_level = 2;
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
                            jsonmap.put("parentid",jsonObject.getString("cateid"));
                            getRegion();
//                            cateId = jsonObject.getString("cateid");
//                            cateName = jsonObject.getString("catename");
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

    class CatesListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public CatesListViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            int length = 0 ;
            try {
                length = catejsonArry.length();
                Log.e("length:",String.valueOf(length));
            }catch (Exception e){
                e.printStackTrace();
            }
            return length;
        }

        public Object getItem(int position) {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) catejsonArry.get(position);
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
                final CatesHolder holder;
                if (convertView == null) {
                    holder = new CatesHolder();
                    convertView = mInflater.inflate(R.layout.region_list_item, null);
                    holder.region_checkBox = (RadioButton) convertView.findViewById(R.id.region_checkBox);
                    convertView.setTag(holder);
                } else {
                    holder = (CatesHolder) convertView.getTag();
//                    holder.region_checkBox.setTag(position);
                }
                final JSONObject jsonObject = (JSONObject)catejsonArry.get(position);
                holder.region_checkBox.setText(jsonObject.getString("catename"));
                final View finalConvertView = convertView;
                holder.region_checkBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            if(index_two == position){
                                index_two = position;
                                map.put("cates",holder);
                            }else{
                                index = position;
                                ViewHolder ho = (ViewHolder) map.get("cates");
                                if (ho == null){
                                    map.put("cates",holder);
                                }else{
                                    ho.region_checkBox.setChecked(false);
                                    map.put("cates",holder);
                                }
                            }
                            returnmap.put("cateId",jsonObject.getString("cateid"));
                            returnmap.put("cateName",jsonObject.getString("catename"));
                            returnmap.put("comm",jsonObject.getDouble("comm"));
                            returnmap.put("catetype",jsonObject.getInt("catetype"));
//                            cateId = jsonObject.getString("cateid");
//                            cateName = jsonObject.getString("catename");
//                            comm = jsonObject.getDouble("comm");
//                            catetype = jsonObject.getInt("catetype");
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
        static class CatesHolder {
        RadioButton region_checkBox;
    }

        private int index = -1;
        private int index_two = -1;
}
