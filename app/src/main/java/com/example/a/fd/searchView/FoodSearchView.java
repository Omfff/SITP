package com.example.a.fd.searchView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.example.a.fd.CompareSearch;
import com.example.a.fd.R;

import com.example.a.fd.model.SearchKeyword;
import com.example.a.fd.recordDiet.DietInfo;
import com.example.a.fd.searchHistory.db.BaseDaoMethod;
import com.example.a.fd.searchHistory.db.DaoUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import ezy.ui.view.BadgeButton;

public class FoodSearchView extends AppCompatActivity {
    public static final int SEARCH_HISTORY = 0;
    public static final int SEARCH_RESULT = 1;


    private Toolbar toolbar;
    public int type;
    public SearchView searchView;
    private BaseDaoMethod<SearchKeyword> keywordBaseDaoMethod;
    public SearchPresenter searchPresenter;
    public BadgeButton badgeButton;
    public int selectedFood = 0;
    private int fragmentType;
    private List<DietInfo> dietInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_view_activity);
        Intent intent = getIntent();
        type = intent.getIntExtra("type",0);
        keywordBaseDaoMethod = new BaseDaoMethod<>(this.getApplicationContext());
        initView();
        initEvent();
        replaceFragment(new SearchHistoryFragment(),SEARCH_HISTORY);
        searchPresenter = new SearchPresenter(keywordBaseDaoMethod);
    }
    private void initView(){
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        searchView = (SearchView)toolbar.findViewById(R.id.searchView);
        searchView.setIconifiedByDefault(true);
        searchView.setFocusable(true);
        searchView.setIconified(false);
        searchView.requestFocusFromTouch();
        searchView.setQueryHint("请输入食物名称");
        searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        fragmentType = SEARCH_HISTORY;
        if(type == SearchResultFragment.RECORD){
            findViewById(R.id.search_icon).setVisibility(View.GONE);
            badgeButton = findViewById(R.id.finish_button);
            dietInfoList = new ArrayList<>();
            badgeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    returnDietInfo();
                }
            });
        }else{
            findViewById(R.id.finish_button).setVisibility(View.GONE);
        }
    }
    private void initEvent(){
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                if(!s.equals("")) {
                    SearchKeyword searchKeyword = new SearchKeyword();
                    searchKeyword.setKeyword(s);
                    keywordBaseDaoMethod.insertObject(searchKeyword);
                    replaceFragment(setResultFragment(s),SEARCH_RESULT);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(fragmentType != SEARCH_HISTORY && hasFocus == true) {
                    replaceFragment(new SearchHistoryFragment(), SEARCH_HISTORY);
                }
            }
        });
    }
    public void replaceFragment(Fragment fragment,int type){
        if(type == SEARCH_RESULT){
            searchView.setFocusable(false);
            searchView.clearFocus();
        }
        fragmentType = type;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.search_fragment, fragment);
        transaction.commit();
    }
    public Fragment setResultFragment(String keyword){
        Fragment fragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString("keyword",keyword);
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }
    public void addSelectedData(DietInfo dietInfo){
        selectedFood++;
        dietInfoList.add(dietInfo);
    }
    public BadgeButton getBadgeButton(){
        return badgeButton;
    }
    private void returnDietInfo(){
        Bundle bundle = new Bundle();
        if(dietInfoList.size()!=0) {
            bundle.putSerializable("record",(Serializable)dietInfoList);
            Intent intent = new Intent();
            intent.putExtra("record", bundle);
            setResult(Activity.RESULT_OK,intent);
        }
        this.finish();
    }
    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home) {
            setResult(RESULT_CANCELED);
            finish();
        }
            return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPause() {
        Log.d("rrrrrraaa","onPause");
        super.onPause();
    }

    @Override
    protected void onStart() {
        Log.d("rrrrrraaa","onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("rrrrrraaa","onResume");
        super.onResume();
    }
}
