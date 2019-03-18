package com.example.a.fd.compareSearch;

import android.support.v7.widget.RecyclerView;

import com.example.a.fd.Util.NumUtil;
import com.example.a.fd.model.Food;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.a.fd.compareSearch
 * @ClassName: ComparePresenter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/24 8:47
 */
public class ComparePresenter {
    private CompareView compareView;
    private RecyclerView recyclerView;
    private NutrCompareAdapter nutrCompareAdapter;
    public ComparePresenter(CompareView compareView){
        this.compareView = compareView;
    }
    public void setData(int type,Food food){

        nutrCompareAdapter.addFoodData(genData(food),type);
    }
    public void deleteData(int type){
        nutrCompareAdapter.setStatus(type);
    }
    public void setRecyclerView(RecyclerView recyclerView,NutrCompareAdapter nutrCompareAdapter){
        this.recyclerView = recyclerView;
        this.nutrCompareAdapter = nutrCompareAdapter;
    }
    private List<String> genData(Food food){
        List<String> data = new ArrayList<>();
        data.add(food.getHeat()+"千卡");
        data.add(NumUtil.setOneDecimal(food.getProtein())+"克");
        data.add(NumUtil.setOneDecimal(food.getFat())+"克");
        data.add(NumUtil.setOneDecimal(food.getCarbohydrate())+"克");
        return data;
    }

}
