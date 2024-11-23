package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.entity.Fav;
import com.example.a2311.services.FavService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/fav")
public class FavController {

    @Autowired
    private FavService favService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String nameGetSToken(String username) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        String serverToken = hashOps.get("login:", username);
        return serverToken;
    }

    @PostMapping("/add")
    public String add(String name, String price, String time, String clientToken, String username) {

        if(clientToken.equals(nameGetSToken(username))){
            return favService.add(name, price, time, username);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/delete")
    public String delete(String id, String clientToken, String username) {


        if(clientToken.equals(nameGetSToken(username))){
            return favService.delete(id);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }


    @GetMapping("/get")
    public List<Fav> get(String clientToken, String username) {
        if(clientToken.equals(nameGetSToken(username))){
            return favService.get(username);
        } else {
            return null;
        }

    }

}
