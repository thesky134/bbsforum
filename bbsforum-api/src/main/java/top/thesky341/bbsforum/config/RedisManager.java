package top.thesky341.bbsforum.config;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author thesky
 * @date 2020/12/15
 * 对Redis操作进行了封装
 */
public class RedisManager {

    /**
     * 默认过期时长，单位：秒
     */
    public final static long DEFAULT_EXPIRE = 60 * 30;
    /**
     * 不设置过期时长
     */
    public final static long NOT_EXPIRE = -1;

    private RedisTemplate redisTemplate;


    public void set(String key, Object value, long expire) {
        try {
            if (expire == NOT_EXPIRE) {
                redisTemplate.opsForValue().set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }

    public <T> T get(String key, Class<T> clazz) {
        ValueOperations<String, T> operations = redisTemplate.opsForValue();
        return operations.get(key);

    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
