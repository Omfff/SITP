package com.example.a.fd.network;

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

    public Single<String> postMoment( MultipartBody.Part file) {
        return api.postMoment(file)
                .map(responseDTO -> {
                    /*responseModel.setStatus(responseDTO.getStatus());
                    responseModel.setError(responseDTO.getError());
                    return responseModel;*/
                    return "";
                });
    }
}
