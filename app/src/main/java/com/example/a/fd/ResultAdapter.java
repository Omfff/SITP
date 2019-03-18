package com.example.a.fd;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import retrofit2.http.Url;

/**
 * @Package: com.example.a.fd
 * @ClassName: ResultAdapter
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/17 23:55
 */

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHoler> {
    List<String> resultImgList;
    List<String> resultNameList;
    List<String> resultProbabilityList;

    public ResultAdapter(List<String> imgList, List<String> nameList, List<String> probabilityList){
        resultImgList = imgList;
        resultNameList = nameList;
        resultProbabilityList = probabilityList;
    }

    @NonNull
    @Override
    public ResultViewHoler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.result_item,parent,false);
        return new ResultViewHoler(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHoler holder, int position) {
        holder.onBindImg(resultImgList.get(position));
        holder.onBindName(resultNameList.get(position));
        holder.onBindProbability(resultProbabilityList.get(position));
    }

    @Override
    public int getItemCount() {
        return resultNameList.size();
    }
}
