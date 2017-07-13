package com.example.fruit.salerapplication.testhttpapi.bean;

import java.io.Serializable;

/**
 * Created by 51499 on 2017/7/10 0010.
 */
public class StoreBean implements Serializable {
    private static final long serialVersionUID = -1L;
    long storeId;
    long uid;
    String address;
    String storename;
    String chargeman;
    String phone;
    String description;
    String desc;

    public StoreBean() {
    }

    public StoreBean(long storeId, long uid, String address, String storename, String chargeman, String phone, String description, String desc) {
        this.storeId = storeId;
        this.uid = uid;
        this.address = address;
        this.storename = storename;
        this.chargeman = chargeman;
        this.phone = phone;
        this.description = description;
        this.desc = desc;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getChargeman() {
        return chargeman;
    }

    public void setChargeman(String chargeman) {
        this.chargeman = chargeman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
