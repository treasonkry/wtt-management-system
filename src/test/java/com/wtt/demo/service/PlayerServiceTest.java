package com.wtt.demo.service;

import com.wtt.demo.mapper.EquipmentMapper;
import com.wtt.demo.mapper.PlayerMapper;
import com.wtt.demo.pojo.Player;
import com.wtt.demo.service.Impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerServiceTest {

    @Mock
    private PlayerMapper playerMapper;

    @Mock
    private EquipmentMapper equipmentMapper;

    @InjectMocks
    private PlayerServiceImpl playerService;

    private Player testPlayer;

    @BeforeEach
    void setUp() {
        testPlayer = new Player(1, "张三", "13800138000", "zhangsan", "password123", 1500);
    }

    @Test
    void selectByName_ShouldReturnPlayer_WhenExists() {
        when(playerMapper.selectByName("张三")).thenReturn(testPlayer);

        Player result = playerService.selectByName("张三");

        assertNotNull(result);
        assertEquals("张三", result.getName());
        assertEquals(1500, result.getPoints());
        verify(playerMapper, times(1)).selectByName("张三");
    }

    @Test
    void selectByName_ShouldReturnNull_WhenNotExists() {
        when(playerMapper.selectByName("不存在")).thenReturn(null);

        Player result = playerService.selectByName("不存在");

        assertNull(result);
        verify(playerMapper, times(1)).selectByName("不存在");
    }

    @Test
    void selectTotalCount_ShouldReturnCount() {
        when(playerMapper.selectTotalCount()).thenReturn(10);

        Integer result = playerService.selectTotalCount();

        assertEquals(10, result);
        verify(playerMapper, times(1)).selectTotalCount();
    }

    @Test
    void playerLogin_ShouldReturnPlayer_WhenCredentialsValid() {
        Player loginPlayer = new Player();
        loginPlayer.setUsername("zhangsan");
        loginPlayer.setPassword("password123");
        when(playerMapper.selectByUsernameAndPassword(loginPlayer)).thenReturn(testPlayer);

        Player result = playerService.playerLogin(loginPlayer);

        assertNotNull(result);
        assertEquals("张三", result.getName());
        verify(playerMapper, times(1)).selectByUsernameAndPassword(loginPlayer);
    }

    @Test
    void playerLogin_ShouldReturnNull_WhenCredentialsInvalid() {
        Player loginPlayer = new Player();
        loginPlayer.setUsername("zhangsan");
        loginPlayer.setPassword("wrongpassword");
        when(playerMapper.selectByUsernameAndPassword(loginPlayer)).thenReturn(null);

        Player result = playerService.playerLogin(loginPlayer);

        assertNull(result);
        verify(playerMapper, times(1)).selectByUsernameAndPassword(loginPlayer);
    }

    @Test
    void findAll_ShouldReturnAllPlayers() {
        List<Player> players = Arrays.asList(testPlayer, new Player(2, "李四", "13900139000", "lisi", "pass", 1200));
        when(playerMapper.findAll()).thenReturn(players);

        List<Player> result = playerService.findAll();

        assertEquals(2, result.size());
        assertEquals("张三", result.get(0).getName());
        assertEquals("李四", result.get(1).getName());
        verify(playerMapper, times(1)).findAll();
    }

    @Test
    void selectById_ShouldReturnPlayer_WhenExists() {
        when(playerMapper.selectById(1)).thenReturn(testPlayer);

        Player result = playerService.selectById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("张三", result.getName());
        verify(playerMapper, times(1)).selectById(1);
    }

    @Test
    void selectById_ShouldReturnNull_WhenNotExists() {
        when(playerMapper.selectById(999)).thenReturn(null);

        Player result = playerService.selectById(999);

        assertNull(result);
        verify(playerMapper, times(1)).selectById(999);
    }

    @Test
    void insert_ShouldReturn1_WhenSuccess() {
        Player newPlayer = new Player(null, "王五", "13700137000", "wangwu", "pass", 1000);
        when(playerMapper.insert(any(Player.class))).thenReturn(1);

        int result = playerService.insert(newPlayer);

        assertEquals(1, result);
        verify(playerMapper, times(1)).insert(newPlayer);
    }

    @Test
    void deleteById_ShouldDeleteEquipmentAndPlayer_WhenPlayerExists() {
        when(playerMapper.selectById(1)).thenReturn(testPlayer);
        when(playerMapper.deleteById(1)).thenReturn(1);

        int result = playerService.deleteById(1);

        assertEquals(1, result);
        verify(equipmentMapper, times(1)).deleteByPlayerName("张三");
        verify(playerMapper, times(1)).deleteById(1);
    }

    @Test
    void deleteById_ShouldReturn0_WhenPlayerNotExists() {
        when(playerMapper.selectById(999)).thenReturn(null);
        when(playerMapper.deleteById(999)).thenReturn(0);

        int result = playerService.deleteById(999);

        assertEquals(0, result);
        verify(equipmentMapper, never()).deleteByPlayerName(any());
        verify(playerMapper, times(1)).deleteById(999);
    }

    @Test
    void update_ShouldReturn1_WhenSuccess() {
        Player updatePlayer = new Player(1, "张三", "13800138000", "zhangsan", "newpassword", 1600);
        when(playerMapper.update(updatePlayer)).thenReturn(1);

        int result = playerService.update(updatePlayer);

        assertEquals(1, result);
        verify(playerMapper, times(1)).update(updatePlayer);
    }

    @Test
    void update_ShouldReturn0_WhenPlayerNotExists() {
        Player updatePlayer = new Player(999, "不存在", "13800138000", "notexist", "pass", 1000);
        when(playerMapper.update(updatePlayer)).thenReturn(0);

        int result = playerService.update(updatePlayer);

        assertEquals(0, result);
        verify(playerMapper, times(1)).update(updatePlayer);
    }
}