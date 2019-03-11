package com.example.a.fd.searchView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.model.Food;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Package: com.example.a.fd.searchView
 * @ClassName: FoodItemViewHolder
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 16:23
 */
public class FoodItemViewHolder extends BaseViewHolder {
    @BindView(R.id.food_pic)
    CircleImageView foodImg;
    @BindView(R.id.food_name)
    TextView name;
    @BindView(R.id.food_energy)
    TextView enery;
    @BindView(R.id.add_result)
    ImageView add;
    public FoodItemViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void onBindData(Food food){
        name.setText(food.getName());
        enery.setText(Integer.toString(food.getHeat()));
        foodImg.setImageBitmap(ImageUtil.getBitmapByName(food.getImageUrl(),itemView.getContext()));
    }

}
