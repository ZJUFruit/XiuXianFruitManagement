package cn.edu.zju.bean;

import java.util.Date;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
public class CashFlowBean {
    private String cardFrom;
    private String cardTo;
    private double amount;
    private Date time;

    public String getCardFrom() {
        return cardFrom;
    }

    public void setCardFrom(String cardFrom) {
        this.cardFrom = cardFrom;
    }

    public String getCardTo() {
        return cardTo;
    }

    public void setCardTo(String cardTo) {
        this.cardTo = cardTo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
