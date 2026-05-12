package com.wtt.demo.service;

public interface ChatCacheService {
    
    String getPlayerAnalysis(Integer playerId);
    
    void setPlayerAnalysis(Integer playerId, String analysis);
    
    String getPlayingStyleSuggestion(Integer playerId, String style);
    
    void setPlayingStyleSuggestion(Integer playerId, String style, String suggestion);
    
    void invalidatePlayerCache(Integer playerId);
    
    void invalidateAllCache();
}