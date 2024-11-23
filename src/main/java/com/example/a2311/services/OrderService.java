package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.OrderDao;
import com.example.a2311.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao orderDao;

    public String addOrder(String name, String price, String time, String username) {

        Order order = new Order();
        order.setName(name);
        order.setPrice(price);
        order.setTime(time);
        order.setUsername(username);
        orderDao.save(order);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "购买成功");
        return jsonObject.toJSONString();
    }

    public String deleteOrder(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        orderDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }


    public List<Order> getOrders(String username) {
        return orderDao.findAll(username);
    }


}
