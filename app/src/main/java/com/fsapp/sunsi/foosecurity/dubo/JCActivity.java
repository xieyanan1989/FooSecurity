package com.fsapp.sunsi.foosecurity.dubo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBeanListParser;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchListBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;
import com.fsapp.sunsi.foosecurity.dubo.util.PlayInfo;
import com.fsapp.sunsi.foosecurity.dubo.view.AmazingListView;
import com.fsapp.sunsi.foosecurity.dubo.view.ChuanSelectView;
import com.fsapp.sunsi.foosecurity.dubo.view.JCSelectPlayTypeView;
import com.fsapp.sunsi.foosecurity.dubo.view.PlaySelectView;
import com.fsapp.sunsi.foosecurity.dubo.view.SectionListAdapter;
import com.fsapp.sunsi.foosecurity.dubo.view.StringUtil;
import com.fsapp.sunsi.foosecurity.util.BookUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JCActivity extends AppCompatActivity {
    //获取所有玩法
    private ArrayList<PlayBean> mListPlayBeans = new ArrayList<PlayBean>();
    //彩种id
    private String lotterylid = "209";
    //添加玩法标签
    JCSelectPlayTypeView jc_play_select;
    private PlaySelectView mPlaySelect;
    private LinearLayout jc_play_type_show;
    private LinearLayout jc_play_type_title;
    private TextView jc_play_type;
    private  int playIndex = 0;
    //串关显示
    private LinearLayout jc_chuan_show;
    private JCSelectPlayTypeView jc_chuan_select_plays;
    private String[] chuanCheck = new String[] { "单 关","2串1", "3串1", "4串1", "5串1", "6串1", "7串1", "8串1" };
    ChuanSelectView chuanSelect;
    private LinearLayout jc_choose_chuan;
    private ImageView jc_choose_chuan_img;
    private JSONArray jsonArry;
    private Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jc);
        inflater = LayoutInflater.from(this);
        mListPlayBeans = PlayInfo.getPlay(lotterylid);
        jc_play_select =  (JCSelectPlayTypeView) findViewById(R.id.jc_play_select);
        jc_play_type_show =  (LinearLayout) findViewById(R.id.jc_play_type_show);
        jc_play_type_title =  (LinearLayout) findViewById(R.id.jc_play_type_title);
        //串关显示
        jc_chuan_show =  (LinearLayout) findViewById(R.id.jc_chuan_show);
        jc_chuan_select_plays =  (JCSelectPlayTypeView) findViewById(R.id.jc_chuan_select_plays);
        jc_play_type =  (TextView) findViewById(R.id.jc_play_type);
        jc_choose_chuan =  (LinearLayout) findViewById(R.id.jc_choose_chuan);
        jc_choose_chuan_img =  (ImageView) findViewById(R.id.jc_choose_chuan_img);
        sectionLV = (AmazingListView) findViewById(R.id.jc_section_list_view);
        //玩法显示
        mPlaySelect = new PlaySelectView(JCActivity.this, jc_play_select, mListPlayBeans,playIndex);
        mPlaySelect.setOnClickPlaySelect(new PlaySelectView.OnClickPlaySelect() {
            @Override
            public void onClick(View v) {
                playIndex = v.getId();
                jc_play_type.setText(mListPlayBeans.get(playIndex).getPlayName());
                jc_play_type_show.setVisibility(View.GONE);
                TradeNoTask();
            }
        });
        jc_play_type_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jc_play_type_show.getVisibility() == View.VISIBLE){
                    jc_play_type_show.setVisibility(View.GONE);
                }else{
                    jc_play_type_show.setVisibility(View.VISIBLE);
                }
            }
        });
        jc_choose_chuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(jc_chuan_select_plays.getVisibility() == View.VISIBLE){
                    jc_chuan_select_plays.setVisibility(View.GONE);
                    jc_choose_chuan_img.setBackground(getResources().getDrawable(R.mipmap.red_expand_up));
                }else{
                    jc_chuan_select_plays.setVisibility(View.VISIBLE);
                    jc_choose_chuan_img.setBackground(getResources().getDrawable(R.mipmap.red_expand_down));
                }
            }
        });

        chuanSelect = new ChuanSelectView(JCActivity.this,jc_chuan_select_plays, chuanCheck);
        View sectionHeaderView = inflater.inflate(R.layout.jingcai_section_header, sectionLV, false);
        sectionLV.setPinnedHeaderView(sectionHeaderView);
        sectionAdapter = new SectionListAdapter(jzMatchBeans,jzMatchListBeans,inflater);
        sectionLV.setAdapter(sectionAdapter);
        TradeNoTask();
    }

    private void TradeNoTask() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map map = new HashMap();
                    map.put("lotteryId",lotterylid);
                    map.put("playType",mListPlayBeans.get(playIndex).getPlayId());
                    String json = UTIL.AjaxJson(map);
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("dubo/getMatch",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        jsonArry=ob.getJSONArray("list");
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
    private List<JZMatchBean> jzMatchBeans = new ArrayList<JZMatchBean>();
    private List<JZMatchListBean> jzMatchListBeans = new ArrayList<JZMatchListBean>();
    private Map<String,JZMatchListBean > jzMatchListMap = new HashMap<String,JZMatchListBean>();
    private SectionListAdapter sectionAdapter;
    private AmazingListView sectionLV;
    private LayoutInflater inflater;
    Handler allhand = new Handler(){
        public void handleMessage(Message msg) {
            this.obtainMessage();
            // 更新UI
            switch (msg.what) {
                case 1:
                    JZMatchBeanListParser beanListParser = new JZMatchBeanListParser();
                    removeallList();
                    jzMatchBeans = (List<JZMatchBean>) StringUtil.parserJsonArray(jsonArry, beanListParser);
                    for (JZMatchBean bean :jzMatchBeans){
                        bean.setWeekName(StringUtil.getWeekName(bean.getWeek()));
                        JZMatchListBean listBean = jzMatchListMap.get(bean.getWeek());
                        if (listBean == null){
                            listBean = new JZMatchListBean();
                            listBean.setStatus(1);
                            jzMatchListMap.put(bean.getWeek(),listBean);
                        }
                        listBean.getMatchList().add(bean);
                    }
                    for ( String key : jzMatchListMap.keySet()){
                        JZMatchListBean listBean = jzMatchListMap.get(key);
                        String no = listBean.getMatchList().get(0).getMatchno();
                        listBean.setSection("20"+no.substring(0, 2)+"-"+no.substring(2, 4)+"-"+no.substring(4, 6)+"("+listBean.getMatchList().get(0).getWeekName()+")"+" 有"+listBean.getMatchList().size()+"场比赛");
                        jzMatchListBeans.add(jzMatchListMap.get(key));
                    }
                    sectionAdapter.setJzMatchBeans(jzMatchBeans);
                    sectionAdapter.setJzMatchListBeans(jzMatchListBeans);
                    sectionAdapter.setLotteryId(lotterylid);
                    sectionAdapter.setPlayType(mListPlayBeans.get(playIndex).getPlayId());
                    sectionAdapter.setContext(JCActivity.this);
                    sectionAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };
    /**
     * 清除所有静态存储数据
     */
    private void removeallList() {
        jzMatchBeans.clear();
        jzMatchListMap.clear();
        jzMatchListBeans.clear();
    }
}
