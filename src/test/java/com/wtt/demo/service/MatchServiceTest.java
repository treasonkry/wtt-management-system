package com.wtt.demo.service;

import com.wtt.demo.mapper.MatchMapper;
import com.wtt.demo.pojo.Match;
import com.wtt.demo.service.Impl.MatchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @Mock
    private MatchMapper matchMapper;

    @InjectMocks
    private MatchServiceImpl matchService;

    private Match testMatch;

    @BeforeEach
    void setUp() {
        testMatch = new Match();
        testMatch.setId(1);
        testMatch.setPlayer1Id(1);
        testMatch.setPlayer2Id(2);
        testMatch.setPlayer1Name("张三");
        testMatch.setPlayer2Name("李四");
        testMatch.setLargeScore("3-2");
        testMatch.setSmallScores("11:9,9:11,11:8,8:11,12:10");
        testMatch.setWinnerId(1);
        testMatch.setWinnerName("张三");
        testMatch.setCreatedAt(LocalDateTime.now());
    }

    @Test
    void findAll_ShouldReturnAllMatches() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchMapper.findAll()).thenReturn(matches);

        List<Match> result = matchService.findAll();

        assertEquals(1, result.size());
        assertEquals("张三", result.get(0).getPlayer1Name());
        verify(matchMapper, times(1)).findAll();
    }

    @Test
    void findAll_ShouldReturnEmptyList_WhenNoMatches() {
        when(matchMapper.findAll()).thenReturn(Arrays.asList());

        List<Match> result = matchService.findAll();

        assertTrue(result.isEmpty());
        verify(matchMapper, times(1)).findAll();
    }

    @Test
    void selectById_ShouldReturnMatch_WhenExists() {
        when(matchMapper.selectById(1)).thenReturn(testMatch);

        Match result = matchService.selectById(1);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("张三", result.getPlayer1Name());
        verify(matchMapper, times(1)).selectById(1);
    }

    @Test
    void selectById_ShouldReturnNull_WhenNotExists() {
        when(matchMapper.selectById(999)).thenReturn(null);

        Match result = matchService.selectById(999);

        assertNull(result);
        verify(matchMapper, times(1)).selectById(999);
    }

    @Test
    void findByPlayerId_ShouldReturnMatches_WhenPlayerExists() {
        List<Match> matches = Arrays.asList(testMatch);
        when(matchMapper.findByPlayerId(1)).thenReturn(matches);

        List<Match> result = matchService.findByPlayerId(1);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getPlayer1Id());
        verify(matchMapper, times(1)).findByPlayerId(1);
    }

    @Test
    void findByPlayerId_ShouldReturnEmptyList_WhenPlayerHasNoMatches() {
        when(matchMapper.findByPlayerId(999)).thenReturn(Arrays.asList());

        List<Match> result = matchService.findByPlayerId(999);

        assertTrue(result.isEmpty());
        verify(matchMapper, times(1)).findByPlayerId(999);
    }

    @Test
    void insert_ShouldSetCreatedAt_WhenNotProvided() {
        Match newMatch = new Match();
        newMatch.setPlayer1Id(1);
        newMatch.setPlayer2Id(2);
        newMatch.setPlayer1Name("张三");
        newMatch.setPlayer2Name("李四");
        newMatch.setLargeScore("3-1");
        newMatch.setWinnerId(1);

        when(matchMapper.insert(any(Match.class))).thenReturn(1);

        int result = matchService.insert(newMatch);

        assertEquals(1, result);
        assertNotNull(newMatch.getCreatedAt());
        verify(matchMapper, times(1)).insert(newMatch);
    }

    @Test
    void insert_ShouldPreserveCreatedAt_WhenProvided() {
        LocalDateTime specificTime = LocalDateTime.of(2024, 1, 1, 12, 0);
        Match newMatch = new Match();
        newMatch.setPlayer1Id(1);
        newMatch.setPlayer2Id(2);
        newMatch.setPlayer1Name("张三");
        newMatch.setPlayer2Name("李四");
        newMatch.setLargeScore("3-1");
        newMatch.setWinnerId(1);
        newMatch.setCreatedAt(specificTime);

        when(matchMapper.insert(any(Match.class))).thenReturn(1);

        int result = matchService.insert(newMatch);

        assertEquals(1, result);
        assertEquals(specificTime, newMatch.getCreatedAt());
        verify(matchMapper, times(1)).insert(newMatch);
    }

    @Test
    void insert_ShouldReturn0_WhenInsertFails() {
        Match newMatch = new Match();
        newMatch.setPlayer1Id(1);
        newMatch.setPlayer2Id(2);

        when(matchMapper.insert(newMatch)).thenReturn(0);

        int result = matchService.insert(newMatch);

        assertEquals(0, result);
        verify(matchMapper, times(1)).insert(newMatch);
    }

    @Test
    void deleteById_ShouldReturn1_WhenSuccess() {
        when(matchMapper.deleteById(1)).thenReturn(1);

        int result = matchService.deleteById(1);

        assertEquals(1, result);
        verify(matchMapper, times(1)).deleteById(1);
    }

    @Test
    void deleteById_ShouldReturn0_WhenMatchNotExists() {
        when(matchMapper.deleteById(999)).thenReturn(0);

        int result = matchService.deleteById(999);

        assertEquals(0, result);
        verify(matchMapper, times(1)).deleteById(999);
    }

    @Test
    void selectById_ShouldReturnMatchWithFullDetails() {
        Match fullMatch = new Match();
        fullMatch.setId(1);
        fullMatch.setPlayer1Id(1);
        fullMatch.setPlayer2Id(2);
        fullMatch.setPlayer1Name("张三");
        fullMatch.setPlayer2Name("李四");
        fullMatch.setLargeScore("3-2");
        fullMatch.setSmallScores("11:9,9:11,11:8,8:11,12:10");
        fullMatch.setWinnerId(1);
        fullMatch.setWinnerName("张三");
        fullMatch.setCreatedAt(LocalDateTime.of(2024, 5, 1, 10, 0));

        when(matchMapper.selectById(1)).thenReturn(fullMatch);

        Match result = matchService.selectById(1);

        assertNotNull(result);
        assertEquals("3-2", result.getLargeScore());
        assertEquals("11:9,9:11,11:8,8:11,12:10", result.getSmallScores());
        assertEquals("张三", result.getWinnerName());
    }
}