package com.example.a.fd.Util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.InputStream;

/**
 * @Package: com.example.a.fd.Util
 * @ClassName: ImageUtil
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 20:19
 */
public class ImageUtil {
    public static Bitmap getBitmapByUrl(String url, Context context){
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalFilesDir = context.getExternalFilesDir("Pictures");
            Log.i("ImageUtil","externalFileDir = "+externalFilesDir);
            String path = externalFilesDir +"/"+ url;
            File mFile = new File(path);
            //若该文件存在
            if (mFile.exists()) {
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                return bitmap;
            } else {
                return null;
            }
        }else{
            return null;
        }
    }
    public static Bitmap getBitmapByName(String name,Context context){
        AssetManager asm=context.getAssets();
        try {
            InputStream is = asm.open(name);//name:图片的名称
            Bitmap bitmap=BitmapFactory.decodeStream(is);
            return bitmap;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
