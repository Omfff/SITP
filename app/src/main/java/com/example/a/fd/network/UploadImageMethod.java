package com.example.a.fd.network;

/**
 * @Package: com.example.a.fd.network
 * @ClassName: UploadImageMethod
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/9 23:14
 */
public interface UploadImageMethod {
    void postSuccess(String a);//ResponseModel responseModel);
    void postError(Throwable e);
}
