package com.fsapp.sunsi.foosecurity.buy;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fsapp.sunsi.foosecurity.LoginActivity;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.product.MapProductActivity;
import com.fsapp.sunsi.foosecurity.util.BookUtil;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.ImagesTransformation;
import com.fsapp.sunsi.foosecurity.util.Settings;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyProActivity extends AppCompatActivity {
    private DBUtil db;
    private JSONArray jsonArry;
    private Context context = BuyProActivity.this;
    private ListView pro_pros;
    private ListViewAdapter lv_adapter;
    private Map map = new HashMap();
    private int pagerWidth;

    private BookListViewAdapter book_lv_adapter;
    private JSONArray bookJsonArry;
    private Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db= new DBUtil(context);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_pro);
        pro_pros = (ListView)findViewById(R.id.buy_pro_pros);
        try {
            jsonArry= db.querPros();
        }catch (Exception e){
            e.printStackTrace();
        }
        //本地购买
        initScroll();
        //购买记录
        initBook();
    }

    private void initBook() {
        DBUtil db= new DBUtil(context);
        Map map = db.querUser();
        String userName = (String)map.get("userName");
        if(userName == "" || userName == null){
            Intent intentd  = new Intent(context,LoginActivity.class);
            context.startActivity(intentd);
        }
        BookUtil.username = userName;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map map = BookUtil.get();
                    String json = UTIL.AjaxJson(map);
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("user/buy",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        bookJsonArry=ob.getJSONArray("list");
                        msg = new Message();
                        msg.what = 1;
                        allhand.removeMessages(1);
                        allhand.sendMessage(msg);
                    }else{

                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void bookScroll() {
        try {
            book_lv_adapter = new BookListViewAdapter(context);
//            lv_adapter.notifyDataSetChanged();
            pro_pros.setAdapter(book_lv_adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
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
    /**
     * 加载viewAdapter
     * @param book_pro_gallery
     * @param convertView
     * @param imageViewList
     */
    private void showBookimgs(final ViewPager book_pro_gallery, View convertView,final List<ImageView> imageViewList) {
        book_pro_gallery.setOffscreenPageLimit(3);
        pagerWidth= (int) (context.getResources().getDisplayMetrics().widthPixels*3.0f/5.0f);
        ViewGroup.LayoutParams lp=book_pro_gallery.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        book_pro_gallery.setLayoutParams(lp);
        book_pro_gallery.setPageMargin(-50);
        convertView.findViewById(R.id.book_pro_gallery).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return book_pro_gallery.dispatchTouchEvent(motionEvent);
            }
        });
        book_pro_gallery.setPageTransformer(true,new ImagesTransformation());
        book_pro_gallery.setAdapter(new PagerAdapter() {
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
    static class BookViewHolder {
        ImageView book_user_pro_img;
        TextView book_user_pro_saleCountMea;
        TextView book_user_pro_catetype;
        TextView book_user_pro_title;
        TextView book_user_pro_cateName;
        TextView book_user_pro_buy_ya_amount;
        ViewPager book_user_pro_imgs;
        TextView boo_user_pro_buy_amount;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    class BookListViewAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        public BookListViewAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
        }

        public int getCount() {
            int length = 0 ;
            try {
                length = bookJsonArry.length();
                Log.e("length:",String.valueOf(length));
            }catch (Exception e){
                e.printStackTrace();
            }
            return length;
        }

        public Object getItem(int position) {
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) bookJsonArry.get(position);
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
                final BookViewHolder holder;
                if (convertView == null) {
                    holder = new BookViewHolder();
                    convertView = mInflater.inflate(R.layout.book_list_item, null);
                    holder.book_user_pro_img = (ImageView) convertView.findViewById(R.id.book_user_pro_img);
                    holder.book_user_pro_saleCountMea = (TextView) convertView.findViewById(R.id.book_user_pro_saleCountMea);
                    holder.book_user_pro_catetype = (TextView) convertView.findViewById(R.id.book_user_pro_catetype);
                    holder.book_user_pro_title = (TextView) convertView.findViewById(R.id.book_user_pro_title);
                    holder.book_user_pro_cateName = (TextView) convertView.findViewById(R.id.book_user_pro_cateName);
                    holder.book_user_pro_buy_ya_amount = (TextView) convertView.findViewById(R.id.book_user_pro_buy_ya_amount);
                    holder.book_user_pro_imgs= (ViewPager)convertView.findViewById(R.id.book_user_pro_imgs);
                    holder.boo_user_pro_buy_amount = (TextView) convertView.findViewById(R.id.boo_user_pro_buy_amount);
                    convertView.setTag(holder);
                } else {
                    holder = (BookViewHolder) convertView.getTag();
                }
                final JSONObject jsonObject = (JSONObject)bookJsonArry.get(position);
                holder.book_user_pro_cateName.setText(jsonObject.getString("catename"));
                int catetype = jsonObject.getInt("catetype");
                if(catetype == 1){
                    holder.book_user_pro_catetype.setText("人工");
                }else{
                    holder.book_user_pro_catetype.setText("野生");
                }
                holder.book_user_pro_title.setText(jsonObject.getString("saletitle"));
                holder.book_user_pro_saleCountMea.setText(jsonObject.getString("salecount")+jsonObject.getString("salemea"));
                holder.boo_user_pro_buy_amount.setText(jsonObject.getString("amount"));
                holder.book_user_pro_buy_ya_amount.setText(jsonObject.getString("deposit"));
                List<ImageView> imageViewList = new ArrayList<ImageView>();
                String[] imgUrls = jsonObject.get("imgurl").toString().split("\\/");
                for(String url : imgUrls[1].split(",")){
                    ImageView imageView = new ImageView(context);
                    Glide.with(context).load(HttpRequest.img_url+imgUrls[0]+"/"+url).into(imageView);
                    imageViewList.add(imageView);
                }
                //将下载下来的图片加载到viewAdapter
                showBookimgs(holder.book_user_pro_imgs,convertView,imageViewList);
            }catch (Exception e){
                e.printStackTrace();
                System.out.print(e.toString());
            }

            return convertView;
        }
    }
    Handler allhand = new Handler(){
        public void handleMessage(Message msg) {
            this.obtainMessage();
            // 更新UI
            switch (msg.what) {
                case 1:
                    bookScroll();
                    break;
            }
        }
    };
}
