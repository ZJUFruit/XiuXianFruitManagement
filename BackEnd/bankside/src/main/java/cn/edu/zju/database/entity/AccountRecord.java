package cn.edu.zju.database.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
@Entity
@Table(name = "account_record")
public class AccountRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long recordId;
    @Column(nullable = false)
    private long accountId;
    @Column(nullable = false)
    private Date time;
    @Column(nullable = false, columnDefinition = "decimal(11,2)")
    private double cashflow;

    public AccountRecord(long accountId, double cashflow, Date time) {
        this.accountId = accountId;
        this.cashflow = cashflow;
        this.time = time;
    }

    public AccountRecord() {
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public long getRecordId() {
        return recordId;
    }

    public void setRecordId(long recordId) {
        this.recordId = recordId;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getCashflow() {
        return cashflow;
    }

    public void setCashflow(double cashflow) {
        this.cashflow = cashflow;
    }
}
