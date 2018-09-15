package com.fsapp.sunsi.foosecurity.dubo;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dubo.util.Tools;
import com.fsapp.sunsi.foosecurity.dubo.view.AutoWrapView;
import com.fsapp.sunsi.foosecurity.dubo.view.BallView;

import java.util.ArrayList;

public class SsqActivity extends AppCompatActivity {

    private int viewSum =0;
    /** 球的初始值 第一个球的数 **/
    private final int initValue = 1;
    private final int redSum = 33;
    private final int blueSum = 16;
    /** 红球号码 **/
    private int[] redTowedFronts;
    /** 红球胆码 **/
    private int[] redTowedBacks = new int[0];
    private boolean   towed= false;
    private AutoWrapView redAutoView;
    private AutoWrapView blueAutoView;
    private TextView dubo_ssq_count_money;
    //机选
    Button dubo_ssq_random;
    //清除
    ImageView dubo_ssq_clear;
    //胆
    RadioButton mBtnTowedBack;
    //号
    RadioButton mBtnTowedFront;
    private Context context;
    LinearLayout dubo_ssq_red;
    LinearLayout dubo_ssq_blue;
    public SsqActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ssq);
        context = SsqActivity.this;
        dubo_ssq_red = (LinearLayout) findViewById(R.id.dubo_ssq_red);
        dubo_ssq_blue = (LinearLayout) findViewById(R.id.dubo_ssq_blue);
        //机选
        dubo_ssq_random = (Button) findViewById(R.id.dubo_ssq_random);
        //清除选项
        dubo_ssq_clear = (ImageView) findViewById(R.id.dubo_ssq_clear);
        //注数金额
        dubo_ssq_count_money = (TextView) findViewById(R.id.dubo_ssq_count_money);
        addBallRed();
        addBallBlue();
        dubo_ssq_random.setOnClickListener(onClick);
        dubo_ssq_clear.setOnClickListener(onClick);
    }

    private void addBallRed() {
        View view = Tools.getXml(context, R.layout.select_ball_much);
        redAutoView = (AutoWrapView) view.findViewById(R.id.select_ball_much_auto_view);
        LinearLayout dubo_dan_tuo = (LinearLayout) view.findViewById(R.id.dubo_dan_tuo);
        dubo_dan_tuo.setVisibility(View.VISIBLE);
        //选号
        mBtnTowedFront = (RadioButton) view.findViewById(R.id.select_ball_much_btn_towed_front);
        //选胆
        mBtnTowedBack = (RadioButton) view.findViewById(R.id.select_ball_much_btn_towed_back);
        redAutoView.setCenter(AutoWrapView.CENTER_CONTEXT);
        redAutoView.setCol(7, false);
        redAutoView.clear();
        BallView.getBall(context, redAutoView, BallView.getListCheck(context, initValue, redSum, R.layout.ball_red, R.drawable.select_check_ball_red));
        dubo_ssq_red.addView(view);
        //胆点击事件
        mBtnTowedBackEvent(mBtnTowedBack);
        //红球点击事件
        redEvent(redAutoView);
    }


    private void addBallBlue() {
        View view = Tools.getXml(context, R.layout.select_ball_much);
        blueAutoView = (AutoWrapView) view.findViewById(R.id.select_ball_much_auto_view);
        blueAutoView.setCenter(AutoWrapView.CENTER_CONTEXT);
        blueAutoView.setCol(7, false);
        blueAutoView.clear();
        BallView.getBall(context, blueAutoView, BallView.getListCheck(context, initValue, blueSum, R.layout.ball_blue, R.drawable.select_check_ball_blue));
        dubo_ssq_blue.addView(view);
        //红球点击事件
        blueEvent(blueAutoView);
    }

    //所有点击事件
    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //机选
            if(view.getId() == dubo_ssq_random.getId()){
                if(towed){
                    Toast.makeText(context, "胆码不支持机选", Toast.LENGTH_LONG).show();
                }else{
                    redTowedFronts = Tools.getRandom(redSum,7,redTowedBacks);
                    redAutoView.setSelect(redTowedFronts);
                    int[] radomBlue = Tools.getRandom(blueSum,2,new int[0]);
                    blueAutoView.setSelect(radomBlue);
                    //双色球计算注数
                    int  count = Tools.danTuoArithmetic(redTowedFronts.length,redTowedBacks.length,6,radomBlue.length);
                    setAmount(count,2);
                }
            }else if (view.getId() == dubo_ssq_clear.getId()){
                //清除所选按钮
                redTowedBacks =new int[0];
                redTowedFronts =new int[0];
                for(CheckBox checkBox: redAutoView.mCheckLists){
                    checkBox.setEnabled(true);
                    checkBox.setChecked(false);
                }
                redAutoView.clear();
                blueAutoView.clear();
                dubo_ssq_count_money.setText("0注0元");
            }

        }
    };
    //胆点击事件
    private void mBtnTowedBackEvent(RadioButton mBtnTowedBack) {
        mBtnTowedBack.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                //胆点击事件
                towed = isChecked;
                if(isChecked){
                    redAutoView.setEnabled(redTowedFronts);
                    redAutoView.setSelect(redTowedBacks);
                }else{
                    redAutoView.setEnabled(redTowedBacks);
                    redAutoView.setSelect(redTowedFronts);
                }
            }
        });
    }


    //红篮球点击事件
    private void redEvent(final AutoWrapView redAutoView) {
        redAutoView.setOnAutoViewListener(new AutoWrapView.OnAutoViewListener() {
            @Override
            public void onView(View v) {
                //点击红球计算注数
                int count = 0;
                int blueCount = 0;
                int frontLength = 0;
                int danlength = 0;
                blueCount = BallView.getResultSelect(blueAutoView).length;
                if(towed){
                    redTowedBacks = BallView.getResultSelect(redAutoView);
                    danlength = redTowedBacks.length;
                    if(danlength >= 6 ){
                        danlength=5;
                        ((CheckBox)v).setChecked(false);
                        Toast.makeText(context, "红球胆码最多为" + 5 + "个", Toast.LENGTH_LONG).show();
                        redTowedBacks = BallView.getResultSelect(redAutoView);
                    }
                    if(redTowedFronts != null){
                        frontLength = redTowedFronts.length;
                    }
                }else{
                    redTowedFronts = BallView.getResultSelect(redAutoView);
                    frontLength = redTowedFronts.length;
                    danlength = redTowedBacks.length;
                }
                count = Tools.danTuoArithmetic(frontLength,danlength,6,blueCount);
                setAmount(count,2);
            }
        });
    }
    private void blueEvent(final AutoWrapView blueAutoView) {
        blueAutoView.setOnAutoViewListener(new AutoWrapView.OnAutoViewListener() {
            @Override
            public void onView(View v) {
                //点击红球计算注数
                int count = 0;
                int blueCount = 0;
                blueCount = BallView.getResultSelect(blueAutoView).length;
                count = Tools.danTuoArithmetic(redTowedFronts.length,redTowedBacks.length,6,blueCount);
                setAmount(count,2);
            }
        });
    }
    /**
     * 计算注数的金额
     */
    public void setAmount(int count,int money) {
        dubo_ssq_count_money.setText(""+count+"注"+(count*money)+"元");
    }
}
