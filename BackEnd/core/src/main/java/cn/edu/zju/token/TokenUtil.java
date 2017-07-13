package cn.edu.zju.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Random;

/**
 * Created by 51499 on 2017/7/7 0007.
 */
public class TokenUtil {
    public static Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    public static String createToken(String auth, Date date) {
        String str = auth + date.toString() + new Random().nextInt();

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("md5");
            byte[] digest = messageDigest.digest(str.getBytes());
            return Base64.getEncoder().encodeToString(digest);
        } catch (NoSuchAlgorithmException e) {
            logger.error("no md5 algorithm! generate token failed!");
        }
        return null;
    }
}
