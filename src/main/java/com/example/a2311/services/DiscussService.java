package com.example.a2311.services;

import com.alibaba.fastjson.JSONObject;
import com.example.a2311.dao.DiscussDao;
import com.example.a2311.entity.Discuss;
import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscussService {

    @Autowired
    private DiscussDao discussDao;
    public String add(String tid, String content, String time, String username) {

        Discuss discuss = new Discuss();
        discuss.setUsername(username);
        discuss.setContent(content);
        discuss.setTime(time);
        discuss.setTid(tid);

        discussDao.save(discuss);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "评论成功");
        return jsonObject.toJSONString();
    }

    public String delete(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        discussDao.delete(query);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("result", "删除成功");
        return jsonObject.toJSONString();
    }


    public List<Discuss> getAll(String tid) {
        return discussDao.findAll(tid);
    }

}
