package com.wtt.demo.controller;

import com.wtt.demo.pojo.UserActionLog;
import com.wtt.demo.service.UserActionLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/log")
public class UserActionLogController {
    
    @Autowired
    private UserActionLogService userActionLogService;
    
    /**
     * 查询所有操作日志
     */
    @GetMapping("/all")
    public ResponseEntity<Map<String, Object>> findAll() {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserActionLog> logs = userActionLogService.findAll();
            response.put("success", true);
            response.put("data", logs);
            response.put("total", logs.size());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据用户名查询日志
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<Map<String, Object>> selectByUsername(@PathVariable String username) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserActionLog> logs = userActionLogService.selectByUsername(username);
            response.put("success", true);
            response.put("data", logs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据模块查询日志
     */
    @GetMapping("/module/{module}")
    public ResponseEntity<Map<String, Object>> selectByModule(@PathVariable String module) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserActionLog> logs = userActionLogService.selectByModule(module);
            response.put("success", true);
            response.put("data", logs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 根据操作类型查询日志
     */
    @GetMapping("/action/{actionType}")
    public ResponseEntity<Map<String, Object>> selectByActionType(@PathVariable String actionType) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<UserActionLog> logs = userActionLogService.selectByActionType(actionType);
            response.put("success", true);
            response.put("data", logs);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 统计日志总数
     */
    @GetMapping("/count")
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> response = new HashMap<>();
        try {
            Integer count = userActionLogService.selectTotalCount();
            response.put("success", true);
            response.put("data", count);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "查询失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
    
    /**
     * 删除日志
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            int result = userActionLogService.deleteById(id);
            if (result > 0) {
                response.put("success", true);
                response.put("message", "删除成功");
                return ResponseEntity.ok(response);
            } else {
                response.put("success", false);
                response.put("message", "删除失败");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "删除失败: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
}
