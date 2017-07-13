package com.example.fruit.salerapplication.bean;

/**
 * Created by luxuhui on 2017/7/12.
 */

public class FruitStorageBean {
    int fsid;
    int typeid;
    int salerid;
    int orderid;
    String date;

    public FruitStorageBean(int fsid, int typeid, int salerid, int orderid, String date){
        this.fsid = fsid;
        this.typeid = typeid;
        this.salerid = salerid;
        this.orderid = orderid;
        this.date = date;
    }

    public int getFsid() {
        return fsid;
    }

    public void setFsid(int fsid) {
        this.fsid = fsid;
    }

    public int getTypeid() {
        return typeid;
    }

    public void setTypeid(int typeid) {
        this.typeid = typeid;
    }

    public int getSalerid() {
        return salerid;
    }

    public void setSalerid(int salerid) {
        this.salerid = salerid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getOrderid() {

        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
