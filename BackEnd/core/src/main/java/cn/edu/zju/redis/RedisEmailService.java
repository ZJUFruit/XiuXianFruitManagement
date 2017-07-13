package cn.edu.zju.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Created by 51499 on 2017/7/12 0012.
 */
@Service
public class RedisEmailService {
    Logger logger  = LoggerFactory.getLogger(RedisEmailService.class);

//    @Resource
//    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void deleteAll(String... keys){
        for(String key: keys){
            delete(key);
        }
    }

    public void deletePattern(String pattern){
        Set<String> keys = redisTemplate.keys(pattern);
        if(keys.size()>0){
            redisTemplate.delete(keys);
        }
    }

    public void delete(String key){
        if(exists(key)){
            redisTemplate.delete(key);
        }
    }

    public boolean exists(String key) {
        if(key==null||key.length()==0)
            return false;
        return redisTemplate.hasKey(key);
    }

    public String get(String key){
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    public boolean set(String key, String value){
        boolean flag = false;
        try{
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
            operations.set(key,value);
            flag=true;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("write cache failed! {}={}", key, value);
        }
        return flag;
    }

    public boolean set(String key, String value, Long expireTime) {
        boolean flag = false;
        try {
            ValueOperations<String, String> operations = redisTemplate.opsForValue();
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
