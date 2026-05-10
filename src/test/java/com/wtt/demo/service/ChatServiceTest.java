package com.wtt.demo.service;

import com.wtt.demo.pojo.*;
import com.wtt.demo.service.Impl.ChatServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ChatServiceTest {

    private ChatServiceImpl chatService;
    private Player testPlayer;

    @BeforeEach
    void setUp() {
        chatService = new ChatServiceImpl();
        testPlayer = new Player(1, "张三", "13800138000", "zhangsan", "password", 1500);
    }

    @Test
    void queryChat_ShouldReturnErrorMessage_WhenContentIsNull() {
        String result = chatService.queryChat(null, "deepseek");
        assertEquals("问题内容不能为空", result);
    }

    @Test
    void queryChat_ShouldReturnErrorMessage_WhenContentIsEmpty() {
        String result = chatService.queryChat("", "deepseek");
        assertEquals("问题内容不能为空", result);
    }

    @Test
    void queryChat_ShouldReturnErrorMessage_WhenContentIsBlank() {
        String result = chatService.queryChat("   ", "deepseek");
        assertEquals("问题内容不能为空", result);
    }

    @Test
    void analyzePlayerLevel_ShouldReturnErrorMessage_WhenPlayerIsNull() {
        String result = chatService.analyzePlayerLevel(null, "deepseek");
        assertEquals("球员信息不能为空", result);
    }

    @Test
    void suggestPlayingStyle_ShouldReturnErrorMessage_WhenPlayerIsNull() {
        String result = chatService.suggestPlayingStyle(null, null, "deepseek");
        assertEquals("球员信息不能为空", result);
    }

    @Test
    void analyzeWithComprehensiveData_ShouldReturnErrorMessage_WhenPlayerIsNull() {
        Equipment equipment = new Equipment();
        ComprehensivePlayerAnalysis analysis = new ComprehensivePlayerAnalysis();
        String result = chatService.analyzeWithComprehensiveData(null, equipment, analysis, "comprehensive", "deepseek");
        assertEquals("球员信息不能为空", result);
    }
}