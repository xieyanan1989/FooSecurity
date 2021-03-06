package com.fsapp.sunsi.foosecurity.dubo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.BaseDialog;
import com.fsapp.sunsi.foosecurity.dialogs.EditDialog;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchBeanListParser;
import com.fsapp.sunsi.foosecurity.dubo.bean.JZMatchListBean;
import com.fsapp.sunsi.foosecurity.dubo.bean.PlayBean;
import com.fsapp.sunsi.foosecurity.dubo.util.Arithmetic;
import com.fsapp.sunsi.foosecurity.dubo.util.LotteryId;
import com.fsapp.sunsi.foosecurity.dubo.util.PlayInfo;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;
import com.fsapp.sunsi.foosecurity.dubo.view.AmazingListView;
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

public class CTZQActivity extends AppCompatActivity {
    //获取所有玩法
    private ArrayList<PlayBean> mListPlayBeans = new ArrayList<PlayBean>();
    //彩种id
    private String lotterylid = "205";
    //添加玩法标签
    JCSelectPlayTypeView jc_play_select;
    private PlaySelectView mPlaySelect;
    private LinearLayout jc_play_type_show;
    private LinearLayout jc_play_type_title;
    private TextView jc_play_type;
    private  int playIndex = 0;

    private ProgressBar mProgressBar;
    private JSONArray jsonArry;
    private Message msg;
    //底部清除所选数据按钮
    private LinearLayout jc_bet_delete;
    private TextView jc_bet_amount;
    private TextView jc_multiple_show;
    private Button jc_multiple_del;
    private Button jc_multiple_add;
    private int betnum = 1;
    //注数
    private int count;
    //最高中奖金额
    private BigDecimal money;
    private SectionListAdapter sectionAdapter;
    private AmazingListView sectionLV;
    private LayoutInflater inflater;
    private TextView no_result_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ctzq);
        inflater = LayoutInflater.from(this);
        mListPlayBeans = PlayInfo.getPlay("205678");
        jc_play_select =  (JCSelectPlayTypeView) findViewById(R.id.jc_play_select);
        jc_play_type_show =  (LinearLayout) findViewById(R.id.jc_play_type_show);
        jc_play_type_title =  (LinearLayout) findViewById(R.id.jc_play_type_title);
        mProgressBar = (ProgressBar) findViewById(R.id.loadding_progressbar_m);
        no_result_text = (TextView) findViewById(R.id.no_result_text);
        jc_play_type =  (TextView) findViewById(R.id.jc_play_type);
        sectionLV = (AmazingListView) findViewById(R.id.jc_section_list_view);
        //玩法显示
        mPlaySelect = new PlaySelectView(CTZQActivity.this, jc_play_select, mListPlayBeans,playIndex);
        jc_multiple_show =  (TextView) findViewById(R.id.jc_multiple_show);
        jc_multiple_del =  (Button) findViewById(R.id.jc_multiple_del);
        jc_multiple_add =  (Button) findViewById(R.id.jc_multiple_add);
        //清除所选数据按钮
        jc_bet_delete =  (LinearLayout) findViewById(R.id.jc_bet_delete);
        jc_bet_amount =  (TextView) findViewById(R.id.jc_bet_amount);
        jc_bet_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllList();
            }
        });
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
        //倍数选择按钮
        checkMultiple();
        //倍数减少点击事件
        multipleDele();
        //倍数增加点击事件
        multipleAdd();
        TradeNoTask();
    }
    private void multipleAdd() {
        jc_multiple_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                betnum = Tools.getStringOfInteger(jc_multiple_show.getText().toString());
                betnum = betnum +5;
                if (betnum >99){
                    betnum= 99;
                }
                jc_multiple_show.setText(betnum+"");
                jc_bet_amount.setText(count+"注"+betnum+"倍"+count*2* betnum+"元");
            }
        });
    }

    private void multipleDele() {
        jc_multiple_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                betnum = Tools.getStringOfInteger(jc_multiple_show.getText().toString());
                betnum = betnum -5;
                if (betnum <1){
                    betnum= 1;
                }
                jc_multiple_show.setText(betnum+"");
                jc_bet_amount.setText(count+"注"+betnum+"倍"+count*2* betnum+"元");
            }
        });
    }

    //倍数选择按钮
    private void checkMultiple() {
        jc_multiple_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final EditDialog dialog = new EditDialog(CTZQActivity.this);
                dialog.setButtonOnClickListener(new BaseDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick() {
                        if (StringUtil.getOfLong(dialog.getString()) > 0) {
                            jc_multiple_show.setText(dialog.getString());
                            betnum = Tools.getStringOfInteger(dialog.getString());
                            jc_bet_amount.setText(count+"注"+betnum+"倍"+count*2* betnum+"元");
                        }
                    }

                    @Override
                    public void onCancleBtnClick() {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }
        });
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
                if(playIndex == 0){
                    lotterylid = "205";
                }
                if(playIndex == 1){
                    lotterylid = "206";
                }
                if(playIndex == 2){
                    lotterylid = "207";
                }
                if(playIndex == 3){
                    lotterylid = "208";
                }
                TradeNoTask();
            }
        });
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
        List<Integer> list = new ArrayList<Integer>();
        if (lotteryId == LotteryId.CTZC14){
            list.add(14);
        }else if(lotteryId == LotteryId.CTZC9){
            list.add(9);
        }
        if(selectMatchs.size() >= list.get(0)){
            count = Tools.getCount(list,selectMatchs,lotteryId,playMethod);
        }
        jc_bet_amount.setText(count+"注"+betnum+"倍"+count*2* betnum+"元");
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
    }

    private void TradeNoTask() {
        mProgressBar.setVisibility(View.VISIBLE);
        no_result_text.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Map map = new HashMap();
                    map.put("lotteryId",lotterylid);
                    map.put("playType",mListPlayBeans.get(playIndex).getPollId());
                    String json = UTIL.AjaxJson(map);
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("dubo/getMatch",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")&& !ob.getString("list").equals("[]")){
                        jsonArry=ob.getJSONArray("list");
                        msg = new Message();
                        msg.what = 1;
                        allhand.removeMessages(1);
                        allhand.sendMessage(msg);
                    }else{
                        msg = new Message();
                        msg.what = 2;
                        allhand.removeMessages(2);
                        allhand.sendMessage(msg);
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
                        bean.setWeek("比賽名稱");
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
                        listBean.setSection(listBean.getMatchList().get(0).getMatched()+" "+listBean.getMatchList().get(0).getEndtime());
                        jzMatchListBeans.add(jzMatchListMap.get(key));
                    }
                    sectionAdapter.setJzMatchBeans(jzMatchBeans);
                    sectionAdapter.setJzMatchListBeans(jzMatchListBeans);
                    sectionAdapter.setLotteryId(lotterylid);
                    sectionAdapter.setPlayType(mListPlayBeans.get(playIndex).getPollId());
                    sectionAdapter.setContext(CTZQActivity.this);
                    sectionAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    mProgressBar.setVisibility(View.GONE);
                    no_result_text.setVisibility(View.VISIBLE);
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
        count = 0;
        betnum =1;
        jc_multiple_show.setText("1");
        jc_bet_amount.setText("投注注数0注，金额0元");
    }

    /**
     * 清除所有选择的对阵
     */
    private void clearAllList() {
        for (int i = 0; i < jzMatchBeans.size(); i++) {
            jzMatchBeans.get(i).zcList.clear();
            jzMatchBeans.get(i).zcPvList.clear();
            jzMatchBeans.get(i).zcbcList.clear();
            jzMatchBeans.get(i).zcbcPvList.clear();
            jzMatchBeans.get(i).zcqcList.clear();
            jzMatchBeans.get(i).zcqcPvList.clear();
            jzMatchBeans.get(i).setHasDan(false);
            jzMatchBeans.get(i).setCount(0);
        }
        //选择的赛事清除掉
        selectMatchs.clear();
        count = 0;
        betnum =1;
        jc_multiple_show.setText("1");
        jc_bet_amount.setText("投注注数0注，金额0元");
        sectionAdapter.notifyDataSetChanged();
    }
}
