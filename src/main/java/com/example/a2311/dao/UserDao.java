package com.example.a2311.dao;

import com.example.a2311.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(User user) {
        mongoTemplate.save(user);
    }

    public User get(Query query) {
        return mongoTemplate.findOne(query, User.class);
    }

    public List<User> findAll() {
        return mongoTemplate.findAll(User.class);
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, User.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, User.class);
    }


}
