package cn.edu.zju.database.entity;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Entity
@Table(name = "bank_account")
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long baid;
    @Column(nullable = false)
    private long uid;
    @Column(nullable = false)
    private String cardId;
    @Column(nullable = false)
    private String cardPwd;

    public BankAccountEntity(long uid, String cardId, String cardPwd) {
        this.uid = uid;
        this.cardId = cardId;
        this.cardPwd = cardPwd;
    }

    public BankAccountEntity() {
    }

    public long getBaid() {
        return baid;
    }

    public void setBaid(long baid) {
        this.baid = baid;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardPwd() {
        return cardPwd;
    }

    public void setCardPwd(String cardPwd) {
        this.cardPwd = cardPwd;
    }
}
