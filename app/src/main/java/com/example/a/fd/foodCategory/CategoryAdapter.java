package com.example.a.fd.foodCategory;

import android.support.v4.content.ContextCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.model.Food;

import java.util.List;

/**
 * @Package: com.example.a.fd.foodCategory
 * @ClassName: CategoryAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/7 22:33
 */
public class CategoryAdapter extends BaseQuickAdapter<Food,BaseViewHolder> {
    public CategoryAdapter(List<Food> data){
        super(R.layout.food_category_item,data);
    }
    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        helper.setText(R.id.food_name,item.getName());
        helper.setText(R.id.food_energy,Integer.toString(item.getHeat()));
        helper.setImageBitmap(R.id.food_pic,ImageUtil.getBitmapByName(item.getImageUrl(),this.mContext));
        switch (item.getEvaluate()){
            case 3:
                helper.setImageDrawable(R.id.type, ContextCompat.getDrawable(mContext,R.drawable.ic_dot_red_24dp));
                break;
            case 2:
                helper.setImageDrawable(R.id.type, ContextCompat.getDrawable(mContext,R.drawable.ic_dot_yellow_24dp));
                break;
            case 1:
                helper.setImageDrawable(R.id.type, ContextCompat.getDrawable(mContext,R.drawable.ic_dot_green_24dp));
                break;
                default:
                    break;
        }
    }
}
