package com.example.a.fd.network;

import com.example.a.fd.model.FoodRecognitionModel;

/**
 * @Package: com.example.a.fd.network
 * @ClassName: UploadImageMethod
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/9 23:14
 */
public interface UploadImageMethod {
    void postSuccess(FoodRecognitionModel a);//ResponseModel responseModel);
    void postError(Throwable e);
}
