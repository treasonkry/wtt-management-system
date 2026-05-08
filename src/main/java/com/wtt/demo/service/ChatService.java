package com.wtt.demo.service;

import com.wtt.demo.pojo.*;

public interface ChatService {
    String queryChat(String content, String model);

    String analyzePlayerLevel(Player player);

    String suggestPlayingStyle(Player player, String style);

    String analyzeWithComprehensiveData(Player player, Equipment equipment, ComprehensivePlayerAnalysis analysis, String focus);
}
