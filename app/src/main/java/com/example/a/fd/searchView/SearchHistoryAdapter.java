package com.example.a.fd.searchView;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.a.fd.R;
import com.example.a.fd.model.SearchKeyword;

import java.util.ArrayList;
import java.util.List;

/**
 * @Package: com.example.a.fd.compareSearch
 * @ClassName: SearchHistoryAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/24 16:50
 */
public class SearchHistoryAdapter  extends BaseQuickAdapter<SearchKeyword, SearchHistoryViewHolder> {
    public static final int DISPLAY_NUM = 50;
    private List<SearchKeyword> historyList;
    private SearchPresenter searchPresenter;
    public SearchHistoryAdapter(SearchPresenter searchPresenter,List<SearchKeyword> data){
        super(R.layout.search_history_item,data);
        this.searchPresenter = searchPresenter;
    }
    @Override
    protected void convert(SearchHistoryViewHolder helper, SearchKeyword item) {
        helper.onBindData(item.getKeyword());
        helper.addOnClickListener(R.id.delete_keyWord);
    }

    public void removeAll(){
        int size = getItemCount();
        for(int i = 0 ;i < size - 1; i++){
            remove(0);
        }
    }
   /* @NonNull
    @Override
    public SearchHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView;
        itemView = inflater.inflate(R.layout.search_history_item,parent,false);
        return new SearchHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchHistoryViewHolder holder, final int position) {
        holder.onBindData(historyList.get(position).getKeyword());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                historyList.remove(position);
                searchPresenter.deleteOneKeyWord(historyList.get(position));
                notifyItemRemoved(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }
    public void resetData(List<SearchKeyword> data){
        historyList.clear();
        historyList.addAll(data);
    }*/
}
