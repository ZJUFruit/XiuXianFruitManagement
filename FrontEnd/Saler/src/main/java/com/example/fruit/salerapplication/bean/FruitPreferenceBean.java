package com.example.fruit.salerapplication.bean;

/**
 * Created by luxuhui on 2017/7/12.
 */

public class FruitPreferenceBean {

    int typeId;
    int minnum;
    int maxnum;
    float minprice;
    float maxprice;
    int salerid;
    int autoorder;

    public FruitPreferenceBean(int typeId, int minnum, int maxnum, float minprice, float maxprice, int salerid, int autoorder) {
        this.typeId = typeId;
        this.minnum = minnum;
        this.maxnum = maxnum;
        this.minprice = minprice;
        this.maxprice = maxprice;
        this.salerid = salerid;
        this.autoorder = autoorder;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public int getMinnum() {
        return minnum;
    }

    public void setMinnum(int minnum) {
        this.minnum = minnum;
    }

    public int getMaxnum() {
        return maxnum;
    }

    public void setMaxnum(int maxnum) {
        this.maxnum = maxnum;
    }

    public float getMinprice() {
        return minprice;
    }

    public void setMinprice(float minprice) {
        this.minprice = minprice;
    }

    public float getMaxprice() {
        return maxprice;
    }

    public void setMaxprice(float maxprice) {
        this.maxprice = maxprice;
    }

    public int getSalerid() {
        return salerid;
    }

    public void setSalerid(int salerid) {
        this.salerid = salerid;
    }

    public int getAutoorder() {
        return autoorder;
    }

    public void setAutoorder(int autoorder) {
        this.autoorder = autoorder;
    }
}
