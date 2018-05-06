package com.fsapp.sunsi.foosecurity.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
            db.execSQL("CREATE TABLE IF NOT EXISTS BA_USER_INFO (USER_NAME VARCHAR,LOGIN_PWD VARCHAR,PAY_PWD VARCHAR,CHECKED INTEGER)");
            db.execSQL("CREATE TABLE IF NOT EXISTS BA_PRO_INFO (USER_NAME VARCHAR,LOGIN_PWD VARCHAR,PAY_PWD VARCHAR,CHECKED INTEGER)");
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
            cursor = db.rawQuery("SELECT USER_NAME,LOGIN_PWD,PAY_PWD,CHECKED FROM BA_USER_INFO", null);
            while (cursor.moveToNext()) {
                map.put("userName",cursor.getString(0));
                map.put("loginPwd",cursor.getString(1));
                map.put("payPwd",cursor.getString(2));
                map.put("checked",cursor.getString(3));
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
    public Map insertPro(String username,String pwd,boolean ischeck) {
        map = new HashMap();
        SQLiteDatabase db = null;
        int num = 1;
        if(ischeck == false){
            num = 0;
        }
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO BA_USER_INFO(USER_NAME,LOGIN_PWD,PAY_PWD,CHECKED) VALUES("+ username+ "," + pwd + "," + pwd + "," + num + ")");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return map;
    }

    public void updateUser(String username, String passwd, boolean ischeck) {
        SQLiteDatabase db = null;
        int num = 1;
        if(ischeck == false){
            num = 0;
        }
        try{
            db = this.getWritableDatabase();
            db.execSQL("UPDATE BA_USER_INFO SET LOGIN_PWD = ?,CHECKED =? , USER_NAME = ?",new Object[]{passwd,num,username});
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
    }

    public int insertUser(String username,String pwd,boolean ischeck) {
        map = new HashMap();
        SQLiteDatabase db = null;
        int num = 1;
        if(ischeck == false){
            num = 0;
        }
        try{
            db = this.getWritableDatabase();
            db.execSQL("INSERT INTO BA_USER_INFO(USER_NAME,LOGIN_PWD,PAY_PWD,CHECKED) VALUES("+ username+ "," + pwd + "," + pwd + "," + num + ")");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.close();
        }
        return 0;
    }
}
