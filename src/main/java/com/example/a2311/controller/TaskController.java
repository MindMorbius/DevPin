package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.entity.Task;
import com.example.a2311.services.TaskService;
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
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String nameGetSToken(String username) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        String serverToken = hashOps.get("login:", username);
        return serverToken;
    }

    @PostMapping("/add")
    public String add(String time, String problem, String tasks, String username, String clientToken) {

        if(clientToken.equals(nameGetSToken(username))){
            if("".equals(problem)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", "描述不能为空");
                return jsonObject.toJSONString();
            }
            return taskService.add(problem, tasks, time, username);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/update")
    public String update(String id, String time, String problem, String tasks, String username, String clientToken) {

        if(clientToken.equals(nameGetSToken(username))){
            if("".equals(problem)){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("result", "描述不能为空");
                return jsonObject.toJSONString();
            }
            return taskService.update(id, problem, tasks, time, username);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/updatepub")
    public String updatepub(String id, String username, boolean pub, String clientToken) {

        if(clientToken.equals(nameGetSToken(username))){
            return taskService.updatePub(id, username, pub);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/delete")
    public String delete(String id, String clientToken, String username) {

        if(clientToken.equals(nameGetSToken(username))){
            return taskService.delete(id);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }


    @GetMapping("/gets")
    public List<Task> get(String clientToken, String username) {
        if(clientToken.equals(nameGetSToken(username))){
            return taskService.getMeAndPubAll(username);
        } else {
            return taskService.getPubAll();
        }

    }

}
