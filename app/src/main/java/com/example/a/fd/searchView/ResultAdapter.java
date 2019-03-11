package com.example.a.fd.searchView;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a.fd.R;
import com.example.a.fd.model.Food;

import java.util.List;

/**
 * @Package: com.example.a.fd.searchView
 * @ClassName: ResultAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 16:21
 */
public class ResultAdapter extends BaseQuickAdapter<Food,FoodItemViewHolder> {
    private int type;
    public ResultAdapter(List<Food> data,int type){
        super(R.layout.food_item,data);
        this.type = type;
    }
    @Override
    protected void convert(FoodItemViewHolder helper, Food item) {
        helper.onBindData(item);
        if(type != SearchResultFragment.COMPARE){
            helper.setGone(R.id.add_result,false);
        }
    }
}
