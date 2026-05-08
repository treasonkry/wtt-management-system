package com.wtt.demo.controller;

import com.wtt.demo.annotation.OperationLog;
import com.wtt.demo.pojo.Match;
import com.wtt.demo.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/match")
public class MatchController {
    @Autowired
    private MatchService matchService;

    @GetMapping("/all")
    public Map<String, Object> findAll() {
        Map<String, Object> resp = new HashMap<>();
        try {
            List<Match> matches = matchService.findAll();
            resp.put("success", true);
            resp.put("data", matches);
            resp.put("total", matches.size());
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return resp;
    }

    @GetMapping("/player/{playerId}")
    public Map<String, Object> findByPlayerId(@PathVariable Integer playerId) {
        Map<String, Object> resp = new HashMap<>();
        try {
            List<Match> matches = matchService.findByPlayerId(playerId);
            resp.put("success", true);
            resp.put("data", matches);
            resp.put("total", matches.size());
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return resp;
    }

    @OperationLog(module = "Match", actionType = "INSERT", description = "创建比赛记录")
    @PostMapping("/add")
    public Map<String, Object> addMatch(@RequestBody Match match) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (match.getPlayer1Id() == null || match.getPlayer2Id() == null) {
                resp.put("success", false);
                resp.put("message", "请选择两名球员");
                return resp;
            }
            if (match.getPlayer1Id().equals(match.getPlayer2Id())) {
                resp.put("success", false);
                resp.put("message", "两名球员不能相同");
                return resp;
            }
            if (match.getLargeScore() == null || match.getLargeScore().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "请输入大比分");
                return resp;
            }
            int result = matchService.insert(match);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "比赛记录创建成功");
            } else {
                resp.put("success", false);
                resp.put("message", "创建失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "创建异常: " + e.getMessage());
        }
        return resp;
    }

    @OperationLog(module = "Match", actionType = "DELETE", description = "删除比赛记录")
    @DeleteMapping("/{id}")
    public Map<String, Object> deleteMatch(@PathVariable Integer id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            int result = matchService.deleteById(id);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "删除成功");
            } else {
                resp.put("success", false);
                resp.put("message", "删除失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "删除异常: " + e.getMessage());
        }
        return resp;
    }
}
