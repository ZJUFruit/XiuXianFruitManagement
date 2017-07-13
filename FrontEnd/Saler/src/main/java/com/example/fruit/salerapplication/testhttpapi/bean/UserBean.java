package com.example.fruit.salerapplication.testhttpapi.bean;

import java.io.Serializable;

/**
 * Created by 51499 on 2017/7/13 0013.
 */
public class UserBean implements Serializable{
    long uid;
    String username;
    String password;
    String phone;
    String email;
    String realname;
    String role;//"buyer" or "saler"

    public UserBean() {
    }

    public UserBean(long uid, String username, String password, String phone, String email, String realname, String role) {
        this.uid = uid;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.realname = realname;
        this.role = role;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
