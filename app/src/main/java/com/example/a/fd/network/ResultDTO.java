package com.example.a.fd.network;

/**
 * @Package: com.example.a.fd.network
 * @ClassName: ResultDTO
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/14 23:33
 */
public class ResultDTO {

    /**
     * resultImgUrl : http://47.107.167.12:8080/api/image/0.jpg.jpg
     * resultJson : {"rice": "0.984724", "fried okra": "0.853255", "sweet and sour fillet": "0.847097"}
     * status : 200
     */

    private String resultImgUrl;
    private String resultJson;
    private int status;

    public String getResultImgUrl() {
        return resultImgUrl;
    }

    public void setResultImgUrl(String resultImgUrl) {
        this.resultImgUrl = resultImgUrl;
    }

    public String getResultJson() {
        return resultJson;
    }

    public void setResultJson(String resultJson) {
        this.resultJson = resultJson;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
