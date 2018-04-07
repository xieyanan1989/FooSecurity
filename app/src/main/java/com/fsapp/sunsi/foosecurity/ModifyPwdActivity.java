package com.fsapp.sunsi.foosecurity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class ModifyPwdActivity extends AppCompatActivity {
    private DBUtil db;
    private EditText  passwd;
    private EditText  passwd_again;
    private Button    pwdSubmit;
    private Context context = ModifyPwdActivity.this;
    private Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_modify_pwd);
            db = new DBUtil(context);
            //提交
            pwdSubmit = (Button) findViewById(R.id.pwd_submit);
            //密码
            passwd = (EditText) findViewById(R.id.passwd);
            //再次确认密码
            passwd_again = (EditText) findViewById(R.id.passwd_again);
            pwdsubmitEvent(pwdSubmit);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String errormsg;
    private void userModify() {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Map map = db.querUser();
                        String userName = (String)map.get("userName");
                        String json = "{\"username\":\""+userName+"\",\"pwd\":\""+passwd.getText().toString()+"\"}";
                        String sig = HttpRequest.md5(json);
                        Map<String,String> paramMap = new HashMap<String, String>();
                        paramMap.put("json",json);
                        paramMap.put("sig", sig);
                        String result = HttpRequest.sendPost("user/reset",paramMap);
                        JSONObject ob = UTIL.StringGetMap(result);
                        if (ob.getString("msg").equals("0")){
                                db.updateUser(userName,passwd.getText().toString(),true);
                                Intent intentd = new Intent(context, MainActivity.class);
                                startActivity(intentd);
                        }else{
                            errormsg = UTIL.errorCode(ob.getString("msg"));
                            msg = new Message();
                            msg.what = 1;
                            allhand.removeMessages(1);
                            allhand.sendMessage(msg);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    private void pwdsubmitEvent(Button pwdSubmit) {
        try{
            pwdSubmit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    String pwd = passwd.getText().toString();
                    String pwdag = passwd_again.getText().toString();
                    if (pwd.equals(pwdag)){
                        userModify();
                    }else {
                        msg = new Message();
                        msg.what = 1;
                        allhand.removeMessages(1);
                        // 发送message值给Handler接收
                        allhand.sendMessage(msg);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}
