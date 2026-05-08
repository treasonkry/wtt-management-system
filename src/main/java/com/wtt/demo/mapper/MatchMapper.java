package com.wtt.demo.mapper;

import com.wtt.demo.pojo.Match;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MatchMapper {
    List<Match> findAll();
    Match selectById(Integer id);
    List<Match> findByPlayerId(Integer playerId);
    int insert(Match match);
    int deleteById(Integer id);
}
