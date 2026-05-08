package com.wtt.demo.service;

import com.wtt.demo.pojo.Equipment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EquipmentService {
    // 查询
    List<Equipment> findAll();
    Equipment selectByPlayerName(String playerName);
    Integer selectTotalCount();
    
    // 增加
    int insert(Equipment equipment);
    
    // 删除
    int deleteByPlayerName(String playerName);
    
    // 修改
    int update(Equipment equipment);
}
