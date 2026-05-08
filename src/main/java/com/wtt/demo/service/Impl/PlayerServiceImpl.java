package com.wtt.demo.service.Impl;

import com.wtt.demo.mapper.EquipmentMapper;
import com.wtt.demo.mapper.PlayerMapper;
import com.wtt.demo.pojo.Player;
import com.wtt.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {
    @Autowired
    private PlayerMapper playerMapper;

    @Autowired
    private EquipmentMapper equipmentMapper;

    @Override
    public Player selectByName(String name) {
        return playerMapper.selectByName(name);
    }
    @Override
    public Integer selectTotalCount() {
        return playerMapper.selectTotalCount();
    }
    @Override
    public Player playerLogin(Player player) {
        return playerMapper.selectByUsernameAndPassword(player);
    }
    @Override
    public List<Player> findAll() {
        return playerMapper.findAll();
    }

    @Override
    public Player selectById(Integer id) {
        return playerMapper.selectById(id);
    }

    @Override
    public int insert(Player player) {
        return playerMapper.insert(player);
    }

    @Override
    public int deleteById(Integer id) {
        Player player = playerMapper.selectById(id);
        if (player != null) {
            equipmentMapper.deleteByPlayerName(player.getName());
        }
        return playerMapper.deleteById(id);
    }

    @Override
    public int update(Player player) {
        return playerMapper.update(player);
    }
}