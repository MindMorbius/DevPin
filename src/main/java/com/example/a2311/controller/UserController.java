package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String nameGetSToken(String username) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        String serverToken = hashOps.get("login:", username);
        return serverToken;
    }

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

    @PostMapping("/check")
    public String check(String clientToken, String username) {
        if(clientToken.equals(nameGetSToken(username))){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "已登录");
            return jsonObject.toJSONString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }
    }

}
