package cn.edu.zju.token;

import cn.edu.zju.database.entity.UserEntity;

import java.io.Serializable;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
public class TokenModel implements Serializable{

    private static final long serialVersionUID = -1L;
    private String token;
    private long uid;
    private String username;
    private String role;

    public TokenModel(String token,long uid, String username, String role) {
        this.token = token;
        this.uid = uid;
        this.username = username;
        this.role = role;
    }
    public TokenModel(String token, UserEntity entity){
        this.token = token;
        this.username = entity.getUsername();
        this.role = entity.getRole();
        this.uid = entity.getUid();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
