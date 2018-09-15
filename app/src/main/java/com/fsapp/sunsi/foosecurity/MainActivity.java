package com.fsapp.sunsi.foosecurity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.fsapp.sunsi.foosecurity.buy.BuyProActivity;
import com.fsapp.sunsi.foosecurity.dialogs.CatesDialog;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.dialogs.MainRegionDialog;
import com.fsapp.sunsi.foosecurity.dubo.JCZQActivity;
import com.fsapp.sunsi.foosecurity.dubo.SsqActivity;
import com.fsapp.sunsi.foosecurity.product.AddProductActivity;
//import com.fsapp.sunsi.foosecurity.product.ImageProductActivity;
import com.fsapp.sunsi.foosecurity.product.ImageProductActivity;
import com.fsapp.sunsi.foosecurity.product.MapProductActivity;
import com.fsapp.sunsi.foosecurity.regist.RegistAddressActivity;
import com.fsapp.sunsi.foosecurity.regist.RegistUserImgActivity;
import com.fsapp.sunsi.foosecurity.util.CacheUtil;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.GeoHash;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.MapUtil;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;
//    private Button testButton;
//    private TextView mTextMessage;
    private  DBUtil db;
    MapView mMapView;
    BaiduMap mBaiduMap;
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    boolean isFirstLoc = true; // 是否首次
    public MyLocationListenner myListener = new MyLocationListenner();
    // 定位相关
    LocationClient mLocClient;
    private Spinner main_book;
    private LinearLayout main_cates;
    private LinearLayout main_cites;
    private TextView main_cates_show;
    private TextView main_cites_show;
    private Button main_search;
    private Map searchMap = new HashMap();
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Map map = db.querUser();
            String userName = (String)map.get("userName");
            Intent intentd = null;
            switch (item.getItemId()) {
                case R.id.nav_home:
//                    mTextMessage.setText(R.string.nav_home);
//                    intentd = new Intent(context, SsqActivity.class);
//                    startActivity(intentd);
                    intentd = new Intent(context, JCZQActivity.class);
                    startActivity(intentd);
                    return true;
                case R.id.nav_buy:
//                    mTextMessage.setText(R.string.title_dashboard);
                    if(userName == "" || userName == null){
                        intentd = new Intent(context,LoginActivity.class);
                        startActivity(intentd);
                        return false;
                    }else{
                        intentd = new Intent(context,BuyProActivity.class);
                        startActivity(intentd);
                    }
                    return true;
                case R.id.nav_add:
//                    mTextMessage.setText(R.string.title_notifications);
                    if(userName == "" || userName == null){
                        intentd = new Intent(context,LoginActivity.class);
                        startActivity(intentd);
                        return false;
                    }else{
                        intentd = new Intent(context,AddProductActivity.class);
                        startActivity(intentd);
                    }
                    return true;
                case R.id.nav_sell:
//                    mTextMessage.setText(R.string.title_notifications);
                    if(userName == "" || userName == null){
                        intentd = new Intent(context,LoginActivity.class);
                        startActivity(intentd);
                        return false;
                    }
                    return true;
                case R.id.nav_self:
//                    mTextMessage.setText(R.string.title_notifications);
                    intentd = new Intent(context,LoginActivity.class);
//                    intentd = new Intent(context,RegistUserImgActivity.class);
                    startActivity(intentd);
                    return true;
            }
            return false;
        }

    };

    /**
     * 查询用户是否存在
     * @return
     */
    private boolean queryUser() {
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        testButton = (Button) findViewById(R.id.testbutton);
//        testButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                Intent intentd = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intentd);
////                Intent intentd = new Intent(MainActivity.this, RegistUserImgActivity.class);
////                startActivity(intentd);
//            }
//        });
        initSqlite();
//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mMapView = (MapView)findViewById(R.id.main_mapView);
        main_book = (Spinner)findViewById(R.id.main_book);
        main_cates = (LinearLayout)findViewById(R.id.main_cates);
        main_cites = (LinearLayout) findViewById(R.id.main_cites);
        main_cates_show = (TextView)findViewById(R.id.main_cates_show);
        main_cites_show = (TextView)findViewById(R.id.main_cites_show);
        main_search = (Button)findViewById(R.id.main_search);
        //获取权限
        showContacts();
        //百度地图定位并显示
        mapView();
        //获取类别事件
        getCateseEvent(main_cates);
        //获取城市事件
        getCitiesEvent(main_cites);
        //查询事件
        mainSearchEvent(main_search);
    }

    //查询事件
    private void mainSearchEvent(Button main_search) {
        main_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    //获取城市
    private void getCitiesEvent(LinearLayout main_cites) {
        main_cites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainRegionDialog dia = new MainRegionDialog(context);
                dia.setButtonOnClickListener(new MainRegionDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(Map mainRegionMap) {
                        for(Iterator it = mainRegionMap.keySet().iterator(); it.hasNext();){
                            String key = it.next().toString();
                            searchMap.put(key, mainRegionMap.get(key));
                        }
                        main_cites_show.setText(UTIL.getCityname(mainRegionMap));
                    }
                });
                dia.show();
            }
        });
    }

    //获取种类点击事件
    private void getCateseEvent(final LinearLayout main_cates) {
        main_cates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Map jsonmap = new HashMap();
                jsonmap.put("parentid","0000000000");
                CatesDialog catesDialog = new CatesDialog(context, jsonmap);
                catesDialog.setButtonOnClickListener(new CatesDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(Map returnamp) {
                        String cate_name = (String) returnamp.get("cateName");
                        if (cate_name == null || cate_name.equals("")){
                            contextDialog("您未选择类别");
                            return;
                        }
                        searchMap.put("cateid",(String) returnamp.get("cateId"));
                        searchMap.put("catetype",returnamp.get("catetype"));
                        main_cates_show.setText(cate_name);
//                        comm = (double) returnamp.get("comm");
//                        catetype = returnamp.get("catetype");
                    }
//                    @Override
//                    public void onOkBtnClick(String cate_id, String cate_name,double com,int ct) {
//                        if (cate_name == null || cate_name.equals("")){
//                            contextDialog("您未选择类别");
//                            return;
//                        }
//                        map.put("cateid",cate_id);
//                        map.put("catetype",ct);
//                        pro_cates.setText(cate_name);
//                        comm = com;
//                        catetype = ct;
//                    }
                });
                catesDialog.show();
            }
        });
    }

    private void mapView() {
        // 开启定位图层
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        //百度地图拖拽事件
        mapStatusChangeListener();
    }

    /**
     * 百度地图拖拽事件监听
     */
    private void mapStatusChangeListener() {
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus, int i) {

            }

            @Override
            public void onMapStatusChange(MapStatus mapStatus) {

            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                LatLng latLng = mapStatus.target;
                mCurrentLat = latLng.latitude;
                mCurrentLon = latLng.longitude;
                //21: 10;20: 20;19: 50;18: 100;17: 200;16: 500;15: 1000;14: 2000;13: 5000;12: 10000;11: 20000;
                //10: 25000;9: 50000;8: 100000;7: 200000;6: 500000;5: 1000000; 4: 2000000;
                //自v5.0.0起，为了优化显示效果，将地图缩放等级由3-21调整为4-21，请开发者注意。
                // 1: 2500km;2: 630km;3: 78km;4: 30km; 5: 2.4km; 6: 610m; 7: 76m; 8: 19m （geoHash距离值）
                Float zoom = mapStatus.zoom;
//                if(zoom < 14){
//                    MapUtil.setMapLevel(3);
//                }
                getGeoHash();
            }
        });
    }

    /**
     * 创建数据库
     */
    private void initSqlite() {
        db = new DBUtil(context);
        db.getWritableDatabase();
    }

    public void showContacts(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE},1 );
        }else{
//            init();
        }
//        int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
//        if (checkPermission != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
//            return;
//        }
    }

    //Android6.0申请权限的回调方法
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            // requestCode即所声明的权限获取码，在checkSelfPermission时传入
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    contextDialog("获取位置权限失败，请手动开启，否则地图无法使用");
                }
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                } else {
                    // 没有获取到权限，做特殊处理
                    contextDialog("获取权限失败，请手动开启，否则注册与发布产品无法使用");
                }
                break;
            default:
                break;
        }
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            try {
                // map view 销毁后不在处理新接收的位置
                if (location == null || mMapView == null) {
                    return;
                }
                mCurrentLat = location.getLatitude();
                mCurrentLon = location.getLongitude();
                mCurrentAccracy = location.getRadius();
                locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(mCurrentDirection).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                mBaiduMap.setMyLocationData(locData);
                if (isFirstLoc) {
                    isFirstLoc = false;
                    getGeoHash();
                    LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(13.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                //根据当前坐标点获取产品数据并显示
                GeoHash g = new GeoHash(mCurrentLat,mCurrentLon,4);
                requestPros(g.getGeoHashBase32());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }

    private void getGeoHash() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String geoHash = CacheUtil.getGeoHash(mCurrentLat,mCurrentLon,4);
                    Map map = new HashMap();
                    map.put("geoHash",String.valueOf(geoHash.substring(0,MapUtil.getMapLevel())));
                    String json = UTIL.AjaxJson(map);
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("pro/nearby",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
//                        MapUtil.clearOverLay(mBaiduMap);
                        String resultStr = MapUtil.overLay(context,ob.getJSONArray("list"),mBaiduMap);
                    }else{
//                        errormsg = UTIL.errorCode(ob.getString("msg"));
//                        msg = new Message();
//                        msg.what = 1;
//                        myHandler.removeMessages(1);
//                        // 发送message值给Handler接收
//                        myHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void requestPros(String geoHashBase32) {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    String json = UTIL.AjaxJson(map);
////                    String json = "{\"username\":\""+map.get("username")+"\",\"pwd\":\""+map.get("pwd")+"\",\"provinceId\":\""+map.get("provinceId")+"\",\"cityId\":\""+map.get("cityId")+"\",\"districtId\":\""+map.get("districtId")+"\",\"townId\":\""+map.get("townId")+"\",\"countryId\":\""+map.get("countryId")+"\",\"address\":\""+map.get("address")+"\",\"userimg\":\""+map.get("userimg")+"\",\"blog\":\""+map.get("blog")+"\",\"blat\":\""+map.get("blat")+"\"}";
//                    String sig = HttpRequest.md5(json);
//                    Map<String,String> paramMap = new HashMap<String, String>();
//                    paramMap.put("json",json);
//                    paramMap.put("sig", sig);
//                    String result = HttpRequest.sendPost("user/regist",paramMap);
//                    JSONObject ob = UTIL.StringGetMap(result);
//                    if (ob.getString("msg").equals("0")){
//                        Map mapReturn = db.querUserById();
//                        int count = (int) mapReturn.get("count");
//                        if(count == 0){
//                            db.insertUser((String)map.get("username"),(String)map.get("pwd"),true);
//                        }else if(count == 1){
//                            db.updateUser((String)map.get("username"),(String)map.get("pwd"),true);
//                        }else{
//                            errormsg = UTIL.errorCode("1013")+"count:"+count;
//                            msg = new Message();
//                            msg.what = 1;
//                            myHandler.removeMessages(1);
//                            // 发送message值给Handler接收
//                            myHandler.sendMessage(msg);
//                        }
//                        errormsg = "恭喜你，注册成功";
//                        msg = new Message();
//                        msg.what = 2;
//                        myHandler.removeMessages(2);
//                        // 发送message值给Handler接收
//                        myHandler.sendMessage(msg);
//                    }else{
//                        errormsg = UTIL.errorCode(ob.getString("msg"));
//                        msg = new Message();
//                        msg.what = 1;
//                        myHandler.removeMessages(1);
//                        // 发送message值给Handler接收
//                        myHandler.sendMessage(msg);
//                    }
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//        }).start();
    }
}
