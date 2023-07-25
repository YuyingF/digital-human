package com.icbc.digitalhuman.controller;

import com.icbc.digitalhuman.entity.User;
import com.icbc.digitalhuman.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        try {
            userService.create(user);
            return ResponseEntity.ok("用户注册成功!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("用户注册失败: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam("username") String username, @RequestParam("password") String password) {
        User user = userService.findByUsername(username);
        if (user != null) {
            if (password.equals(user.getPassword())) {
                return ResponseEntity.ok("登录成功");
            }
        }
        return ResponseEntity.ok("登陆失败");
    }
}
