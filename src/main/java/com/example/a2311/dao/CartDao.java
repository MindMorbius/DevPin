package com.example.a2311.dao;

import com.example.a2311.entity.Cart;
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
public class CartDao {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Resource
    private RedisTemplate redisTemplate;

    public void save(Cart cart) {
        mongoTemplate.save(cart);
    }

    public Cart get(Query query) {
        return mongoTemplate.findOne(query, Cart.class);
    }

    public List<Cart> findAll(String username) {
        // 从Sorted Set获取cart id
        Set<String> ids = redisTemplate.opsForZSet()
                .range("cart:" + username, 0, -1);

        if(!ids.isEmpty()) {
            // 根据id列表查询MongoDB
            Query idQuery = new Query();
            idQuery.addCriteria(Criteria.where("id").in(ids));

            List<Cart> carts = mongoTemplate.find(idQuery, Cart.class);
            return carts;
        }

        // 缓存不存在,查询MongoDB
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is(username));

        List<Cart> carts = mongoTemplate.find(query, Cart.class);

        // 写入Sorted Set
        carts.forEach(c -> {
            redisTemplate.opsForZSet()
                    .add("cart:" + username, c.getId(), c.getCreateTime());
        });

        ///redis 过期时间
        int expireTime = 2;
        redisTemplate.expire("cart:" + username, expireTime, TimeUnit.SECONDS);


        return carts;
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Cart.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Cart.class);
    }


}
