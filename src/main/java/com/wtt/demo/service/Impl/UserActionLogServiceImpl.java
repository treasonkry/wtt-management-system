package com.wtt.demo.service.Impl;

import com.wtt.demo.mapper.UserActionLogMapper;
import com.wtt.demo.pojo.UserActionLog;
import com.wtt.demo.service.UserActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActionLogServiceImpl implements UserActionLogService {
    
    @Autowired
    private UserActionLogMapper userActionLogMapper;

    @Override
    public int insert(UserActionLog log) {
        return userActionLogMapper.insert(log);
    }

    @Override
    public List<UserActionLog> findAll() {
        return userActionLogMapper.findAll();
    }

    @Override
    public UserActionLog selectById(Long id) {
        return userActionLogMapper.selectById(id);
    }

    @Override
    public List<UserActionLog> selectByUsername(String username) {
        return userActionLogMapper.selectByUsername(username);
    }

    @Override
    public List<UserActionLog> selectByModule(String module) {
        return userActionLogMapper.selectByModule(module);
    }

    @Override
    public List<UserActionLog> selectByActionType(String actionType) {
        return userActionLogMapper.selectByActionType(actionType);
    }

    @Override
    public int deleteById(Long id) {
        return userActionLogMapper.deleteById(id);
    }

    @Override
    public Integer selectTotalCount() {
        return userActionLogMapper.selectTotalCount();
    }
}
