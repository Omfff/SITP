package com.example.a.fd.searchView;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a.fd.R;
import com.example.a.fd.model.SearchKeyword;
import com.example.a.fd.searchHistory.db.BaseDaoMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.a.fd.compareSearch
 * @ClassName: SearchHistoryFragment
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/24 16:25
 */
public class SearchHistoryFragment  extends Fragment {
    private BaseDaoMethod<SearchKeyword> keywordBaseDaoMethod;
    private RecyclerView recyclerView;
    private SearchHistoryAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        keywordBaseDaoMethod = new BaseDaoMethod<>(this.getActivity().getApplicationContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search_fragment,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.search_history_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity().getApplicationContext(),LinearLayout.VERTICAL,false));
        adapter = new SearchHistoryAdapter(new SearchPresenter(new BaseDaoMethod<SearchKeyword>(this.getActivity().getApplicationContext())),getData());
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Toast.makeText(SearchHistoryFragment.this.getActivity(), "onItemClick" + position, Toast.LENGTH_SHORT).show();
                FoodSearchView foodSearchView = (FoodSearchView)SearchHistoryFragment.this.getActivity();
                foodSearchView.replaceFragment(foodSearchView.setResultFragment(((SearchKeyword)adapter.getData().get(position)).getKeyword())
                ,FoodSearchView.SEARCH_RESULT);
            }
        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                keywordBaseDaoMethod.deleteObject((SearchKeyword) adapter.getItem(position));
                adapter.remove(position);
            }
        });
        adapter.addHeaderView(inflater.inflate((R.layout.search_history_header),container,false));
        if(adapter.getItemCount()> 1) {
            View footerView = inflater.inflate(R.layout.search_hisotry_footer, container, false);
            footerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    keywordBaseDaoMethod.deleteAll(SearchKeyword.class);
                    adapter.removeAllFooterView();
                    adapter.removeAll();
                }
            });
            adapter.addFooterView(footerView);
        }
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this.getActivity().getApplicationContext(),DividerItemDecoration.VERTICAL));
        return view;
    }
    private List<SearchKeyword> getData(){
        List<SearchKeyword> data = keywordBaseDaoMethod.QueryAll(SearchKeyword.class);
        List<SearchKeyword> dataList = new ArrayList<>();
        int i =0 ;
        for(int j = data.size() - 1; j >= 0 ; j--){
            dataList.add(data.get(j));
            i++;
            if(i >= SearchHistoryAdapter.DISPLAY_NUM ){
                break;
            }
        }
        return dataList;
    }
}
