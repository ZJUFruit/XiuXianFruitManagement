package com.example.fruit.salerapplication.testhttpapi.bean;

import java.io.Serializable;

/**
 * Created by 51499 on 2017/7/10 0010.
 */
public class FruitTypeBean implements Serializable{
    private static final long serialVersionUID = -1L;
    private long typeId;
    private String className;
    private String typeName;

    public FruitTypeBean() {
    }

    public FruitTypeBean(long typeId, String className, String typeName) {
        this.typeId = typeId;
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

    @Override
    public String toString() {
        return "id:"+typeId+",name:"+typeName+className;
    }
}
