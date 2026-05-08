package com.wtt.demo.service.Impl;

import com.wtt.demo.mapper.MatchMapper;
import com.wtt.demo.pojo.Match;
import com.wtt.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {
    @Autowired
    private MatchMapper matchMapper;

    @Override
    public List<Match> findAll() {
        return matchMapper.findAll();
    }

    @Override
    public Match selectById(Integer id) {
        return matchMapper.selectById(id);
    }

    @Override
    public List<Match> findByPlayerId(Integer playerId) {
        return matchMapper.findByPlayerId(playerId);
    }

    @Override
    public int insert(Match match) {
        if (match.getCreatedAt() == null) {
            match.setCreatedAt(LocalDateTime.now());
        }
        return matchMapper.insert(match);
    }

    @Override
    public int deleteById(Integer id) {
        return matchMapper.deleteById(id);
    }
}
