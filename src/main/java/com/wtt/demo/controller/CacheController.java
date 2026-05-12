package com.wtt.demo.controller;

import com.wtt.demo.service.ChatCacheService;
import com.wtt.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cache")
public class CacheController {
    
    @Autowired(required = false)
    private ChatCacheService chatCacheService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/status")
    public Map<String, Object> getCacheStatus() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Long analysisCount = redisUtil.keys("chat:analysis:player:*") != null ? 
                    (long) redisUtil.keys("chat:analysis:player:*").size() : 0L;
            Long styleCount = redisUtil.keys("chat:style:player:*") != null ? 
                    (long) redisUtil.keys("chat:style:player:*").size() : 0L;
            
            result.put("success", true);
            result.put("analysisCacheCount", analysisCount);
            result.put("styleCacheCount", styleCount);
            result.put("totalCacheCount", analysisCount + styleCount);
            result.put("redisConnected", true);
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
            result.put("redisConnected", false);
        }
        
        return result;
    }
    
    @DeleteMapping("/player/{playerId}")
    public Map<String, Object> invalidatePlayerCache(@PathVariable Integer playerId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (chatCacheService != null) {
                chatCacheService.invalidatePlayerCache(playerId);
                result.put("success", true);
                result.put("message", "球员 " + playerId + " 的缓存已清除");
            } else {
                result.put("success", false);
                result.put("message", "缓存服务未启用");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
    
    @DeleteMapping("/all")
    public Map<String, Object> invalidateAllCache() {
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (chatCacheService != null) {
                chatCacheService.invalidateAllCache();
                result.put("success", true);
                result.put("message", "所有 AI 缓存已清除");
            } else {
                result.put("success", false);
                result.put("message", "缓存服务未启用");
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("error", e.getMessage());
        }
        
        return result;
    }
}