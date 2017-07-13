package cn.edu.zju.database.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Entity
@Table(name = "flow_record")
public class FlowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long flowId;
    @Column(nullable = false)
    private long fromAccountId;
    @Column(nullable = false)
    private long toAccountId;
    @Column(nullable = false, columnDefinition = "decimal(11,2)")
    private double money;
    @Column(nullable = false)
    private Date time;

    public FlowRecord(long fromAccountId, long toAccountId, double money, Date time) {
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.money = money;
        this.time = time;
    }

    public FlowRecord() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getFlowId() {
        return flowId;
    }

    public void setFlowId(long flowId) {
        this.flowId = flowId;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }
}
