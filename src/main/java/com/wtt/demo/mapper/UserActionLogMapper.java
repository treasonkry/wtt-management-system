package com.wtt.demo.mapper;

import com.wtt.demo.pojo.UserActionLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserActionLogMapper {
    // 插入日志
    int insert(UserActionLog log);
    
    // 查询所有日志
    List<UserActionLog> findAll();
    
    // 根据ID查询
    UserActionLog selectById(Long id);
    
    // 根据用户名查询
    List<UserActionLog> selectByUsername(String username);
    
    // 根据模块查询
    List<UserActionLog> selectByModule(String module);
    
    // 根据操作类型查询
    List<UserActionLog> selectByActionType(String actionType);
    
    // 删除日志
    int deleteById(Long id);
    
    // 统计总数
    Integer selectTotalCount();
}
