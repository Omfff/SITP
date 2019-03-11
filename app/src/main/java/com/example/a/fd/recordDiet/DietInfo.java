package com.example.a.fd.recordDiet;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.a.fd.model.Food;

import java.io.Serializable;

/**
 * @Package: com.example.a.fd.recordDiet
 * @ClassName: DietInfo
 * @Description: java类作用描述
 * @Author: omf
 * @UpdateDate: 2019/2/28 16:01
 */
public class DietInfo implements MultiItemEntity ,Serializable {
    public static final int TYPE_HEADER = 1;
    public static final int TYPE_DATA = 2;

    private int itemType;

    public String pinnedHeaderName;

    public Food food;

    public double amount;

    public DietInfo(int itemType) {
        this.itemType = itemType;
    }

    public DietInfo(int itemType, String pinnedHeaderName) {
        this(itemType);
        this.pinnedHeaderName = pinnedHeaderName;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public boolean check;


}
