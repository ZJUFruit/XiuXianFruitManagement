package cn.edu.zju.bean;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class GoodsBean {
    long   typeId;
    String typeName;
    int    number;

    Float  price;
    //not nessary
    String desc;
    Float  rank;//0.0-5.0

    public GoodsBean(long typeId, String typeName, int number, float price, String desc, float rank) {
        this.typeId = typeId;
        this.typeName = typeName;
        this.number = number;
        this.price = price;
        this.desc = desc;
        this.rank = rank;
    }
    public GoodsBean(){
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Float getRank() {
        return rank;
    }

    public void setRank(Float rank) {
        this.rank = rank;
    }
}
