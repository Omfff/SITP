package com.example.a.fd.compareSearch;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;
import com.example.a.fd.model.Food;
import com.example.a.fd.searchView.FoodSearchView;
import com.example.a.fd.searchView.SearchResultFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CompareView extends AppCompatActivity {

    public static final int CODE = 1;
    @BindView(R.id.left_add)ImageView leftAdd;
    @BindView(R.id.right_add)ImageView rightAdd;
    @BindView(R.id.left_delete)ImageView leftDelete;
    @BindView(R.id.right_delete)ImageView rightDelete;
    @BindView(R.id.nutr_recyclerView)RecyclerView recyclerView;
    private Toolbar toolbar;
    private NutrCompareAdapter nutrCompareAdapter;
    private ComparePresenter comparePresenter;
    private String jumpType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compare_search_activity);
        comparePresenter = new ComparePresenter(this);
        ButterKnife.bind(this);
        initView();
        comparePresenter.setRecyclerView(recyclerView,nutrCompareAdapter);
        jumpType = "";


    }
    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        //toolbar.setNavigationIcon(R.mipmap.ic_launcher_round);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        rightDelete.setVisibility(View.GONE);
        leftDelete.setVisibility(View.GONE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayout.VERTICAL,false));
        nutrCompareAdapter = new NutrCompareAdapter();
        recyclerView.setAdapter(nutrCompareAdapter);
    }

    @OnClick({R.id.left_add,R.id.left_delete,R.id.right_add,R.id.right_delete})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.left_add:
                //comparePresenter.setData(NutrCompareAdapter.LEFT);
                jumpType = "left";
                jumpToSearhView();
                break;
            case R.id.right_add:
                //comparePresenter.setData(NutrCompareAdapter.RIGHT);
                jumpType = "right";
                jumpToSearhView();
                break;
            case R.id.left_delete:
                comparePresenter.deleteData(NutrCompareAdapter.LEFT);
                leftDelete.setVisibility(View.GONE);
                leftAdd.setClickable(true);
                leftAdd.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.add1, null));
                break;
            case R.id.right_delete:
                comparePresenter.deleteData(NutrCompareAdapter.RIGHT);
                rightDelete.setVisibility(View.GONE);
                rightAdd.setClickable(true);
                rightAdd.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.add1, null));
                break;
            default:
                break;
        }
    }

    private void jumpToSearhView(){
        Intent intent = new Intent(CompareView.this,FoodSearchView.class);
        intent.putExtra("type",SearchResultFragment.COMPARE);
        startActivityForResult(intent,CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (resultCode) { //resultCode为回传的标记，在FoodSearchView中回传的是RESULT_OK
            case RESULT_OK:
                Bundle b = data.getBundleExtra("food");
                Food food= (Food)b.getSerializable("food");
                if(jumpType.equals("left")){
                    comparePresenter.setData(NutrCompareAdapter.LEFT,food);
                    leftAdd.setImageBitmap(ImageUtil.getBitmapByName(food.getImageUrl(),this));
                    leftDelete.setVisibility(View.VISIBLE);
                    leftAdd.setClickable(false);
                }else{
                    comparePresenter.setData(NutrCompareAdapter.RIGHT,food);
                    rightAdd.setImageBitmap(ImageUtil.getBitmapByName(food.getImageUrl(),this));
                    rightDelete.setVisibility(View.VISIBLE);
                    rightAdd.setClickable(false);
                }
                break;
            default:
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
