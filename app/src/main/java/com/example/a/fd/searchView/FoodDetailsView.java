package com.example.a.fd.searchView;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.model.Food;
import com.example.a.fd.searchHistory.db.FoodMethod;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodDetailsView extends AppCompatActivity {

    @BindView(R.id.food_advice)
    TextView foodAdvice;
    @BindView(R.id.food_name)
    TextView name;
    @BindView(R.id.food_calorie)
    TextView energy;
    @BindView(R.id.calorie_remark)
    TextView calorie;
    @BindView(R.id.carbohydrate_remark)
    TextView carbohydrate;
    @BindView(R.id.fat_remark)
    TextView fat;
    @BindView(R.id.protein_remark)
    TextView protein;
    @BindView(R.id.food_img)
    ImageView img;
    @BindView(R.id.food_evaluation_img)
    ImageView evaluationImg;
    @BindView(R.id.food_evaluation)
    TextView evaluationText;

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_details);
        ButterKnife.bind(this);
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        Food food = (new FoodMethod(this)).queryByName(name);
        ((TextView)toolbar.findViewById(R.id.food_title)).setText(name);
        initData(food);
    }
    private void initData(Food food){
        name.setText(food.getName());
        energy.setText(Integer.toString(food.getHeat()));
        calorie.setText(Integer.toString(food.getHeat())+"千卡");
        carbohydrate.setText(food.getCarbohydrate().toString()+"克");
        fat.setText(food.getFat().toString()+"克");
        protein.setText(food.getProtein().toString()+"克");
        img.setImageBitmap(ImageUtil.getBitmapByName(food.getImageUrl(),this));

        switch (food.getEvaluate()){
            case 3:
                evaluationText.setText("红灯食物");
                evaluationImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_red_24dp));
                foodAdvice.setText("少吃");
                evaluationText.setTextColor(Color.RED);
                break;
            case 2:
                evaluationText.setText("黄灯食物");
                evaluationImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_yellow_24dp));
                foodAdvice.setText("适量");
                evaluationText.setTextColor(Color.YELLOW);
                break;
            case 1:
                evaluationText.setText("绿灯食物");
                evaluationImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_dot_green_24dp));
                foodAdvice.setText("推荐。");
                evaluationText.setTextColor(Color.GREEN);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
