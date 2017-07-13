package cn.edu.zju.utils;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
public class MoneyUtil {
    public static double toNegtive(double money){
        return -1.0*Math.abs(money);
    }

    public static double toPositive(double money){
        return Math.abs(money);
    }

    public static boolean isMoneyAmountValid(double amount){
        if(amount < 0){
            return false;
        }
        return true;
    }
}
