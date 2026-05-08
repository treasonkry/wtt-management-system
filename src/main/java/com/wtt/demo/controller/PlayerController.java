package com.wtt.demo.controller;

import com.wtt.demo.annotation.OperationLog;
import com.wtt.demo.pojo.Player;
import com.wtt.demo.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @OperationLog(module = "Player", actionType = "QUERY", description = "查询所有球员")
    @GetMapping("/allPlayers")
    public Map<String,Object> findAll(){
        List<Player> players=playerService.findAll();
        Map<String,Object> resp=new HashMap<>();
        resp.put("success",true);
        resp.put("data",players);
        return resp;
    }

    @OperationLog(module = "Player", actionType = "INSERT", description = "添加新球员")
    @PostMapping("/add")
    public Map<String, Object> addPlayer(Player player) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (player.getName() == null || player.getName().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "姓名不能为空");
                return resp;
            }
            if (player.getUsername() == null || player.getUsername().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "用户名不能为空");
                return resp;
            }
            if (player.getPassword() == null || player.getPassword().length() < 6) {
                resp.put("success", false);
                resp.put("message", "密码长度不能少于6位");
                return resp;
            }
            Player existing = playerService.selectByName(player.getName());
            if (existing != null) {
                resp.put("success", false);
                resp.put("message", "该姓名球员已存在");
                return resp;
            }
            if (player.getPoints() == null) {
                player.setPoints(0);
            }
            int result = playerService.insert(player);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "添加球员成功");
            } else {
                resp.put("success", false);
                resp.put("message", "添加球员失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "添加球员异常: " + e.getMessage());
        }
        return resp;
    }

    @OperationLog(module = "Player", actionType = "DELETE", description = "删除球员")
    @DeleteMapping("/delete/{id}")
    public Map<String, Object> deletePlayer(@PathVariable Integer id) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Player player = playerService.selectById(id);
            if (player == null) {
                resp.put("success", false);
                resp.put("message", "球员不存在");
                return resp;
            }
            int result = playerService.deleteById(id);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "删除球员成功");
            } else {
                resp.put("success", false);
                resp.put("message", "删除球员失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "删除球员异常: " + e.getMessage());
        }
        return resp;
    }

    @OperationLog(module = "Player", actionType = "UPDATE", description = "更新球员信息")
    @PutMapping("/update")
    public Map<String, Object> updatePlayer(Player player) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (player.getId() == null) {
                resp.put("success", false);
                resp.put("message", "球员ID不能为空");
                return resp;
            }
            if (player.getName() == null || player.getName().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "姓名不能为空");
                return resp;
            }
            if (player.getUsername() == null || player.getUsername().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "用户名不能为空");
                return resp;
            }
            if (player.getPassword() == null || player.getPassword().length() < 6) {
                resp.put("success", false);
                resp.put("message", "密码长度不能少于6位");
                return resp;
            }
            Player existing = playerService.selectById(player.getId());
            if (existing == null) {
                resp.put("success", false);
                resp.put("message", "球员不存在");
                return resp;
            }
            int result = playerService.update(player);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "更新球员成功");
            } else {
                resp.put("success", false);
                resp.put("message", "更新球员失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "更新球员异常: " + e.getMessage());
        }
        return resp;
    }

    @PostMapping("/register")
    public Map<String, Object> register(Player player) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (player.getName() == null || player.getName().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "姓名不能为空");
                return resp;
            }
            if (player.getUsername() == null || player.getUsername().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "用户名不能为空");
                return resp;
            }
            if (player.getPassword() == null || player.getPassword().length() < 6) {
                resp.put("success", false);
                resp.put("message", "密码长度不能少于6位");
                return resp;
            }
            Player existing = playerService.selectByName(player.getName());
            if (existing != null) {
                resp.put("success", false);
                resp.put("message", "该姓名球员已存在");
                return resp;
            }
            if (player.getPoints() == null) {
                player.setPoints(0);
            }
            int result = playerService.insert(player);
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "注册成功");
            } else {
                resp.put("success", false);
                resp.put("message", "注册失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", "注册异常: " + e.getMessage());
        }
        return resp;
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> getPlayerByUsername(@PathVariable String username) {
        Map<String, Object> resp = new HashMap<>();
        try {
            List<Player> players = playerService.findAll();
            Player found = players.stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst()
                .orElse(null);
            if (found != null) {
                resp.put("success", true);
                resp.put("data", found);
            } else {
                resp.put("success", false);
                resp.put("message", "球员不存在");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
}