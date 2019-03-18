package com.example.a.fd;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.example.a.fd.Util.FileHelper;
import com.example.a.fd.model.FoodRecognitionModel;
import com.example.a.fd.network.APIRepositoryImpl;
import com.example.a.fd.network.UploadImageMethod;
import com.example.a.fd.searchHistory.db.FoodMethod;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static java.security.AccessController.getContext;

public class FoodScan extends AppCompatActivity implements UploadImageMethod {

    @BindView(R.id.take_photo)
    ImageView photoButton;
    @BindView(R.id.picture)
    ImageView photo;
    @BindView(R.id.scan_results)
    RecyclerView results;
    ResultAdapter adapter;

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private APIRepositoryImpl apiRepository = new APIRepositoryImpl();
    private UploadImageMethod uploadImageMethod;
    public static final int TAKE_PHOTO =1 ;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_scan);
        uploadImageMethod =this;
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                photoButton.setVisibility(View.GONE);
            }
        });
    }
    private void takePhoto(){
        File image = new File(getExternalCacheDir(),"meal.jpg");
        try{
            if(image.exists()){
                image.delete();
            }
            image.createNewFile();
        }catch (IOException e){
            e.printStackTrace();
        }
        if(Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(FoodScan.this,"com.example.a.fd.fileprovider",image);
        }else{
            imageUri = Uri.fromFile(image);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        startActivityForResult(intent,TAKE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        postPhoto(imageUri,compressImage(bitmap));
                        photo.setImageBitmap(bitmap);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }
    public void postPhoto(Uri uri,File file) {

        if (uri != null) {
            File imageFile;
            if(file!=null){
                imageFile = file;
                Log.i("jjjj", "file size:" + imageFile.length());
            }else {
                imageFile = new File(uri.getPath());//new File(new URI(uri.toString()));

            }
           //new File(url);//new File(uri.getPath());
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),imageFile);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file",imageFile.getName(),requestBody);
            subscriptions.add(apiRepository.postPhoto(part)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(uploadImageMethod::postSuccess,uploadImageMethod::postError));
        }

    }

    @Override
    public void postSuccess(FoodRecognitionModel a) {
        if(a!=null) {
            if(a.getStatus()==200) {
                Glide.with(this).load(a.getResultImgUrl()).into(photo);
                String jsonString = a.getResultJson();
                Log.i("jsonString", jsonString);
                if(!jsonString.equals("{}")&&jsonString.length()>4) {
                    Map mapTypes = JSON.parseObject(jsonString);
                    List<String> foodNameList = new ArrayList<>();
                    List<String> foodPosibility = new ArrayList<>();
                    FoodMethod foodMethod = new FoodMethod(this.getApplicationContext());
                    String realName = "";
                    List<String> foodImg = new ArrayList<>();
                    for (Object obj : mapTypes.keySet()) {
                        switch (obj.toString()) {
                            case "sweet and sour fillet":
                                realName = "咕噜肉";
                                break;
                            case "fried okra":
                                realName = "炒秋葵";
                                break;
                            case "scrambled egg":
                                realName = "韭黄炒蛋";
                                break;
                            case "rice":
                                realName = "米饭";
                                break;
                        }
                        foodNameList.add(realName);
                        foodPosibility.add(mapTypes.get(obj).toString());
                        foodImg.add(foodMethod.queryByName(realName).getImageUrl());
                        Log.i("jjjjjjj", "key为：" + obj + "值为：" + mapTypes.get(obj));
                    }
                    adapter = new ResultAdapter(foodImg, foodNameList, foodPosibility);
                    results.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(), LinearLayout.VERTICAL, false));
                    results.setAdapter(adapter);
                }else{
                    Toast.makeText(this,"无结果",Toast.LENGTH_SHORT).show();
                }
            }else{
                Log.i("jjjjj","result is null");
                Toast.makeText(this,"无结果",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void postError(Throwable e) {
        Toast.makeText(this,"服务器错误，识别失败",Toast.LENGTH_SHORT).show();
        Log.i("jjjjj","服务器错误");
    }

    public static File compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 500) {  //循环判断如果压缩后图片是否大于500kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            options -= 10;//每次都减少10
            bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            long length = baos.toByteArray().length;
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String filename = format.format(date);
        File file = new File(Environment.getExternalStorageDirectory(),filename+".jpg");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            try {
                fos.write(baos.toByteArray());
                fos.flush();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
       // recycleBitmap(bitmap);
        return file;
    }
    public static void recycleBitmap(Bitmap... bitmaps) {
        if (bitmaps==null) {
            return;
        }
        for (Bitmap bm : bitmaps) {
            if (null != bm && !bm.isRecycled()) {
                bm.recycle();
            }
        }
    }
}
