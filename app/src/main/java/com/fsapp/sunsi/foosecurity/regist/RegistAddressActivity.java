package com.fsapp.sunsi.foosecurity.regist;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.fsapp.sunsi.foosecurity.MainActivity;
import com.fsapp.sunsi.foosecurity.Map.LocationDemo;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.dialogs.RegiontDialog;
import com.fsapp.sunsi.foosecurity.util.UTIL;

import java.util.Map;

public class RegistAddressActivity extends AppCompatActivity {

    MapView mMapView;
    BaiduMap mBaiduMap;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private double mCurrentLat = 0.0;
    private double mCurrentLon = 0.0;
    private float mCurrentAccracy;
    private MyLocationData locData;
    private int mCurrentDirection = 0;
    boolean isFirstLoc = true; // 是否首次定位
    private Context context = RegistAddressActivity.this;
    //省显示
    private TextView regist_text_province;
    //市显示
    private TextView regist_text_city;
    //县区显示
    private TextView regist_text_county;
    //镇显示
    private TextView regist_text_town;
    //村显示
    private TextView regist_text_village;
    //地址显示
    private EditText regist_text_address;
    //省选择按钮
    private Button regist_check_province;
    //市选择按钮
    private Button regist_check_city;
    //县选择按钮
    private Button regist_check_county;
    //镇选择按钮
    private Button regist_check_town;
    //村选择按钮
    private Button regist_check_village;
    //下一步
    private Button regist_address_submit;
    Map map = UTIL.getRegistSingle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_regist_address);
            regist_text_province = (TextView) findViewById(R.id.regist_text_province);
            regist_text_city = (TextView) findViewById(R.id.regist_text_city);
            regist_text_county = (TextView) findViewById(R.id.regist_text_county);
            regist_text_town = (TextView) findViewById(R.id.regist_text_town);
            regist_text_village = (TextView) findViewById(R.id.regist_text_village);
            regist_text_address = (EditText) findViewById(R.id.regist_text_address);
            regist_check_province = (Button) findViewById(R.id.regist_check_province);
            regist_check_city = (Button) findViewById(R.id.regist_check_city);
            regist_check_county = (Button) findViewById(R.id.regist_check_county);
            regist_check_town = (Button) findViewById(R.id.regist_check_town);
            regist_check_village = (Button) findViewById(R.id.regist_check_village);
            regist_address_submit = (Button) findViewById(R.id.regist_address_submit);
            mMapView = (MapView)findViewById(R.id.regist_mapView);
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
            int checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            if (checkPermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
                return;
            }
            //获取省事件
            regionProvinceEvent(regist_check_province);
            //获取市事件
            regionCityeEvent(regist_check_city);
            //获取县事件
            regionCountyeEvent(regist_check_county);
            //获取县事件
            regionTownEvent(regist_check_town);
            //获取县事件
            regionVallageEvent(regist_check_village);
            //下一步点击事件
            nextSubmitEvent(regist_address_submit);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    //下一步点击事件
    private void nextSubmitEvent(Button regist_address_submit) {
        regist_address_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = regist_text_address.getText().toString();
                 if(map.get("provinceId") == null || map.get("provinceId").equals("")){
                    contextDialog("请选择省份");
                }else if(map.get("cityId") == null || map.get("cityId").equals("")){
                    contextDialog("请选择城市");
                }else if(map.get("districtId") == null || map.get("districtId").equals("")){
                    contextDialog("请选择区县");
                }else if(map.get("townId") == null || map.get("townId").equals("")){
                    contextDialog("请选择乡镇");
                }else if(map.get("countryId") == null || map.get("countryId").equals("")){
                    contextDialog("请选择村街");
                }else if(regist_text_address.getText().toString().equals("")){
                    contextDialog("请输入详细地址");
                }else{
                     map.put("address",regist_text_address.getText().toString());
                     Intent intent = new Intent(context,RegistUserImgActivity.class);
                     startActivity(intent);
                }
            }
        });
    }

    private void regionVallageEvent(Button regist_check_village) {
        regist_check_village.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) map.get("townId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        map.put("countryId",reginCode);
                        regist_text_village.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionTownEvent(Button regist_check_town) {
        regist_check_town.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) map.get("districtId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        map.put("townId",reginCode);
                        regist_text_town.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionCountyeEvent(Button regist_check_county) {
        regist_check_county.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) map.get("cityId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        map.put("districtId",reginCode);
                        regist_text_county.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionProvinceEvent(Button regist_check_province) {
        regist_check_province.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context,"000000000000");
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        Map map = UTIL.getRegistSingle();
                        map.put("provinceId",reginCode);
                        regist_text_province.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
    }

    private void regionCityeEvent(Button regist_check_city) {
        regist_check_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegiontDialog regionDialog = new RegiontDialog(context, (String) map.get("provinceId"));
                regionDialog.setButtonOnClickListener(new RegiontDialog.OnButtonOnClickListener() {
                    @Override
                    public void onOkBtnClick(String reginCode, String regionName) {
                        map.put("cityId",reginCode);
                        regist_text_city.setText(regionName);
                    }
                });
                regionDialog.show();
            }
        });
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
                    LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
                    MapStatus.Builder builder = new MapStatus.Builder();
                    builder.target(ll).zoom(18.0f);
                    mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                }
                map.put("blat",mCurrentLat);
                map.put("blog",mCurrentLon);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }
    }
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
        //为系统的方向传感器注册监听器
//        mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onStop() {
        //取消注册传感器监听
//        mSensorManager.unregisterListener(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }

}
