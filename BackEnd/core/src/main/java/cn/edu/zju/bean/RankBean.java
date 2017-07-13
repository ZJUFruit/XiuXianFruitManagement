package cn.edu.zju.bean;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class RankBean {
    long orderId;
    long typeId;
    float rank;
    String desc;

    public RankBean() {
    }

    public RankBean(long orderId, long typeId, float rank, String desc) {
        this.orderId = orderId;
        this.typeId = typeId;
        this.rank = rank;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public float getRank() {
        return rank;
    }

    public void setRank(float rank) {
        this.rank = rank;
    }
}
