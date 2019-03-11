package com.example.a.fd;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.Util.JsonUtil;
import com.example.a.fd.model.Food;
import com.example.a.fd.searchHistory.db.DaoUtils;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.example.a.fd.searchView.FoodSearchView;

import java.util.List;

public class Test extends AppCompatActivity {

    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = findViewById(R.id.test_img);
        imageView.setImageBitmap(ImageUtil.getBitmapByName("banana.png",this));
        DaoUtils.init(Test.this);
        DaoUtils.getDaoManagerInstance();
        addData();

    }
    private void addData(){
        FoodMethod foodMethod = new FoodMethod(this);
        foodMethod.deleteAll(Food.class);/*
        for(int i = 10;i<20 ;i++) {
            Food food = new Food();
            food.setCarbohydrate(i/1.0);
            food.setEvaluate(i%3);
            food.setFat(i/1.0);
            food.setHeat(i);
            food.setImageUrl("banana.png");
            food.setName("香蕉"+Integer.toString(i));
            food.setType(i%5);
            food.setProtein(i/1.0);
            foodMethod.insertObject(food);
        }*/
        List<Food> foods = JsonUtil.jsonToList(JsonUtil.getJson(this,"food.json"),Food.class);
        for (Food food :foods) {
            foodMethod.insertObject(food);
        }
        List<Food> data = foodMethod.QueryAll(Food.class);
        String temp="";
        for(Food e: data){
            temp = temp +e.getName();
        }
        Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
        temp = "searchresult:";
        int totalPage = (int)Math.ceil(foodMethod.queryResultPage("香")/10.0);
        temp = temp +" pages:"+Integer.toString(totalPage);
        data = foodMethod.queryByKeyWord("香",0);
        for(Food e: data){
            temp = temp +e.getName();
        }
        Toast.makeText(this,temp,Toast.LENGTH_SHORT).show();
    }
}
