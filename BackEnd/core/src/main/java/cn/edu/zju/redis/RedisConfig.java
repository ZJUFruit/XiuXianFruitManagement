package cn.edu.zju.redis;

import cn.edu.zju.token.TokenModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Created by 51499 on 2017/7/8 0008.
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, TokenModel> redisTemplate(RedisConnectionFactory factory){
        RedisTemplate<String, TokenModel> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new RedisObjectSerializer());
        return template;
    }

}
