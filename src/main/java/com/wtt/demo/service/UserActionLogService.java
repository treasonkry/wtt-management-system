package com.wtt.demo.service;

import com.wtt.demo.pojo.UserActionLog;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserActionLogService {
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
