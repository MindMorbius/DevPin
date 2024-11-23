package com.example.a2311.dao;

import com.example.a2311.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class TaskDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public void save(Task task) {
        mongoTemplate.save(task);
    }

    public Task get(Query query) {
        return mongoTemplate.findOne(query, Task.class);
    }

    public List<Task> findAll() {
        return mongoTemplate.findAll(Task.class);
    }

    public void update(Query query, Update update) {
        mongoTemplate.updateMulti(query, update, Task.class);
    }

    public void delete(Query query) {
        mongoTemplate.remove(query, Task.class);
    }

}
