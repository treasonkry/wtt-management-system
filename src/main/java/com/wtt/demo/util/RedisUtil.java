package com.wtt.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisUtil {
    
    private final RedisTemplate<String, Object> redisTemplate;
    
    @Autowired
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
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
    
    public void deleteByPattern(String pattern) {
        if (redisTemplate != null) {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
            }
        }
    }
    
    public Set<String> keys(String pattern) {
        if (redisTemplate != null) {
            return redisTemplate.keys(pattern);
        }
        return null;
    }
}