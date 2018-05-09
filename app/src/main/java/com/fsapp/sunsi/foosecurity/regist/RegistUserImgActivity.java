package com.fsapp.sunsi.foosecurity.regist;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.fsapp.sunsi.foosecurity.MainActivity;
import com.fsapp.sunsi.foosecurity.R;
import com.fsapp.sunsi.foosecurity.dialogs.ContextDialog;
import com.fsapp.sunsi.foosecurity.util.DBUtil;
import com.fsapp.sunsi.foosecurity.util.FileSizeUtil;
import com.fsapp.sunsi.foosecurity.util.HttpRequest;
import com.fsapp.sunsi.foosecurity.util.ImageUtils;
import com.fsapp.sunsi.foosecurity.util.UTIL;
import com.fsapp.sunsi.foosecurity.util.UploadUtil;
import com.soundcloud.android.crop.Crop;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RegistUserImgActivity extends AppCompatActivity {
    private DBUtil db;
    private Context context = this;
    private static final int TAKE_PICTURE = 0;
    private static final int CHOOSE_PICTURE = 1;
    private static final int CROP_SMALL_PICTURE = 2;
    private static final int SCALE = 5;//照片缩小比例
    private Uri imageUri =null;
    //摄像获取图片
    private LinearLayout regist_user_img_photo;
    //本地图库获取图片
    private LinearLayout regist_user_img_pic;
    private ImageView reist_user_img;
    //提交按钮
    private Button regist_submit;
    private Message msg;
    private String errormsg ;
    private Uri uritempFile;
    Map map = UTIL.getRegistSingle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_user_img);
        db = new DBUtil(context);
        reist_user_img = (ImageView) findViewById(R.id.reist_user_img);
        regist_user_img_photo = (LinearLayout) findViewById(R.id.regist_user_img_photo);
        regist_user_img_pic = (LinearLayout) findViewById(R.id.regist_user_img_pic);
        regist_submit = (Button) findViewById(R.id.regist_submit);
        //拍照获取图片
        getPicFromPhotoEvent(regist_user_img_photo);
        //相册获取图片
        getPicFromPicEvent(regist_user_img_pic);
        //注册事件
        registEvent(regist_submit);
    }

    /**
     * 注册事件
     * @param regist_submit
     */
    private void registEvent(Button regist_submit) {
        regist_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(map.get("userimg") == null || map.get("userimg").equals("")){
                    contextDialog("上传图片");
                }else{
                    regist();
                }
            }
        });
    }


    //从摄像头获取图片
    private void getPicFromPhotoEvent(LinearLayout regist_user_img_photo) {
        regist_user_img_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takePhoto();
            }
        });
    }

    //从图库中获取图片
    private void getPicFromPicEvent(LinearLayout regist_user_img_pic) {
        regist_user_img_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickPhoto();
            }
        });
    }


    /**
    * 拍照获取图片
     */
    private void takePhoto() {
        String intentactiong = "";
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {//4.4版本前
            intentactiong = Intent.ACTION_PICK;
        } else {//4.4版本后
            intentactiong = Intent.ACTION_GET_CONTENT;
        }
        Intent openCameraIntent = new Intent(intentactiong);
        imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"fsuser.jpg"));
        //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(openCameraIntent, TAKE_PICTURE);

    }

    /***
          * 从相册中取图片
          */
     private void pickPhoto() {
         Intent openAlbumIntent = new  Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         openAlbumIntent.setType("image/*");
         startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    startPhotoZoom(imageUri);
                    break;

                case CHOOSE_PICTURE:
//                    startPhotoZoom(data.getData());
                    beginCrop(data.getData());
                    break;
                case CROP_SMALL_PICTURE:
                    setImageToView(data);
                    break;
                case Crop.REQUEST_CROP:
                    handleCrop(resultCode, data);
                    break;

                default:
                    break;
            }
        }
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    protected void startPhotoZoom(Uri uri) {
        try{
            if (uri == null) {
                Log.i("tag", "The uri is not exist.");
            }
            Intent intent = new Intent("com.android.camera.action.CROP");

            intent.setDataAndType(imageUri, "image/*");

            // 设置裁剪
            intent.putExtra("crop", "true");
            // aspectX aspectY 是宽高的比例
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            // outputX outputY 是裁剪图片宽高
            intent.putExtra("outputX", 320);
            intent.putExtra("outputY", 320);
            intent.putExtra("return-data", false);
            uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "small.jpg");
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

            startActivityForResult(intent, CROP_SMALL_PICTURE);
//            Crop.of(uri,uritempFile).asSquare().start((Activity) context);
//              Crop.pickImage(this);
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     */
    protected void setImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            photo = ImageUtils.toRoundBitmap(photo,imageUri); // 这个时候的图片已经被处理成圆形的了
            reist_user_img.setImageBitmap(photo);
            uploadPic("");
        }
    }
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "png"));
        System.out.print(destination.getPath());
        System.out.print(destination.getEncodedPath()+","+destination.getPathSegments());
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            Uri photo =Crop.getOutput(result);
            reist_user_img.setImageURI(Crop.getOutput(result));
                uploadPic(photo.getEncodedPath());
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    private void uploadPic(String  imgurl) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

       final File upFile = new File(imgurl);
        if (upFile.exists()){
            double aaaa = FileSizeUtil.getFileOrFilesSize(imgurl,2);
            Log.e("aaaa",""+aaaa);
        }
            // 拿着imagePath上传了
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String result = UploadUtil.uploadFile(upFile,HttpRequest.url+"pro/upload");
                        JSONObject ob = UTIL.StringGetMap(result);
                        if (ob.getString("msg").equals("0")){
                            map.put("userimg",ob.getString("filename"));
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
                    }
                }
            }).start();
    }

    /**
     * 注册
     */
    private void regist() {
        // 拿着imagePath上传了
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
                    String result = HttpRequest.sendPost("user/regist",paramMap);
                    JSONObject ob = UTIL.StringGetMap(result);
                    if (ob.getString("msg").equals("0")){
                        Map mapReturn = db.querUserById();
                        int count = (int) mapReturn.get("count");
                        if(count == 0){
                            db.insertUser((String)map.get("username"),(String)map.get("pwd"),true,ob.getString("bonus"));
                        }else if(count == 1){
                            db.updateUser((String)map.get("username"),(String)map.get("pwd"),true,ob.getString("bonus"));
                        }else{
                            errormsg = UTIL.errorCode("1013")+"count:"+count;
                            msg = new Message();
                            msg.what = 1;
                            myHandler.removeMessages(1);
                            // 发送message值给Handler接收
                            myHandler.sendMessage(msg);
                        }
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
                case 2: //错误 
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
