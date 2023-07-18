package com.example.digitalhuman.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserInfo_Login {
    @RequestMapping ("/login")
    public String Get_User(String username,  String password) {
        System.out.println("有新的登录请求");
        System.out.println("username:"+username+"     password:"+password);
        if(username.equals("12345")&&password.equals("123")){
            System.out.println("密码正确");
            return "index.html";
        }
        return "error.html";
    }
    @RequestMapping("/yzm")
    public String Login() {
        return "login.html";
    }
}
