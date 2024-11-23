package com.example.a2311.dao;

import com.example.a2311.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Component
public class OrderDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    public void save(Order order) {
        mongoTemplate.save(order);
    }

    public Order get(Query query) {
        return mongoTemplate.findOne(query, Order.class);
    }

    public List<Order> findAll(String username) {
        ///redis 列表
        List<Order> orders = (List<Order>) redisTemplate.opsForList().leftPop("order:" + username);

        if(orders != null) {
            System.out.println("redis命中");
            return orders;
        }

        // 使用聚合
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.match(Criteria.where("username").is(username))
        );
        AggregationResults<Order> results = mongoTemplate.aggregate(aggregation, "order", Order.class);
        orders = results.getMappedResults();


        // 查询结果存入Redis列表右侧
        redisTemplate.opsForList().rightPush("order:" + username, orders);
        System.out.println("redis未命中");

        return orders;
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Order.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Order.class);
    }


}
