package com.fsapp.sunsi.foosecurity.dubo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBeanListParser;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchListBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.util.PlayInfo;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;
import com.fsapp.sunsi.foosecurity.dubo.view.AmazingListView;
import com.fsapp.sunsi.foosecurity.dubo.view.ChuanSelectView;
import com.fsapp.sunsi.foosecurity.dubo.view.JCSelectPlayTypeView;
import com.fsapp.sunsi.foosecurity.dubo.view.PlaySelectView;
import com.fsapp.sunsi.foosecurity.dubo.view.SectionListAdapter;
import com.fsapp.sunsi.foosecurity.dubo.view.StringUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private ProgressBar mProgressBar;
    //串关显示
    private LinearLayout jc_chuan_show;
    private JCSelectPlayTypeView jc_chuan_select_plays;
    private String[] chuanCheck = new String[] { "单 关","2串1", "3串1", "4串1", "5串1", "6串1", "7串1", "8串1" };
    ChuanSelectView chuanSelect;
    private LinearLayout jc_choose_chuan;
    private ImageView jc_choose_chuan_img;
    private JSONArray jsonArry;
    private Message msg;
    //底部清除所选数据按钮
    private LinearLayout jc_bet_delete;
    private TextView jc_bet_amount;
    private TextView jc_bet_prize;
    //注数
    private int count;
    //最高中奖金额
    private  BigDecimal money;
    private SectionListAdapter sectionAdapter;
    private AmazingListView sectionLV;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jc);
        inflater = LayoutInflater.from(this);
        mListPlayBeans = PlayInfo.getPlay(lotterylid);
        jc_play_select =  (JCSelectPlayTypeView) findViewById(R.id.jc_play_select);
        jc_play_type_show =  (LinearLayout) findViewById(R.id.jc_play_type_show);
        jc_play_type_title =  (LinearLayout) findViewById(R.id.jc_play_type_title);
        mProgressBar = (ProgressBar) findViewById(R.id.loadding_progressbar_m);
        //串关显示
        jc_chuan_show =  (LinearLayout) findViewById(R.id.jc_chuan_show);
        jc_chuan_select_plays =  (JCSelectPlayTypeView) findViewById(R.id.jc_chuan_select_plays);
        jc_play_type =  (TextView) findViewById(R.id.jc_play_type);
        jc_choose_chuan =  (LinearLayout) findViewById(R.id.jc_choose_chuan);
        jc_choose_chuan_img =  (ImageView) findViewById(R.id.jc_choose_chuan_img);
        sectionLV = (AmazingListView) findViewById(R.id.jc_section_list_view);
        //玩法显示
        mPlaySelect = new PlaySelectView(JCActivity.this, jc_play_select, mListPlayBeans,playIndex);
        //清除所选数据按钮
        jc_bet_delete =  (LinearLayout) findViewById(R.id.jc_bet_delete);
        jc_bet_amount =  (TextView) findViewById(R.id.jc_bet_amount);
        jc_bet_prize =  (TextView) findViewById(R.id.jc_bet_prize);
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
        jc_bet_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllList();
            }
        });
        chuanSelect = new ChuanSelectView(JCActivity.this,jc_chuan_select_plays, chuanCheck);
        View sectionHeaderView = inflater.inflate(R.layout.jingcai_section_header, sectionLV, false);
        sectionLV.setPinnedHeaderView(sectionHeaderView);
        sectionAdapter = new SectionListAdapter(jzMatchBeans,jzMatchListBeans,inflater);
        sectionLV.setAdapter(sectionAdapter);
        //玩法标题选择
        palaySelectTitle();
        //玩法选择方式
        playSelect();
        //赛事选择的回调监听
        jcItemsBack();
        //串关回调函数监听
        chuanItemSelect();
        TradeNoTask();
    }
    //玩法标题选择
    private void palaySelectTitle() {
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
    }

    //玩法选择方式
    private void playSelect() {
        mPlaySelect.setOnClickPlaySelect(new PlaySelectView.OnClickPlaySelect() {
            @Override
            public void onClick(View v) {
                playIndex = v.getId();
                jc_play_type.setText(mListPlayBeans.get(playIndex).getPlayName());
                jc_play_type_show.setVisibility(View.GONE);
                TradeNoTask();
            }
        });
    }

    //串关回调函数监听
    private void chuanItemSelect() {
        chuanSelect.setOnClickPlaySelect(new ChuanSelectView.OnClickPlaySelect() {
            @Override
            public void onClick(View v) {
                int chuan = v.getId();
                    if ((chuan+1) > selectMatchs.size()){
                        CheckBox check = (CheckBox) v;
                        check.setChecked(false);
                    }else{
                        //是否单关，有单关，按钮显示，无单关提示不存在
                        if(chuan == 0){
                            boolean single = false;
                            for(JZMatchBean bean : selectMatchs){
                                if (bean.getSingle().equals("1")){
                                    single = true;
                                    break;
                                }
                            }
                            if (!single){
                                chuanSelect.getListViews().get(0).setChecked(false);
                                Toast.makeText(JCActivity.this, "未选择单关赛事",Toast.LENGTH_SHORT).show();
                            }
                        }
                        //当赛事大于串关的时候，串关选择规则
                        matchsMorethenChuan(chuan);
                        countAndAount(lotterylid, mListPlayBeans.get(playIndex).getPlayId(), null, true);
                    }
            }
        });
    }
    //当赛事大于串关的时候，串关选择规则
    private void matchsMorethenChuan(int chuan) {
        //总进球数最多串六关
        if(mListPlayBeans.get(playIndex).getPlayId().equals(LotteryId.PLAY_ID_03)){
            if(chuan > 5){
                chuanSelect.setUnCeck(chuan);
            }
        }
        //半全场、比分、混合过关最多串四关
        if(mListPlayBeans.get(playIndex).getPlayId().equals(LotteryId.PLAY_ID_04) || mListPlayBeans.get(playIndex).getPlayId().equals(LotteryId.PLAY_ID_05) || mListPlayBeans.get(playIndex).getPlayId().equals(LotteryId.PLAY_ID_06)){
            if(chuan > 3){
                chuanSelect.setUnCeck(chuan);
            }
        }
    }

    //赛事选择的回调监听
    private void jcItemsBack() {
        sectionAdapter.setOnsEectionBetOnClickListener(new SectionListAdapter.OnsEectionBetOnClickListener() {
            @Override
            public void onsEectionBetOnClickListener(String lotteryId, String playMethod, JZMatchBean jzMatchBean, boolean status) {
                countAndAount(lotteryId, playMethod, jzMatchBean,status);
            }
        });
    }

    private List<JZMatchBean> selectMatchs = new ArrayList<JZMatchBean>();
    /**
     * 计算金额与注数
     * @param lotteryId
     * @param playMethod
     * @param jzMatchBean
     * @param status
     */
    private void countAndAount(String lotteryId, String playMethod, JZMatchBean jzMatchBean, boolean status) {
        //赛事是否存在，存在替换，不存在加入选中的赛事
        if (jzMatchBean != null){
            addOrReplaceMatchs(jzMatchBean,status);
        }
        //串关是否存在
        List<Integer> list = chuanSelect.getSelectChuan();
        //当选中的赛事小于串关的时候，串关隐藏掉
        List<CheckBox> listView = chuanSelect.getListViews();
        for(int i = selectMatchs.size() ;i <list.size(); i ++){
            listView.get(i).setChecked(false);
            list.remove(i+1);
        }
        if(list.size() <= 0){
            count =0;
            money =new BigDecimal(0);
        }else {
            Collections.sort(list);
            count = Tools.getCount(list,selectMatchs,lotteryId,playMethod);
            money = Tools.getPrize(list,selectMatchs,lotteryId,playMethod);
        }
        jc_bet_amount.setText(count+"注，金额"+count*2+"元");
        jc_bet_prize.setText("理论最高奖金"+money+"元");
    }


    //赛事是否存在，存在替换，不存在加入选中的赛事
    private void addOrReplaceMatchs(JZMatchBean jzMatchBean, boolean status) {
        boolean noexist = true;
        for (int i = 0 ; i < selectMatchs.size() ; i++){
            JZMatchBean bean = selectMatchs.get(i);
            if(jzMatchBean.getMatchno().equals(bean.getMatchno())){
                selectMatchs.set(i,jzMatchBean);
                noexist = false;
                break;
            }
        }
        if(noexist){
            selectMatchs.add(jzMatchBean);
        }
        //移除
        if(!status){
            selectMatchs.remove(jzMatchBean);
        }
        chuanSelect.setUnCeck(selectMatchs.size());
    }

    private void TradeNoTask() {
        mProgressBar.setVisibility(View.VISIBLE);
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
    Handler allhand = new Handler(){
        public void handleMessage(Message msg) {
            this.obtainMessage();
            // 更新UI
            switch (msg.what) {
                case 1:
                    mProgressBar.setVisibility(View.GONE);
                    JZMatchBeanListParser beanListParser = new JZMatchBeanListParser();
                    removeallList();
                    jzMatchBeans = (List<JZMatchBean>) StringUtil.parserJsonArray(jsonArry, beanListParser);
                    //保持按天先后顺序，否则显示的时候会乱
                    List<String> weekKeys = new ArrayList<String>();
                    for (JZMatchBean bean :jzMatchBeans){
                        bean.setWeekName(StringUtil.getWeekName(bean.getWeek()));
                        JZMatchListBean listBean = jzMatchListMap.get(bean.getWeek());
                        if (listBean == null){
                            weekKeys.add(bean.getWeek());
                            listBean = new JZMatchListBean();
                            listBean.setStatus(1);
                            jzMatchListMap.put(bean.getWeek(),listBean);
                        }
                        listBean.getMatchList().add(bean);
                    }
                    for ( String key : weekKeys){
                        JZMatchListBean listBean = jzMatchListMap.get(key);
                        String no = listBean.getMatchList().get(0).getMatchno();
                        listBean.setSection("20"+no.substring(0, 2)+"-"+no.substring(2, 4)+"-"+no.substring(4, 6)+"("+listBean.getMatchList().get(0).getWeekName()+")"+" 有"+listBean.getMatchList().size()+"场比赛");
//                        listBean.setSection("20"+no.substring(0, 2)+"-"+no.substring(2, 4)+"-"+no.substring(4, 6));
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
        //选择的赛事清除掉
        selectMatchs.clear();
        //清除所有串关方式
        chuanSelect.setUnCeck(0);
        jc_bet_amount.setText("投注注数0注，金额0元");
        jc_bet_prize.setText("理论最高奖金0元");
    }

    /**
     * 清除所有选择的对阵
     */
    private void clearAllList() {
        for (int i = 0; i < jzMatchBeans.size(); i++) {
            jzMatchBeans.get(i).getJzspfList().clear();
            jzMatchBeans.get(i).getJzrqspfLiset().clear();
            jzMatchBeans.get(i).getJzzjqsList().clear();
            jzMatchBeans.get(i).getJzbqcList().clear();
            jzMatchBeans.get(i).getJzbfList().clear();
            jzMatchBeans.get(i).getJzspfPvList().clear();
            jzMatchBeans.get(i).getJzrqspfPvLiset().clear();
            jzMatchBeans.get(i).getJzzjqsPvList().clear();
            jzMatchBeans.get(i).getJzbqcPvList().clear();
            jzMatchBeans.get(i).getJzbfPvList().clear();
            jzMatchBeans.get(i).getJlsfList().clear();
            jzMatchBeans.get(i).getJlrfsfList().clear();
            jzMatchBeans.get(i).getJldxfList().clear();
            jzMatchBeans.get(i).getJlsfcList().clear();
            jzMatchBeans.get(i).getJlsfPvList().clear();
            jzMatchBeans.get(i).getJlrfsfPvList().clear();
            jzMatchBeans.get(i).getJldxfPvList().clear();
            jzMatchBeans.get(i).getJlsfcPvList().clear();
            jzMatchBeans.get(i).getBdsxdsList().clear();
            jzMatchBeans.get(i).getBdsxdsPvList().clear();
        }
        //选择的赛事清除掉
        selectMatchs.clear();
        //清除所有串关方式
        chuanSelect.setUnCeck(0);
        jc_bet_amount.setText("投注注数0注，金额0元");
        jc_bet_prize.setText("理论最高奖金0元");
        sectionAdapter.notifyDataSetChanged();
//        TradeNoTask();
    }
}
