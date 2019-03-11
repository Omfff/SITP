package com.example.a.fd.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Package: com.example.a.fd.model
 * @ClassName: SearchKeyWord
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/22 11:22
 */
@Entity
public class SearchKeyword {
    @Id(autoincrement = true)
    Long id;
    @Unique
    String keyword;
    @Generated(hash = 1882422239)
    public SearchKeyword(Long id, String keyword) {
        this.id = id;
        this.keyword = keyword;
    }
    @Generated(hash = 160030699)
    public SearchKeyword() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getKeyword() {
        return this.keyword;
    }
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
