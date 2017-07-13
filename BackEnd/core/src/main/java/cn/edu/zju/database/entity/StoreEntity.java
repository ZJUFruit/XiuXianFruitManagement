package cn.edu.zju.database.entity;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Entity
@Table(name = "saler_store")
public class StoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long storeId;
    @Column(nullable = false)
    private long uid;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String storename;
    @Column(nullable = false)
    private String chargeman;
    @Column(nullable = false)
    private String phone;

    private String description;

    public StoreEntity(long uid, String address, String storename, String chargeman, String phone, String desc) {
        this.uid = uid;
        this.address = address;
        this.storename = storename;
        this.chargeman = chargeman;
        this.phone = phone;
        this.description = desc;
    }

    public StoreEntity() {
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

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

