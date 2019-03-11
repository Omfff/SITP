package com.example.a.fd.searchView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.example.a.fd.R;
import com.example.a.fd.Util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Package: com.example.a.fd.compareSearch
 * @ClassName: SearchHistoryViewHolder
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/24 16:51
 */
public class SearchHistoryViewHolder extends BaseViewHolder {
    @BindView(R.id.term_name)
    TextView foodName;
    @BindView(R.id.delete_keyWord)
    ImageView delete;
    public SearchHistoryViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void onBindData(String name){
        foodName.setText(name);
    }
}
