package com.fsapp.sunsi.foosecurity.util;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ProDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
    public static int mapLevel = 4;
    public static int setMapLevel(int level){
        mapLevel = level;
        return mapLevel;
    }
    public static int getMapLevel(){
        return mapLevel;
    }
    //绘图点标记
    public static String overLay(final Context context, final JSONArray list, BaiduMap mBaiduMap){
        String resultStr = "success";
        try {
            List<OverlayOptions> overList = new ArrayList<OverlayOptions>();
            for (int i =0 ; i < list.length() ; i++) {
                JSONObject ob = list.getJSONObject(i);
                //定义Maker坐标点
                LatLng point = new LatLng(Double.parseDouble(ob.getString("blat")),Double.parseDouble(ob.getString("blog")));
                //构建Marker图标  
                BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.mipmap.pig);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions().position(point) .icon(bitmap);
                //在地图上添加Marker，并显示
//                mBaiduMap.addOverlay(option);
                overList.add(option);
            }
            mBaiduMap.addOverlays(overList);
            mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    try {
                        JSONArray jarr = new JSONArray();
                        LatLng point =  marker.getPosition();
                        GeoHash geoHash = new GeoHash(point.latitude,point.longitude,8);
                        String geoString  = geoHash.getGeoHashBase32();
                        for (int i =0 ; i < list.length() ; i++) {
                            JSONObject ob = list.getJSONObject(i);
                            if(ob.get("geoHash").equals(geoString)){
                                jarr.put(ob);
                            }
                        }
                        ProDialog pd = new ProDialog(context,jarr);
                        pd.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return false;
                }
            });
        }catch (Exception e){
            resultStr ="fail";
            e.printStackTrace();
        }

        return resultStr;
    }


    //清除绘图点标记
    public static String clearOverLay(BaiduMap mBaiduMap){
        String resultStr = "success";
        try {
            mBaiduMap.clear();
        }catch (Exception e){
            resultStr ="fail";
            e.printStackTrace();
        }

        return resultStr;
    }

}
