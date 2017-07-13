package cn.edu.zju.database.entity;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/7/6 0006.
 */
@Entity
@Table(name = "fruit_type")
public class FruitType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long typeId;
    @Column(nullable = false)
    private String className;
    @Column(nullable = false)
    private String typeName;

    public FruitType() {
    }

    public FruitType(String className, String typeName) {
        this.className = className;
        this.typeName = typeName;
    }

    public long getTypeId() {
        return typeId;
    }

    public void setTypeId(long typeId) {
        this.typeId = typeId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
