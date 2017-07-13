package cn.edu.zju.bean;

import cn.edu.zju.database.entity.OrderEntity;
import cn.edu.zju.utils.OrderStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class OrderBean {

    String address;
    long storeId;
    long buyerId;
    List<GoodsBean> goods;

    public OrderBean(String address, long storeId, long buyerId, List<GoodsBean> goods) {
        this.address = address;
        this.storeId = storeId;
        this.buyerId = buyerId;
        this.goods = goods;
    }

    public OrderBean() {
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(long buyerId) {
        this.buyerId = buyerId;
    }

    public List<GoodsBean> getGoods() {
        return goods;
    }

    public void setGoods(List<GoodsBean> goods) {
        this.goods = goods;
    }

}
