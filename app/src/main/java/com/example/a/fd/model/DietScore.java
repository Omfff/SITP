package com.example.a.fd.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Package: com.example.a.fd.model
 * @ClassName: DietScore
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/4 10:04
 */
@Entity
public class DietScore {
    @Id(autoincrement = true)
    Long id;
    @Property
    int score;
    @Unique
    Date date;
    @Generated(hash = 317800558)
    public DietScore(Long id, int score, Date date) {
        this.id = id;
        this.score = score;
        this.date = date;
    }
    @Generated(hash = 841557252)
    public DietScore() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getScore() {
        return this.score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
}
