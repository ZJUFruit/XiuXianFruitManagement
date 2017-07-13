package cn.edu.zju.utils;

import cn.edu.zju.token.TokenModel;

/**
 * Created by 51499 on 2017/7/9 0009.
 */
public class AuthUtil {
    public static boolean isSaler(TokenModel tokenModel){
        if(tokenModel.getRole().equalsIgnoreCase("saler")){
            return true;
        }
        return false;
    }
    public static boolean isBuyer(TokenModel tokenModel){
        if(tokenModel.getRole().equalsIgnoreCase("buyer")){
            return true;
        }
        return false;
    }
}
