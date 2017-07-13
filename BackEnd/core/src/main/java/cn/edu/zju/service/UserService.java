package cn.edu.zju.service;

import cn.edu.zju.database.entity.UserEntity;
import cn.edu.zju.database.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // 判断用户是否存在
    public UserEntity isUserValid(String role, String username, String password) {
        UserEntity user = userRepository.findByUsername(username);
        if(user==null){
            return null;
        }
        if(user.getPassword().equals(password) && user.getRole().equals(role)){
            return user;
        }
        return null;
    }

    public boolean isUserNameExist(String username) {
        if(null != userRepository.findByUsername(username)){
            return true;
        }
        return false;
    }
    public boolean isPhoneExist(String phone){
        if(null != userRepository.findByPhone(phone)){
            return true;
        }
        return false;
    }
    public boolean isEmailExist(String email){
        if(null != userRepository.findByEmail(email)){
            return true;
        }
        return false;
    }

    public void createUser(UserEntity entity) {
        userRepository.save(entity);
    }

    public UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserEntity findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void updateUser(UserEntity entity) {
        userRepository.save(entity);
    }

    public UserEntity findUserByUid(long uid) {
        return userRepository.findOne(uid);
    }
}
