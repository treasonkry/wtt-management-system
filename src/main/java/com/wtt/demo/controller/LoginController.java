package com.wtt.demo.controller;

import com.wtt.demo.pojo.Admin;
import com.wtt.demo.pojo.Player;
import com.wtt.demo.service.AdminService;
import com.wtt.demo.service.PlayerService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class LoginController {
    private static final String ADMIN_LOGIN_KEY="admin";
    @Autowired
    private AdminService adminService;
    @Autowired
    private PlayerService playerService;
    @PostMapping("/adminlogin")
    public ResponseEntity<Map<String, Object>> adminLogin(Admin admin, HttpSession session){
        Admin loggedIn=adminService.adminLogin(admin);
        if(loggedIn!=null){
            session.setAttribute("admin",loggedIn);
            putAdminInfo(session,loggedIn);
            return ResponseEntity.ok(Map.of("message","登录成功"));

        }else{
            return ResponseEntity.ok(Map.of("message","登录失败，用户名或密码错误"));
        }
    }
    @PostMapping("/playerlogin")
    public ResponseEntity<Map<String, Object>> playerLogin(String username, String password, HttpSession session){
        Player player = new Player();
        player.setUsername(username);
        player.setPassword(password);
        Player loggedIn = playerService.playerLogin(player);
        if(loggedIn != null){
            session.setAttribute("player", loggedIn);
            return ResponseEntity.ok(Map.of("message","登录成功", "player", loggedIn));
        }else{
            return ResponseEntity.ok(Map.of("message","登录失败，用户名或密码错误"));
        }
    }
    @GetMapping("/adminMain")
    public ResponseEntity<Map<String, Object>> adminMain(HttpSession session){
        Map<String,Object> body=new HashMap<>();
        body.put("message","登录成功");
        body.put("admin",session.getAttribute("admin"));
        body.put("playerCount",session.getAttribute("playerCount"));
        body.put("adminCount",session.getAttribute("adminCount"));
        return ResponseEntity.ok(body);
    }
    private void putAdminInfo(HttpSession session, Admin admin){
        session.setAttribute(ADMIN_LOGIN_KEY,admin);
        session.setAttribute("playerCount",adminService.getPlayerTotalCount());
        session.setAttribute("adminCount",adminService.getAdminTotalCount());
    }
}
