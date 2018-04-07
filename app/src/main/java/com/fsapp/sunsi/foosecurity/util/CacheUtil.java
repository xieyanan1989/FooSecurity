package com.fsapp.sunsi.foosecurity.util;

/**
 * Created by xyn-pc on 2018/4/6.
 */

public class CacheUtil {
    public static String getGeoHash(double lat,double lon,int hashSize){
        GeoHash geo = new GeoHash(lat,lon,hashSize);
        return geo.getGeoHashBase32();
    }
}
