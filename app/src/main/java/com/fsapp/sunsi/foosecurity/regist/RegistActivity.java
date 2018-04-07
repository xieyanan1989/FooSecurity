package com.fsapp.sunsi.foosecurity.regist;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsapp.sunsi.foosecurity.Map.LocationDemo;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONObject;

import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegistActivity extends AppCompatActivity {

    private Context context ;
    private EditText phoneNum;
    private EditText  validNum;
    private EditText  registPwd;
    private Button sendValid;
    private Button    validSubmit;
    private Message msg;
    private String errormsg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        context = RegistActivity.this;
        //发送验证码
        sendValid = (Button) findViewById(R.id.regist_send_valid);
        //提交
        validSubmit = (Button) findViewById(R.id.regist_submit);
        //手机号
        phoneNum = (EditText) findViewById(R.id.regist_phone);
        //密码
        registPwd = (EditText) findViewById(R.id.regist_pwd);
        //验证码
        validNum = (EditText) findViewById(R.id.regist_valid_num);
        //发送验证码事件
        validSendEvent(sendValid);
        //提交事件
        validSubmitEvent(validSubmit);
        initSDK();//短信初始化
        //常量：注册中的数据清空
        UTIL.clearRegist();
    }


    private void validSendEvent(Button sendValid) {
        sendValid.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String num = phoneNum.getText().toString();
                    SMSSDK.getVerificationCode("86",num);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
    private void validSubmitEvent(Button validSubmit) {
        validSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                String num = phoneNum.getText().toString();
//                SMSSDK.submitVerificationCode("86",num,validNum.getText().toString());
                msg = new Message();
                msg.what = 3;
                myHandler.removeMessages(3);
                // 发送message值给Handler接收
                myHandler.sendMessage(msg);
            }
        });
    }

    //初始化SMSSDK
    private void initSDK()
    {
        try {
            SMSSDK.initSDK(context, "1e45c1a5278cc", "7d3c56e42c3577f52a9c3658a33cf06a");
            SMSSDK.registerEventHandler(eh);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    EventHandler eh =new EventHandler(){
        @Override
        public void afterEvent(int event, int result, Object data) {
            try {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        msg = new Message();
                        msg.what = 3;
                        myHandler.removeMessages(3);
                        // 发送message值给Handler接收
                        myHandler.sendMessage(msg);
                    }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                        //获取验证码成功
                        msg = new Message();
                        msg.what = 2;
                        myHandler.removeMessages(2);
                        // 发送message值给Handler接收
                        myHandler.sendMessage(msg);
                    }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                        //返回支持发送验证码的国家列表
                    }
                }else{
                    ((Throwable)data).printStackTrace();
                    String str = ((Throwable) data).getMessage();
                    JSONObject jb = new JSONObject(str);
                    errormsg = jb.getString("detail").replaceAll("需要校验的","");
                    msg = new Message();
                    msg.what = 1;
                    myHandler.removeMessages(1);
                    // 发送message值给Handler接收
                    myHandler.sendMessage(msg);
                    //此语句代表接口返回失败  
                    //获取验证码失败。短信验证码验证失败（用flage标记来判断）  
//                            if (smsFlage==1) {
//                                myHandler.sendEmptyMessage(3);
//                            }else if (smsFlage==2){
//                                myHandler.sendEmptyMessage(4);
//                            }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        @Override
        public void onRegister() {
            super.onRegister();
        }

        @Override
        public void onUnregister() {
            super.onUnregister();
        }

        @Override
        public void beforeEvent(int i, Object o) {
            super.beforeEvent(i, o);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1: //错误 
                    contextDialog(errormsg);
                    break;
                case 2: //获取验证码成功,注意查看  
                    contextDialog("获取验证码成功,注意查看");
                    break;
                case 3: //验证成功页面跳转  
                    registValidRequest();
                    break;
            }
        }

    };
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }

    private void registValidRequest() {
        if(registPwd.getText().toString().equals("")){
            contextDialog("密码不能为空");
        }else {
            //将用户名密码保存到Map
            Map map= UTIL.getRegistSingle();
            map.put("username",phoneNum.getText().toString());
            map.put("pwd",registPwd.getText().toString());
            intentActivity();
        }
    }
    public void intentActivity(){
        try {
//            Intent intentd = new Intent(context,LocationDemo.class);
//            startActivity(intentd);
            Intent intentd = new Intent(context,RegistAddressActivity.class);
            startActivity(intentd);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
