package com.example.a.fd.compareSearch;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a.fd.R;
import com.example.a.fd.Util.NumUtil;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @Package: com.example.a.fd.compareSearch
 * @ClassName: NutrCompareViewHolder
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/23 11:19
 */
public class NutrCompareViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.left)
    TextView left;
    @BindView(R.id.middle)
    TextView middle;
    @BindView(R.id.right)
    TextView right;
    public NutrCompareViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
    }
    public void onBindLeftData(String arg){
        left.setText(arg);
    }
    public void onBindRightData(String arg){
        right.setText(arg);
    }
    public void onBindMiddleData(String name){
        middle.setText(name);
    }
    public void deleteLeftData(){
        left.setText("");
    }
    public void deleteRightData(){
        right.setText("");
    }





}
