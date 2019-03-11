package com.example.a.fd;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;
import com.example.a.fd.Util.JsonUtil;
import com.example.a.fd.compareSearch.CompareView;
import com.example.a.fd.foodCategory.FoodCategory;
import com.example.a.fd.model.Food;
import com.example.a.fd.recordDiet.RecordDiet;
import com.example.a.fd.searchHistory.db.DaoManager;
import com.example.a.fd.searchHistory.db.DaoMaster;
import com.example.a.fd.searchHistory.db.DaoUtils;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.example.a.fd.searchView.FoodSearchView;
import com.example.a.fd.searchView.SearchResultFragment;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomePage extends AppCompatActivity {

    public static final int FOOD_RECORD_NUM =120;
    @BindView(R.id.food_search)
    View mFoodSearch;

    @BindView(R.id.food_analysis)
    ImageView mFoodAnalysis;
    @BindView(R.id.food_compare)
    ImageView mFoodCompare;
    @BindView(R.id.food_scan)
    ImageView mFoodScan;

    @BindView(R.id.staple_food)
    ImageView mStapleFood;
    @BindView(R.id.meat)
    ImageView mMeat;
    @BindView(R.id.vegetable)
    ImageView mVegetable;
    @BindView(R.id.fruit)
    ImageView mFruit;
    @BindView(R.id.milk)
    ImageView mMilk;
    @BindView(R.id.other_kinds)
    ImageView mOtherKinds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(android.R.id.content).setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
        DaoUtils.init(this.getApplicationContext());
        DaoUtils.getDaoManagerInstance();
        if((new FoodMethod(this)).QueryAll(Food.class).size()!=FOOD_RECORD_NUM){
            (new FoodMethod(this)).insertMultObject(
                    JsonUtil.jsonToList(JsonUtil.getJson(this,"food.json"),Food.class));
        }
        setContentView(R.layout.home_page);
        ButterKnife.bind(this);

        mFoodSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToSearchView();
            }
        });


        mFoodAnalysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToRecordView();
            }
        });

        mFoodCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToCompareView();
            }
        });

        mFoodScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodScan();
            }
        });

        mStapleFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(1);
            }
        });

        mMeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(2);
            }
        });

        mVegetable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(3);
            }
        });

        mFruit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(4);
            }
        });

        mMilk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(5);
            }
        });

        mOtherKinds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpToFoodCategory(6);
            }
        });
    }
    private void jumpToSearchView(){
        Intent intent = new Intent(HomePage.this,FoodSearchView.class);
        intent.putExtra("type",SearchResultFragment.SEARCH);
        startActivity(intent);
    }
    private void jumpToCompareView(){
        Intent intent = new Intent(HomePage.this,CompareView.class);
        startActivity(intent);
    }
    private void jumpToRecordView(){
        Intent intent = new Intent(HomePage.this,RecordDiet.class);
        startActivity(intent);
    }
    private void jumpToFoodCategory(int type){
        Intent intent = new Intent(HomePage.this,FoodCategory.class);
        intent.putExtra("type",type);
        startActivity(intent);
    }
    private void jumpToFoodScan(){
        Intent intent = new Intent(HomePage.this,FoodScan.class);
        startActivity(intent);
    }
}
