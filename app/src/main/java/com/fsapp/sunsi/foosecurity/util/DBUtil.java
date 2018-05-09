package com.fsapp.sunsi.foosecurity.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xyn-pc on 2017/5/28.
 */

public class DBUtil extends SQLiteOpenHelper {
    private static final String name = "xs";
    private static final int version = 4;
    private Map map ;
    public DBUtil(Context context) {
            super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            Log.i("Line", "52");
            db.execSQL("CREATE TABLE IF NOT EXISTS BA_USER_INFO (USER_NAME VARCHAR,LOGIN_PWD VARCHAR,PAY_PWD VARCHAR,CHECKED INTEGER,BONUS VARCHAR)");
            db.execSQL("CREATE TABLE IF NOT EXISTS BA_PRO_INFO (USER_NAME VARCHAR,PRO_ID VARCHAR,CATE_ID VARCHAR,CATE_NAME VARCHAR,PROVINCE_ID VARCHAR,CITY_ID VARCHAR,DISTRICT_ID VARCHAR,TOWN_ID VARCHAR,COUNTRY_ID VARCHAR,SALE_TYPE VARCHAR,SALE_DETAIL VARCHAR,SALE_TITLE VARCHAR,IMG_URL VARCHAR,SALE_COUNT VARCHAR,SALE_MEA VARCHAR,B_LOG VARCHAR,B_LAT VARCHAR,SALE_SINGLE VARCHAR,GEO_HASH VARCHAR,PRO_PRICE VARCHAR)");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public Map querUser() {
        map = new HashMap();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT USER_NAME,LOGIN_PWD,PAY_PWD,CHECKED,BONUS FROM BA_USER_INFO", null);
            while (cursor.moveToNext()) {
                map.put("userName",cursor.getString(0));
                map.put("loginPwd",cursor.getString(1));
                map.put("payPwd",cursor.getString(2));
                map.put("checked",cursor.getString(3));
                map.put("bonus",cursor.getString(4));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }
        return map;
    }
    public Map querUserById() {
        map = new HashMap();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT COUNT(*) FROM BA_USER_INFO ",null);
            while (cursor.moveToNext()) {
                map.put("count",cursor.getInt(0));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }
        return map;
    }
    public int insertPro(JSONObject jsonObject) {
        int success = 0;
        SQLiteDatabase db = null;
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO BA_PRO_INFO(USER_NAME,PRO_ID,CATE_ID,CATE_NAME,PROVINCE_ID,CITY_ID,DISTRICT_ID,TOWN_ID,COUNTRY_ID,SALE_TYPE,SALE_DETAIL,SALE_TITLE,IMG_URL,SALE_COUNT,SALE_MEA,B_LOG,B_LAT,SALE_SINGLE,GEO_HASH,PRO_PRICE)" +
                    " VALUES("
                    +"'"+ jsonObject.getString("username")+"',"
                    +"'"+ jsonObject.getString("proid")+"',"
                    +"'"+  jsonObject.getString("cateid")+"',"
                    +"'"+  jsonObject.getString("catename")+"',"
                    +"'"+  jsonObject.getString("provinceId")+"',"
                    +"'"+ jsonObject.getString("cityId")+"',"
                    +"'"+  jsonObject.getString("districtId")+"',"
                    +"'"+  jsonObject.getString("townId")+"',"
                    +"'"+ jsonObject.getString("countryId")+"',"
                    +"'"+  jsonObject.getString("saletype")+"',"
                    +"'"+  jsonObject.getString("saledetail")+"',"
                    +"'"+  jsonObject.getString("saletitle")+"',"
                    +"'"+ jsonObject.getString("imgurl")+"',"
                    +"'"+  jsonObject.getString("salecount")+"',"
                    +"'"+  jsonObject.getString("salemea")+"',"
                    +"'"+  jsonObject.getString("blog")+"',"
                    +"'"+  jsonObject.getString("blat")+"',"
                    +"'"+  jsonObject.getString("salesingle")+"',"
                    +"'"+  jsonObject.getString("geoHash")+"',"
                    +"'"+  jsonObject.getString("proPrice")+"'"
                    + ")");
        }catch (Exception e){
            e.printStackTrace();
            success = 1;
        }finally {
            db.close();
        }
        return success;
    }

    public void updateUser(String username, String passwd, boolean ischeck,String bonus) {
        SQLiteDatabase db = null;
        int num = 1;
        if(ischeck == false){
            num = 0;
        }
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE BA_USER_INFO SET LOGIN_PWD = ?,CHECKED =? , USER_NAME = ?,BONUS",new Object[]{passwd,num,username,bonus});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    public int insertUser(String username,String pwd,boolean ischeck,String bonus) {
        map = new HashMap();
        SQLiteDatabase db = null;
        int num = 1;
        if(ischeck == false){
            num = 0;
        }
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO BA_USER_INFO(USER_NAME,LOGIN_PWD,PAY_PWD,CHECKED,BONUS) VALUES("+ username+ "," + pwd + "," + pwd + "," + num + "," + bonus + ")");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return 0;
    }
    public JSONArray querPros() {
        JSONArray jsonArray = new JSONArray();
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try{
            db = this.getWritableDatabase();
            cursor = db.rawQuery("SELECT USER_NAME,PRO_ID,CATE_ID,CATE_NAME,PROVINCE_ID,CITY_ID,DISTRICT_ID,TOWN_ID,COUNTRY_ID,SALE_TYPE,SALE_DETAIL,SALE_TITLE,IMG_URL,SALE_COUNT,SALE_MEA,B_LOG,B_LAT,SALE_SINGLE,GEO_HASH,PRO_PRICE FROM BA_PRO_INFO", null);
            while (cursor.moveToNext()) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username",cursor.getString(0));
                jsonObject.put("proid",cursor.getString(1));
                jsonObject.put("cateid",cursor.getString(2));
                jsonObject.put("catename",cursor.getString(3));
                jsonObject.put("provinceId",cursor.getString(4));
                jsonObject.put("cityId",cursor.getString(5));
                jsonObject.put("districtId",cursor.getString(6));
                jsonObject.put("townId",cursor.getString(7));
                jsonObject.put("countryId",cursor.getString(8));
                jsonObject.put("saletype",cursor.getString(9));
                jsonObject.put("saledetail",cursor.getString(10));
                jsonObject.put("saletitle",cursor.getString(11));
                jsonObject.put("imgurl",cursor.getString(12));
                jsonObject.put("salecount",cursor.getString(13));
                jsonObject.put("salemea",cursor.getString(14));
                jsonObject.put("blog",cursor.getString(15));
                jsonObject.put("blat",cursor.getString(16));
                jsonObject.put("salesingle",cursor.getString(17));
                jsonObject.put("geoHash",cursor.getString(18));
                jsonObject.put("proPrice",cursor.getString(19));
                jsonArray.put(jsonObject);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cursor.close();
            db.close();
        }
        return jsonArray;
    }
}
