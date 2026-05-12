package com.wtt.demo.service.Impl;

import com.wtt.demo.service.ChatCacheService;
import com.wtt.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ChatCacheServiceImpl implements ChatCacheService {
    
    private static final String PLAYER_ANALYSIS_KEY = "chat:analysis:player:";
    private static final String PLAYING_STYLE_KEY = "chat:style:player:";
    
    private static final long ANALYSIS_CACHE_HOURS = 2;
    private static final long STYLE_CACHE_HOURS = 24;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @Override
    public String getPlayerAnalysis(Integer playerId) {
        if (playerId == null) {
            return null;
        }
        String key = PLAYER_ANALYSIS_KEY + playerId;
        Object cached = redisUtil.get(key);
        return cached != null ? cached.toString() : null;
    }
    
    @Override
    public void setPlayerAnalysis(Integer playerId, String analysis) {
        if (playerId == null || analysis == null) {
            return;
        }
        String key = PLAYER_ANALYSIS_KEY + playerId;
        redisUtil.set(key, analysis, ANALYSIS_CACHE_HOURS, TimeUnit.HOURS);
    }
    
    @Override
    public String getPlayingStyleSuggestion(Integer playerId, String style) {
        if (playerId == null) {
            return null;
        }
        String key = PLAYING_STYLE_KEY + playerId + ":" + (style != null ? style.hashCode() : "default");
        Object cached = redisUtil.get(key);
        return cached != null ? cached.toString() : null;
    }
    
    @Override
    public void setPlayingStyleSuggestion(Integer playerId, String style, String suggestion) {
        if (playerId == null || suggestion == null) {
            return;
        }
        String key = PLAYING_STYLE_KEY + playerId + ":" + (style != null ? style.hashCode() : "default");
        redisUtil.set(key, suggestion, STYLE_CACHE_HOURS, TimeUnit.HOURS);
    }
    
    @Override
    public void invalidatePlayerCache(Integer playerId) {
        if (playerId == null) {
            return;
        }
        String analysisKey = PLAYER_ANALYSIS_KEY + playerId;
        String stylePattern = PLAYING_STYLE_KEY + playerId + ":*";
        redisUtil.delete(analysisKey);
        redisUtil.deleteByPattern(stylePattern);
    }
    
    @Override
    public void invalidateAllCache() {
        redisUtil.deleteByPattern(PLAYER_ANALYSIS_KEY + "*");
        redisUtil.deleteByPattern(PLAYING_STYLE_KEY + "*");
    }
}