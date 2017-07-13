package cn.edu.zju.database.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by 51499 on 2017/7/5 0005.
 */

@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long accountId;
    @Column(nullable = false)
    private Date createTime;
    //card ID
    @Column(nullable = false, unique = true, length = 16)
    private String cardNumber;
    //card password
    @Column(nullable = false)
    private String password;
    // account balance
    @Column(nullable = false, columnDefinition = "decimal(11,2)")
    private double balance;

    public Account(String cardNumber, String password, double balance) {
        this.cardNumber = cardNumber;
        this.password = password;
        this.balance = balance;
        this.createTime = new Date();
    }

    public Account(String cardNumber, String password) {
        this.cardNumber = cardNumber;
        this.password = password;
        this.createTime = new Date();
        this.balance = 0.0;
    }

    public Account() {
    }

    public synchronized void transIn(double cash){
        balance = balance + cash;
    }

    public synchronized void transOut(double cash){
        balance = balance - cash;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
