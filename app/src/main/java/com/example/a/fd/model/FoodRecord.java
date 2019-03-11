package com.example.a.fd.model;

import com.google.gson.Gson;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @Package: com.example.a.fd.model
 * @ClassName: FoodRecord
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/3/3 19:31
 */
@Entity
public class FoodRecord {
    @Id(autoincrement = true)
    Long id;
    @Property
    Date date;
    @Property
    String type;
    @Property
    Double amount;
    @Property
    String name;
    @Generated(hash = 8920089)
    public FoodRecord(Long id, Date date, String type, Double amount, String name) {
        this.id = id;
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.name = name;
    }
    @Generated(hash = 1519283106)
    public FoodRecord() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public Double getAmount() {
        return this.amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }

   /* public static class FoodConverter implements PropertyConverter<Food,String>{
        @Override
        public Food convertToEntityProperty(String databaseValue) {
            if (databaseValue == null) {
                return null;
            }
             return new Gson().fromJson(databaseValue, Cat.class);
        }

        @Override
        public String convertToDatabaseValue(Food entityProperty) {
            if(entityProperty == null){
                return null;
            }
            return new Gson().toJson(entityProperty);
        }
    }*/
}
