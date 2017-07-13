package cn.edu.zju.redis;

import cn.edu.zju.token.TokenModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by 51499 on 2017/7/7 0007.
 */
@Service
public class RedisService {

    Logger logger = LoggerFactory.getLogger(RedisService.class);

    @Resource
    private RedisTemplate<String, TokenModel> redisTemplate;

    // 批量删除对应的value
    public void deleteAll(String... keys){
        for(String key: keys){
            delete(key);
        }
    }

    // 批量删除key
    public void deletePattern(String pattern) {
        Set<String> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0)
            redisTemplate.delete(keys);
    }

    // 删除指定key的value
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    // 判断缓存中是否有对应的value
    public boolean exists(String key) {
        if(key==null||key.length()==0)
            return false;
        return redisTemplate.hasKey(key);
    }

    // 读取缓存
    public TokenModel get(String key) {
        ValueOperations<String, TokenModel> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    // 写入缓存
    public boolean set(String key, TokenModel value) {
        boolean flag = false;
        try {
            ValueOperations<String, TokenModel> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("write cache failed! {}={}",key,value);
        }
        return flag;
    }

    // 写入缓存,设置过期时间，单位：s
    public boolean set(String key, TokenModel value, Long expireTime) {
        boolean flag = false;
        try {
            ValueOperations<String, TokenModel> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("write cache failed! {}={}",key,value);
        }
        return flag;
    }

}
