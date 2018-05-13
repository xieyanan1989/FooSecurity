package com.fsapp.sunsi.foosecurity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2018/5/13.
 */

public class Settings {
    /** 用户名 **/
    public static String userName="";
    /** 产品ID **/
    public static String proId;
    /** 购买数量**/
    public static String saleCount;
    /** 计量单位 **/
    public static String saleMea;
    //支付密码
    public static String payPwd;
    //押金
    public static String deposit;
    public static void clear(){
        userName="";
        proId="";
        saleCount="";
        saleMea="";
        payPwd="";
        deposit="";
    }

    public static void put(String userName,String proId,String saleCount,String saleMea,String payPwd,String deposit){
        Settings.userName=userName;
        Settings.proId=proId;
        Settings.saleCount=saleCount;
        Settings.saleMea=saleMea;
        Settings.payPwd=payPwd;
        Settings.deposit=deposit;
    }

    public static Map get(){
        Map map = new HashMap();
        map.put("userName",userName);
        map.put("proId",proId);
        map.put("saleCount",saleCount);
        map.put("saleMea",saleMea);
        map.put("payPwd",payPwd);
        map.put("deposit",deposit);
        return map;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        Settings.userName = userName;
    }
}
