package cn.edu.zju.database.entity;

import cn.edu.zju.utils.OrderStatus;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
@Entity
@Table(name = "fruit_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long orderId;
    @Column(nullable = false)
    private long salerId;//storeId
    @Column(nullable = false)
    private long buyerId;//buyer user id
    @Column(nullable = false)
    private float price;
    @Column(nullable = false, length = 8192)
    private String goods;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Date odate;
    @Column(nullable = false)
    private OrderStatus status;

    public OrderEntity(){}

    public OrderEntity(long salerId, long buyerId, float price, String goods, String address, Date odate) {
        this.salerId = salerId;
        this.buyerId = buyerId;
        this.price = price;
        this.goods = goods;
        this.address = address;
        this.odate = odate;
        this.status = OrderStatus.NOTACCEPTED;
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

    public String getGoods() {
        return goods;
    }

    public void setGoods(String goods) {
        this.goods = goods;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDate() {
        return odate;
    }

    public void setDate(Date date) {
        this.odate = date;
    }

    public Date getOdate() {
        return odate;
    }

    public void setOdate(Date odate) {
        this.odate = odate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
