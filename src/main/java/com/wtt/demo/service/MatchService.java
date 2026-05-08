package com.wtt.demo.service;

import com.wtt.demo.pojo.Match;
import java.util.List;

public interface MatchService {
    List<Match> findAll();
    Match selectById(Integer id);
    List<Match> findByPlayerId(Integer playerId);
    int insert(Match match);
    int deleteById(Integer id);
}
