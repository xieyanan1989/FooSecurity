package com.fsapp.sunsi.foosecurity.product;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fsapp.sunsi.foosecurity.MainActivity;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.FileSizeUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.ImageUtils;
import com.fsapp.sunsi.foosecurity.util.UTIL;
import com.fsapp.sunsi.foosecurity.util.UploadUtil;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;

public class ImageProductActivity extends AppCompatActivity {
    private DBUtil db;
    private Context context = ImageProductActivity.this;
    private PhotoAdapter photoAdapter;
    //是否包括当前图片变量
    Map proImgmap = UTIL.getProImgSingle();
    Map map = UTIL.getProductSingle();
    private ArrayList<String> selectedPhotos = new ArrayList<>();
    private Message msg;
    private String errormsg ;
    private Button pro_pro_create;
    private int countdowns = 0;
    private CountDownLatch latch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_product);
        pro_pro_create = (Button)findViewById(R.id.pro_pro_create);
        db = new DBUtil(context);
        //上传图片中的数据清空
        UTIL.clearProImg();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        photoAdapter = new PhotoAdapter(this, selectedPhotos);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        try{
                            if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                                PhotoPicker.builder()
                                        .setPhotoCount(PhotoAdapter.MAX)
                                        .setShowCamera(true)
                                        .setPreviewEnabled(false)
                                        .setSelected(selectedPhotos)
                                        .start(ImageProductActivity.this);
                            } else {
                                PhotoPreview.builder()
                                        .setPhotos(selectedPhotos)
                                        .setCurrentItem(position)
                                        .start(ImageProductActivity.this);
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }));

        //创建商品点击事件
        createProEvent(pro_pro_create);
    }

    private void createProEvent(Button pro_pro_create) {
        pro_pro_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (map.get("imgs") == null || map.get("imgs").equals("")){
                        contextDialog("请选择商品图片");
                    }else{
//                    map.put("imgurl",map.get("imgurl")+":"+UTIL.getCurrDate()+"|");
                        latch.await();
                        map.put("imgurl",map.get("imgs")+":"+UTIL.getCurrDate()+"|");
                        map.put("username",db.querUser().get("userName"));
                        createPro();
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
    }

    private void createPro() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String json = UTIL.AjaxJson(map);
//                    String json = "{\"username\":\""+map.get("username")+"\",\"pwd\":\""+map.get("pwd")+"\",\"provinceId\":\""+map.get("provinceId")+"\",\"cityId\":\""+map.get("cityId")+"\",\"districtId\":\""+map.get("districtId")+"\",\"townId\":\""+map.get("townId")+"\",\"countryId\":\""+map.get("countryId")+"\",\"address\":\""+map.get("address")+"\",\"userimg\":\""+map.get("userimg")+"\",\"blog\":\""+map.get("blog")+"\",\"blat\":\""+map.get("blat")+"\"}";
                    String sig = HttpRequest.md5(json);
                    Map<String,String> paramMap = new HashMap<String, String>();
                    paramMap.put("json",json);
                    paramMap.put("sig", sig);
                    String result = HttpRequest.sendPost("pro/create",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        errormsg = "恭喜你，注册成功";
                        msg = new Message();
                        msg.what = 2;
                        myHandler.removeMessages(2);
                        // 发送message值给Handler接收
                        myHandler.sendMessage(msg);
                    }else{
                        errormsg = UTIL.errorCode(ob.getString("msg"));
                        msg = new Message();
                        msg.what = 1;
                        myHandler.removeMessages(1);
                        // 发送message值给Handler接收
                        myHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK &&(requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)) {
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                //判断需上传的个数
                countdowns = 0;
                map.put("imgs","");
                for(int i = 0 ; i < photos.size() ; i ++){
                    //已上传的图片中是否包括当前的
                    String photoUrl = (String) proImgmap.get(photos.get(i));
                    if(photoUrl == null || photoUrl.equals("")){
                        countdowns++ ;
                    }
                }
                latch = new CountDownLatch(countdowns);
                for(int i = 0 ; i < photos.size() ; i ++){
                    //已上传的图片中是否包括当前的
                   String photoUrl = (String) proImgmap.get(photos.get(i));
                    if(photoUrl == null || photoUrl.equals("")){
                        uploadPic(photos.get(i));
                    }
                }
            }
            selectedPhotos.clear();
            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            photoAdapter.notifyDataSetChanged();
        }
    }
    private void uploadPic(final String imgurl) {
        // 拿着imagePath上传了

        final File upFile = new File(imgurl);
        if (upFile.exists()){
            double aaaa = FileSizeUtil.getFileOrFilesSize(imgurl,2);
            Log.e("aaaa",""+aaaa);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String result = UploadUtil.uploadFile(upFile, HttpRequest.url+"pro/upload");
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        if (map.get("imgs") == null){
                            map.put("imgs",ob.getString("filename"));
                        }else {
                            map.put("imgs",map.get("imgs")+","+ob.getString("filename"));
                        }
                        proImgmap.put(imgurl,imgurl);
                    }else{
                        errormsg = ob.getString("msg");
                        msg = new Message();
                        msg.what = 1;
                        myHandler.removeMessages(1);
                        // 发送message值给Handler接收
                        myHandler.sendMessage(msg);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //当前线程完成计算减一
                    latch.countDown();
                }
            }
        }).start();
    }
    private void contextDialog(String errormsg){
        new ContextDialog(context,errormsg).show();
    }
    Handler myHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1: //错误 
                    contextDialog(errormsg);
                    break;
                case 2: //成功
                    Intent intentd = new Intent(context, MainActivity.class);
                    startActivity(intentd);
                    break;
            }
        }

    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
