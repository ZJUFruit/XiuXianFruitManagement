package com.example.fruit.salerapplication.bean;

import com.example.fruit.salerapplication.commontool.SystemSettingHelper;

/**
 * Created by luxuhui on 2017/7/13.
 */

public class SystemSettingBean {
    String key;
    String value;

    public SystemSettingBean(String key, String value){
        this.key = key;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {

        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
