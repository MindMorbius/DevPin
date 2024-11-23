package com.example.a2311.dao;

import com.example.a2311.entity.Fav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class FavDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    public void save(Fav fav) {

        mongoTemplate.save(fav);
    }

    public Fav get(Query query) {
        return mongoTemplate.findOne(query, Fav.class);
    }

    public List<Fav> findAll(String username) {
        // 从Redis Set获取对象ID集合
        Set<String> ids = redisTemplate.opsForSet().members("fav:" + username);

        if(!ids.isEmpty()) {
            // 根据ID集合查询MongoDB
            Query query = new Query();
            query.addCriteria(Criteria.where("id").in( ids));
            List<Fav> favs = mongoTemplate.find(query, Fav.class);
            return favs;
        }

        // 缓存不存在,查询MongoDB
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        List<Fav> favs = mongoTemplate.find(query, Fav.class);


        // 查询结果存入Redis Set
        favs.forEach(f -> {
            redisTemplate.opsForSet().add("fav:" + username, f.getId());
        });
        ///redis 过期时间
        int expireTime = 2;
        redisTemplate.expire("fav:" + username, expireTime, TimeUnit.SECONDS);


        return favs;
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Fav.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Fav.class);
    }


}
