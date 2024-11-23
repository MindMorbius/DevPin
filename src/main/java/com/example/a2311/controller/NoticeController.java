package com.example.a2311.controller;


import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @PostMapping("/add")
    public String add(String notice) {
        redisTemplate.opsForValue().set("notice", notice);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "发布通知成功");
        return jsonObject.toJSONString();
    }

    @GetMapping("/get")
    public String get() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", redisTemplate.opsForValue().get("notice"));
        return jsonObject.toJSONString();
    }

    @PostMapping("/update")
    public String update(String notice) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "修改通知成功");
        redisTemplate.opsForValue().getAndSet("notice", notice);
        return jsonObject.toJSONString();
    }

    @PostMapping("/delete")
    public String delete() {
        redisTemplate.delete("notice");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除通知成功");
        return jsonObject.toJSONString();
    }


}
