package cn.edu.zju.database.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/6 0006.
 */
@Entity
@Table(name = "fruit")
public class Fruit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uuid;
    @Column(nullable = false)
    private long typeId;
    @Column(nullable = false)
    private Date birth;
    @Column(nullable = false)
    private String addr;

    private long salerId;
    private long orderId;

    public Fruit() {
    }

    public Fruit(long typeId, Date birth, String addr) {
        this.typeId = typeId;
        this.birth = birth;
        this.addr = addr;
    }

    public long getSalerId() {
        return salerId;
    }

    public void setSalerId(long salerId) {
        this.salerId = salerId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getUuid() {
        return uuid;
    }

    public void setUuid(long uuid) {
        this.uuid = uuid;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
