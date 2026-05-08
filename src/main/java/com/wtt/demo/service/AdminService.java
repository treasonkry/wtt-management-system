package com.wtt.demo.service;

import com.wtt.demo.pojo.Admin;
import org.springframework.stereotype.Service;

@Service
public interface AdminService {
    Admin adminLogin(Admin admin);
    Integer getPlayerTotalCount();
    Integer getAdminTotalCount();
}
