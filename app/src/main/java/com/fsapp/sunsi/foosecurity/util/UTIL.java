package com.fsapp.sunsi.foosecurity.util;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2017/5/29.
 */

public class UTIL {
    public static JSONObject StringGetMap(String json) {
        JSONObject jb = null;
        try {
            jb = new JSONObject(json);
        }catch (Exception e){
            e.printStackTrace();
        }
        return jb;
    }
    public static String errorCode(String code) {
        int codenum= Integer.parseInt(code);
        String error = "未知错误";
        switch (codenum){
            case 1001:
                error = "失败";
                break;
            case 1002:
                error = "用户或密码不正确";
                break;
            case 1003:
                error = "用户已存在";
                break;
            case 1004:
                error = "签名验证失败";
                break;
            case 1005:
                error = "用户未登陆";
                break;
            case 1006:
                error = "未实名认证";
                break;
            case 1007:
                error = "购买数量超过限制";
                break;
            case 1008:
                error = "已实名认证";
                break;
            case 1009:
                error = "余额不足";
                break;
            case 1010:
                error = "允许上传的文件类型（\"jpg\", \"jpeg\", \"gif\", \"png\"）";
                break;
            case 1011:
                error = "请选择上传文件";
                break;
            case 1012:
                error = "文件尺寸超过规定大小";
                break;
            case 1013:
                error = "本地数据库用户表异常";
                break;
            case 1014:
                error = "验证码错误";
                break;
            case 1015:
                error = "请填写正确的手机号码";
                break;
            case 1016:
                error = "用户不存在";
                break;
            case 1017:
                error = "支付密码不正确";
                break;

        }
        return error;
    }
    private  static Map registMap;
    private  static Map productMap;
    private  static Map proImgMap;
    public static Map getRegistSingle(){
        if (registMap == null){
            registMap = new HashMap<>();
        }
        return registMap;
    }
    public  static void clearRegist(){
        if (registMap != null){
            registMap.clear();
        }
    }
    public static Map getProductSingle(){
        if (productMap == null){
            productMap = new HashMap<>();
        }
        return productMap;
    }
    public  static void clearProduct(){
        if (productMap != null){
            productMap.clear();
        }
    }
    public static String AjaxJson(Map<?, ?> map) throws Exception {
        JSONObject json = new JSONObject(map);
        return json.toString();
    }

    public static Map getProImgSingle(){
        if (proImgMap == null){
            proImgMap = new HashMap<>();
        }
        return proImgMap;
    }
    public  static void clearProImg(){
        if (proImgMap != null){
            proImgMap.clear();
        }
    }

    public static String  getCurrDate(){
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH)+1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        String strmonth = String.format("%02d", mMonth);
        String strday = String.format("%02d", mDay);
        return mYear+"-"+strmonth+"-"+strday;
    }
    public static String  getCityname(Map map){
        String name = "";
        if(map.get("countryId") != null ){
            name = (String)map.get("countryName");
            return name;
        }
        if(map.get("townId") != null){
            name = (String)map.get("townName");
            return name;
        }
        if(map.get("districtId") != null ){
            name = (String)map.get("districtName");
            return name;
        }
        if(map.get("cityId") != null ){
            name = (String)map.get("cityName");
            return name;
        }
        if(map.get("provinceId") != null ){
            name = (String)map.get("provinceName");
            return name;
        }
        return name;
    }
}
