package com.wtt.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class RedisUtil {
    
    @Autowired(required = false)
    private RedisTemplate<String, Object> redisTemplate;
    
    public void set(String key, Object value) {
        if (redisTemplate != null) {
            redisTemplate.opsForValue().set(key, value);
        }
    }
    
    public void set(String key, Object value, long timeout, TimeUnit unit) {
        if (redisTemplate != null) {
            redisTemplate.opsForValue().set(key, value, timeout, unit);
        }
    }
    
    public Object get(String key) {
        if (redisTemplate != null) {
            return redisTemplate.opsForValue().get(key);
        }
        return null;
    }
    
    public void delete(String key) {
        if (redisTemplate != null) {
            redisTemplate.delete(key);
        }
    }
    
    public boolean hasKey(String key) {
        if (redisTemplate != null) {
            return Boolean.TRUE.equals(redisTemplate.hasKey(key));
        }
        return false;
    }
    
    public void expire(String key, long timeout, TimeUnit unit) {
        if (redisTemplate != null) {
            redisTemplate.expire(key, timeout, unit);
        }
    }
}
