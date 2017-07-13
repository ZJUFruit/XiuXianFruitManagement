package com.example.fruit.salerapplication.testhttpapi.bean;

/**
 * Created by 51499 on 2017/7/13 0013.
 */
public class StoreReverseBean {
    long csid;
    long storeId;
    long typeId;
    int num;
    Float price;
    int volume;
    float rank;
    int rankedNum;
    boolean isvalid;

    public StoreReverseBean() {
    }

    public StoreReverseBean(long csid, long storeId, long typeId, int num, Float price, int volume, float rank, int rankedNum, boolean isvalid) {
        this.csid = csid;
        this.storeId = storeId;
        this.typeId = typeId;
        this.num = num;
        this.price = price;
        this.volume = volume;
        this.rank = rank;
        this.rankedNum = rankedNum;
        this.isvalid = isvalid;
    }

    public long getCsid() {
        return csid;
    }

    public void setCsid(long csid) {
        this.csid = csid;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }

    public int getRankedNum() {
        return rankedNum;
    }

    public void setRankedNum(int rankedNum) {
        this.rankedNum = rankedNum;
    }

    public boolean isvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }
}
