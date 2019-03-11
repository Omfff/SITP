package com.example.a.fd.foodCategory;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a.fd.R;
import com.example.a.fd.model.Food;
import com.example.a.fd.searchHistory.db.FoodMethod;
import com.example.a.fd.searchView.FoodDetailsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FoodCategory extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private RecyclerView recyclerView;
    private int type;
    private int offset = 0;
    private int totalPage = 0;
    private CategoryAdapter categoryAdapter;
    private final String[] categoryName = {"","主食类","肉蛋类","蔬菜类","水果类","奶制品","其他类"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_category);
        offset = 0;
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        totalPage =(int)Math.ceil((new FoodMethod(this.getApplicationContext())).queryCategoryPage(type)/10.0);
        ButterKnife.bind(this);
        initView();
    }
    private void initView(){
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        TextView title = toolbar.findViewById(R.id.food_title);
        title.setText(categoryName[type]);
        recyclerView = findViewById(R.id.food_recyclerview_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getApplicationContext(),LinearLayout.VERTICAL,false));
        categoryAdapter = new CategoryAdapter(new FoodMethod(this).queryCategoryList(type,offset));
        offset++;
        categoryAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                jumpToFoodDetails((Food)adapter.getData().get(position));
            }
        });
        categoryAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override public void onLoadMoreRequested() {
                recyclerView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (offset >= totalPage) {
                            //数据全部加载完毕
                            categoryAdapter.loadMoreEnd();
                        } else {
                            List<Food> foods =new FoodMethod(FoodCategory.this.getApplicationContext()).
                                    queryCategoryList(type,offset);
                            if (foods != null) {
                                //成功获取更多数据
                                categoryAdapter.addData(foods);
                                offset++;
                                categoryAdapter.loadMoreComplete();
                            } else {
                                //获取更多数据失败
                                //isErr = true;
                                Toast.makeText(FoodCategory.this, "load more data failed", Toast.LENGTH_LONG).show();
                                categoryAdapter.loadMoreFail();

                            }
                        }
                    }

                },3);
            }
        }, recyclerView);
        recyclerView.setAdapter(categoryAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getApplicationContext(),DividerItemDecoration.VERTICAL));
    }
    private void jumpToFoodDetails(Food food){
        Intent intent = new Intent(FoodCategory.this,FoodDetailsView.class);
        intent.putExtra("name",food.getName());
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
