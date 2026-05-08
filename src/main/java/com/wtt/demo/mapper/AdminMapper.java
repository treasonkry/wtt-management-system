package com.wtt.demo.mapper;

import com.wtt.demo.pojo.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    Admin selectByNameAndPassword(Admin admin);
    Integer selectTotalCount();
}
