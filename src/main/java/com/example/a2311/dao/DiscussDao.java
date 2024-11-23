package com.example.a2311.dao;

import com.example.a2311.entity.Discuss;
import com.example.a2311.entity.Fav;
import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DiscussDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Discuss discuss) {
        mongoTemplate.save(discuss);
    }

    public Discuss get(Query query) {
        return mongoTemplate.findOne(query, Discuss.class);
    }

    public List<Discuss> findAll(String tid) {

        Query query = new Query();
        query.addCriteria(Criteria.where("tid").is(tid));
        return mongoTemplate.find(query, Discuss.class);

    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Discuss.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Discuss.class);
    }
}
