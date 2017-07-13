package com.example.fruit.salerapplication.testhttpapi.bean;

import java.io.Serializable;

/**
 * Created by 51499 on 2017/7/11 0011.
 */
public class GoodsBean implements Serializable {
    long typeId;
    String typeName;
    int number;

    String desc;
    float price;
    float rank;

    public GoodsBean() {
    }

    public GoodsBean(long typeId, String typeName, int number, String desc, float price, float rank) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.number = number;
        this.desc = desc;
        this.price = price;
        this.rank = rank;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}
