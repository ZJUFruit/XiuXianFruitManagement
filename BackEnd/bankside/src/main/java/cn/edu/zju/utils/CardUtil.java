package cn.edu.zju.utils;

/**
 * Created by 51499 on 2017/7/5 0005.
 */
public class CardUtil {
    public static final int CARDID_LENGTH = 16;

    public static boolean isCardIdValid(String cardId){
        if(cardId==null || cardId.length()!=CARDID_LENGTH){
            return false;
        }
        return true;
    }
}
