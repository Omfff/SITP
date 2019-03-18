package com.example.a.fd.network;

import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @Package: com.example.a.fd.network
 * @ClassName: ApiInterface
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/9 23:05
 */
public interface ApiInterface {

    @POST("upload")
    @Multipart
    Single<ResultDTO> postPhoto
            ( @Part MultipartBody.Part file);
}
