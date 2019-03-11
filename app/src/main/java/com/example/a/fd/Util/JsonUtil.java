package com.example.a.fd.Util;

import android.content.Context;
import android.content.res.AssetManager;

import com.example.a.fd.model.Food;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.a.fd.Util
 * @ClassName: JsonUtil
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/4 23:43
 */
public class JsonUtil {
    public static String getJson(Context context, String fileName){
        StringBuilder stringBuilder = new StringBuilder();
        //获得assets资源管理器
        AssetManager assetManager = context.getAssets();
        //使用IO流读取json文件内容
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName),"utf-8"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    //使用Gson将Json字符串转换为对象
    /**
     * 将字符串转换为 对象
     * @param json
     * @param
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T> cls){
        /*Gson gson =new Gson();
        List<Food> list= gson.fromJson(json,  new TypeToken<List<Food>>(){}.getType());
        return list;*/
        Gson gson = new Gson();
        List<T> list = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(json).getAsJsonArray();
        for(final JsonElement elem : array){
            list.add(gson.fromJson(elem, cls));
        }
        return list;
    }

}
