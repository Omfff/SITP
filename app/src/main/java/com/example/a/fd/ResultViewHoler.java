package com.example.a.fd;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.a.fd.Util.ImageUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.http.Url;

/**
 * @Package: com.example.a.fd
 * @ClassName: ResultViewHoler
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/17 23:50
 */

public class ResultViewHoler extends RecyclerView.ViewHolder {

    @BindView(R.id.result_img)
    CircleImageView img;
    @BindView(R.id.result_name)
    TextView resultName;
    @BindView(R.id.result_probability)
    TextView resultProbability;

    public ResultViewHoler(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void onBindImg(String name){
        img.setImageBitmap(ImageUtil.getBitmapByName(name,itemView.getContext()));
    }
    public void onBindName(String name){resultName.setText(name);}
    public void onBindProbability(String probability){resultProbability.setText(probability);}

}
