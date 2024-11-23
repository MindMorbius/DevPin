package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(String username, String password, String email) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", userService.register(username, password, email));
        return jsonObject.toJSONString();
    }

    @PostMapping("/login")
    public String login(String username, String password) {
        return userService.login(username, password);
    }

    @PostMapping("/logout")
    public String logout(String username) {
        return userService.logout(username);
    }

}
