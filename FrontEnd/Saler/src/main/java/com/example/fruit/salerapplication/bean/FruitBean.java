package com.example.fruit.salerapplication.bean;

/**
 * Created by fruit on 2017/7/9.
 */

public class FruitBean {
    private int imgID;
    private String name;
    private float price;
    private int reserve;
    private int typeID;

    private String nameList[] = {"苹果","watermelon"};

    public FruitBean (int imgID, float price, int reserve, int typeID) {
        this.imgID = imgID;
        this.price = price;
        this.typeID = typeID;
        this.reserve = reserve;

        this.name = nameList[typeID];
    }

    public int getImgID () {
        return imgID;
    }
    public String getName () {
        return name;
    }
    public float getPrice () {
        return price;
    }
    public int getReserve () {
        return reserve;
    }
    public int setImgID (int imgID) {
        return this.imgID = imgID;
    }
    public int setTypeID (int typeID) {
        this.name = nameList[typeID];
        return this.typeID = typeID;
    }
    public float setPrice (float price) {
        return this.price = price;
    }
    public int setReserve (int reserve) {
        return this.reserve = reserve;
    }
}
