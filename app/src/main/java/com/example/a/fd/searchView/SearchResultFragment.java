package com.example.a.fd.searchView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.Util.JsonUtil;
import com.example.a.fd.model.Food;
import com.example.a.fd.recordDiet.DietInfo;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.jdqm.tapelibrary.TapeView;
import com.wefika.horizontalpicker.HorizontalPicker;

import org.w3c.dom.Text;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

import ezy.ui.view.BadgeButton;
import me.shaohui.bottomdialog.BottomDialog;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * @Package: com.example.a.fd.searchView
 * @ClassName: SearchResultFragment
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 16:04
 */
public class SearchResultFragment extends Fragment {
    private RecyclerView recyclerView;
    private ResultAdapter resultAdapter;
    private int type;
    private String keyWord;
    private int offset = 0;
    private int totalPage = 0;
    public static final int SEARCH = 0;
    public static final int COMPARE = 1;
    public static final int RECORD = 2;
    public static final int PAGESIZE = 10;
    public String typeMeal = "";
    public Food addfood;
    public double foodAmount;
    public BadgeButton badgeButton;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        offset = 0;
        type = getArguments().getInt("type");
        this.keyWord =getArguments().getString("keyword");
        totalPage = (int)Math.ceil((new FoodMethod(this.getActivity().getApplicationContext())).queryResultPage(keyWord)/10.0);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_result,container,false);
        recyclerView = view.findViewById(R.id.food_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext(),LinearLayout.VERTICAL,false));
        resultAdapter = new ResultAdapter(new FoodMethod(this.getActivity().getApplicationContext()).
                queryByKeyWord(keyWord,offset),type);
        offset++;
        resultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(type == COMPARE){
                    returnData((Food)adapter.getData().get(position));
                }else if(type == SEARCH){
                    jumpToFoodDetail( (Food)adapter.getData().get(position));
                }else{
                    showBottomDialog((Food)adapter.getData().get(position));
                }
            }
        });
        resultAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (offset >= totalPage) {
                            //数据全部加载完毕
                            resultAdapter.loadMoreEnd();
                        } else {
                            List<Food> foods =new FoodMethod(SearchResultFragment.this.getActivity().getApplicationContext()).
                                    queryByKeyWord(keyWord,offset);
                            if (foods != null) {
                                //成功获取更多数据
                                resultAdapter.addData(foods);
                                offset++;
                                resultAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                //isErr = true;
                                Toast.makeText(SearchResultFragment.this.getActivity(), "load more data failed", Toast.LENGTH_LONG).show();
                                resultAdapter.loadMoreFail();

                            }
                        }
                    }

                },3);
            }
        }, recyclerView);
        recyclerView.setAdapter(resultAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        badgeButton = ((FoodSearchView)this.getActivity()).getBadgeButton();
        return view;
    }
    private void jumpToFoodDetail(Food food){
        Intent intent = new Intent(getActivity(),FoodDetailsView.class);
        intent.putExtra("name",food.getName());
        startActivity(intent);
    }
    private void returnData(Food food){
        Bundle bundle = new Bundle();
        bundle.putSerializable("food",food);
        Intent intent = new Intent();
        intent.putExtra("food",bundle);
        this.getActivity().setResult(Activity.RESULT_OK,intent);
        this.getActivity().finish();
    }

    private void showBottomDialog(Food food){
        BottomDialog bottomDialog = BottomDialog.create(getActivity().getSupportFragmentManager());
        List<String> meal = new ArrayList<>();
        meal.add("早餐");
        meal.add("午餐");
        meal.add("晚餐");
        bottomDialog.setViewListener(new BottomDialog.ViewListener() {
                    @Override
                    public void bindView(View v) {
                        // // You can do any of the necessary the operation with the view
                        TapeView tapeView = v.findViewById(R.id.tape);
                        HorizontalPicker horizontalPicker = v.findViewById(R.id.meal_type);
                        v.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bottomDialog.dismiss();
                            }
                        });

                        ((TextView)v.findViewById(R.id.added_food_name)).setText(food.getName());
                        ((ImageView)v.findViewById(R.id.added_food_img)).setImageBitmap(ImageUtil.getBitmapByName(food.getImageUrl(),v.getContext()));
                        TextView amount =  ((TextView)v.findViewById(R.id.added_food_weight_red));
                        TextView amount1 = (TextView)v.findViewById(R.id.added_food_weight);
                        TextView calorie = (TextView)v.findViewById(R.id.added_food_calorie);
                        calorie.setText(Integer.toString((food.getHeat()))+"千卡");
                        tapeView.setOnValueChangeListener(new TapeView.OnValueChangeListener() {
                            @Override
                            public void onChange(float value) {
                               amount.setText(Integer.toString((int)value));
                               amount1.setText(Integer.toString((int)value)+".0克");
                               calorie.setText(Integer.toString((int)(food.getHeat()*(value/100)))+"千卡");
                            }
                        });
                        v.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                typeMeal =meal.get(horizontalPicker.getSelectedItem());
                                addfood =food;
                                foodAmount =tapeView.getValue();
                                bottomDialog.dismiss();
                                setSelectedData(typeMeal,addfood,foodAmount);
                                Toast.makeText(SearchResultFragment.this.getContext(),"添加了"+addfood.getName(),Toast.LENGTH_SHORT).show();
                            }
                        });


                    }
                });
                bottomDialog.setLayoutRes(R.layout.bottom_dialog)
                .setDimAmount(0.1f)            // Dialog window dim amount(can change window background color）, range：0 to 1，default is : 0.2f
                .setCancelOutside(true)     // click the external area whether is closed, default is : true
                .setTag("BottomDialog")     // setting the DialogFragment tag
                .show();
        Log.d("rrrrrr","bottomDialog");
    }

    private void setSelectedData(String mealType,Food food,double amount){
        DietInfo dietInfo = new DietInfo(DietInfo.TYPE_DATA);
        dietInfo.pinnedHeaderName = mealType;
        dietInfo.food = food;
        dietInfo.amount = amount;
        ((FoodSearchView)this.getActivity()).addSelectedData(dietInfo);
    }


}
