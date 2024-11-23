package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.CartDao;
import com.example.a2311.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    public String add(String name, String price, String time, String username) {

        Cart cart = new Cart();
        cart.setName(name);
        cart.setPrice(price);
        cart.setTime(time);
        cart.setUsername(username);
        cart.setCreateTime(new Date().getTime());
        cartDao.save(cart);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "添加购物车成功");
        return jsonObject.toJSONString();
    }

    public String delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        cartDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }


    public List<Cart> get(String username) {
        return cartDao.findAll(username);
    }


}
