package com.wtt.demo.service.Impl;

import com.wtt.demo.mapper.AdminMapper;
import com.wtt.demo.mapper.PlayerMapper;
import com.wtt.demo.pojo.Admin;
import com.wtt.demo.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;
    @Autowired
    private PlayerMapper playerMapper;
    @Override
    public Admin adminLogin(Admin admin){
        return adminMapper.selectByNameAndPassword(admin);
    }

    @Override
    public Integer getPlayerTotalCount(){
        return playerMapper.selectTotalCount();
    }
    @Override
    public Integer getAdminTotalCount(){
        return adminMapper.selectTotalCount();
    }
}
