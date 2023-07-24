package com.icbc.digitalhuman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String username, @RequestParam("password") String password) {
        System.out.println("有新的登录请求");
        System.out.println("username:" + username + "     password:" + password);
        if (username.equals("12345") && password.equals("123")) {
            System.out.println("密码正确");
            return "success"; // 登录成功
        } else {
            return "failure"; // 登录失败
        }
    }
//    @RequestMapping("/login")
//    public String getUser(String username, String password) {
//        System.out.println("有新的登录请求");
//        System.out.println("username:" + username + "     password:" + password);
//        if (username.equals("12345") && password.equals("123")) {
//            System.out.println("密码正确");
//            return "index.html";
//        }
//        return "error.html";
//    }
//
//    @RequestMapping("/yzm")
//    public String login() {
//        return "login.html";
//    }
}
