package com.fsapp.sunsi.foosecurity.product;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.ImagesTransformation;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapProductActivity extends AppCompatActivity {
    private JSONArray jsonArry;
    private Context context = MapProductActivity.this;
    private ListView pro_pros;
    private ListViewAdapter lv_adapter;
    private Map map = new HashMap();
    private int pagerWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_product);
        pro_pros = (ListView)findViewById(R.id.pro_pros);
        try {
            jsonArry= new JSONArray(getIntent().getBundleExtra("jsonArray"));
        }catch (Exception e){
            e.printStackTrace();
        }
        initScroll();
    }
    private  void initScroll(){
        try {
            lv_adapter = new ListViewAdapter(context);
//            lv_adapter.notifyDataSetChanged();
            pro_pros.setAdapter(lv_adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    class ListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public ListViewAdapter(Context context) {
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
                    convertView = mInflater.inflate(R.layout.pro_list_item, null);
                    holder.user_pro_img = (ImageView) convertView.findViewById(R.id.user_pro_img);
                    holder.user_pro_cateName = (TextView) convertView.findViewById(R.id.user_pro_cateName);
                    holder.user_pro_saletype = (TextView) convertView.findViewById(R.id.user_pro_saletype);
                    holder.user_pro_title = (TextView) convertView.findViewById(R.id.user_pro_title);
                    holder.user_pro_saleCountMea = (TextView) convertView.findViewById(R.id.user_pro_saleCountMea);
                    holder.user_pro_saleCount = (TextView) convertView.findViewById(R.id.user_pro_saleCount);
                    holder.user_pro_saleSingle = (TextView) convertView.findViewById(R.id.user_pro_saleSingle);
                    holder.user_pro_imgs= (ViewPager)convertView.findViewById(R.id.user_pro_imgs);
                    holder.user_pro_subtract= (Button) convertView.findViewById(R.id.user_pro_subtract);
                    holder.user_pro_add= (Button) convertView.findViewById(R.id.user_pro_add);
                    holder.user_pro_buy_amount = (TextView) convertView.findViewById(R.id.user_pro_buy_amount);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                final JSONObject jsonObject = (JSONObject)jsonArry.get(position);
                holder.user_pro_cateName.setText(jsonObject.getString("catename"));
                int saletype = jsonObject.getInt("saletype");
                if(saletype == 1){
                    holder.user_pro_saletype.setText("预售");
                }else{
                    holder.user_pro_saletype.setText("在售");
                }
                holder.user_pro_title.setText(jsonObject.getString("saletitle"));
                holder.user_pro_saleCountMea.setText(jsonObject.getString("salecount")+jsonObject.getString("salemea"));
                Double proPrice = Double.parseDouble(jsonObject.getString("proPrice"));
                holder.user_pro_buy_amount.setText(jsonObject.getString("proPrice"));
//                holder.user_pro_saleCount.setText(jsonObject.getString("salecount"));
                int salesingle = jsonObject.getInt("salesingle");
                if(salesingle == 1){
                    holder.user_pro_saleSingle.setText("单卖");
                }else{
                    holder.user_pro_saleSingle.setText("整售");
                }
                List<ImageView> imageViewList = new ArrayList<ImageView>();
                String[] imgUrls = jsonObject.get("imgurl").toString().split("\\/");
                for(String url : imgUrls[1].split(",")){
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(HttpRequest.img_url+imgUrls[0]+"/"+url).into(imageView);
                    imageViewList.add(imageView);
                }
                //将下载下来的图片加载到viewAdapter
                showimgs(holder.user_pro_imgs,convertView,imageViewList);
                int saleCount =Integer.parseInt(jsonObject.getString("salecount"));
                int currCount =Integer.parseInt((String) holder.user_pro_saleCount.getText());
                //减少数量的事件
                countSubtract(holder.user_pro_subtract,currCount,holder.user_pro_saleCount,holder.user_pro_buy_amount,proPrice);
                //增加数量的事件
                countAdd(holder.user_pro_add,saleCount,currCount,holder.user_pro_saleCount,holder.user_pro_buy_amount,proPrice);
            }catch (Exception e){
                e.printStackTrace();
            }

            return convertView;
        }
    }

    private void countAdd(final Button user_pro_saleCount, final int saleCount, final int currCount, final TextView userProSaleCount, final TextView user_pro_buy_amount, final Double proPrice) {
        user_pro_saleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count  =Integer.parseInt((String) userProSaleCount.getText())+1;
                if(saleCount >= count){
                    userProSaleCount.setText(""+count);
                    user_pro_buy_amount.setText(""+(proPrice*count));
                }
            }
        });
    }

    private void countSubtract(final Button user_pro_saleCount, final int currCount,final TextView pro_saleCount, final TextView user_pro_buy_amount,final Double proPrice) {
        user_pro_saleCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int count  =Integer.parseInt((String) pro_saleCount.getText())-1;
                if(count >= 1){
                    pro_saleCount.setText(""+count);
                    user_pro_buy_amount.setText(""+(proPrice*count));
                }
            }
        });
    }

    /**
     * 加载viewAdapter
     * @param user_pro_imgs
     * @param convertView
     * @param imageViewList
     */
    private void showimgs(final ViewPager user_pro_imgs, View convertView,final List<ImageView> imageViewList) {
        user_pro_imgs.setOffscreenPageLimit(3);
        pagerWidth= (int) (context.getResources().getDisplayMetrics().widthPixels*3.0f/5.0f);
        ViewGroup.LayoutParams lp=user_pro_imgs.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        user_pro_imgs.setLayoutParams(lp);
        user_pro_imgs.setPageMargin(-50);
        convertView.findViewById(R.id.user_pro_gallery).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return user_pro_imgs.dispatchTouchEvent(motionEvent);
            }
        });
        user_pro_imgs.setPageTransformer(true,new ImagesTransformation());
        user_pro_imgs.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(imageViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView=imageViewList.get(position);
                container.addView(imageView,position);
                return imageView;
            }
        });
    }

    static class ViewHolder {
        ImageView user_pro_img;
        TextView user_pro_cateName;
        TextView user_pro_saletype;
        TextView user_pro_title;
        TextView user_pro_saleCountMea;
        TextView user_pro_saleCount;
        TextView user_pro_saleSingle;
        ViewPager user_pro_imgs;
        Button user_pro_subtract;
        Button user_pro_add;
        TextView user_pro_buy_amount;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
