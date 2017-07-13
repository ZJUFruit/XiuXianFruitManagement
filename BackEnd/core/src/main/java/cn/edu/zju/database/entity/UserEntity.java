package cn.edu.zju.database.entity;

import javax.persistence.*;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long uid;
    @Column(nullable = false, length = 20)
    private String username;
    @Column(nullable = false, length = 20)
    private String password;
    @Column(nullable = false, length = 20)
    private String phone;
    @Column(nullable = false, length = 255)
    private String email;
    @Column(nullable = false, length = 20)
    private String realname;
    @Column(nullable = false, length = 5)
    private String role;//"buyer" or "saler"

    public UserEntity(String username, String password, String phone, String email, String realname, String role) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.realname = realname;
        this.role = role;
    }

    public UserEntity() {
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
