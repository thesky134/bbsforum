package top.thesky341.bbsforum.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author thesky
 * @date 2020/12/15
 */

@Configuration
public class RedisConfig {


    @Bean
    public RedisManager redisManager(RedisTemplate redisTemplate) {
        RedisManager redisManager = new RedisManager();
        redisManager.setRedisTemplate(redisTemplate);
        return redisManager;
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory) {

        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(factory);

        StringRedisSerializer ss = new StringRedisSerializer();

        redisTemplate.setKeySerializer(ss);

        return redisTemplate;
    }
}

