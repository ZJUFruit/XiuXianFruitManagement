package cn.edu.zju.database.entity;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Entity
@Table(name = "current_storage")
public class CurrentStorageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long csid;
    @Column(nullable = false)
    private long storeId;
    @Column(nullable = false)
    private long typeId;
    @Column(nullable = false)
    private int num;
    @Column
    private Float price;
    @Column(nullable = false)
    private int volume;
    @Column
    private float rank;
    @Column
    private int rankedNum;
    @Column(nullable = false)
    private boolean isvalid;

    public CurrentStorageEntity(long storeId, long typeId, int num, float price, int volume, float rank, boolean isvalid) {
        this.storeId = storeId;
        this.typeId = typeId;
        this.num = num;
        this.price = price;
        this.volume = volume;
        this.rank = rank;
        this.isvalid = isvalid;
        this.rankedNum = 0;
    }

    public CurrentStorageEntity(long storeId, long typeId) {
        this.storeId = storeId;
        this.typeId = typeId;
        this.isvalid = true;
        this.num = 0;
        this.price = null;
        this.volume = 0;
        this.rank = 0.0f;
        this.rankedNum = 0;
    }

    public CurrentStorageEntity() {
    }

    public int getRankedNum() {
        return rankedNum;
    }

    public void setRankedNum(int rankedNum) {
        this.rankedNum = rankedNum;
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

    public boolean isIsvalid() {
        return isvalid;
    }

    public void setIsvalid(boolean isvalid) {
        this.isvalid = isvalid;
    }
}
