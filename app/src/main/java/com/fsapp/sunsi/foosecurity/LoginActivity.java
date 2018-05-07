package com.fsapp.sunsi.foosecurity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;

import com.fsapp.sunsi.foosecurity.buy.BuyProActivity;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.regist.RegistActivity;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Context context = LoginActivity.this ;
    private DBUtil db;
    private Button login;
    private Button regist;
    private EditText userName;
    private EditText pwd;
    private LinearLayout remember;
    private TextView forget;
    private String username ;
    private String passwd;
    private boolean checked;
    private CheckBox isCheck;
    private Message msg;
    String errormsg ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        db = new DBUtil(context);
        //登录
        login = (Button) findViewById(R.id.login);
        //注册
        regist = (Button) findViewById(R.id.regist);
        //用户名
        userName = (EditText) findViewById(R.id.phone);
        //密码
        pwd = (EditText) findViewById(R.id.pwd);
        //记住密码
        remember = (LinearLayout) findViewById(R.id.remember);
        //忘记密码
        forget = (TextView) findViewById(R.id.forget);
        //是否选中
        isCheck = (CheckBox) findViewById(R.id.ischeck);
        //登录点击事件
        loginevent(login);
        //注册点击事件
        registevent(regist);
        //忘记密码点击事件
        forgetevent(forget);
        //加载用户名与密码
       loadingUser();
    }

    private void loadingUser() {
        Map map = db.querUser();
        String username = (String)map.get("userName");
        if(username == "" || username == null){
            return;
        }else{
            String checked = (String) map.get("checked");
            if(checked.equals("0")){
                isCheck.setChecked(false);
                userName.setText("");
                pwd.setText("");
            }else {
                isCheck.setChecked(true);
                userName.setText(username);
                pwd.setText((String) map.get("loginPwd"));
            }
        }
    }

    /**
     * 忘记密码事件
     * @param forget
     */
    private void forgetevent(TextView forget) {
        forget.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentd = new Intent(context, ForgetPwdActivity.class);
                startActivity(intentd);
//                Intent intentd = new Intent(context, ModifyPwdActivity.class);
//                startActivity(intentd);
            }
        });
    }

    /**
     * 注册事件
     * @param regist
     */
    private void registevent(Button regist) {
        regist.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentd = new Intent(context, RegistActivity.class);
                startActivity(intentd);
            }
        });
    }

    /**
     * 登录事件
     * @param login
     */
    private void loginevent(final Button login) {
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                username = userName.getText().toString();
                passwd = pwd.getText().toString();
                ///user/login
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            String json = "{\"username\":\""+username+"\",\"password\":\""+passwd+"\"}";
                            String sig = HttpRequest.md5(json);
                            Map<String,String> paramMap = new HashMap<String, String>();
                            paramMap.put("json",json);
                            paramMap.put("sig", sig);
                            String result = HttpRequest.sendPost("user/login",paramMap);
                            JSONObject ob = UTIL.StringGetMap(result);
                            if (ob.getString("msg").equals("0")){
                                Map map = db.querUserById();
                                int count = (int) map.get("count");
                                checked = isCheck.isChecked();
                                if(count == 0){
                                    db.insertUser(username,passwd,checked);
                                }else if(count == 1){
                                    db.updateUser(username,passwd,checked);
                                }else{
                                    errormsg = UTIL.errorCode("1013")+"count:"+count;
                                    msg = new Message();
                                    msg.what = 1;
                                    allhand.removeMessages(1);
                                    // 发送message值给Handler接收
                                    allhand.sendMessage(msg);
                                }
                                if (getIntent().getStringExtra("send").equals("buy")){
                                    Intent intentd = new Intent(context,BuyProActivity.class);
                                    startActivity(intentd);
                                }else{
                                    finish();
                                }
                            }else{
                                errormsg = UTIL.errorCode(ob.getString("msg"));
                                msg = new Message();
                                msg.what = 1;
                                allhand.removeMessages(1);
                                // 发送message值给Handler接收
                                allhand.sendMessage(msg);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }
    Handler allhand = new Handler(){
        public void handleMessage(Message msg) {
            this.obtainMessage();
            // 更新UI
            switch (msg.what) {
                case 1:
                        contextDialog(errormsg);
                    break;
            }
        }
    };
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
