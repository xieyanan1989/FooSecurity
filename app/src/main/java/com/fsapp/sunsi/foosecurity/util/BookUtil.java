package com.fsapp.sunsi.foosecurity.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2018/5/13.
 */

public class BookUtil {
    /** 用户名 **/
    public static String username="";
    /** 每页条数 **/
    public static String pagesize="5";
    /** 页数**/
    public static String pagenum="0";
    /** 状态 **/
    public static String saletype="0";
    public static void clear(){
        BookUtil.username="";
        BookUtil.pagesize="";
        BookUtil.pagenum="";
        BookUtil.saletype="";
    }

    public static void put(String username,String pagesize,String pagenum,String saletype){
        BookUtil.username=username;
        BookUtil.pagesize=pagesize;
        BookUtil.pagenum=pagenum;
        BookUtil.saletype=saletype;
    }

    public static Map get(){
        Map map = new HashMap();
        map.put("username",username);
        map.put("pagesize",pagesize);
        map.put("pagenum",pagenum);
        map.put("saletype",saletype);
        return map;
    }
}
