package com.example.a.fd.compareSearch;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a.fd.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.a.fd.CompareSearch
 * @ClassName: NutrCompareAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/23 11:09
 */
public class NutrCompareAdapter extends RecyclerView.Adapter<NutrCompareViewHolder> {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int BOTH = 2;
    public static final int EMPTY = 3;

    private List<String> leftFood;
    private List<String> rightFood;
    private List<String> nutrNameList;
    private int rowNum;
    private int type;

    public NutrCompareAdapter(){
        type = EMPTY;
        rowNum = 0;
        leftFood = new ArrayList<>();
        rightFood = new ArrayList<>();
        nutrNameList = new ArrayList<>();
        addData();
    }

    @NonNull
    @Override
    public NutrCompareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = inflater.inflate(R.layout.compare_nutrition,parent,false);
        return new NutrCompareViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull NutrCompareViewHolder holder, int position) {
        if(type == EMPTY){
            return;
        }else {
            holder.onBindMiddleData(nutrNameList.get(position));
            if (type == LEFT) {
                holder.onBindLeftData(leftFood.get(position));
                holder.deleteRightData();
            } else if (type == RIGHT) {
                holder.deleteLeftData();
                holder.onBindRightData(rightFood.get(position));
            } else {
                holder.onBindLeftData(leftFood.get(position));
                holder.onBindRightData(rightFood.get(position));
            }
        }
    }

    @Override
    public int getItemCount() {
        return rowNum;
    }
    public void addFoodData(List<String> data,int type){
        if(type == LEFT) {
            leftFood.clear();
            leftFood.addAll(data);
        }else {
            if(type == RIGHT){
                rightFood.clear();
                rightFood.addAll(data);
            }
        }
        setStatus(type);
    }
    public void setStatus(int status){
        int preType = type;
        type = status;
        if(type == EMPTY ){
            rowNum = 0;
            leftFood.clear();
            rightFood.clear();
        }else {
            if(preType == EMPTY){
                rowNum = 4;
            }else if(preType == BOTH ){
                if(type == LEFT){
                    leftFood.clear();
                    type = RIGHT;
                }else{
                    rightFood.clear();
                    type = LEFT;
                }
            }else{
                if(type == preType){
                    type = EMPTY;
                    rowNum = 0;
                    leftFood.clear();;
                    rightFood.clear();
                }else{
                    type = BOTH;
                }
            }
        }
        notifyDataSetChanged();
    }
    private void addData(){
        nutrNameList.add("热量");
        nutrNameList.add("蛋白质");
        nutrNameList.add("脂肪");
        nutrNameList.add("碳水化合物");
    }
}
