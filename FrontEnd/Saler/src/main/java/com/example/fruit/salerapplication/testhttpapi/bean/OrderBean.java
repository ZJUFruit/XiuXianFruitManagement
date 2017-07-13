package com.example.fruit.salerapplication.testhttpapi.bean;

import com.example.fruit.salerapplication.testhttpapi.bean.GoodsBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/11 0011.
 */
public class OrderBean implements Serializable {
    private static final long serialVersionUID = -1L;
    long orderId;
    long salerId;
    String storeName;
    long buyerId;
    String buyerName;
    float price;
    ArrayList<GoodsBean> goods;
    String address;
    String status;
    Date odate;
    Date date;

    public OrderBean() {
    }

    public OrderBean(long orderId, long salerId, String storeName, long buyerId,String buyerName, float price, ArrayList<GoodsBean> goods, String address, Date odate, String status, Date date) {
        this.orderId = orderId;
        this.salerId = salerId;
        this.buyerId = buyerId;
        this.price = price;
        this.goods = goods;
        this.address = address;
        this.odate = odate;
        this.status = status;
        this.date = date;
        this.buyerName = buyerName;
        this.storeName = storeName;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getSalerId() {
        return salerId;
    }

    public void setSalerId(long salerId) {
        this.salerId = salerId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public ArrayList<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(ArrayList<GoodsBean> goods) {
        this.goods = goods;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
