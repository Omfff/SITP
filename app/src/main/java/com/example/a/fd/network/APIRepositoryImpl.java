package com.example.a.fd.network;

import com.example.a.fd.model.FoodRecognitionModel;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @Package: com.example.a.fd.network
 * @ClassName: APIRepositoryImpl
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/9 22:52
 */
public class APIRepositoryImpl {
    private final ApiInterface api = NetWorkUtil.makeRetrofit().create(ApiInterface.class);

    public Single<FoodRecognitionModel> postPhoto(MultipartBody.Part file) {
        FoodRecognitionModel foodRecognitionModel = new FoodRecognitionModel();
        return api.postPhoto(file).map(new io.reactivex.functions.Function<ResultDTO,FoodRecognitionModel>(){
            @Override
            public FoodRecognitionModel apply(ResultDTO resultDTO) throws Exception {
                foodRecognitionModel.setResultImgUrl(resultDTO.getResultImgUrl());
                foodRecognitionModel.setResultJson(resultDTO.getResultJson());
                foodRecognitionModel.setStatus(resultDTO.getStatus());
                return foodRecognitionModel;
            }
        });
    }
}
