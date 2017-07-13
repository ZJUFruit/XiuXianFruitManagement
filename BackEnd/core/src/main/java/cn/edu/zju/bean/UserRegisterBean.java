package cn.edu.zju.bean;

import cn.edu.zju.database.entity.UserEntity;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
public class UserRegisterBean {

    String username;
    String password;
    String phone;
    String email;
    String code;
    String realname;
    String role;

    public UserRegisterBean(String username, String password, String phone, String email, String code, String realname, String role) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.realname = realname;
        this.role = role;
        this.code = code;
    }

    public UserRegisterBean() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public UserEntity beanToEntity() {
        //TODO: 验证注册用户的字符串合法性
        if(null==username || null==password || null==phone || null==email || null==realname|| role==null)
            return null;
        return new UserEntity(username,password,phone,email,realname,role);
    }
}
