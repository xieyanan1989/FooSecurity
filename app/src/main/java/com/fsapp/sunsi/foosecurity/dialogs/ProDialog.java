package com.fsapp.sunsi.foosecurity.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2018/4/7.
 */

public class ProDialog extends Dialog {
    private JSONArray jsonArry;
    private Context context;
    private ListView pro_pros;
    private Button pro_sure;
    private OnButtonOnClickListener buttonOnClickListener;
    private MyListViewAdapter lv_adapter;
    private Map map = new HashMap();
    public ProDialog(@NonNull Context context, JSONArray jsonArray) {
        super(context);
        this.jsonArry = jsonArray;
        this.context = context;
        initView();
    }

    private void initView() {
        setCanceledOnTouchOutside(false);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_product, null);
        pro_pros = (ListView) view.findViewById(R.id.pro_pros);
        pro_sure = (Button) view.findViewById(R.id.pro_sure);
        diasureEvent(pro_sure);
        setContentView(view);
        initScroll();
    }

    private void diasureEvent(Button diasure) {
        diasure.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
    public void show(){
        super.show();
    }
    public interface OnButtonOnClickListener {
        //        public void onOkBtnClick(String cate_id, String cate_name,double comm,int ct);
        public void onOkBtnClick(Map returnamp);
    }
    public void setButtonOnClickListener(ProDialog.OnButtonOnClickListener buttonOnClickListener) {
        this.buttonOnClickListener = buttonOnClickListener;
    }


    private  void initScroll(){
        try {
            lv_adapter = new MyListViewAdapter(context);
            lv_adapter.notifyDataSetChanged();
            pro_pros.setAdapter(lv_adapter);
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
                final ViewHolder holder;
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = mInflater.inflate(R.layout.pro_list_item, null);
                    holder.user_pro_img = (ImageView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_cateName = (TextView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_saletype = (TextView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_title = (TextView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_saleCountMea = (TextView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_saleSingle = (TextView) convertView.findViewById(R.id.user_pro_img);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
            return convertView;
        }
    }

    static class ViewHolder {
        ImageView user_pro_img;
        TextView user_pro_cateName;
        TextView user_pro_saletype;
        TextView user_pro_title;
        TextView user_pro_saleCountMea;
        TextView user_pro_saleSingle;
    }
}
