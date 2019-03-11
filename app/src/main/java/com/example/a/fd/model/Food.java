package com.example.a.fd.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * @Package: com.example.a.fd.model
 * @ClassName: Food
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/25 16:29
 */
@Entity
public class Food implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id(autoincrement = true)
    Long id;
    int heat;
    Double protein;
    Double fat;
    Double carbohydrate;
    String imageUrl;
    int evaluate;
    int type;
    @Unique
    String name;
    @Generated(hash = 1700845088)
    public Food(Long id, int heat, Double protein, Double fat, Double carbohydrate,
            String imageUrl, int evaluate, int type, String name) {
        this.id = id;
        this.heat = heat;
        this.protein = protein;
        this.fat = fat;
        this.carbohydrate = carbohydrate;
        this.imageUrl = imageUrl;
        this.evaluate = evaluate;
        this.type = type;
        this.name = name;
    }
    @Generated(hash = 866324199)
    public Food() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public int getHeat() {
        return this.heat;
    }
    public void setHeat(int heat) {
        this.heat = heat;
    }
    public Double getProtein() {
        return this.protein;
    }
    public void setProtein(Double protein) {
        this.protein = protein;
    }
    public Double getFat() {
        return this.fat;
    }
    public void setFat(Double fat) {
        this.fat = fat;
    }
    public Double getCarbohydrate() {
        return this.carbohydrate;
    }
    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
    public String getImageUrl() {
        return this.imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public int getEvaluate() {
        return this.evaluate;
    }
    public void setEvaluate(int evaluate) {
        this.evaluate = evaluate;
    }
    public int getType() {
        return this.type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
