package com.example.a.fd;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.a.fd.Util.FileHelper;
import com.example.a.fd.network.APIRepositoryImpl;
import com.example.a.fd.network.UploadImageMethod;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.security.PublicKey;

import id.zelory.compressor.Compressor;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static java.security.AccessController.getContext;

public class FoodScan extends AppCompatActivity implements UploadImageMethod {

    private CompositeDisposable subscriptions = new CompositeDisposable();
    private APIRepositoryImpl apiRepository = new APIRepositoryImpl();
    private UploadImageMethod uploadImageMethod;
    public static final int TAKE_PHOTO =1 ;
    private Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_scan);
        uploadImageMethod =this;
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
        switch (requestCode){
            case TAKE_PHOTO:
                if(resultCode == RESULT_OK){
                    try{
                        getContentResolver().openInputStream(imageUri);
                        postPhoto(imageUri);
                    }catch (FileNotFoundException e){
                        e.printStackTrace();
                    }
                }
                break;
                default:
                    break;
        }
    }
    public void postPhoto(Uri uri) {

        if (uri != null) {

            String url = FileHelper.getFilePath(this.getApplicationContext(),uri);
            assert url != null;
            File imageFile = new File(url);
            new Compressor(FoodScan.this.getApplicationContext())
                    .compressToFileAsFlowable(imageFile)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(file -> {
                                Log.d("compress", "accept: Ok");
                                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                                MultipartBody.Part part = MultipartBody.Part.createFormData("file",file.getName(),requestFile);
                                subscriptions.add(apiRepository.postMoment(part)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(uploadImageMethod::postSuccess,uploadImageMethod::postError));
                            }, throwable -> {
                                //throwable.printStackTrace();
                                //ConstantMethod.toastShort(getContext(),throwable.getMessage());
                            }
                    );
        }

    }

    @Override
    public void postSuccess(String a) {

    }

    @Override
    public void postError(Throwable e) {

    }
}
