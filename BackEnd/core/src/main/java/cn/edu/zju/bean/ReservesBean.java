package cn.edu.zju.bean;

import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class ReservesBean {
    List<FruitReservesBean> fruits;
    long storeId;

    public ReservesBean() {
    }

    public List<FruitReservesBean> getFruits() {
        return fruits;
    }

    public void setFruits(List<FruitReservesBean> fruits) {
        this.fruits = fruits;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
