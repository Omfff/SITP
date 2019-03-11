package com.example.a.fd.recordDiet;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a.fd.R;
import com.example.a.fd.model.Food;
import com.oushangfeng.pinnedsectionitemdecoration.utils.FullSpanUtil;

import java.util.List;

/**
 * @Package: com.example.a.fd.recordDiet
 * @ClassName: DietAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/28 15:57
 */
public class DietAdapter extends BaseMultiItemQuickAdapter<DietInfo,BaseViewHolder> {

    public int breakfastPos = 0 ;
    public int lunchPos = 0;
    public int dinnerPos = 0;
    public View footerView;
    public DietAdapter(List<DietInfo> data) {
        super(data);
        addItemType(DietInfo.TYPE_HEADER, R.layout.record_header);
        addItemType(DietInfo.TYPE_DATA, R.layout.record_food_item);
    }
    public void deleteData(int position){
        String type = getData().get(position).pinnedHeaderName;
        if(type.equals("晚餐")){
            if(dinnerPos -lunchPos == 2 || dinnerPos == 2 ||dinnerPos - breakfastPos == 2){
                remove(position);
                remove(position -1);
                dinnerPos = 0;
            }else{
                remove(position);
                dinnerPos--;
            }
        }else if(type.equals("午餐")){
            if(lunchPos -breakfastPos == 2 || lunchPos == 2 ){
                remove(position);
                remove(position - 1);
                lunchPos = 0;
                if(dinnerPos!=0){
                    dinnerPos-=2;
                }
            }else{
                remove(position);
                lunchPos--;
                if(dinnerPos!=0){
                    dinnerPos--;
                }
            }
        }else{
            if(breakfastPos == 2){
                remove(position);
                remove(position-1);
                breakfastPos = 0;
                if(dinnerPos!=0){
                    dinnerPos-=2;
                }
                if(lunchPos!=0){
                    lunchPos-=2;
                }
            }else{
                remove(position);
                breakfastPos--;
                if(dinnerPos!=0){
                    dinnerPos-=1;
                }
                if(lunchPos!=0){
                    lunchPos-=1;
                }
            }
        }
        if(getItemCount()<=1){
            footerView.setVisibility(View.GONE);
        }else{
            ((TextView)footerView.findViewById(R.id.total_energy)).setText("总摄入："+Integer.toString(calHeat())+"千焦");
        }
    }

    public void insertData(DietInfo data){
        int preCount = getItemCount();
        String type = data.pinnedHeaderName;
        if(type.equals("早餐")){
            if(breakfastPos == 0 ) {
                addData(0, new DietInfo(DietInfo.TYPE_HEADER, "早餐"));
                breakfastPos++;
                if(lunchPos != 0){
                    lunchPos++;
                }
                if(dinnerPos != 0 ){
                    dinnerPos++;
                }
            }
            addData(breakfastPos,data);
            breakfastPos++;
            if(lunchPos != 0){
                lunchPos++;
            }
            if(dinnerPos != 0 ){
                dinnerPos++;
            }
        }else if(type.equals("午餐")){
            if(lunchPos == 0){
                if(breakfastPos == 0){
                    addData(0,new DietInfo(DietInfo.TYPE_HEADER,"午餐"));
                    lunchPos++;
                    if(dinnerPos != 0 ){
                        dinnerPos++;
                    }
                }else{
                    addData(breakfastPos,new DietInfo(DietInfo.TYPE_HEADER,"午餐"));
                    lunchPos = breakfastPos + 1;
                    if(dinnerPos!=0){
                        dinnerPos += 2;
                    }
                }
                addData(lunchPos,data);
                lunchPos++;
            }else{
                addData(lunchPos,data);
                lunchPos++;
                if(dinnerPos!=0){
                    dinnerPos++;
                }
            }
        }else{
            if(dinnerPos == 0){
                if(breakfastPos == 0 && lunchPos == 0){
                    addData(0,new DietInfo(DietInfo.TYPE_HEADER,"晚餐"));
                    dinnerPos++;
                }else {
                    dinnerPos = breakfastPos > lunchPos ? breakfastPos : lunchPos;
                    addData(dinnerPos, new DietInfo(DietInfo.TYPE_HEADER, "晚餐"));
                    dinnerPos++;
                }
                    addData(dinnerPos,data);
                    dinnerPos++;
            }else{
                addData(dinnerPos,data);
                dinnerPos++;
            }
        }
        ((TextView)footerView.findViewById(R.id.total_energy)).setText("总摄入："+Integer.toString(calHeat())+"千焦");
        if(getItemCount() > 1 && preCount <= 1){
            footerView.setVisibility(View.VISIBLE);
        }
    }
    public void insertData(List<DietInfo> dataList){
        for(DietInfo e: dataList){
            insertData(e);
        }
    }


    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        FullSpanUtil.onAttachedToRecyclerView(recyclerView, this, DietInfo.TYPE_HEADER);
    }

    @Override
    public void onViewAttachedToWindow(BaseViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        FullSpanUtil.onViewAttachedToWindow(holder, this, DietInfo.TYPE_HEADER);
    }

    @Override
    protected void convert(BaseViewHolder holder, DietInfo item) {
        switch (holder.getItemViewType()) {
            case DietInfo.TYPE_HEADER:
                holder.setText(R.id.diet_time, item.pinnedHeaderName);
                break;
            case DietInfo.TYPE_DATA:
                Food food = item.food;
                holder.setText(R.id.food_name,food.getName());//需要修改
                holder.setText(R.id.energy,Integer.toString((int)(food.getHeat()*(item.amount/100))));
                holder.setText(R.id.amount,Integer.toString((int)item.amount)+"克");
                holder.addOnClickListener(R.id.delete);
                break;

        }
    }
    private void resetInsertPos(String type){
        if(type.equals("早餐")){
            breakfastPos++;
        }else if(type.equals("午餐")){
            lunchPos++;
        }else{
            dinnerPos++;
        }
    }
    public void setMyFooterView(View view){
        footerView = view;
        if(getItemCount()<=1){
            footerView.setVisibility(View.GONE);
        }
    }
    public void removeAll(){
        breakfastPos = 0;
        lunchPos = 0;
        dinnerPos = 0;
        int size = getItemCount();
        for(int i = 0 ;i < size - 1  ; i++){
            remove(0);
        }
    }
    private int calHeat(){
        int totalHeat = 0;
        if(getData()!=null && getData().size()>0) {
            for (DietInfo diet : getData()) {
                if(diet.getItemType() == DietInfo.TYPE_DATA) {
                    totalHeat += diet.food.getHeat();
                }
            }
        }
        return totalHeat;
    }
}
