package com.wtt.demo.service;

import com.wtt.demo.pojo.Player;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PlayerService {
    // 查询
    Player selectByName(String name);
    Integer selectTotalCount();
    Player playerLogin(Player player);
    List<Player> findAll();
    Player selectById(Integer id);
    
    // 增加
    int insert(Player player);
    
    // 删除
    int deleteById(Integer id);
    
    // 修改
    int update(Player player);
}
