package com.wtt.demo.mapper;

import com.wtt.demo.pojo.Equipment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EquipmentMapper {
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
