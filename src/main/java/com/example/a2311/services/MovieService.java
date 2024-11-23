package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.MovieDao;
import com.example.a2311.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieDao movieDao;

    // 新增电影票
    public String addMovie(String name, String price, String time, String durtime) {

        Movie movie = new Movie();
        movie.setName(name);
        movie.setPrice(price);
        movie.setTime(time);
        movie.setDurtime(durtime);
        movieDao.save(movie);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "新增电影票成功");
        return jsonObject.toJSONString();
    }

    // 根据id删除电影票
    public String deleteMovie(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        movieDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }

    // 修改电影票信息
    public String updateMovie(String id, String name, String price, String time, String durtime) {
        Movie movie = new Movie();
        movie.setId(id);
        movie.setName(name);
        movie.setPrice(price);
        movie.setTime(time);
        movie.setDurtime(durtime);
        movieDao.save(movie);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "修改成功");
        return jsonObject.toJSONString();
    }

    // 根据id查询电影票
    public List<Movie> getMovies() {
        return movieDao.findAll();
    }


}
