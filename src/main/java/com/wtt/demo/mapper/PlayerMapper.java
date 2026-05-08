package com.wtt.demo.mapper;

import com.wtt.demo.pojo.Player;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PlayerMapper {
    // 查询
    Player selectByName(String name);
    Integer selectTotalCount();
    Player selectByUsernameAndPassword(Player player);
    List<Player> findAll();
    Player selectById(Integer id);
    
    // 增加
    int insert(Player player);
    
    // 删除
    int deleteById(Integer id);
    
    // 修改
    int update(Player player);
}
