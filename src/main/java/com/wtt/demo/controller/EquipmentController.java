package com.wtt.demo.controller;

import com.wtt.demo.pojo.Equipment;
import com.wtt.demo.service.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    @Autowired
    private EquipmentService equipmentService;

    @GetMapping("/player/{playerName}")
    public ResponseEntity<Map<String, Object>> getEquipment(@PathVariable String playerName) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Equipment equipment = equipmentService.selectByPlayerName(playerName);
            resp.put("success", true);
            resp.put("data", equipment);
            return ResponseEntity.ok(resp);
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
            return ResponseEntity.ok(resp);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveEquipment(@RequestBody Equipment equipment) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (equipment.getPlayerName() == null || equipment.getPlayerName().trim().isEmpty()) {
                resp.put("success", false);
                resp.put("message", "球员名不能为空");
                return ResponseEntity.ok(resp);
            }
            Equipment existing = equipmentService.selectByPlayerName(equipment.getPlayerName());
            int result;
            if (existing != null) {
                result = equipmentService.update(equipment);
            } else {
                result = equipmentService.insert(equipment);
            }
            if (result > 0) {
                resp.put("success", true);
                resp.put("message", "保存成功");
            } else {
                resp.put("success", false);
                resp.put("message", "保存失败");
            }
        } catch (Exception e) {
            resp.put("success", false);
            resp.put("message", e.getMessage());
        }
        return ResponseEntity.ok(resp);
    }
}
