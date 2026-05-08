package com.wtt.demo.controller;

import com.wtt.demo.pojo.*;
import com.wtt.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private EquipmentService equipmentService;

    @PostMapping("/query")
    public ResponseEntity<Map<String, Object>> queryChat(
            @RequestParam String content,
            @RequestParam(required = false) String model) {
        Map<String, Object> response = new HashMap<>();
        try {
            String aiModel = (model != null && !model.trim().isEmpty()) ? model : "deepseek";
            String answer = chatService.queryChat(content, aiModel);
            response.put("success", true);
            response.put("data", answer);
            response.put("model", aiModel);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "问答失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/analyze/{playerId}")
    public ResponseEntity<Map<String, Object>> analyzePlayerLevel(@PathVariable Integer playerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Player player = playerService.selectById(playerId);
            if (player == null) {
                response.put("success", false);
                response.put("message", "球员不存在");
                return ResponseEntity.badRequest().body(response);
            }

            String analysis = chatService.analyzePlayerLevel(player);
            response.put("success", true);
            response.put("data", analysis);
            response.put("player", player);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "分析失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/suggest/{playerId}")
    public ResponseEntity<Map<String, Object>> suggestPlayingStyle(
            @PathVariable Integer playerId,
            @RequestParam(required = false) String style) {
        Map<String, Object> response = new HashMap<>();
        try {
            Player player = playerService.selectById(playerId);
            if (player == null) {
                response.put("success", false);
                response.put("message", "球员不存在");
                return ResponseEntity.badRequest().body(response);
            }

            String suggestion = chatService.suggestPlayingStyle(player, style);
            response.put("success", true);
            response.put("data", suggestion);
            response.put("player", player);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取建议失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @GetMapping("/comprehensive/{playerId}")
    public ResponseEntity<Map<String, Object>> getComprehensiveAnalysis(@PathVariable Integer playerId) {
        Map<String, Object> response = new HashMap<>();
        try {
            Player player = playerService.selectById(playerId);
            if (player == null) {
                response.put("success", false);
                response.put("message", "球员不存在");
                return ResponseEntity.badRequest().body(response);
            }

            ComprehensivePlayerAnalysis analysis = new ComprehensivePlayerAnalysis();
            analysis.setPlayer(player);

            Equipment equipment = equipmentService.selectByPlayerName(player.getName());
            analysis.setEquipment(equipment);

            List<Match> matches = matchService.findByPlayerId(playerId);
            List<ComprehensivePlayerAnalysis.MatchRecord> matchRecords = matches.stream()
                    .map(m -> {
                        ComprehensivePlayerAnalysis.MatchRecord record = new ComprehensivePlayerAnalysis.MatchRecord();
                        record.setId(m.getId());
                        record.setLargeScore(m.getLargeScore());
                        record.setSmallScores(m.getSmallScores());
                        record.setIsWin(m.getWinnerId().equals(playerId));
                        record.setMatchTime(m.getCreatedAt() != null ?
                                m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "");

                        if (m.getPlayer1Id().equals(playerId)) {
                            record.setOpponentId(m.getPlayer2Id());
                            record.setOpponentName(m.getPlayer2Name());
                        } else {
                            record.setOpponentId(m.getPlayer1Id());
                            record.setOpponentName(m.getPlayer1Name());
                        }
                        return record;
                    })
                    .collect(Collectors.toList());
            analysis.setMatches(matchRecords);

            int totalMatches = matchRecords.size();
            int winCount = (int) matchRecords.stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count();
            int loseCount = totalMatches - winCount;
            double winRate = totalMatches > 0 ? (double) winCount / totalMatches * 100 : 0;

            int recentForm = 0;
            int recentCount = Math.min(5, totalMatches);
            if (recentCount > 0) {
                List<ComprehensivePlayerAnalysis.MatchRecord> recentMatches = matchRecords.subList(0, recentCount);
                int recentWins = (int) recentMatches.stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count();
                recentForm = recentWins * 2 - recentCount;
            }

            ComprehensivePlayerAnalysis.MatchStats stats = new ComprehensivePlayerAnalysis.MatchStats();
            stats.setTotalMatches(totalMatches);
            stats.setWinCount(winCount);
            stats.setLoseCount(loseCount);
            stats.setWinRate(winRate);
            stats.setRecentForm(recentForm);
            analysis.setStats(stats);

            Map<Integer, List<ComprehensivePlayerAnalysis.MatchRecord>> byOpponent = matchRecords.stream()
                    .collect(Collectors.groupingBy(ComprehensivePlayerAnalysis.MatchRecord::getOpponentId));

            List<ComprehensivePlayerAnalysis.OpponentAnalysis> opponentAnalyses = new ArrayList<>();
            for (Map.Entry<Integer, List<ComprehensivePlayerAnalysis.MatchRecord>> entry : byOpponent.entrySet()) {
                ComprehensivePlayerAnalysis.OpponentAnalysis opp = new ComprehensivePlayerAnalysis.OpponentAnalysis();
                opp.setOpponentId(entry.getKey());
                opp.setTimesPlayed(entry.getValue().size());
                opp.setWins((int) entry.getValue().stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count());
                opp.setLosses(opp.getTimesPlayed() - opp.getWins());
                if (!entry.getValue().isEmpty()) {
                    opp.setOpponentName(entry.getValue().get(0).getOpponentName());
                }
                opponentAnalyses.add(opp);
            }
            analysis.setOpponentAnalysis(opponentAnalyses);

            response.put("success", true);
            response.put("data", analysis);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "获取综合分析失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    @PostMapping("/analyzeWithData/{playerId}")
    public ResponseEntity<Map<String, Object>> analyzeWithData(
            @PathVariable Integer playerId,
            @RequestParam(required = false) String focus) {
        Map<String, Object> response = new HashMap<>();
        try {
            Player player = playerService.selectById(playerId);
            if (player == null) {
                response.put("success", false);
                response.put("message", "球员不存在");
                return ResponseEntity.badRequest().body(response);
            }

            ComprehensivePlayerAnalysis analysis = new ComprehensivePlayerAnalysis();
            analysis.setPlayer(player);

            Equipment equipment = equipmentService.selectByPlayerName(player.getName());
            analysis.setEquipment(equipment);

            List<Match> matches = matchService.findByPlayerId(playerId);
            List<ComprehensivePlayerAnalysis.MatchRecord> matchRecords = matches.stream()
                    .map(m -> {
                        ComprehensivePlayerAnalysis.MatchRecord record = new ComprehensivePlayerAnalysis.MatchRecord();
                        record.setId(m.getId());
                        record.setLargeScore(m.getLargeScore());
                        record.setSmallScores(m.getSmallScores());
                        record.setIsWin(m.getWinnerId().equals(playerId));
                        record.setMatchTime(m.getCreatedAt() != null ?
                                m.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) : "");

                        if (m.getPlayer1Id().equals(playerId)) {
                            record.setOpponentId(m.getPlayer2Id());
                            record.setOpponentName(m.getPlayer2Name());
                        } else {
                            record.setOpponentId(m.getPlayer1Id());
                            record.setOpponentName(m.getPlayer1Name());
                        }
                        return record;
                    })
                    .collect(Collectors.toList());
            analysis.setMatches(matchRecords);

            int totalMatches = matchRecords.size();
            int winCount = (int) matchRecords.stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count();
            int loseCount = totalMatches - winCount;
            double winRate = totalMatches > 0 ? (double) winCount / totalMatches * 100 : 0;

            int recentForm = 0;
            int recentCount = Math.min(5, totalMatches);
            if (recentCount > 0) {
                List<ComprehensivePlayerAnalysis.MatchRecord> recentMatches = matchRecords.subList(0, recentCount);
                int recentWins = (int) recentMatches.stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count();
                recentForm = recentWins * 2 - recentCount;
            }

            ComprehensivePlayerAnalysis.MatchStats stats = new ComprehensivePlayerAnalysis.MatchStats();
            stats.setTotalMatches(totalMatches);
            stats.setWinCount(winCount);
            stats.setLoseCount(loseCount);
            stats.setWinRate(winRate);
            stats.setRecentForm(recentForm);
            analysis.setStats(stats);

            Map<Integer, List<ComprehensivePlayerAnalysis.MatchRecord>> byOpponent = matchRecords.stream()
                    .collect(Collectors.groupingBy(ComprehensivePlayerAnalysis.MatchRecord::getOpponentId));

            List<ComprehensivePlayerAnalysis.OpponentAnalysis> opponentAnalyses = new ArrayList<>();
            for (Map.Entry<Integer, List<ComprehensivePlayerAnalysis.MatchRecord>> entry : byOpponent.entrySet()) {
                ComprehensivePlayerAnalysis.OpponentAnalysis opp = new ComprehensivePlayerAnalysis.OpponentAnalysis();
                opp.setOpponentId(entry.getKey());
                opp.setTimesPlayed(entry.getValue().size());
                opp.setWins((int) entry.getValue().stream().filter(ComprehensivePlayerAnalysis.MatchRecord::getIsWin).count());
                opp.setLosses(opp.getTimesPlayed() - opp.getWins());
                if (!entry.getValue().isEmpty()) {
                    opp.setOpponentName(entry.getValue().get(0).getOpponentName());
                }
                opponentAnalyses.add(opp);
            }
            analysis.setOpponentAnalysis(opponentAnalyses);

            String aiFocus = (focus != null && !focus.trim().isEmpty()) ? focus : "comprehensive";
            String analysisText = chatService.analyzeWithComprehensiveData(player, equipment, analysis, aiFocus);

            response.put("success", true);
            response.put("data", analysisText);
            response.put("analysis", analysis);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "综合分析失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
