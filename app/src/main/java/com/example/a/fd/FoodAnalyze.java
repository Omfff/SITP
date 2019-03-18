package com.example.a.fd;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a.fd.recordDiet.DietInfo;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodAnalyze extends AppCompatActivity {

    @BindView(R.id.date)
    TextView mDate;
    @BindView(R.id.back_button)
    ImageView mBackButton;

    @BindView(R.id.diet_score)
    TextView mDietScore;
    @BindView(R.id.score_bar)
    CircularProgressBar mScoreBar;

    @BindView(R.id.analysis_calorie_pie_chart)
    PieChart mCaloriePieChart;
    @BindView(R.id.analysis_nutrition_pie_chart)
    PieChart mNutritionPieChart;

    @BindView(R.id.breakfast_calorie_percent)
    TextView mBreakfastCaloriePercent;
    @BindView(R.id.breakfast_calorie_remark)
    TextView mBreakfastCalorieRemark;
    @BindView(R.id.breakfast_calorie_remark_icon)
    ImageView mBreakfastCalorieRemarkIcon;

    @BindView(R.id.lunch_calorie_percent)
    TextView mLunchCaloriePercent;
    @BindView(R.id.lunch_calorie_remark)
    TextView mLunchCalorieRemark;
    @BindView(R.id.lunch_calorie_remark_icon)
    ImageView mLunchCalorieRemarkIcon;

    @BindView(R.id.dinner_calorie_percent)
    TextView mDinnerCaloriePercent;
    @BindView(R.id.dinner_calorie_remark)
    TextView mDinnerCalorieRemark;
    @BindView(R.id.dinner_calorie_remark_icon)
    ImageView mDinnerCalorieRemarkIcon;

    @BindView(R.id.carbohydrate)
    TextView mCarbohydrate;
    @BindView(R.id.carbohydrate_remark)
    TextView mCarbohydrateRemark;
    @BindView(R.id.carbohydrate_remark_icon)
    ImageView mCarbohydrateRemarkIcon;

    @BindView(R.id.fat)
    TextView mFat;
    @BindView(R.id.fat_remark)
    TextView mFatRemark;
    @BindView(R.id.fat_remark_icon)
    ImageView mFatRemarkIcon;

    @BindView(R.id.protein)
    TextView mProtein;
    @BindView(R.id.protein_remark)
    TextView mProteinRemark;
    @BindView(R.id.protein_remark_icon)
    ImageView mProteinRemarkIcon;

    private float mBreakfastCalorie = 0;
    private float mLunchCalorie = 0;
    private float mDinnerCalorie = 0;
    private float mCarbohydrateWeight = 0;
    private float mFatWeight = 0;
    private float mProteinWeight = 0;
    private float mCalorieAmount;

    private List<DietInfo> mDietInfos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        Bundle bundle=intent.getBundleExtra("record");
        mDietInfos = (List<DietInfo>) bundle.getSerializable("list");

        for(DietInfo diet:mDietInfos){
            if(diet.pinnedHeaderName.equals("早餐")){
                mBreakfastCalorie += diet.food.getHeat()*(diet.amount/100.0);
            }
            if(diet.pinnedHeaderName.equals("午餐")){
                mLunchCalorie += diet.food.getHeat()*(diet.amount/100.0);
            }
            if(diet.pinnedHeaderName.equals("晚餐")){
                mDinnerCalorie += diet.food.getHeat()*(diet.amount/100.0);
            }
            mCarbohydrateWeight += diet.food.getCarbohydrate()*(diet.amount/100.0);
            mFatWeight += diet.food.getFat()*(diet.amount/100.0);
            mProteinWeight += diet.food.getProtein()*(diet.amount/100.0);
        }

        List<PieEntry> calorieEntries = new ArrayList<>();
        List<PieEntry> nutritionEntries = new ArrayList<>();

        mDate.setText(bundle.getString("date").substring(0,10));
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mCalorieAmount = mBreakfastCalorie + mLunchCalorie + mDinnerCalorie;

        int percent = Math.round(mBreakfastCalorie / mCalorieAmount * 100);
        calorieEntries.add(new PieEntry(percent, "早餐"));
        mBreakfastCaloriePercent.setText(Integer.toString(percent) + "%");
        Judge(25, 30, percent, mBreakfastCalorieRemark, mBreakfastCalorieRemarkIcon);

        percent = Math.round(mLunchCalorie / mCalorieAmount * 100);
        calorieEntries.add(new PieEntry(percent, "午餐"));
        mLunchCaloriePercent.setText(Integer.toString(percent) + "%");
        Judge(35, 40, percent, mLunchCalorieRemark, mLunchCalorieRemarkIcon);

        percent = Math.round(mDinnerCalorie / mCalorieAmount * 100);
        calorieEntries.add(new PieEntry(percent, "晚餐"));
        mDinnerCaloriePercent.setText(Integer.toString(percent) + "%");
        Judge(35, 40, percent, mDinnerCalorieRemark, mDinnerCalorieRemarkIcon);

        float amount = mCarbohydrateWeight * 4 + mFatWeight * 8 + mProteinWeight * 4;

        percent = Math.round(mCarbohydrateWeight * 4 / amount * 100);
        nutritionEntries.add(new PieEntry(percent, "碳水化合物"));
        mCarbohydrate.setText(String.format("%.1f",mCarbohydrateWeight) + "克");
        Judge(55, 65, percent, mCarbohydrateRemark, mCarbohydrateRemarkIcon);

        percent = Math.round(mFatWeight * 8 / amount * 100);
        nutritionEntries.add(new PieEntry(percent, "脂肪"));
        mFat.setText(String.format("%.1f",mFatWeight) + "克");
        Judge(20, 25, percent, mFatRemark, mFatRemarkIcon);

        percent = Math.round(mProteinWeight * 4 / amount * 100);
        nutritionEntries.add(new PieEntry(percent, "蛋白质"));
        mCarbohydrate.setText(String.format("%.1f",mProteinWeight) + "克");
        Judge(10, 20, percent, mProteinRemark, mProteinRemarkIcon);

        PieDataSet calorieSet = new PieDataSet(calorieEntries, "三餐热量占比");
        calorieSet.setColors(new int[]{R.color.colorBreakfast, R.color.colorLunch, R.color.colorDinner}, this);
        PieData calorieData = new PieData(calorieSet);
        mCaloriePieChart.setData(calorieData);
        mCaloriePieChart.invalidate();

        PieDataSet nutritionSet = new PieDataSet(nutritionEntries, "三大营养素供能比");
        nutritionSet.setColors(new int[]{R.color.colorBreakfast, R.color.colorLunch, R.color.colorDinner}, this);
        PieData nutritionData = new PieData(nutritionSet);
        mNutritionPieChart.setData(nutritionData);
        mNutritionPieChart.invalidate();

        float score = 40 * (1 - (Math.abs(mCalorieAmount - 1200) + Math.abs(1800 - mCalorieAmount) - 600) / 1500)
                + 10 * (1 - (Math.abs(mBreakfastCalorie / mCalorieAmount - 0.250f) + Math.abs(0.300f - mBreakfastCalorie / mCalorieAmount) - 0.050f) / 0.275f)
                + 10 * (1 - (Math.abs(mLunchCalorie / mCalorieAmount - 0.350f) + Math.abs(0.400f - mLunchCalorie / mCalorieAmount) - 0.050f) / 0.375f)
                + 10 * (1 - (Math.abs(mDinnerCalorie / mCalorieAmount - 0.300f) + Math.abs(0.350f - mDinnerCalorie / mCalorieAmount) - 0.050f) / 0.325f)
                + 10 * (1 - (Math.abs(mCarbohydrateWeight * 4 / amount - 0.550f) + Math.abs(0.650f - mCarbohydrateWeight * 4 / amount) - 0.100f) / 0.600f)
                + 10 * (1 - (Math.abs(mFatWeight * 8 / amount - 0.200f) + Math.abs(0.250f - mFatWeight * 4 / amount) - 0.050f) / 0.225f)
                + 10 * (1 - (Math.abs(mProteinWeight * 4 / amount - 0.100f) + Math.abs(0.200f - mProteinWeight * 4 / amount) - 0.100f) / 0.150f);
        mDietScore.setText(Integer.toString(Math.round(score)));
        mScoreBar.setProgress(score);
    }

    private void Judge(float low, float high, float value, TextView remark, ImageView icon){
        if(value < low){
            remark.setText("偏低");
            icon.setImageResource(R.drawable.ic_arrow_downward_red_24dp);
        }else if(value <= high){
            remark.setText("合适");
            icon.setImageResource(R.drawable.ic_check_black_24dp);
        }else{
            remark.setText("偏高");
            icon.setImageResource(R.drawable.ic_arrow_upward_red_24dp);
        }
    }
}
