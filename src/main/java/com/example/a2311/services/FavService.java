package com.example.a2311.services;


import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.FavDao;
import com.example.a2311.entity.Fav;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavService {

    @Autowired
    private FavDao favDao;

    public String add(String name, String price, String time, String username) {

        Fav fav = new Fav();
        fav.setName(name);
        fav.setPrice(price);
        fav.setTime(time);
        fav.setUsername(username);
        favDao.save(fav);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "收藏成功");
        return jsonObject.toJSONString();
    }

    public String delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        favDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }


    public List<Fav> get(String username) {
        return favDao.findAll(username);
    }


}
