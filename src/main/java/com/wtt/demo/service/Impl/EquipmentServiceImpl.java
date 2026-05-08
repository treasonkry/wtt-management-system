package com.wtt.demo.service.Impl;

import com.wtt.demo.mapper.EquipmentMapper;
import com.wtt.demo.pojo.Equipment;
import com.wtt.demo.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EquipmentServiceImpl implements EquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public List<Equipment> findAll() {
        return equipmentMapper.findAll();
    }

    @Override
    public Equipment selectByPlayerName(String playerName) {
        return equipmentMapper.selectByPlayerName(playerName);
    }

    @Override
    public Integer selectTotalCount() {
        return equipmentMapper.selectTotalCount();
    }

    @Override
    public int insert(Equipment equipment) {
        return equipmentMapper.insert(equipment);
    }

    @Override
    public int deleteByPlayerName(String playerName) {
        return equipmentMapper.deleteByPlayerName(playerName);
    }

    @Override
    public int update(Equipment equipment) {
        return equipmentMapper.update(equipment);
    }
}
