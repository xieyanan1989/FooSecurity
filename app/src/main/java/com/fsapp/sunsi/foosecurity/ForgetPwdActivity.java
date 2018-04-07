package com.fsapp.sunsi.foosecurity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class ForgetPwdActivity extends AppCompatActivity {
    private DBUtil db;
    private Context context ;
    private EditText  phoneNum;
    private EditText  validNum;
    private Button    sendValid;
    private Button    validSubmit;
    private Message msg;
    private String errormsg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forget_pwd);
            context = ForgetPwdActivity.this;
            db = new DBUtil(context);
            //发送验证码
            sendValid = (Button) findViewById(R.id.send_valid);
            //提交
            validSubmit = (Button) findViewById(R.id.num_submit);
            //手机号
            phoneNum = (EditText) findViewById(R.id.phone_num);
            //验证码
            validNum = (EditText) findViewById(R.id.valid_num);
            //发送验证码事件
            validSendEvent(sendValid);
            //提交事件
            validSubmitEvent(validSubmit);
            initSDK();//短信初始化
        }catch (Exception e){
            e.printStackTrace();
        }
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
//                    Toast.makeText(context, "获取验证码成功,注意查看",Toast.LENGTH_SHORT).show();
                    contextDialog("获取验证码成功,注意查看");
                    break;
                case 3: //验证成功页面跳转  
                    userExist();
                    break;
            }
        }

    };
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
//    private String userName = "";
    private void userExist() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String userName = phoneNum.getText().toString();
                    String json = "{\"username\":\""+userName+"\"}";
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("user/exist",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        Map map = db.querUserById();
                        int count = (int) map.get("count");
                        if(count == 0){
                            db.insertUser(userName,"123456",true);
                            intentModify();
                        }else if(count == 1){
                            db.updateUser(userName,"123456",true);
                            intentModify();
                        }else{
                            errormsg = UTIL.errorCode("1013")+"count:"+count;
                            msg = new Message();
                            msg.what = 1;
                            myHandler.removeMessages(1);
                            myHandler.sendMessage(msg);
                        }
                    }else{
                        errormsg = UTIL.errorCode(ob.getString("msg"));
                        msg = new Message();
                        msg.what = 1;
                        myHandler.removeMessages(1);
                        myHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void intentModify(){
        Intent intentd = new Intent(context, ModifyPwdActivity.class);
        startActivity(intentd);
    }
}
