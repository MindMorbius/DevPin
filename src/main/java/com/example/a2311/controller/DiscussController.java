package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.entity.Discuss;
import com.example.a2311.entity.Task;
import com.example.a2311.services.DiscussService;
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
@RequestMapping("/dis")
public class DiscussController {


    @Autowired
    private DiscussService discussService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String nameGetSToken(String username) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        String serverToken = hashOps.get("login:", username);
        return serverToken;
    }


    /**
     * 生成一个不重复的自增 ID
     * @return 自增 ID
     */
    @PostMapping("/generateId")
    public String generateId(String username, String clientToken) {

        if(clientToken.equals(nameGetSToken(username))){
            // 自增 ID 的 Redis 键，可以根据需求来设置命名
            String redisKey = "unique:id";

            // 使用 Redis INCR 命令生成自增 ID
            Long newId = stringRedisTemplate.opsForValue().increment(redisKey, 1);

            // 返回生成的 ID
            return newId.toString();
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/add")
    public String add(String tid, String time, String content, String username, String clientToken) {

        if(clientToken.equals(nameGetSToken(username))){
            return discussService.add(tid, content, time, username);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }


    @PostMapping("/delete")
    public String delete(String id, String clientToken, String username) {


        if(clientToken.equals(nameGetSToken(username))){
            return discussService.delete(id);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @GetMapping("/gets")
    public List<Discuss> get(String clientToken, String username, String tid) {
        return discussService.getAll(tid);
    }


}
