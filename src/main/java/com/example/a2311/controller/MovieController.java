package com.example.a2311.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.entity.Movie;
import com.example.a2311.services.MovieService;
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
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private String nameGetSToken(String username) {
        HashOperations<String, String, String> hashOps = stringRedisTemplate.opsForHash();
        String serverToken = hashOps.get("login:", username);
        return serverToken;
    }

    @PostMapping("/add")
    public String addMovie(String name, String price, String time, String durtime, String clientToken, String username) {

        if(clientToken.equals(nameGetSToken(username))){
            return movieService.addMovie(name, price, time, durtime);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/delete")
    public String deleteMovie(String id, String clientToken, String username) {


        if(clientToken.equals(nameGetSToken(username))){
            return movieService.deleteMovie(id);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @PostMapping("/update")
    public String updateMovie(String id,
                              String name, String price, String time, String durtime, String clientToken, String username) {
        if(clientToken.equals(nameGetSToken(username))){
            return movieService.updateMovie(id, name, price, time, durtime);
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("result", "token无效");
            return jsonObject.toJSONString();
        }

    }

    @GetMapping("/get")
    public List<Movie> getMovie() {
        return movieService.getMovies();
    }

}
