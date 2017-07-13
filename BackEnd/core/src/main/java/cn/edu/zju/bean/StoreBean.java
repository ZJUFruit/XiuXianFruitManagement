package cn.edu.zju.bean;

import cn.edu.zju.database.entity.StoreEntity;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class StoreBean {
    String address;
    String storename;
    String chargeman;
    String phone;
    String desc;

    public StoreBean(String address, String storename, String chargeman, String phone, String desc) {
        this.address = address;
        this.storename = storename;
        this.chargeman = chargeman;
        this.phone = phone;
        this.desc = desc;
    }

    public StoreBean() {
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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public StoreEntity beanToEntity() {
        return new StoreEntity(-1, address,storename,chargeman,phone,desc);
    }
}
